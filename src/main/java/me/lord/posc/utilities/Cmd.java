package me.lord.posc.utilities;

import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Adds functionality to specify the name of the command(s).
 * This interface must be implemented in command classes
 * in order for the command registration to work.
 */
public interface Cmd extends TabExecutor {
    /**
     * Name of the command without the slash (e.g. {@code "kill"}).
     * Leave null if class has multiple commands (see {@link #names()}).
     */
    default String name() {
        return null;
    }

    /**
     * Names of the commands without the slash (e.g. {@code {"kill", "killall"}}).
     * Leave null if class has only one command (see {@link #name()}).
     */
    default String[] names() {
        return null;
    }

    /**
     * Permission required to execute the command.
     * Leave null if the command can be executable by anyone.
     * Leave null if class has multiple commands (see {@link #permissions(String)}).
     */
    default String permission() {
        return null;
    }

    /**
     * Permission required to execute the commands.
     * Leave null if the commands can be executable by anyone.
     * Leave null if class has only one command (see {@link #permission()}).
     *
     * @param command Name of the command being executed
     */
    default String permissions(@NotNull String command) {
        return null;
    }
}
