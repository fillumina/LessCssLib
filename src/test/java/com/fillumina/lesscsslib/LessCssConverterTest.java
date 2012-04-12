package com.fillumina.lesscsslib;

import java.io.IOException;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author fillumina@gmail.com
 */
public class LessCssConverterTest {

    // see http://lesscss.org/
    private static final String LESS_CSS =
            "@base: #f938ab;\n" +
            ".box-shadow(@style, @c) when (iscolor(@c)) {\n" +
            "   box-shadow:         @style @c;\n" +
            "   -webkit-box-shadow: @style @c;\n" +
            "   -moz-box-shadow:    @style @c;\n" +
            "\n}" +
            ".box-shadow(@style, @alpha: 50%) when (isnumber(@alpha)) {\n" +
            "   .box-shadow(@style, rgba(0, 0, 0, @alpha));\n" +
            "}\n" +
            ".box {\n" +
            "   color: saturate(@base, 5%);\n" +
            "   border-color: lighten(@base, 30%);\n" +
            "   div { .box-shadow(0 0 5px, 30%) }\n" +
            "}\n";

    private static final String INCORRECT_LESS_CSS =
            "@base: #f938ab;\n" +
            ".box-shadow(@style, @c) when (iscolor(@c)) {\n";

    private static final String EXPECTED_CSS =
            ".box {\n" +
            "  color: #fe33ac;\n" +
            "  border-color: #fdcdea;\n" +
            "}\n" +
            ".box div {\n" +
            "  box-shadow: 0 0 5px rgba(0, 0, 0, 0.3);\n" +
            "  -webkit-box-shadow: 0 0 5px rgba(0, 0, 0, 0.3);\n" +
            "  -moz-box-shadow: 0 0 5px rgba(0, 0, 0, 0.3);\n" +
            "}\n";

    @Test
    public void shouldConvertToCss() throws LessCssException {
        final LessCssConverter converter = new LessCssConverter();
        final String css = converter.convertToCss(LESS_CSS);
        assertEquals(EXPECTED_CSS, css);
    }

    @Test
    public void shouldUseAnExternalLessJsFile() throws IOException, LessCssException {
        final String externalFile = getRootPath() + "/less-1.3.0-external.js";
        final String content = FileUtils.fileToString(externalFile);
        final LessCssConverter converter = new LessCssConverter(content);
        final String css = converter.convertToCss(LESS_CSS);
        assertEquals(EXPECTED_CSS, css);
    }

    private static final String CORRUPTED_LESS_CESS_ERROR =
        "Error on run.js line 6: TypeError: Cannot call method \"toCSS\" of undefined\n" +
	"\tat run.js:6\n" +
	"\tat less.js:8\n" +
        "\tat run.js:5\n" +
	"\tat executor.js:1\n";

    @Test
    public void shouldDetectACorruptedLessCss() {
        final LessCssConverter converter = new LessCssConverter();
        try {
            converter.convertToCss(INCORRECT_LESS_CSS);
        } catch (final LessCssException ex) {
            assertEquals(CORRUPTED_LESS_CESS_ERROR, ex.getMessage());
            return;
        }
        fail();
    }

    private static final String CORRUPTED_LESS_JS_ERROR =
            "Error on less.js line 8: missing ) after formal parameters";

    @Test
    public void shouldDetectACorruptedExternalLessJs() throws IOException {
        final String externalFile = getRootPath() + "/less-1.3.0-corrupted.js";
        final String content = FileUtils.fileToString(externalFile);
        try {
            new LessCssConverter(content);
        } catch (LessCssException ex) {
            assertEquals(CORRUPTED_LESS_JS_ERROR, ex.getMessage());
            return;
        }
        fail();
    }

    @Test
    @Ignore
    public void shouldBeFast() throws LessCssException {
        long time = System.currentTimeMillis();
        for (int i=0; i<100; i++) {
            shouldConvertToCss();
        }
        time = (System.currentTimeMillis() - time);
        System.out.println("time: " + time + " ms");
    }

    private String getRootPath() {
        return ClassUtils.getRootAbsolutePathname(getClass());
    }
}
