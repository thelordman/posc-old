package me.lord.posc.data;

import me.lord.posc.Posc;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * The superclass of all persistent data storing classes.
 */
public class Data implements Serializable {
    @Serial
    private static final long serialVersionUID = 747871004192330158L;

    private static final HashMap<UUID, PlayerData> playerDataMap = new HashMap<>();

    public static final File PLAYER_DATA_FOLDER = new File(Posc.get().getDataFolder() + File.separator + "playerdata");
    private static final File GLOBAL_DATA_FILE = new File(Posc.get().getDataFolder() + File.separator + "globaldata.dat");

    private static GlobalData globalData = null;

    public static void loadAll() {
        if (!Posc.get().getDataFolder().exists()) Posc.get().getDataFolder().mkdir();

        if (GLOBAL_DATA_FILE.exists()) {
            globalData = (GlobalData) deserialize(GLOBAL_DATA_FILE.getPath());
        } else if (globalData == null) {
            globalData = new GlobalData();
        }

        if (!PLAYER_DATA_FOLDER.exists()) PLAYER_DATA_FOLDER.mkdir();
        for (Player player : Bukkit.getOnlinePlayers()) {
            loadPlayerData(player);
        }
    }

    public static void saveAll() {
        if (!Posc.get().getDataFolder().exists()) Posc.get().getDataFolder().mkdir();

        if (globalData != null) {
            globalData.serialize(GLOBAL_DATA_FILE.getPath());
        }

        if (!PLAYER_DATA_FOLDER.exists()) PLAYER_DATA_FOLDER.mkdir();
        for (Player player : Bukkit.getOnlinePlayers()) {
            savePlayerData(player);
        }
    }

    public static void loadPlayerData(Player player) {
        UUID uuid = player.getUniqueId();
        playerDataMap.put(uuid, getPlayerData(uuid) == null ? new PlayerData(uuid) : getPlayerData(uuid));
    }

    public static void savePlayerData(Player player) {
        UUID uuid = player.getUniqueId();
        playerDataMap.get(uuid).serialize(PLAYER_DATA_FOLDER.getPath() + File.separator + uuid + ".dat");
    }

    public static GlobalData getGlobal() {
        return globalData;
    }

    public void serialize(String path) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(path)))) {
            outputStream.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Data deserialize(String path) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new GZIPInputStream(new FileInputStream(path)))) {
            return (Data) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static PlayerData getPlayerData(UUID uuid) {
        File file = new File(PLAYER_DATA_FOLDER.getPath() + File.separator + uuid.toString() + ".dat");
        if (!file.exists()) return null;
        return (PlayerData) deserialize(file.getPath());
    }

    public static PlayerData getPlayerData(Player player) {
        return playerDataMap.get(player.getUniqueId());
    }
}
