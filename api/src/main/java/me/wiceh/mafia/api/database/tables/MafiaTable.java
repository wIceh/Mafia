package me.wiceh.mafia.api.database.tables;

import me.wiceh.mafia.api.database.DatabaseManager;
import me.wiceh.mafia.api.database.DatabaseTable;
import me.wiceh.mafia.api.objects.Mafia;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public abstract class MafiaTable extends DatabaseTable {

    public MafiaTable(DatabaseManager dbManager, String tableQuery) {
        super(dbManager, tableQuery);
    }

    public abstract CompletableFuture<Set<Mafia>> getMafias();

    public abstract CompletableFuture<Optional<Mafia>> getMafia(String name);
}
