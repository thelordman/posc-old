package me.lord.posc.data;

import me.lord.posc.Posc;
import me.lord.posc.economy.Account;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
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

    public PlayerData(@NotNull UUID uuid) {
        this.uuid = uuid;
    }

    public PlayerData() {
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

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        try {
            out.writeObject(uuid);
        } catch (IOException e) {
            String field = "uuid";
            Posc.LOGGER.warning("Could not write + '" + field + "', writing null");
            out.writeObject(null);
        }
        try {
            out.writeObject(account);
        } catch (IOException e) {
            String field = "account";
            Posc.LOGGER.warning("Could not write + '" + field + "', writing default value");
            out.writeObject(new Account());
        }
    }

    @Override
    public void readExternal(ObjectInput in) {
        try {
            setUUID((UUID) in.readObject());
        } catch (IOException | ClassNotFoundException e) {
            String field = "uuid";
            Posc.LOGGER.severe("Could not read + '" + field + "', reading null");
            setUUID(null);
        }
        try {
            setAccount((Account) in.readObject());
        } catch (IOException | ClassNotFoundException e) {
            String field = "account";
            Posc.LOGGER.severe("Could not read + '" + field + "', reading default value");
            setAccount(new Account());
        }
    }
}
