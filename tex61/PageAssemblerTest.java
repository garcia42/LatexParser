package tex61;

import static org.junit.Assert.assertEquals;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/** Unit tests of PageAssemblers.
 *  @author Jesus Garcia
 */
public class PageAssemblerTest {
    /** STRING THAT HOLDS THE LIST OF WORDS PRINTED PAGEPRINTER. */
    private static final String NL = System.getProperty("line.separator");
    /** STRING THAT HOLDS THE LIST OF WORDS PRINTED PAGEPRINTER. */
    private Controller controller;
    /** STRING THAT WILL HOLD THE LINES THAT NEED TO BE PRINTED FROM LINE. */
    private String string;

    /** SETUPWRITER, SETS UP A STRING WRITER AND A PRINT WRITER
     * TO THE STRING WRITER. */
    private void setupWriter() {
        output = new StringWriter();
        writer = new PrintWriter(output);
    }
    /** SETUPCOLLECTOR IS WHERE THE PAGEPRINTER SENDS ITS STUFF. */
    private void setupCollector() {
        outList = new ArrayList<>();
    }
    /** SETUPCONTROLLER SETS UP CONTROLLER FOR THE PAGECOLLECTOR. */
    private void setupController() {
        setupWriter();
        Controller controller1 = new Controller(writer);
        controller = controller1;
    }
    /** MAKETESTLINES MAKES THE TEST LINES, TAKES IN INT N AND
     * ADDS N MANY TESTLINES . */
    private void makeTestLines(int n) {
        testLines = new ArrayList<>();
        for (int i = 0; i < n; i += 1) {
            testLines.add("Line " + i);
        }
    }
    /** PRINTLINES TAKES THE LINES FROM PRINTWRITER AND IMMEDIATELY
     * PRINTS THEM. /*
     */
    private void printLines() {
        for (String L: testLines) {
            print.write(L);
        }
    }
    /** WRITETESTLINES WRITES TEST LINES THAT ARE IN ADDLINES AND TAKES
     * IN THE STRINGS ONE AT A TIME PRINTING THEM. */
    private void writeTestLines() {
        for (String L : testLines) {
            pages.write(L);
        }
    }
    /** JOIN LINES WILL APPEND EACH LINE IN TESTLINES AND THEN EACH
     * LINE IN NL ONTO STRINGBUILDER S. */
    private String joinLines() {
        StringBuilder S = new StringBuilder();
        for (String L : testLines) {
            S.append(L);
            S.append(NL);
        }
        return S.toString();
    }

    @Test
    public void testPrinterContents1() {
        makeTestLines(20);
        setupWriter();
        print = new PagePrinter(writer);
        printLines();
        assertEquals("wrong contents: printer", joinLines(), output.toString());
    }

    @Test
    public void testCollectorContents1() {
        makeTestLines(20);
        setupCollector();
        pages = new PageCollector(outList, controller);
        writeTestLines();
        assertEquals("wrong contents: collector", testLines, outList);
    }

    /** Collects output to a PrintWriter. */
    private StringWriter output;
    /** Collects output from a PageAssembler. */
    private PrintWriter writer;
    /** Lines of test data. */
    private List<String> testLines;
    /** Lines from a PageCollector. */
    private ArrayList<String> outList;
    /** Target PageCOLLECTOR. */
    private PageCollector pages;
    /** TARGET PAGEPRINTER. */
    private PagePrinter print;

}
