package io.github.thelordman.costrength.punishments;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.UUID;

public class Punishment {
    private final PunishmentType punismentType;

    private final Date created;
    private final Date expiration;

    private final String reason;

    private final UUID punisher;
    private final int ID;

    public Punishment(@NotNull PunishmentType punismentType, @NotNull Date created, @Nullable Date expiration, @Nullable String reason, @Nullable UUID punisher, int ID) {
        this.punismentType = punismentType;
        this.created = created;
        this.expiration = expiration;
        this.reason = reason;
        this.punisher = punisher;
        this.ID = ID;
    }

    public PunishmentType getPunismentType() {
        return punismentType;
    }

    public Date getCreated() {
        return created;
    }

    public Date getExpiration() {
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
