package com.epam.testapp.database;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.epam.testapp.exception.NewsNotFoundException;
import com.epam.testapp.exception.NoConnectionAvailableException;
import com.epam.testapp.model.News;

@Repository
@Transactional(rollbackFor = Exception.class)
public final class JpaNewsDAO implements INewsDAO {
	private final String JPQL_GET_LIST = "SELECT n FROM News n ORDER BY n.date DESC, n.id DESC";

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional(readOnly = false)
	public int create(News news) {
		return entityManager.merge(news).getId();
	}

	@Override
	@Transactional(readOnly = true)
	public List<News> getList() {
		return entityManager.createNamedQuery("getNewsList").getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public News fetchById(int id) throws NewsNotFoundException {
		News news = (News) entityManager.find(News.class, id);
		if (news == null) {
			throw new NewsNotFoundException();
		}
		return news;
	}

	@Override
	@Transactional(readOnly = false)
	public void modify(News news) {
		entityManager.merge(news);
	}

	@Override
	@Transactional(readOnly = false)
	public void removeById(Integer[] id) throws SQLException,
			NoConnectionAvailableException {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaDelete<News> criteria = cb.createCriteriaDelete(News.class);
		Root<News> root = criteria.from(News.class);
		criteria.where(root.get("id").in(id));
		entityManager.createQuery(criteria).executeUpdate();
	}
}
