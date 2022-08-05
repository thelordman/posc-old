package io.github.thelordman.costrength.utilities.data;

import io.github.thelordman.costrength.guis.GUIHandler;
import io.github.thelordman.costrength.scoreboard.FastBoard;
import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Data {
    public static final Inventory[] GUIs = {Bukkit.createInventory(null, 27, "Food Shop"), Bukkit.createInventory(null, 27, "Kitchen"), Bukkit.createInventory(null, 27, "Shop"), Bukkit.createInventory(null, 45, "Block Shop"), Bukkit.createInventory(null, 27, "Tool Menu"), Bukkit.createInventory(null, 27, "Enchantment Menu")};

    public static final ItemStack beer = GUIHandler.quickItem(Material.HONEY_BOTTLE, Methods.cStr("&6Beer"), 1, Methods.cStr("&6Restores &f3 hunger &6and &f0.5 saturation&6."), Methods.cStr("&6Can be drunk even when full to restore saturation."), Methods.cStr("&6Stats can be modified from the kitchen menu."));

    public static final HashMap<UUID, FastBoard> scoreboard = new HashMap<>();
    public static final HashMap<Player, Long> combatTag = new HashMap<>();
    public static final HashMap<Player, Pair<Player, Byte>> lastHitData = new HashMap<>();
    public static final List<Player> newPlayers = new ArrayList<>();
    public static final ArrayList<Player> vanishedPlayers = new ArrayList<>();
}