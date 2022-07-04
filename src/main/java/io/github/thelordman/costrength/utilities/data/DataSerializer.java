package io.github.thelordman.costrength.utilities.data;

import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public record DataSerializer(File file) {

    public DataSerializer(@Nonnull File file) {
        this.file = file;
    }

    public <T extends Serializable> void serialize(@Nonnull T objectToSerialize) {

        try (BukkitObjectOutputStream objectOutputStream = new BukkitObjectOutputStream(new GZIPOutputStream(new FileOutputStream(file)));) {

            objectOutputStream.writeObject(objectToSerialize);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    public Object deserialize() {

        try (BukkitObjectInputStream objectInputStream = new BukkitObjectInputStream(new GZIPInputStream(new FileInputStream(file)))) {

            return objectInputStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

}
