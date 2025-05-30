package me.wiceh.mafia.api.database;

import me.wiceh.mafia.api.utils.LogUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public abstract class DatabaseTable {
    protected final DatabaseManager dbManager;
    protected final String tableQuery;

    private static final ExecutorService DATABASE_EXECUTOR = Executors.newVirtualThreadPerTaskExecutor();

    protected DatabaseTable(DatabaseManager dbManager, String tableQuery) {
        this.dbManager = dbManager;
        this.tableQuery = tableQuery;
        createTable();
    }

    protected <T> CompletableFuture<T> supplyAsync(Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(supplier, DATABASE_EXECUTOR);
    }

    private void createTable() {
        try (Connection connection = dbManager.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(tableQuery);
        } catch (SQLException e) {
            LogUtils.logError(e, "Unable to create table");
        }
    }
}
