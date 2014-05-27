package com.epam.testapp.util;
import oracle.jdbc.pool.OracleDataSource;

import javax.sql.DataSource;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.XADataSource;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
	private static final Logger LOGGER = Logger.getLogger(HibernateUtil.class);
	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			 Configuration config = new Configuration().configure();
			 ServiceRegistry serviceRegistry = new
			 StandardServiceRegistryBuilder()
			 .applySettings(config.getProperties()).build();
			 return config.buildSessionFactory(serviceRegistry);
		} catch (Throwable ex) {
			LOGGER.error("Initial SessionFactory creation failed. "
					+ ex.getMessage());
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
