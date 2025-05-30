package me.wiceh.mafia.api.managers;

import me.wiceh.mafia.api.objects.Mafia;

import java.util.Map;
import java.util.Optional;

public abstract class MafiaManager {
    public abstract Optional<Mafia> getMafia(String name);

    public abstract Map<Integer, Mafia> getMafias();
}
