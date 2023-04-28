package me.lord.posc.data;

import me.lord.posc.Posc;
import me.lord.posc.event.Event;
import me.lord.posc.npc.interaction.NPCInteraction;
import me.lord.posc.ranks.Rank;
import me.lord.posc.scoreboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serial;
import java.util.UUID;

/**
 * A player's data which won't be wiped on a server reload or a server restart.
 */
public final class PlayerData implements Data {
    @Serial
    private static final long serialVersionUID = 3585230923101039049L;

    @Nullable
    private UUID uuid;
    private double balance = 0d;
    private Rank rank = Rank.DEFAULT;

    private transient Integer selectedNPC = null;
    private transient NPCInteraction currentInteraction = null;
    private transient Event.Data eventData = null;
    private transient boolean godMode = false;
    private transient boolean hunger = true;
    private transient FastBoard scoreboard;

    public PlayerData(@NotNull UUID uuid) {
        this.uuid = uuid;
        scoreboard = new FastBoard(uuid);
    }

    @Nullable
    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        Player player = Bukkit.getPlayer(getUUID());
        if (player != null) {
            for (PermissionAttachmentInfo info : player.getEffectivePermissions()) {
                if (info.getAttachment() != null)
                    player.removeAttachment(info.getAttachment());
            }
        }
        this.rank = rank;
        initPermissions();
    }

    public Integer getSelectedNPC() {
        return selectedNPC;
    }

    public void setSelectedNPC(Integer selectedNPC) {
        this.selectedNPC = selectedNPC;
    }

    public NPCInteraction getCurrentInteraction() {
        return currentInteraction;
    }

    public void setCurrentInteraction(NPCInteraction currentInteraction) {
        this.currentInteraction = currentInteraction;
    }

    public void initPermissions() {
        Player player = Bukkit.getPlayer(getUUID());
        if (player != null) {
            PermissionAttachment attachment = player.addAttachment(Posc.get());
            for (String permission : getRank().getPermissions()) {
                if (!player.hasPermission(permission))
                    attachment.setPermission(permission, true);
            }
        }
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
        getScoreboard().updateBalance();
    }

    public void addBalance(double amount) {
        setBalance(getBalance() + amount);
    }

    public void removeBalance(double amount) {
        setBalance(getBalance() - amount);
    }

    public Event.Data getEventData() {
        return eventData;
    }

    public void setEventData(Event.Data eventData) {
        this.eventData = eventData;
    }

    public boolean godMode() {
        return godMode;
    }

    public void setGodMode(boolean godMode) {
        this.godMode = godMode;
    }

    public boolean hunger() {
        return hunger;
    }

    public void setHunger(boolean hunger) {
        this.hunger = hunger;
    }

    public FastBoard getScoreboard() {
        if (scoreboard == null) {
            scoreboard = new FastBoard(uuid);
        }
        return scoreboard;
    }

    public void setScoreboard(FastBoard scoreboard) {
        this.scoreboard = scoreboard;
    }
}
