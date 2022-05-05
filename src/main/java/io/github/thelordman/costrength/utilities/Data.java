package io.github.thelordman.costrength.utilities;

import org.bukkit.entity.Player;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Data implements Serializable {
    private static transient final long serialVersionUID = 7192687676555786196L;

    public static transient final HashMap<Player, Byte> combatTag = new HashMap<>();

    public final HashMap<UUID, Float> bounty = new HashMap<>();
    public final HashMap<UUID, Float> balance = new HashMap<>();
    public final HashMap<UUID, Float> xp = new HashMap<>();
    public final HashMap<UUID, Integer> level = new HashMap<>();
    public final HashMap<UUID, Integer> killstreak = new HashMap<>();

    public boolean saveData(String filePath) {
        try {
            BukkitObjectOutputStream bukkitObjectOutputStream = new BukkitObjectOutputStream(new GZIPOutputStream(new FileOutputStream(filePath)));
            bukkitObjectOutputStream.writeObject(this);
            bukkitObjectOutputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean loadData(String filePath) {
        try {
            BukkitObjectInputStream bukkitObjectInputStream = new BukkitObjectInputStream(new GZIPInputStream(new FileInputStream(filePath)));
            Data data = (Data) bukkitObjectInputStream.readObject();
            bukkitObjectInputStream.close();
            return true;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
