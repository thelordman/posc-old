package io.github.thelordman.costrength.commands;

import io.github.thelordman.costrength.utilities.CommandName;
import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

@CommandName("donator")
public class DonatorCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        sender.sendMessage(Methods.cStr("""

                &r    &6&lRanks
                &r
                &a&lVIP&f:
                &6Bypass chat cooldown
                &6/ec (opens your enderchest)
                &fWhite &6chat color
                &aVIP &6prefix
                &6VIP rank in Discord
                &r
                &e&lMVP&f:
                &6Everything from &aVIP
                &6Shortcut commands for GUIs
                &eMVP &6prefix
                &6MVP rank in Discord
                &r
                &3&lELITE&f:
                &6Everything from &eMVP &6and &aVIP
                &6/mine (resets mine)
                &65% discount on block shop
                &3ELITE &6prefix
                &6ELITE rank in Discord
                &r
                &6&lLEGEND&f:
                &6Everything from &eMVP &6and &aVIP
                &6/glow (make yourself glow)
                &r
                &6&lBuy a rank here&f&l:
                &6&nLink Here"""));
        return true;
    }
}
