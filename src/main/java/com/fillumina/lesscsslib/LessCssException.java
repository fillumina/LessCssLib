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
