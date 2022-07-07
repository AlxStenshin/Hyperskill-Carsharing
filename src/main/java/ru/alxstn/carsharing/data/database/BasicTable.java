package ru.alxstn.carsharing.data.database;

public class BasicTable {
    protected static DatabaseManager dbManager;
    private String tableName;

    public BasicTable(String tableName) {
        dbManager = DatabaseManager.getInstance();
        this.tableName = tableName;
    }

    final private static String DROP_TABLE_IF_EXISTS =
            "DROP TABLE IF EXISTS %s CASCADE;";

    protected boolean dropTableIfExists() {
        return dbManager.execute(String.format(DROP_TABLE_IF_EXISTS, tableName));
    }
}
