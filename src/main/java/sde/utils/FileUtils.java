package sde.utils;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {

    public static void writeTextFile(String fileName, String content) throws Exception {
        try (Writer wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"))) {
            wr.write(content, 0, content.length());
        }
    }

    public static String readTextFile(String filename) throws Exception {
        return new String(Files.readAllBytes(Paths.get(filename)));
    }
}
