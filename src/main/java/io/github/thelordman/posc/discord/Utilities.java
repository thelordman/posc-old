package io.github.thelordman.posc.discord;

import net.dv8tion.jda.api.entities.Member;
import org.bukkit.ChatColor;

public class Utilities {
    public static ChatColor memberChatColor(Member member, String type) {
        ChatColor color;

        if (type.equals("primary")) {
            color = (member.getRoles().contains(member.getGuild().getRoleById("921439677574160498")) | member.getRoles().contains(member.getGuild().getRoleById("995764844584259664"))) ? ChatColor.WHITE : ChatColor.GRAY;
        }
        else {
            color = (member.getRoles().contains(member.getGuild().getRoleById("921439677574160498")) | member.getRoles().contains(member.getGuild().getRoleById("995764844584259664"))) ? ChatColor.GRAY : ChatColor.DARK_GRAY;
        }

        return color;
    }
}