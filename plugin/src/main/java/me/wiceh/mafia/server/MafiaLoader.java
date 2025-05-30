package me.wiceh.mafia.server;

import me.wiceh.mafia.MafiaPlugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class MafiaLoader extends JavaPlugin {
    private final MafiaPlugin plugin;

    public MafiaLoader() {
        this.plugin = new MafiaPlugin(this);
    }

    @Override
    public void onEnable() {
        this.plugin.enable();
    }

    @Override
    public void onDisable() {
        this.plugin.disable();
    }
}
