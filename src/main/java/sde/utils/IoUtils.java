package sde.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

public class IoUtils {

    public static String stackTraceToString(Exception ex) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintWriter pwWriter = new PrintWriter(baos, true);
        ex.printStackTrace(pwWriter);
        return baos.toString();
    }
}
