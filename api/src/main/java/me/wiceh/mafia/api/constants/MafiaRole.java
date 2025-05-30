package me.wiceh.mafia.api.constants;

public enum MafiaRole {
    RECLUTA("Recluta"),
    MEMBRO("Membro"),
    MANAGER("Manager"),
    CO_LEADER("CoLeader"),
    LEADER("Leader");

    private final String displayName;

    MafiaRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
