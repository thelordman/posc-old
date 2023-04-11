package me.lord.posc.data;

import me.lord.posc.Posc;
import me.lord.posc.npc.NPC;
import me.lord.posc.npc.NPCManager;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R3.util.DatFileFilter;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataManager {
    private static final HashMap<UUID, PlayerData> playerDataMap = new HashMap<>();

    public static final File PLAYER_DATA_FOLDER = new File(Posc.get().getDataFolder() + File.separator + "playerdata");
    public static final File NPC_DATA_FOLDER = new File(Posc.get().getDataFolder() + File.separator + "npcdata");
    public static final File GLOBAL_DATA_FILE = new File(Posc.get().getDataFolder() + File.separator + "globaldata.dat");

    private static GlobalData globalData = null;

    public static void loadPlayerData(Player player) {
        UUID uuid = player.getUniqueId();
        playerDataMap.put(uuid, getPlayerDataFromFile(uuid) == null ? new PlayerData(uuid) : getPlayerDataFromFile(uuid));
    }

    public static void savePlayerData(Player player) {
        UUID uuid = player.getUniqueId();
        playerDataMap.get(uuid).serialize(PLAYER_DATA_FOLDER.getPath() + File.separator + uuid + ".dat");
    }

    public static GlobalData getGlobal() {
        return globalData;
    }


    public static void loadAll() {
        if (!Posc.get().getDataFolder().exists()) Posc.get().getDataFolder().mkdir();

        if (GLOBAL_DATA_FILE.exists()) {
            globalData = (GlobalData) Data.deserialize(GLOBAL_DATA_FILE.getPath());
        } else if (globalData == null) {
            globalData = new GlobalData();
        }

        if (!PLAYER_DATA_FOLDER.exists()) PLAYER_DATA_FOLDER.mkdir();
        for (Player player : Bukkit.getOnlinePlayers()) {
            loadPlayerData(player);
        }

        if (!NPC_DATA_FOLDER.exists()) NPC_DATA_FOLDER.mkdir();
        for (File file : NPC_DATA_FOLDER.listFiles(new DatFileFilter())) {
            NPCData npcData = (NPCData) Data.deserialize(file.getPath());
            assert npcData != null : "Wrong and/or corrupted file at " + file.getPath();
            NPCManager.createNPC(npcData.getName(), npcData.getLocation(), npcData.getSkin(), Integer.parseInt(file.getName().split("\\.")[0]));
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

            NPCManager.sendRemovePacketAll(player);
        }

        NPCManager.getNPCMap().values().forEach(
                npc -> Posc.MAIN_WORLD.getEntity(npc.getInteractionId()).remove()
        );

        if (!NPC_DATA_FOLDER.exists()) NPC_DATA_FOLDER.mkdir();
        for (Map.Entry<Integer, NPC> entry : NPCManager.getNPCMap().entrySet()) {
            NPCData.fromNPC(entry.getValue()).serialize(NPC_DATA_FOLDER.getPath() + File.separator + entry.getKey() + ".dat");
        }
    }


    private static PlayerData getPlayerDataFromFile(UUID uuid) {
        File file = new File(PLAYER_DATA_FOLDER.getPath() + File.separator + uuid.toString() + ".dat");
        if (!file.exists()) return null;
        PlayerData data = (PlayerData) Data.deserialize(file.getPath());
        if (data.getUUID() == null) data.setUUID(uuid);
        return data;
    }

    public static PlayerData getPlayerData(Player player) {
        return getPlayerData(player.getUniqueId());
    }

    public static PlayerData getPlayerData(UUID uuid) {
        return playerDataMap.get(uuid);
    }

}
