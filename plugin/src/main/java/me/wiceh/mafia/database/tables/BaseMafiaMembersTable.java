package me.wiceh.mafia.database.tables;

import me.wiceh.mafia.api.constants.MafiaRole;
import me.wiceh.mafia.api.database.DatabaseManager;
import me.wiceh.mafia.api.database.tables.MafiaMembersTable;
import me.wiceh.mafia.api.utils.LogUtils;
import me.wiceh.mafia.api.utils.UUIDUtils;
import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BaseMafiaMembersTable extends MafiaMembersTable {
    @Language("SQL")
    private static final String MAFIA_MEMBERS_TABLE = """
            CREATE TABLE IF NOT EXISTS mafia_members
            (
                id              INTEGER         PRIMARY KEY     AUTOINCREMENT,
                mafia_id        INTEGER         NOT NULL,
                player          BINARY(16)      NOT NULL,
                role            VARCHAR(255)    NOT NULL,
            
                FOREIGN KEY(mafia_id) REFERENCES mafia(id)
                    ON UPDATE CASCADE
                    ON DELETE CASCADE
            );
            """;

    @Language("SQL")
    private static final String SELECT_MAFIA_MEMBERS = """
            SELECT * FROM mafia_members
            WHERE mafia_id = ?
            """;

    public BaseMafiaMembersTable(DatabaseManager dbManager) {
        super(dbManager, MAFIA_MEMBERS_TABLE);
    }

    @Override
    public Map<UUID, MafiaRole> getMembers(Connection connection, int mafiaId) {
        Map<UUID, MafiaRole> mafiaMembers = new HashMap<>();

        try (PreparedStatement statement = connection.prepareStatement(SELECT_MAFIA_MEMBERS)) {
            statement.setInt(1, mafiaId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                mafiaMembers.put(UUIDUtils.bytesToUUID(resultSet.getBytes("player")), MafiaRole.valueOf(resultSet.getString("role")));
        } catch (SQLException e) {
            LogUtils.logError(e, "Unable to get all mafia members of mafia #" + mafiaId);
        }

        return mafiaMembers;
    }
}
