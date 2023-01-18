package me.lord.posc.utilities;

import org.bukkit.command.TabExecutor;

/**
 * Adds functionality to specify the name of the command(s).
 * This interface must be implemented in command classes
 * in order for the command registration to work.
 */
public interface Cmd extends TabExecutor {
    /**
     * Name of the command without the slash (e.g. {@code "kill"}).
     * Leave null if class has multiple commands (see {@link #getNames()}).
     */
    default String getName() {
        return null;
    }

    /**
     * Names of the commands without the slash (e.g. {@code {"kill", "killall"}}).
     * Leave null if class has only one command (see {@link #getName()}).
     */
    default String[] getNames() {
        return null;
    }
}
