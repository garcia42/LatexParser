package tex61;

import ucb.junit.textui;

/** The suite of all JUnit tests for the Text Formatter.
 *  @author Jesus Garcia
 */
public class UnitTest {

    /** Run the JUnit tests in the tex61 package. */
    public static void main(String[] ignored) {
        textui.runClasses(tex61.PageAssemblerTest.class);
        textui.runClasses(tex61.InputParserTest.class);
    }

}

