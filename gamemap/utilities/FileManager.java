package com.plugin.utilities.minigame.gamemap.utilities;

import com.plugin.utilities.minigame.gamemap.GameMap;
import com.plugin.utilities.minigame.gamemap.LocalGameMap;

import java.io.*;

public class FileManager {

    public static void copy(File source, File destination) throws IOException {
        if (!destination.exists()) destination.mkdirs();

        String[] files = source.list();
        if (files == null) return;

        for (String file : files) {
            if (source.isDirectory()) {
                File sourceFile = new File(source, file);
                File destinationCopied = new File(destination, file);
                copy(sourceFile, destinationCopied);
            }

            InputStream inputStream = new FileInputStream(source);
            OutputStream outputStream = new FileOutputStream(destination);

            byte[] buffer = new byte[2048];

            int length;
            while ((length = inputStream.read(buffer)) > 0) outputStream.write(buffer, 0, length);

            inputStream.close();
            outputStream.close();
        }
    }

    public static void delete(File source) {
        if (!source.exists()) return;

        String[] files = source.list();
        if (files == null) return;

        for (String fileName : files) {
            if (source.isDirectory()) {
                File file = new File(source, fileName);
                if (!file.exists()) return;

                file.delete();
                delete(file);
            }
        }

        source.delete();
    }
}
