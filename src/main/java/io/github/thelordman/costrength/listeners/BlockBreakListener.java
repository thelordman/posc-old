package io.github.thelordman.costrength.listeners;

import io.github.thelordman.costrength.economy.EconomyManager;
import io.github.thelordman.costrength.economy.LevelHandler;
import io.github.thelordman.costrength.mining.MineHandler;
import io.github.thelordman.costrength.scoreboard.ScoreboardHandler;
import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.javatuples.Triplet;

import java.util.HashMap;
import java.util.List;

public class BlockBreakListener implements Listener {
    private final HashMap<Player, Triplet<Material, Long, Integer>> lastBlockData = new HashMap<>();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        event.setExpToDrop(0);

        Player player = event.getPlayer();
        Block block = event.getBlock();
        if (List.of(Material.STONE, Material.COAL_ORE, Material.IRON_ORE, Material.LAPIS_ORE, Material.DIAMOND_ORE, Material.EMERALD_ORE, Material.BEACON).contains(block.getType())) return;
        event.setDropItems(false);

        if (block.getType().equals(Material.BEACON)) MineHandler.resetMine((byte) 1, player);
        float reward = 0;
        switch (block.getType()) {
            case STONE -> reward = 1f;
            case COAL_ORE -> reward = 1.5f;
            case IRON_ORE -> reward = 2.25f;
            case LAPIS_ORE -> reward = 3f;
            case DIAMOND_ORE -> reward = 5f;
            case EMERALD_ORE -> reward = 8f;
            case BEACON -> reward = EconomyManager.getBalance(player) / 100f + 1000f;
        }

        Material m = !lastBlockData.containsKey(player) ? Material.AIR : lastBlockData.get(player).getValue0();
        long l = !lastBlockData.containsKey(player) ? 0 : lastBlockData.get(player).getValue1();
        lastBlockData.put(player, new Triplet<>(block.getType(), System.currentTimeMillis(),
                !lastBlockData.containsKey(player) | m != block.getType() | 5000 < System.currentTimeMillis() - l
                        ? 0 : lastBlockData.get(player).getValue2() + 1));
        float multi = lastBlockData.containsKey(player)
                ? (float) (block.getType().equals(Material.STONE)
                ? lastBlockData.get(player).getValue2() / 2
                : lastBlockData.get(player).getValue2()) / 100 : 0;

        float moneyMulti = 1 + multi;
        float xpMulti = 1 + multi;

        float money = reward * moneyMulti, xp = reward * xpMulti;

        player.sendActionBar(Methods.cStr("&f+$" + Methods.rStr(money) + " &7(" + Methods.rStr(moneyMulti) + "x) &8| &f+" + Methods.rStr(xp) + "xp &7(" + Methods.rStr(xpMulti) + "x) &8| &6Streak&7: &f" + Methods.rStr((float) lastBlockData.get(player).getValue2())));
        EconomyManager.setBalance(player, EconomyManager.getBalance(player) + money);
        EconomyManager.setXp(player, EconomyManager.getXp(player) + xp);
        LevelHandler.xp(player);
        ScoreboardHandler.updateBoard(player);
    }
}