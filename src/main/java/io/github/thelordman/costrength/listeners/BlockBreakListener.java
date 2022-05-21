package io.github.thelordman.costrength.listeners;

import io.github.thelordman.costrength.economy.EconomyManager;
import io.github.thelordman.costrength.economy.LevelHandler;
import io.github.thelordman.costrength.mining.MineHandler;
import io.github.thelordman.costrength.scoreboard.ScoreboardHandler;
import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.javatuples.Triplet;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class BlockBreakListener implements Listener {
    private final HashMap<Player, Triplet<Material, Long, Integer>> lastBlockData = new HashMap<>();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        event.setExpToDrop(0);

        Player player = event.getPlayer();
        Block block = event.getBlock();
        if (!List.of(Material.STONE, Material.COAL_ORE, Material.IRON_ORE, Material.LAPIS_ORE, Material.DIAMOND_ORE, Material.EMERALD_ORE, Material.BEACON).contains(block.getType())) return;
        event.setDropItems(false);

        if (block.getType().equals(Material.BEACON)) MineHandler.resetMine((byte) 1, player);
        double reward = 0;
        switch (block.getType()) {
            case STONE -> reward = 1;
            case COAL_ORE -> reward = 1.5;
            case IRON_ORE -> reward = 2.25;
            case LAPIS_ORE -> reward = 3;
            case DIAMOND_ORE -> reward = 5;
            case EMERALD_ORE -> reward = 8;
            case BEACON -> reward = EconomyManager.getBalance(player.getUniqueId()) / 100 + 1000;
        }

        Material m = !lastBlockData.containsKey(player) ? Material.AIR : lastBlockData.get(player).getValue0();
        long l = !lastBlockData.containsKey(player) ? 0 : lastBlockData.get(player).getValue1();
        lastBlockData.put(player, new Triplet<>(block.getType(), System.currentTimeMillis(),
                !lastBlockData.containsKey(player) | m != block.getType() | 5000 < System.currentTimeMillis() - l
                        ? 0 : lastBlockData.get(player).getValue2() + 1));
        double multi = lastBlockData.containsKey(player)
                ? (float) (block.getType().equals(Material.STONE)
                ? lastBlockData.get(player).getValue2() / 2
                : lastBlockData.get(player).getValue2()) / 100 : 0;

        if (block.getType().equals(Material.DIAMOND_ORE) | block.getType().equals(Material.EMERALD_ORE)) {
            multi += 0.5d * player.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.SILK_TOUCH);
        }
        if (player.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) != 0 && Math.random() <= ((double) player.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS)) / 20d) {
            multi++;
        }

        double moneyMulti = 1 + multi;
        double xpMulti = 1 + multi;

        double money = reward * moneyMulti, xp = reward * xpMulti;

        player.sendActionBar(Methods.cStr("&f+$" + Methods.rStr(money) + " &7(" + Methods.rStr(moneyMulti) + "x) &8| &f+" + Methods.rStr(xp) + "xp &7(" + Methods.rStr(xpMulti) + "x) &8| &6Streak&7: &f" + Methods.rStr((double) lastBlockData.get(player).getValue2())));
        EconomyManager.setBalance(player.getUniqueId(), EconomyManager.getBalance(player.getUniqueId()) + money);
        EconomyManager.setXp(player.getUniqueId(), EconomyManager.getXp(player.getUniqueId()) + xp);
        LevelHandler.xp(player);
        ScoreboardHandler.updateBoard(player);
    }
}