package com.epam.testapp.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.epam.testapp.exception.NewsNotFoundException;
import com.epam.testapp.exception.NoConnectionAvailableException;
import com.epam.testapp.model.News;

@Transactional
public final class HibernateNewsDAO extends HibernateDaoSupport implements INewsDAO {
	private static final Logger LOGGER = Logger
			.getLogger(HibernateNewsDAO.class);
	private final String HQL_GET_LIST = "from News n order by n.date DESC, n.id DESC";
	private final String HQL_GET_LIST_TO_DELETE = "from News n where n.id IN(:ids)";
	private final String IDS_TO_DELETE = "ids";

	@Override
	@Transactional(readOnly = false)
	public int create(News news) throws SQLException,
			NoConnectionAvailableException {
		return (Integer) getHibernateTemplate().save(news);
	}

	@Override
	@Transactional(readOnly = true)
	public List getList() {
		return getHibernateTemplate().find(HQL_GET_LIST);
	}

	@Override
	@Transactional(readOnly = true)
	public News fetchById(int id) throws NewsNotFoundException {
		News news = (News) getHibernateTemplate().get(News.class, id);
		if (news == null) {
			throw new NewsNotFoundException();
		}
		return news;
	}

	@Override
	@Transactional(readOnly = false)
	public void modify(News news) throws SQLException,
			NoConnectionAvailableException {
		getHibernateTemplate().update(news);
	}

	@Override
	@Transactional(readOnly = false)
	public void removeById(Integer[] id) throws SQLException,
			NoConnectionAvailableException {
		getHibernateTemplate().deleteAll(
				getHibernateTemplate().findByNamedParam(HQL_GET_LIST_TO_DELETE,
						IDS_TO_DELETE, id));
	}
}
