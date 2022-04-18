package io.github.thelordman.costrength.discord;

import net.dv8tion.jda.api.entities.Member;
import org.bukkit.ChatColor;

public class Utilities {
    public static ChatColor memberChatColor(Member member, String type) {
        ChatColor color;

        if (type.equals("primary")) {
            color = (member.getRoles().contains(member.getGuild().getRoleById("950087504080547840")) | member.getRoles().contains(member.getGuild().getRoleById("950087282206072963"))) ? ChatColor.WHITE : ChatColor.GRAY;
        }
        else {
            color = (member.getRoles().contains(member.getGuild().getRoleById("950087504080547840")) | member.getRoles().contains(member.getGuild().getRoleById("950087282206072963"))) ? ChatColor.GRAY : ChatColor.DARK_GRAY;
        }

        return color;
    }
}
