package io.github.thelordman.costrength.utilities;

import io.github.thelordman.costrength.scoreboard.FastBoard;
import org.bukkit.entity.Player;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

public class Data implements Serializable {
    public static final HashMap<UUID, FastBoard> scoreboard = new HashMap<>();
    public static final HashMap<Player, Byte> combatTag = new HashMap<>();

    public final HashMap<UUID, Float> balance = new HashMap<>();
    public final HashMap<UUID, Float> bounty = new HashMap<>();
    public final HashMap<UUID, Float> xp = new HashMap<>();
    public final HashMap<UUID, Integer> level = new HashMap<>();
    public final HashMap<UUID, Integer> killstreak = new HashMap<>();
}