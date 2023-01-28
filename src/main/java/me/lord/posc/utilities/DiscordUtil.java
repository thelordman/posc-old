package me.lord.posc.utilities;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import static me.lord.posc.discord.Discord.jda;

public class DiscordUtil {
    private static final Role[] privilegedRoles = {jda.getRoleById("921439677574160498"), jda.getRoleById("995764844584259664")};

    public static boolean isPrivileged(Member member) {
        if (member.isOwner() || member.isBoosting()) return true;
        for (Role role : privilegedRoles) {
            if (member.getRoles().contains(role)) return true;
        }
        return false;
    }

    public static Role getRole(Member member, int index) {
        if (member.getRoles().isEmpty()) {
            return null;
        }
        else {
            try {
                return member.getRoles().get(index);
            } catch (IndexOutOfBoundsException ignored) {
                return null;
            }
        }
    }

    public static Role getHighestRole(Member member) {
        return getRole(member, 0);
    }
}
