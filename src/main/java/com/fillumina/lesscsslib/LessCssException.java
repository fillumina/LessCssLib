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

import org.mozilla.javascript.RhinoException;
import org.mozilla.javascript.ScriptStackElement;

/**
 * It's a wrapper for {@link RhinoException} so the upper layers don't
 * have to care about the implementation details.
 *
 * @author fillumina@gmail.com
 */
public class LessCssException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final RhinoException rhinoException;

    LessCssException(final RhinoException rhinoException) {
        this.rhinoException = rhinoException;
    }

    public final String sourceName() {
        return rhinoException.sourceName();
    }

    public final String lineSource() {
        return rhinoException.lineSource();
    }

    public final int lineNumber() {
        return rhinoException.lineNumber();
    }

    public String getScriptStackTrace() {
        return rhinoException.getScriptStackTrace();
    }

    public ScriptStackElement[] getScriptStack() {
        return rhinoException.getScriptStack();
    }

    public final int columnNumber() {
        return rhinoException.columnNumber();
    }

    public String details() {
        return rhinoException.details();
    }

    @Override
    public final String getMessage() {
        final StringBuilder buf = new StringBuilder();
        buf
                .append("Error on ").append(sourceName())
                .append(" line ").append(lineNumber())
                .append(": ").append(rhinoException.details());

        final String stack = getScriptStackTrace();
        if (stack != null && !stack.isEmpty()) {
            buf.append("\n").append(getScriptStackTrace());
        }

        return buf.toString();
    }

}
