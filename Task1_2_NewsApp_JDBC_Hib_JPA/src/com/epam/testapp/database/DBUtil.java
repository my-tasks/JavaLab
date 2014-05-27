package com.epam.testapp.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public final class DBUtil {
	private static final Logger LOGGER = Logger.getLogger(DBUtil.class);

	private DBUtil() {
	}

	public static void rollback(Connection conn) {
		if (conn != null) {
			try {
				conn.rollback();
			} catch (SQLException ex) {
			}
		}
	}

	public static void close(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException ex) {
				LOGGER.warn("Error occured when attempting to close a Statement");
			}
		}
	}

	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				LOGGER.warn("Error occured when attempting to close a connection");
			}
		}

	}

}
