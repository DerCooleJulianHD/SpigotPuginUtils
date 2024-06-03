package de.dercoolejulianhd.plugin.utils.filemanager;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class FileManager {

    public static void copy(File dir, File toDestination) {
        if (!dir.exists()) return;
        if (!toDestination.exists()) toDestination.mkdirs();
        if (!dir.isDirectory()) throw new RuntimeException("File must be a directory!");
        String[] files = dir.list();
        if (files == null) return;
        if (files.length == 0) return;
        List<String> list = Arrays.stream(files).toList();
        if (list.isEmpty()) return;
        list.forEach(s -> {
            File child = new File(dir, s);
            if (child.isDirectory()) {
                copy(child, toDestination);
                return;
            }
            copyFile(child, toDestination);
        });
    }

    public static void copyFile(File file, File toDestination) {
        if (!file.exists()) return;
        boolean readable0 = file.setReadable(true, false);
        boolean writable0 = file.setWritable(true, false);
        if (!toDestination.exists()) toDestination.mkdirs();
        try {
            File copied = new File(toDestination, file.getName());
            InputStream input = new FileInputStream(file);
            OutputStream output = new FileOutputStream(copied);
            boolean isCreated = copied.createNewFile();
            boolean readable1 = copied.setReadable(true, false);
            boolean writable1 = copied.setWritable(true, false);
            output.write(input.readAllBytes());
            input.close();
            output.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void delete(File file) {
        if (!file.exists()) return;
        if (file.isDirectory()) {
            String[] files = file.list();
            assert files != null;
            if (files.length == 0) return;

            for (String fileName : files) {
                File child = new File(file, fileName);
                if (!child.exists()) continue;
                delete(child);
            }
         }
        file.delete();
    }
}
