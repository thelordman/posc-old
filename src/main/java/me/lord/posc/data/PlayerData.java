package me.lord.posc.data;

import me.lord.posc.Posc;
import me.lord.posc.economy.Account;
import me.lord.posc.ranks.Rank;
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

    private Account account = null;
    private Rank rank = Rank.DEFAULT;

    public PlayerData(@NotNull UUID uuid) {
        this.uuid = uuid;
    }

    @Nullable
    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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
}