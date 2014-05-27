package com.epam.testapp.database;

import java.sql.Connection;

import com.epam.testapp.exception.NoConnectionAvailableException;

public interface IConnectionPool {

	public Connection getConnection() throws NoConnectionAvailableException;

	public void returnConnection(Connection connection);

}
