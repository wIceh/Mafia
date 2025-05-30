package me.wiceh.mafia.api.permissions;

public enum MafiaPermission {
    CREATE("command.mafia.create")
    ;

    private final String permission;

    MafiaPermission(String permission) {
        this.permission = "mafia." + permission;
    }

    public String getPermission() {
        return permission;
    }
}
