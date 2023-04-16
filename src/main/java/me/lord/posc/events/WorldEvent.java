package me.lord.posc.events;

import org.bukkit.World;

/**
 * Implement if Event has a world specifically made for it.
 */
public interface WorldEvent extends Event {
    World getWorld();
}
