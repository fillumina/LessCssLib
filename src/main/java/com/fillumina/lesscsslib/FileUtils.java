/*
 *  Copyright 2012 Francesco Illuminati
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */
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
