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
