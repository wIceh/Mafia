package me.wiceh.mafia;

import me.wiceh.mafia.api.MafiaAPI;
import me.wiceh.mafia.api.database.DatabaseManager;
import me.wiceh.mafia.api.database.tables.MafiaMembersTable;
import me.wiceh.mafia.api.database.tables.MafiaTable;
import me.wiceh.mafia.api.managers.MafiaManager;
import me.wiceh.mafia.database.BaseDatabaseManager;
import me.wiceh.mafia.database.tables.BaseMafiaMembersTable;
import me.wiceh.mafia.database.tables.BaseMafiaTable;
import me.wiceh.mafia.server.commands.MafiaCommand;
import me.wiceh.mafia.server.managers.BaseMafiaManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public class MafiaPlugin extends MafiaAPI {
    private final JavaPlugin plugin;

    private final DatabaseManager databaseManager;
    private final MafiaTable mafiaTable;
    private final MafiaMembersTable mafiaMembersTable;

    private final MafiaManager mafiaManager;

    public MafiaPlugin(JavaPlugin plugin) {
        this.plugin = plugin;

        this.databaseManager = new BaseDatabaseManager(plugin);
        this.mafiaTable = new BaseMafiaTable(databaseManager, this);
        this.mafiaMembersTable = new BaseMafiaMembersTable(databaseManager);

        this.mafiaManager = new BaseMafiaManager(this);
    }

    public void enable() {
        Bukkit.getServicesManager().register(MafiaAPI.class, this, plugin, ServicePriority.Normal);

        plugin.getCommand("mafia").setExecutor(new MafiaCommand(this));
    }

    public void disable() {

    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    @Override
    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    @Override
    public MafiaTable getMafiaTable() {
        return mafiaTable;
    }

    @Override
    public MafiaMembersTable getMafiaMembersTable() {
        return mafiaMembersTable;
    }

    @Override
    public MafiaManager getMafiaManager() {
        return mafiaManager;
    }
}
