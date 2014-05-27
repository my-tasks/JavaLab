package com.epam.testapp.database;

import java.sql.SQLException;
import java.util.List;

import com.epam.testapp.exception.NoConnectionAvailableException;
import com.epam.testapp.exception.NewsNotFoundException;
import com.epam.testapp.model.News;

public interface INewsDAO {
	public int create(News news) throws SQLException, NoConnectionAvailableException;

	public List<News> getList() throws SQLException, NoConnectionAvailableException;

	public News fetchById(int id) throws SQLException, NoConnectionAvailableException, NewsNotFoundException;

	public void modify(News news) throws SQLException, NoConnectionAvailableException;

	public void removeById(Integer[] id) throws SQLException, NoConnectionAvailableException;
}
