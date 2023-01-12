package me.lord.posc.data;

import me.lord.posc.Posc;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Data implements Serializable {
    @Serial
    private static final long serialVersionUID = 747871004192330158L;

    private static final File USER_DATA = new File(Posc.get().getDataFolder() + File.separator + "userdata");
    private static final File GLOBAL_DATA = new File(Posc.get().getDataFolder() + File.separator + "globaldata.dat");

    private static GlobalData globalData = null;

    public static void init() {
        if (!Posc.get().getDataFolder().exists()) Posc.get().getDataFolder().mkdir();

        if (GLOBAL_DATA.exists()) {
            globalData = (GlobalData) deserialize(GLOBAL_DATA.getPath());
        } else if (globalData == null) {
            globalData = new GlobalData();
        }
    }

    public static void saveAll() {
        if (globalData != null) {
            globalData.serialize(GLOBAL_DATA.getPath());
        }
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
}
