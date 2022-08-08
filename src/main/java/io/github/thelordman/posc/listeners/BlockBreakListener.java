package io.github.thelordman.posc.listeners;

import io.github.thelordman.posc.mining.MineHandler;
import io.github.thelordman.posc.utilities.Methods;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import java.util.List;

public class BlockBreakListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (Methods.inSpawn(event.getBlock().getLocation()) && !event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            event.setCancelled(true);
            return;
        }

        event.setExpToDrop(0);

        Player player = event.getPlayer();
        Block block = event.getBlock();
        if (!List.of(Material.STONE, Material.COAL_ORE, Material.IRON_ORE, Material.LAPIS_ORE, Material.DIAMOND_ORE, Material.EMERALD_ORE, Material.BEACON).contains(block.getType())) return;
        event.setDropItems(false);

        if (block.getType().equals(Material.BEACON)) MineHandler.resetMine((byte) 1, player);

        MineHandler.mineBlock(event.getPlayer(), event.getBlock());
    }
}