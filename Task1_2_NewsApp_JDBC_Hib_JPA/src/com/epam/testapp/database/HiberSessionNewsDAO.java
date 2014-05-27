package com.epam.testapp.database;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.epam.testapp.exception.NewsNotFoundException;
import com.epam.testapp.exception.NoConnectionAvailableException;
import com.epam.testapp.model.News;

public final class HiberSessionNewsDAO implements INewsDAO {
	private static final Logger LOGGER = Logger
			.getLogger(HibernateNewsDAO.class);

	private SessionFactory sessionFactory;

	@Override
	public int create(News news) throws SQLException,
			NoConnectionAvailableException {
		Session session = getSession();
		session.beginTransaction();
		try {
			Integer id = (Integer) session.save(news);
			session.getTransaction().commit();
			return id;
		} catch (Throwable ex) {
			LOGGER.warn("Error occured when saving a news message with ID "
					+ news.getId() + "to database. ", ex);
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public List<News> getList() throws SQLException,
			NoConnectionAvailableException {
		Session session = getSession();
		session.beginTransaction();
		try {
			List<News> list = session.getNamedQuery("getNewsList").list();
			session.getTransaction().commit();
			return list;
		} catch (Throwable ex) {
			LOGGER.warn(
					"Error occured when loading list of news messages from database. ",
					ex);
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public News fetchById(int id) throws SQLException,
			NoConnectionAvailableException, NewsNotFoundException {
		Session session = getSession();
		session.beginTransaction();
		try {
			News news = (News) session.get(News.class, id);
			session.getTransaction().commit();
			if (news == null) {
				LOGGER.warn("News with ID " + id + "is not found");
				throw new NewsNotFoundException();
			}
			return news;
		} catch (Throwable ex) {
			LOGGER.warn("Error occured when loading news messages with ID "
					+ id + " from database. ", ex);
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public void modify(News news) throws SQLException,
			NoConnectionAvailableException {
		Session session = getSession();
		session.beginTransaction();
		try {
			session.update(news);
			session.getTransaction().commit();
		} catch (Throwable ex) {
			LOGGER.warn("Error occured when modifying news messages with ID "
					+ news.getId(), ex);
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public void removeById(Integer[] id) throws SQLException,
			NoConnectionAvailableException {
		Session session = getSession();
		try {
			session.beginTransaction();
			session.getNamedQuery("deleteNewsList").setParameterList("id", id)
					.executeUpdate();
			session.getTransaction().commit();
		} catch (Throwable ex) {
			LOGGER.warn("Error occured when deleting news messages with IDs "
					+ Arrays.toString(id), ex);
			session.getTransaction().rollback();
			throw ex;
		}
	}

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
