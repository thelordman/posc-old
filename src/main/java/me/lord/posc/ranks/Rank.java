package me.lord.posc.ranks;

import kotlin.CharCodeJVMKt;
import me.lord.posc.utilities.TextUtil;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public enum Rank {

    DEFAULT("&7[DEFAULT]", ChatColor.GRAY),
    DEVELOPER("&d[DEVELOPER]"),
    ADMIN("&6[ADMIN]", List.of("posc.nick")),
    OWNER("&c[OWNER]");

    private final String prefix;
    private final ChatColor chatColor;
    private List<String> permissions = new ArrayList<>();

    Rank(String prefix) {
        this.prefix = TextUtil.cs(prefix);
        this.chatColor = ChatColor.WHITE;
    }

    Rank(String prefix, ChatColor chatColor) {
        this.prefix = TextUtil.cs(prefix);
        this.chatColor = chatColor;
    }

    Rank(String prefix, List<String> permissions) {
        this.prefix = TextUtil.cs(prefix);
        this.chatColor = ChatColor.WHITE;
        this.permissions = permissions;
    }

    Rank(String prefix, ChatColor chatColor, List<String> permissions) {
        this.prefix = TextUtil.cs(prefix);
        this.chatColor = chatColor;
        this.permissions = permissions;
    }

    public String getPrefix() {
        return prefix;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
