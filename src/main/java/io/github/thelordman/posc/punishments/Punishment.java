package io.github.thelordman.posc.punishments;

import io.github.thelordman.posc.utilities.data.DataManager;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.UUID;

public class Punishment {
    private final PunishmentType punismentType;
    private final int created;
    private final int expiration;
    private final String reason;
    private final UUID punisher;
    private final int ID;

    public Punishment(@NotNull PunishmentType punismentType, int created, int expiration, @Nullable String reason, @Nullable UUID punisher) {
        this.punismentType = punismentType;
        this.created = created;
        this.expiration = expiration;
        this.reason = reason;
        this.punisher = punisher;
        this.ID = DataManager.getHighID();
        DataManager.incrementHighID();
    }

    public PunishmentType getPunismentType() {
        return punismentType;
    }

    public int getCreated() {
        return created;
    }

    public int getExpiration() {
        return expiration;
    }

    public String getReason() {
        return reason == null ? "No Reason" : reason;
    }

    public UUID getPunisher() {
        return punisher;
    }

    public int getID() {
        return ID;
    }
}
