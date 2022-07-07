package ru.alxstn.carsharing.data.database;

import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.*;

public class DatabaseManager {
    private static final String DATABASE_FILE_NAME_KEY = "-databaseFileName";
    private static final String DEFAULT_DB_NAME = "carsharing";
    private static final String JDBC_DRIVER = "org.h2.Driver";

    private static DatabaseManager instance;
    private static JdbcConnectionPool connectionPool;

    private DatabaseManager(String dbName) {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String dbPathPrefix = "./src/carsharing/db/";
        String dbPath = "jdbc:h2:" + dbPathPrefix + dbName;
        connectionPool = JdbcConnectionPool.create(dbPath, "", "");
    }

    public static DatabaseManager getInstance(String[] args) {
        if (instance == null) {
            instance = new DatabaseManager(
                    getDbName(args)
            );
        }
        return instance;
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager(
                    DEFAULT_DB_NAME
            );
        }
        return instance;
    }

    private static String getDbName(String[] args) {
        String dbName = DEFAULT_DB_NAME;
        if  (args.length > 0 && args[0].equals(DATABASE_FILE_NAME_KEY)) {
            dbName = args[1];
        }
        return dbName;
    }

    public Connection getConnection() {
        try {
            Connection connection = connectionPool.getConnection();
            connection.setAutoCommit(true);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean execute(String sql){
        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement()){
                return statement.execute(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int executeUpdate(String sql){
        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement()){
                System.out.println(sql);
                return statement.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
