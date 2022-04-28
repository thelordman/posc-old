package io.github.thelordman.costrength.listeners;

import io.github.thelordman.costrength.economy.EconomyManager;
import io.github.thelordman.costrength.mine.MineHandler;
import io.github.thelordman.costrength.scoreboard.ScoreboardHandler;
import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import static io.github.thelordman.costrength.CoStrength.lastMinedBlock;

public class BlockBreakListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        event.setDropItems(false);

        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (block.getType().equals(Material.BEACON)) {
            float reward = EconomyManager.getBalance(player) / 100;
            player.sendActionBar(Methods.cStr("&6Beacon &8| &f+$" + reward + " &8| &f+" + reward + "xp"));
            EconomyManager.setBalance(player, EconomyManager.getBalance(player) / 100);
            ScoreboardHandler.updateBoard(player);
            MineHandler.resetMine((byte) 1, player);
            return;
        }

        float reward = 0;
        switch (block.getType()) {
            case STONE -> reward = 1f;
            case COAL_ORE -> reward = 1.5f;
            case IRON_ORE -> reward = 2.25f;
            case LAPIS_ORE -> reward = 3f;
            case DIAMOND_ORE -> reward = 5f;
            case EMERALD_ORE -> reward = 8f;
        }

        if (!(lastMinedBlock.get(player) == null)) {
            if (lastMinedBlock.get(player)[0].equals(block.getType().toString()) && System.currentTimeMillis() - Long.parseLong(lastMinedBlock.get(player)[1]) > 5000) {
                reward *= Float.parseFloat(lastMinedBlock.get(player)[2]) / 100;
            }
        }
        String[] blockData = new String[3];
        blockData[0] = block.getType().toString();
        blockData[1] = String.valueOf(System.currentTimeMillis());
        blockData[2] = lastMinedBlock.get(player) == null ? String.valueOf(0) : String.valueOf(Integer.parseInt(lastMinedBlock.get(player)[2]) + 1);
        lastMinedBlock.put(player, blockData);

        float money = reward;
        float xp = reward;

        player.sendActionBar(Methods.cStr("&f+$" + money + " &8| &f+" + xp + "xp"));
        EconomyManager.setBalance(player, EconomyManager.getBalance(player) + money);
        EconomyManager.setXp(player, EconomyManager.getXp(player) + xp);
        ScoreboardHandler.updateBoard(player);
    }
}