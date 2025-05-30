package me.wiceh.mafia.api.database.tables;

import me.wiceh.mafia.api.constants.MafiaRole;
import me.wiceh.mafia.api.database.DatabaseManager;
import me.wiceh.mafia.api.database.DatabaseTable;

import java.sql.Connection;
import java.util.Map;
import java.util.UUID;

public abstract class MafiaMembersTable extends DatabaseTable {

    public MafiaMembersTable(DatabaseManager dbManager, String tableQuery) {
        super(dbManager, tableQuery);
    }

    public abstract Map<UUID, MafiaRole> getMembers(Connection connection, int mafiaId);

}
