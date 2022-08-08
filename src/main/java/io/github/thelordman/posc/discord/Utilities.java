package io.github.thelordman.posc.discord;

import net.dv8tion.jda.api.entities.Member;
import org.bukkit.ChatColor;

public class Utilities {
    public static ChatColor memberChatColor(Member member, String type) {
        ChatColor color;

        if (type.equals("primary")) {
            color = (member.getRoles().contains(member.getGuild().getRoleById("996795355268137061")) | member.getRoles().contains(member.getGuild().getRoleById("921439677574160498"))) ? ChatColor.WHITE : ChatColor.GRAY;
        }
        else {
            color = (member.getRoles().contains(member.getGuild().getRoleById("996795355268137061")) | member.getRoles().contains(member.getGuild().getRoleById("921439677574160498"))) ? ChatColor.GRAY : ChatColor.DARK_GRAY;
        }

        return color;
    }
}