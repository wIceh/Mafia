package me.wiceh.mafia.database;

import me.wiceh.mafia.api.database.DatabaseManager;
import me.wiceh.mafia.api.utils.LogUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseDatabaseManager extends DatabaseManager {
    private File dbFile;

    public BaseDatabaseManager(JavaPlugin plugin) {
        createFile(plugin.getDataFolder().getAbsolutePath());
    }

    private void createFile(String path) {
        File dataFolder = new File(path);
        if (!dataFolder.exists()) dataFolder.mkdirs();

        dbFile = new File(dataFolder, "mafia.db");
        if (!dbFile.exists()) {
            try {
                dbFile.createNewFile();
            } catch (IOException e) {
                LogUtils.logError(e, "Unable to create database file");
            }
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile.getAbsolutePath());
        try (Statement statement = connection.createStatement()) {
            statement.execute("PRAGMA foreign_keys = ON;");
        } catch (SQLException e) {
            LogUtils.logError(e, "Failed to enable foreign keys");
        }

        return connection;
    }
}
