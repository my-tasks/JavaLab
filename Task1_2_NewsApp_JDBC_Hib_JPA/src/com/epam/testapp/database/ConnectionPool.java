package com.epam.testapp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.epam.testapp.exception.ConnectionPoolException;
import com.epam.testapp.exception.NoConnectionAvailableException;

public final class ConnectionPool implements IConnectionPool {
	private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class);
	Lock locking = new ReentrantLock(true);
	private String driverName;
	private String url;
	private String user;
	private String password;
	private int minPoolSize;
	private int maxPoolSize;
	private int acquireIncrement;
	private long timeOut;
	private BlockingQueue<Connection> connectionsAvailable;
	private BlockingQueue<Connection> connectionsInUse;

	private ConnectionPool() {
	}

	public void initialize() throws ConnectionPoolException {
		registerDriver();
		// checking if the pool size was configured correctly:
		if (minPoolSize > maxPoolSize) {
			LOGGER.error("DBConnectionPool is configured wrong. minPoolSize must be lower than maxPoolSize");
			LOGGER.error("DBConnectionPool initialization failed");
			throw new ConnectionPoolException();
		}
		if (maxPoolSize > 0) {
			connectionsAvailable = new ArrayBlockingQueue<Connection>(
					maxPoolSize);
			connectionsInUse = new ArrayBlockingQueue<Connection>(maxPoolSize);
		}
		for (int i = 0; i < minPoolSize; i++) {
			connectionsAvailable.add(prepareConnection());
		}
	}

	public Connection getConnection() throws NoConnectionAvailableException {
		Connection conn = null;
		try {
			conn = connectionsAvailable.poll();
			if (conn != null) {
				return addToConnectionsInUse(conn) ? conn : getConnection();
			}
			if (getCurrentPoolSize() < maxPoolSize) {
				locking.lock();
				try {
					extendPool();
					conn = connectionsAvailable.poll();
				} finally {
					locking.unlock();
				}
			} else {
				conn = connectionsAvailable.poll(timeOut, TimeUnit.SECONDS);
			}
			if (conn != null) {
				return addToConnectionsInUse(conn) ? conn : getConnection();
			}
		} catch (InterruptedException ex) {
			LOGGER.warn("Getting a connection was interrupted "
					+ ex.getMessage());
		}
		throw new NoConnectionAvailableException();
	}

	// method addToConnectionsInUse checks if the given Connection is valid. If
	// so the connection is added to the Queue of connections being used and
	// "true" value is returned. If the connection is not valid "false"
	// value is returned.
	private boolean addToConnectionsInUse(Connection conn) {
		try {
			if (conn.isClosed()) {
				LOGGER.info("Invalid connection was found");
				return false;
			} else {
				LOGGER.debug("A connection got being used");
				connectionsInUse.add(conn);
				return true;
			}
		} catch (SQLException ex) {
			LOGGER.warn("Invalid connection was found. " + ex.getMessage());
			return false;
		}
	}

	// method extendPool returns "true" if the extension was made and "false" if
	// the maximum amount of connections was already created
	private boolean extendPool() {
		int currentPoolSize = getCurrentPoolSize();
		if (currentPoolSize < maxPoolSize && connectionsAvailable.isEmpty()) {
			int currentIncrement = ((currentPoolSize + acquireIncrement) <= maxPoolSize) ? acquireIncrement
					: (maxPoolSize - currentPoolSize);
			for (int i = 0; i < currentIncrement; i++) {
				connectionsAvailable.add(prepareConnection());
			}
			return true;
		}
		return false;
	}

	public void returnConnection(Connection conn) {
		if (conn != null) {
			connectionsInUse.remove(conn);
			try {
				if (!conn.isClosed()) {
					LOGGER.debug("A connection was returned to ConnectionPool");
					connectionsAvailable.add(conn);
				}
			} catch (SQLException ex) {
				LOGGER.warn("Error occured when attempting to return Connection to the pool");
			}
		}
	}

	private int getCurrentPoolSize() {
		return connectionsInUse.size() + connectionsAvailable.size();
	}

	private void registerDriver() {
		try {
			Class.forName(driverName);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("JDBC driver \"" + driverName + "\" is registered");
			}
		} catch (ClassNotFoundException ex) {
			LOGGER.error("Error occured when registering JDBC driver \""
					+ driverName + "\"", ex);
		}
	}

	private Connection prepareConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		} catch (SQLException ex) {
			LOGGER.error("Error occured when creating Connection.", ex);
		}
		return conn;
	}

	public void destroyPool() {
		Connection conn = null;
		while ((conn = connectionsAvailable.poll()) != null) {
			close(conn);
		}
		while ((conn = connectionsInUse.poll()) != null) {
			close(conn);
		}
		LOGGER.info("ConnectionPool is destroyed");
	}

	private void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException ex) {
				LOGGER.warn("Error occured when closing a connection");
			}
		}
	}

	// Getters and Setters

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getMinPoolSize() {
		return minPoolSize;
	}

	public void setMinPoolSize(int minPoolSize) {
		this.minPoolSize = minPoolSize;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public int getAcquireIncrement() {
		return acquireIncrement;
	}

	public void setAcquireIncrement(int acquireIncrement) {
		this.acquireIncrement = acquireIncrement;
	}

	public long getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(long timeOut) {
		this.timeOut = timeOut;
	}

	public void setConnectionsAvailable(
			BlockingQueue<Connection> connectionsAvailable) {
		this.connectionsAvailable = connectionsAvailable;
	}

	public void setConnectionsInUse(BlockingQueue<Connection> connectionsInUse) {
		this.connectionsInUse = connectionsInUse;
	}

}
