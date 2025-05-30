package me.wiceh.mafia.server.managers;

import me.wiceh.mafia.MafiaPlugin;
import me.wiceh.mafia.api.managers.MafiaManager;
import me.wiceh.mafia.api.objects.Mafia;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BaseMafiaManager extends MafiaManager {
    private final MafiaPlugin plugin;

    private final Map<Integer, Mafia> mafias;

    public BaseMafiaManager(MafiaPlugin plugin) {
        this.plugin = plugin;

        this.mafias = new HashMap<>();
        plugin.getMafiaTable().getMafias().thenAccept(mafias -> mafias.forEach(mafia -> this.mafias.put(mafia.getId(), mafia)));
    }

    @Override
    public Optional<Mafia> getMafia(String name) {
        return mafias.values().stream()
                .filter(mafia -> mafia.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    public Map<Integer, Mafia> getMafias() {
        return mafias;
    }
}
