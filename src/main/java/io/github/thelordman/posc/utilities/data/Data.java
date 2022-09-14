package io.github.thelordman.posc.utilities.data;

import io.github.thelordman.posc.scoreboard.FastBoard;
import org.bukkit.entity.Player;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Data {
    public static final HashMap<UUID, FastBoard> scoreboard = new HashMap<>();
    public static final HashMap<Player, Long> combatTag = new HashMap<>();
    public static final HashMap<Player, Pair<Player, Byte>> lastHitData = new HashMap<>();
    public static final List<Player> newPlayers = new ArrayList<>();
    public static final ArrayList<Player> vanishedPlayers = new ArrayList<>();
}