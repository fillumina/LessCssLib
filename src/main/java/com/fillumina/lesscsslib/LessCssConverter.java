/*
 *  Copyright 2010 Richard Nichols, Francesco Illuminati
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
import org.mozilla.javascript.*;

/**
 * Transform a LessCSS file (see {@linkplain http;//lesscss.org}) into
 * a standard CSS. It uses the Mozilla Rhino Javascript engine to execute
 * the standard less.js file which then is used for the actual transformation.
 * Please note that the conversion made by less.js doesn't operate any
 * validity check over the passed less files. So, if they are wrong, the
 * produced CSS may probably be wrong as well. Also note that this class
 * is NOT {@link java.util.Serializable}.
 *
 * Adapted from an original concept by Richard Nichols.
 * @author fillumina@gmail.com
 */
public class LessCssConverter {

    private final Context context;
    private final ScriptableObject scope;

    /**
     * Use the internal less.js file (which is actually version 1.3.0).
     *
     * @throws LessCssException something went really wrong with the embedded
     *                          libraries or javascript files
     */
    public LessCssConverter() throws LessCssException {
        this(null);
    }

    /**
     * Accept an external less.js file given as a String
     * (see <a href='http://lesscss.org'>LessCSS</a>).<br />
     * Use {@link FileUtils#fileToString(java.lang.String)} if you need
     * to provide a file.
     *
     * @param lessJs    the less.js content as a string
     * @throws LessCssException  a problem in the external less.js
     */
    public LessCssConverter(final String lessJs) throws LessCssException {
        final ContextFactory contextFactory = new ContextFactory();
        context = contextFactory.enterContext();
        scope = context.initStandardObjects();
        context.setOptimizationLevel(-1); // -1 is ~15% faster than 9

        initConverter(lessJs);
    }

    private void initConverter(final String lessJsContent) throws LessCssException {
        final String lessJs = lessJsContent != null ?
                lessJsContent : getResourceAsString("less-1.3.0.min.js");
        final String initJs = getResourceAsString("init.js");
        final String runJs = getResourceAsString("run.js");

        evaluate(initJs, "init.js");
        evaluate(lessJs, "less.js");
        evaluate(runJs, "run.js");
    }

    /**
     * Perform the conversion from LessCSS to CSS.
     *
     * @param lessCss string representation of the LessCSS file
     * @return        string representation of the converted CSS
     * @throws LessCssException  a problem in the conversion process
     */
    public String convertToCss(final String lessCss)
            throws LessCssException {
        final String executorJs = "lessIt(\"" + prepareCss(lessCss) + "\")";
        try {
            return evaluate(executorJs, "executor.js").toString();
        } catch (final RhinoException e) {
            // wrap the exception into a conversion agnostic one
            throw new LessCssException(e);
        }
    }

    private String prepareCss(final String inputCss) {
        return inputCss.replaceAll("\"", "\\\"").replaceAll("\n", "").replace("\r", "");
    }

    private String getResourceAsString(final String filename) {
        try {
            return FileUtils.resourceToString(LessCssConverter.class, filename);
        } catch (final IOException ex) {
            throw new RuntimeException("Cannot load internal resource " + filename, ex);
        }
    }

    private Object evaluate(final String initJs, final String filename)
            throws LessCssException {
        try {
            return context.evaluateString(scope, initJs, filename, 1, null);
        } catch (RhinoException ex) {
            throw new LessCssException(ex);
        }
    }

}
