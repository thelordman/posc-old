package me.lord.posc.utilities;

import me.lord.posc.Posc;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ReflectionUtil {
    public static <T> Class<? extends T>[] getSubclasses(Class<T> c, String packName, Predicate<Class<? extends T>> except) {
        ArrayList<Class<? extends T>> classes = new ArrayList<>();
        try {
            String packPath = packName.replace('.', '/');
            URI pack = Posc.getInstanceClassLoader().getResource(packPath).toURI();
            Path root;
            if (pack.toString().startsWith("jar" + File.pathSeparator)) {
                try {
                    root = FileSystems.getFileSystem(pack).getPath(packPath);
                } catch (FileSystemNotFoundException e) {
                    root = FileSystems.newFileSystem(pack, Collections.emptyMap()).getPath(packPath);
                }
            } else {
                root = Paths.get(pack);
            }

            String extension = ".class";
            try (final Stream<Path> allPaths = Files.walk(root)) {
                allPaths.filter(Files::isRegularFile).forEach(file -> {
                    try {
                        String path = file.toString().replace(File.separatorChar, '.');
                        String name = path.substring(path.indexOf(packName), path.length() - extension.length());
                        Class<? extends T> c1 = (Class<? extends T>) Class.forName(name);
                        if (c.isAssignableFrom(c1)) {
                            if (!except.test(c1)) {
                                classes.add(c1);
                            }
                        }
                    } catch (ClassNotFoundException | StringIndexOutOfBoundsException ignored) {
                        // Continue loop if file doesn't match
                    }
                });
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        return (Class<? extends T>[]) classes.toArray(new Class<?>[classes.size()]);
    }

    public static <T> Class<? extends T>[] getSubclasses(Class<T> c, String packPath) {
        return getSubclasses(c, packPath, Objects::isNull);
    }
}
