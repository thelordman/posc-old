package io.github.thelordman.costrength.punishments;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.UUID;

public record Punishment(PunishmentType punismentType, int created, int expiration, String reason, UUID punisher, int ID) {
    public Punishment(@NotNull PunishmentType punismentType, int created, int expiration, @Nullable String reason, @Nullable UUID punisher, int ID) {
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
