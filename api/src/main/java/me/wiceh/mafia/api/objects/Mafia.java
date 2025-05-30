package me.wiceh.mafia.api.objects;

import me.wiceh.mafia.api.constants.MafiaRole;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;

public class Mafia {
    private final int id;
    private final String name;
    private String color;
    private UUID owner;
    private int level;
    private int xp;
    private int kills;
    private final Timestamp whenCreated;
    private final Map<UUID, MafiaRole> members;

    public Mafia(int id, String name, String color, UUID owner, int level, int xp, int kills, Timestamp whenCreated, Map<UUID, MafiaRole> members) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.owner = owner;
        this.level = level;
        this.xp = xp;
        this.kills = kills;
        this.whenCreated = whenCreated;
        this.members = members;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public Timestamp getWhenCreated() {
        return whenCreated;
    }

    public Map<UUID, MafiaRole> getMembers() {
        return members;
    }

    public void addMember(Player player, MafiaRole role) {
        this.members.put(player.getUniqueId(), role);
    }

    public void removeMember(OfflinePlayer player) {
        this.members.remove(player.getUniqueId());
    }
}
