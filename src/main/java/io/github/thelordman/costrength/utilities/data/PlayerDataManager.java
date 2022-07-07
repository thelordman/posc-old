package io.github.thelordman.costrength.utilities.data;

import io.github.thelordman.costrength.CoStrength;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Pattern;

public class PlayerDataManager {

    private static final File playerDataFolder = new File(CoStrength.get().getDataFolder() + "/playerdata");

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

    public static void saveAllPlayerData() {
        for (PlayerData playerData : playerDataMap.values()) {
            savePlayerData(playerData);
        }
    }

    public static HashMap<UUID, PlayerData> loadAllPlayerData() {

        if (!playerDataFolder.exists()) {
            return playerDataMap;
        }

        for (File dataFile : playerDataFolder.listFiles(File::isFile)) {
            if (playerDataMap.containsKey(UUID.fromString(dataFile.getName().split(Pattern.quote("."))[0]))) continue;

            DataSerializer dataSerializer = new DataSerializer(dataFile);
            PlayerData data = (PlayerData) dataSerializer.deserialize();

            playerDataMap.put(data.getUUID(), data);
        }

        return playerDataMap;
    }
}