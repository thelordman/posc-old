package io.github.thelordman.costrength.commands;

import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class DonatorCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        sender.sendMessage(Methods.cStr("""

                &r    &6&lYour Statistics
                &r
                &aVIP&f:
                &6Bypass chat cooldown
                &6/ec
                &fWhite &6chat color
                &aVIP &6prefix
                &6VIP rank in Discord
                &r
                &eMVP&f:
                &6Everything from &aVIP
                &6/pv 1
                &6/food
                &eMVP &6prefix
                &6MVP rank in Discord
                &r
                &3ELITE&f:
                &6Everything from &eMVP &6and &aVIP
                &6/pv 1 and 2
                &6/mine
                &65% discount on block shop
                &3ELITE &6prefix
                &6ELITE rank in Discord
                &r
                &6LEGEND&f:
                &6Everything from &eMVP &6and &aVIP
                &6/pv 1, 2 and 3
                &6Choose the color of the rank
                &r
                &6&lBuy a rank here&f&l:
                &6&nLink Here"""));
        return true;
    }
}
