package io.github.thelordman.costrength.commands;

import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class GlowCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!Methods.checkDonatorCommandPermission(sender, (byte) 4)) return true;
        Player player = (Player) sender;

        if (player.hasPotionEffect(PotionEffectType.GLOWING)) player.removePotionEffect(PotionEffectType.GLOWING);
        else player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 0));
        return true;
    }
}
