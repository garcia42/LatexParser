package tex61;

import static org.junit.Assert.assertEquals;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.Test;

/**
 * Unit tests of PageAssemblers.
 *
 * @author Jesus Garcia
 */

public class InputParserTest {

    /** THIS IS A CONTROLLER, NEEDED TO CREATE THE INPUTPARSER. */
    private Controller controller;

    /**
     * THIS IS A STRING THAT IS WRITTEN TO. OUTPUT.
     */
    private String outString;
    /** INSTRING IS A STRING THAT IS PUT INTO THE INPUT PARSER. */
    private String inString;
    /** INPUTPARSER INSTANCE THAT HOLDS CONTROLLER AND STRING. */
    private InputParser inputParser;

    private void setupInputParser() {
        PrintWriter printWriter = new PrintWriter(System.out);
        controller = new Controller(printWriter);
        inputParser = new InputParser(inString, controller);
    }

    /** TESTSTRING IN ORDER TO TEST THE OUTPUT. */
    private String testString = "This is a test of the emergency broadcast "
        + "system, testing to" + " see if the input + "
        + "will take more lines and also if it will read it all.";

    /** COMMANDSTRING TO TEST WHAT COMES OUT. */
    private String commandString =
        "\\parindent{5}\\textwidth{5}\\indent{5}\\parskip{5}";

    /** CMDOUTSTRING IS THE STRING I HOPE TO GET AFTER COMMAND STRING. */
    private String cmdOutString = "parindent{textwidth{indent{parskip{";

    @Test
    public void testParser() {
        inString = commandString;
        setupInputParser();
        inputParser.process();
        assertEquals("this test passed ", cmdOutString,
            controller.arrayToString(inputParser.getWords()));
    }

    /** Collects output to a PrintWriter. */
    private StringWriter output;
    /** Collects output from a PageAssembler. */
    private PrintWriter writer;
    /** Lines of test data. */
}
