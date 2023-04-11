package me.lord.posc.npc.interaction;

import me.lord.posc.Posc;
import me.lord.posc.data.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@NPCIndex(id = 0)
public class DaveNPCInteraction extends NPCInteraction {
    @Override
    public void callEvent() {
        DataManager.getPlayerData(getPlayer()).setCurrentInteraction(this);
        getNPC().message(getPlayer(), "Hello there, welcome to Posc!", 0L);
        getNPC().message(getPlayer(), "My name is Dave, and I'm quite popular around here. Consider me your guide.", 50L);
        getNPC().message(getPlayer(), "Posc is an SMP where new quality content is constantly being added. Here are some tips:", 150L);
        getNPC().message(getPlayer(), "Right click on different NPCs to see what they're offering.", 250L);
        getNPC().message(getPlayer(), "Do /discord to join our Discord to suggest new features, report bugs and players, and have fun with other people.", 350L);
        getNPC().message(getPlayer(), "Do /help to see all the custom commands and their usages.", 450L);
        getNPC().message(getPlayer(), "With that you should be good to go, now either start running away from spawn or do /rtp to teleport to a random location to begin playing.", 550L);

        Bukkit.getScheduler().runTaskLater(Posc.get(), () -> getPlayer().getInventory().addItem(new ItemStack(Material.BREAD, 64)), 650L);

        Bukkit.getScheduler().runTaskLater(Posc.get(), () -> DataManager.getPlayerData(getPlayer()).setCurrentInteraction(null), 750L);
    }
}
