package com.fillumina.lesscsslib;

import java.io.*;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fillumina@gmail.com
 */
public class FileUtils {

    public static String resourceToString(final Class<?> clazz,
            final String resource) throws IOException {
        return urlToString(clazz.getResource(resource));
    }

    public static String fileToString(final String filename) throws IOException {
        final File file = new File(filename);
        if (!file.exists()) {
            throw new IOException("file " + filename + " not found");
        }
        return urlToString(file.toURI().toURL());
    }

    public static String urlToString(final URL url) throws IOException {
        InputStream is = null;
        try {
            final StringBuilder buf = new StringBuilder();
            is = url.openStream();
            int n = 0;
            do {
                n = is.read();
                if (n >= 0) {
                    buf.append((char) n);
                }
            } while (n >= 0);
            is.close();
            return buf.toString();
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(LessCssConverter.class.getName()).
                        log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void stringToFile(final String filename, final String data)
            throws IOException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filename));
            writer.write(data);
            writer.flush();
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(LessCssConverter.class.getName()).log(
                        Level.SEVERE, "Could not close stream in stringToFile()",
                        ex);
            }
        }
    }
}
