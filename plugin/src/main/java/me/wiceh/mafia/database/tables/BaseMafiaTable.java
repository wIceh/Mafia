package me.wiceh.mafia.database.tables;

import me.wiceh.mafia.MafiaPlugin;
import me.wiceh.mafia.api.constants.MafiaRole;
import me.wiceh.mafia.api.database.DatabaseManager;
import me.wiceh.mafia.api.database.tables.MafiaTable;
import me.wiceh.mafia.api.objects.Mafia;
import me.wiceh.mafia.api.utils.LogUtils;
import me.wiceh.mafia.api.utils.UUIDUtils;
import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class BaseMafiaTable extends MafiaTable {
    private final MafiaPlugin plugin;

    @Language("SQL")
    private static final String MAFIA_TABLE = """
            CREATE TABLE IF NOT EXISTS mafia
            (
                id              INTEGER         PRIMARY KEY     AUTOINCREMENT,
                name            VARCHAR(255)    NOT NULL        UNIQUE,
                color           VARCHAR(6)      NOT NULL,
                owner           BINARY(16)      NOT NULL,
                level           INTEGER         NOT NULL        DEFAULT 0,
                xp              INTEGER         NOT NULL        DEFAULT 0,
                kills           INTEGER         NOT NULL        DEFAULT 0,
                when_created    TIMESTAMP       NOT NULL        DEFAULT NOW
            );
            """;

    @Language("SQL")
    private static final String SELECT_MAFIAS = """
            SELECT * FROM mafia
            """;

    @Language("SQL")
    private static final String SELECT_MAFIA = """
            SELECT * FROM mafia
            WHERE name = ?
            """;

    public BaseMafiaTable(DatabaseManager dbManager, MafiaPlugin plugin) {
        super(dbManager, MAFIA_TABLE);
        this.plugin = plugin;
    }

    @Override
    public CompletableFuture<Set<Mafia>> getMafias() {
        return supplyAsync(() -> {
            Set<Mafia> mafias = new HashSet<>();

            try (Connection connection = dbManager.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SELECT_MAFIAS)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int mafiaId = resultSet.getInt("id");
                    Map<UUID, MafiaRole> members = plugin.getMafiaMembersTable().getMembers(connection, mafiaId);

                    mafias.add(new Mafia(
                            mafiaId,
                            resultSet.getString("name"),
                            resultSet.getString("color"),
                            UUIDUtils.bytesToUUID(resultSet.getBytes("owner")),
                            resultSet.getInt("level"),
                            resultSet.getInt("xp"),
                            resultSet.getInt("kills"),
                            resultSet.getTimestamp("when_created"),
                            members
                    ));
                }
            } catch (SQLException e) {
                LogUtils.logError(e, "Unable to get all mafias");
            }

            return mafias;
        });
    }

    @Override
    public CompletableFuture<Optional<Mafia>> getMafia(String name) {
        return supplyAsync(() -> {
            try (Connection connection = dbManager.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SELECT_MAFIA)) {
                statement.setString(1, name);

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    int mafiaId = resultSet.getInt("id");
                    Map<UUID, MafiaRole> members = plugin.getMafiaMembersTable().getMembers(connection, mafiaId);

                    return Optional.of(new Mafia(
                            mafiaId,
                            resultSet.getString("name"),
                            resultSet.getString("color"),
                            UUIDUtils.bytesToUUID(resultSet.getBytes("owner")),
                            resultSet.getInt("level"),
                            resultSet.getInt("xp"),
                            resultSet.getInt("kills"),
                            resultSet.getTimestamp("when_created"),
                            members
                    ));
                }
            } catch (SQLException e) {
                LogUtils.logError(e, "Unable to get mafia '" + name + "'");
            }

            return Optional.empty();
        });
    }
}
