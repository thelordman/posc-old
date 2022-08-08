package io.github.thelordman.posc.utilities.data;

import io.github.thelordman.posc.Posc;
import org.bukkit.Location;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Pattern;

public class DataManager {
    public static GlobalData globalData;

    private static final File playerDataFolder = new File(Posc.get().getDataFolder() + "/playerdata");
    private static final File globalDataFile = new File(Posc.get().getDataFolder() + "/globaldata.dat");

    public static HashMap<UUID, PlayerData> playerDataMap = new HashMap<>();

    public static PlayerData getPlayerData(UUID uuid) {
        if (playerDataMap.containsKey(uuid)) {
            return playerDataMap.get(uuid);
        }

        return loadPlayerData(uuid);
    }

    public static void savePlayerData(UUID uuid) {
        if (!playerDataFolder.exists()) {
            playerDataFolder.mkdirs();
        }
        if (!playerDataMap.containsKey(uuid)) return;
        savePlayerData(playerDataMap.get(uuid));
    }

    public static void savePlayerData(PlayerData data) {
        if (!playerDataFolder.exists()) {
            playerDataFolder.mkdirs();
        }

        DataSerializer dataSerializer = new DataSerializer(new File(playerDataFolder + "/" + data.getUUID() + ".dat"));
        dataSerializer.serialize(data);
    }

    public static PlayerData loadPlayerData(UUID uuid) {

        PlayerData data;

        if (playerDataFolder.exists()) {
            for (File file : playerDataFolder.listFiles(File::isFile)) {
                String[] fileName = file.getName().split(Pattern.quote("."));

                if (fileName[0].equals(uuid.toString())) {
                    DataSerializer dataSerializer = new DataSerializer(file);
                    data = (PlayerData) dataSerializer.deserialize();

                    playerDataMap.put(uuid, data);
                    return data;
                }
            }
        }

        data = new PlayerData(uuid);
        playerDataMap.put(uuid, data);
        return data;
    }

    public static void saveAllData() {
        for (PlayerData playerData : playerDataMap.values()) {
            savePlayerData(playerData);
        }

        DataSerializer dataSerializer = new DataSerializer(new File(globalDataFile.getPath()));
        dataSerializer.serialize(globalData);
    }

    public static void loadAllData() {
        for (File dataFile : playerDataFolder.listFiles(File::isFile)) {
            if (playerDataMap.containsKey(UUID.fromString(dataFile.getName().split(Pattern.quote("."))[0]))) continue;

            DataSerializer dataSerializer = new DataSerializer(dataFile);
            PlayerData data = (PlayerData) dataSerializer.deserialize();

            playerDataMap.put(data.getUUID(), data);
        }

        DataSerializer dataSerializer = new DataSerializer(globalDataFile);
        globalData = (GlobalData) dataSerializer.deserialize();
    }

    // GlobalData manipulation

    public static int getHighID() {
        return globalData.highID;
    }

    public static void incrementHighID() {
        globalData.highID++;
    }

    public static int getAllPlayers() {
        return globalData.allPlayers;
    }

    public static void incrementAllPlayers() {
        globalData.allPlayers++;
    }

    public static Location getJailLocation() {
        return globalData.jailLocation;
    }

    public static void setJailLocation(Location location) {
        globalData.jailLocation = location;
    }
}