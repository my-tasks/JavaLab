package com.epam.testapp.database;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import oracle.jdbc.driver.OracleConnection;

import org.apache.log4j.Logger;

import com.epam.testapp.exception.NoConnectionAvailableException;
import com.epam.testapp.exception.NewsNotFoundException;
import com.epam.testapp.model.News;

public final class JdbcNewsDAO implements INewsDAO {
	private static final Logger LOGGER = Logger.getLogger(JdbcNewsDAO.class);
	private final String TABLE_NAME = "NEWS";

	private final String COLUMN_ID = "NEWS_ID";
	private final String COLUMN_TITLE = "NEWS_TITLE";
	private final String COLUMN_BRIEF = "NEWS_BRIEF";
	private final String COLUMN_CONTENT = "NEWS_CONTENT";
	private final String COLUMN_CREATED = "NEWS_DATE";

	private final String INSERT_PROCEDURE = "INSERT_NEWS";

	private final String SQL_CREATE_NEWS = "{CALL INSERT_NEWS(?,?,?,?,?)}";
	private final String SQL_GET_ALL_NEWS = "SELECT * FROM NEWS ORDER BY NEWS_DATE DESC, NEWS_ID DESC";
	private final String SQL_GET_NEWS_BY_ID = "SELECT * FROM NEWS WHERE NEWS_ID=?";
	private final String SQL_MODIFY_NEWS = "UPDATE NEWS SET NEWS_TITLE=?, NEWS_BRIEF=?, NEWS_CONTENT=?, NEWS_DATE=? WHERE NEWS_ID=?";
	// private final String SQL_DELETE_NEWS =
	// "DELETE FROM NEWS WHERE NEWS_ID=?";
	private final String SQL_DELETE_NEWS = "DELETE FROM NEWS WHERE NEWS_ID IN(?)";

	private IConnectionPool pool;

	@Override
	public int create(News news) throws SQLException,
			NoConnectionAvailableException {
		Connection conn = null;
		CallableStatement call = null;
		try {
			conn = pool.getConnection();
			call = conn.prepareCall(SQL_CREATE_NEWS);
			setStatementParams(call, news);
			call.registerOutParameter(5, java.sql.Types.NUMERIC);
			call.execute();
			int id = call.getInt(5);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("A new News message was saved to the Database with ID "
						+ id);
			}
			conn.commit();
			DBUtil.close(call);
			pool.returnConnection(conn);
			return id;
		} catch (Exception ex) {
			LOGGER.warn(
					"Error occured when attempting to write a new message to Database: ",
					ex);
			DBUtil.rollback(conn);
			DBUtil.close(call);
			pool.returnConnection(conn);
			throw ex;
		}
	}

	@Override
	public List<News> getList() throws SQLException,
			NoConnectionAvailableException {
		Connection conn = null;
		Statement statement = null;
		try {
			conn = pool.getConnection();
			statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(SQL_GET_ALL_NEWS);
			List<News> list = createNewsList(rs);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("The list of news messages is loaded. Amount: "
						+ list.size());
			}
			DBUtil.close(statement);
			pool.returnConnection(conn);
			return list;
		} catch (Exception ex) {
			LOGGER.warn(
					"Error occured when loading news messages list from database",
					ex);
			DBUtil.close(statement);
			pool.returnConnection(conn);
			throw ex;
		}
	}

	@Override
	public News fetchById(int id) throws SQLException,
			NoConnectionAvailableException, NewsNotFoundException {
		Connection conn = null;
		PreparedStatement statement = null;
		try {
			conn = pool.getConnection();
			statement = conn.prepareStatement(SQL_GET_NEWS_BY_ID);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			News news = createNews(rs);
			if (news != null) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("The News message with ID " + id
							+ " is loaded.");
				}
				DBUtil.close(statement);
				pool.returnConnection(conn);
				return news;
			} else {
				LOGGER.warn("News with ID " + id + " is not found.");
				throw new NewsNotFoundException();
			}
		} catch (Exception ex) {
			LOGGER.warn("Error occured when attempting to get a News with ID "
					+ id + " from Database", ex);
			DBUtil.close(statement);
			pool.returnConnection(conn);
			throw ex;
		}
	}

	@Override
	public void modify(News news) throws SQLException,
			NoConnectionAvailableException {
		Connection conn = null;
		PreparedStatement statement = null;
		try {
			conn = pool.getConnection();
			statement = conn.prepareStatement(SQL_MODIFY_NEWS);
			setStatementParams(statement, news);
			int result = statement.executeUpdate();
			if (result == 1) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("News with ID " + news.getId()
							+ " was modified");
				}
				conn.commit();
			} else if (result == 0) {
				LOGGER.warn("There is no News with ID " + news.getId()
						+ " to modify.");
			}
			DBUtil.close(statement);
			pool.returnConnection(conn);
		} catch (Exception ex) {
			LOGGER.warn(
					"Error occured when attempting to modify a News with ID "
							+ news.getId(), ex);
			DBUtil.rollback(conn);
			DBUtil.close(statement);
			pool.returnConnection(conn);
			throw ex;
		}
	}

	@Override
	public void removeById(Integer[] id) throws SQLException,
			NoConnectionAvailableException {
		Connection conn = null;
		PreparedStatement statement = null;
		try {
			conn = pool.getConnection();
			statement = conn.prepareStatement(SQL_DELETE_NEWS);
			// for (int i = 0; i < id.length; i++) {
			// statement.setInt(1, id[i]);
			// statement.addBatch();
			// }
			// statement.executeBatch();
			oracle.jdbc.OracleDriver ora = new oracle.jdbc.OracleDriver();
			// conn = ora.defaultConnection();
			OracleConnection oraConn = (OracleConnection) conn;
			Array idArray = oraConn.createARRAY("IDs", id);
			statement.setArray(1, idArray);
			statement.execute();
			LOGGER.debug("News messages were deleted");
			conn.commit();
			DBUtil.close(statement);
			pool.returnConnection(conn);
		} catch (Exception ex) {
			LOGGER.warn("Error occured when attempting to delete News with ID "
					+ Arrays.toString(id), ex);
			DBUtil.rollback(conn);
			DBUtil.close(statement);
			pool.returnConnection(conn);
			throw ex;
		}
	}

	private void setStatementParams(PreparedStatement statement, News news)
			throws SQLException {
		statement.setString(1, news.getTitle().trim());
		statement.setString(2, news.getBrief().trim());
		statement.setString(3, news.getContent().trim());
		statement.setDate(4, new Date(news.getDate().getTime()));
		if (news.getId() != null) {
			statement.setInt(5, news.getId());
		}
	}

	private List<News> createNewsList(ResultSet rs) throws SQLException {
		List<News> list = new ArrayList<>();
		News news = null;
		while ((news = createNews(rs)) != null) {
			list.add(news);
		}
		return list;
	}

	private News createNews(ResultSet rs) throws SQLException {
		News news = new News();
		if (rs.next()) {
			news.setId(rs.getInt(COLUMN_ID));
			news.setTitle(rs.getString(COLUMN_TITLE));
			news.setBrief(rs.getString(COLUMN_BRIEF));
			news.setContent(rs.getString(COLUMN_CONTENT));
			news.setDate(rs.getDate(COLUMN_CREATED));
		} else {
			news = null;
		}
		return news;
	}

	public IConnectionPool getPool() {
		return pool;
	}

	public void setPool(IConnectionPool pool) {
		this.pool = pool;
	}

}
