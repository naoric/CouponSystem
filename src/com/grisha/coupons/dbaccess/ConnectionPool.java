package com.grisha.coupons.dbaccess;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

public class ConnectionPool {

    private Queue<Connection> connectionsQueue;

    private static final int CONNECTIONS_LIMIT = 5;
    private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
    private static final String DB_NAME = "coupon_system";
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    /**
     * Since we are implementing a singleton, it is important we will keep
     * a static , single, instance of ConnectionPool
     */
    private static ConnectionPool instance = null;

    public static ConnectionPool getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new ConnectionPool();
        }

        return instance;
    }

    private ConnectionPool() throws SQLException, ClassNotFoundException {
        connectionsQueue = new LinkedList<>();
        Class.forName(DRIVER_NAME);
        for (int i = 0; i < CONNECTIONS_LIMIT; i++) {
            connectionsQueue.add(createConnection());
        }
    }

    private Connection createConnection() throws SQLException {
        // TODO create new connection
        final String connectionString = String.format("%s/%s", CONNECTION_STRING, DB_NAME);
        return DriverManager.getConnection(connectionString, USERNAME, PASSWORD);
    }

    public Connection getConnection() {
        // TODO apply thread lock if the queue is empty
        if (connectionsQueue.size() >= 0) {
            return connectionsQueue.remove();
        }
        return null;
    }

    public void closeAllConnections() throws SQLException {
        for (Connection connection : connectionsQueue) {
            connection.close();
        }
    }

    public void returnConnection(Connection connection) {
        // TODO free thread if there available connections
        connectionsQueue.add(connection);
    }

}


