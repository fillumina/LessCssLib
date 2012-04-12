package com.fillumina.lesscsslib;

import java.net.URL;

/**
 *
 * @author fillumina@gmail.com
 */
public class ClassUtils {
    public static String getRootAbsolutePathname(final Class<?> clazz) {
        return getClassAbsolutePathname(clazz)
                .replace(getClassRelativePathname(clazz), "");
    }

    public static String getClassRelativePathname(final Class<?> clazz) {
        final String path = clazz.getName();
        return "/" + path.replace(clazz.getSimpleName(), "")
                .replaceAll("\\.", "/");
    }

    public static String getClassAbsolutePathname(final Class<?> clazz) {
        final String fullpath = getClassAbsoluteFilename(clazz);
        return fullpath.replace(clazz.getSimpleName() + ".class", "");
    }

    public static String getClassAbsoluteFilename(final Object obj) {
        return getClassAbsoluteFilename(obj.getClass());
    }

    public static String getClassAbsoluteFilename(final Class<?> clazz) {
        final String name = clazz.getName().replaceAll("\\.", "/") + ".class";
        final URL url = clazz.getClassLoader().getResource(name);
        return url.getFile();
    }

}
