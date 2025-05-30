package me.wiceh.mafia.api;

import me.wiceh.mafia.api.database.DatabaseManager;
import me.wiceh.mafia.api.database.tables.MafiaMembersTable;
import me.wiceh.mafia.api.database.tables.MafiaTable;
import me.wiceh.mafia.api.managers.MafiaManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public abstract class MafiaAPI {

    public static MafiaAPI get() {
        RegisteredServiceProvider<MafiaAPI> registration = Bukkit.getServicesManager().getRegistration(MafiaAPI.class);
        if (registration == null) return null;
        return registration.getProvider();
    }

    public abstract DatabaseManager getDatabaseManager();

    public abstract MafiaTable getMafiaTable();

    public abstract MafiaMembersTable getMafiaMembersTable();

    public abstract MafiaManager getMafiaManager();
}
