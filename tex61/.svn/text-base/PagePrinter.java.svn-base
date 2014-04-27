package tex61;

import java.io.PrintWriter;
import java.util.ArrayList;

/** A PageAssembler that sends lines immediately to a PrintWriter, with
 *  terminating newlines.
 *  @author Jesus Garcia
 */
class PagePrinter {

    /** A new PagePrinter that sends lines to OUT. */
    PagePrinter(PrintWriter out) {
        _out = out;
    }

    /** Print LINE to my output. */
    void write(String line) {
        if (currentTextHeight == textHeight && line == null) {
            return;
        }
        if (currentTextHeight == textHeight) {
            currentTextHeight = 1;
            pageCount++;
            if (line != null && pageCount != 0) {
                _out.println("\f" + line);
            }
        } else {
            if (line != null) {
                _out.println(line);
                currentTextHeight++;
            } else if (line == null) {
                _out.println("");
                currentTextHeight++;
            }
        }
    }

    /** Print LINE to my output. */
    void write(ArrayList<String> line) {
        for (String x: line) {
            _out.print(x);
        }
    }

    /** SETTEXTHEIGHT TO SET THE TEXTHEIGHT, MAY ONLY BE NEEDED
     * IN PAGEPRINTER TAKES IN TEXTH INT. */
    void setTextHeight(int textH) {
        textHeight = textH;
    }

    /** CURRENT TEXT HEIGHT. */
    private int currentTextHeight = 0;
    /** TEXT HEIGHT VARIABLE TO DETERMINE WHEN TO STOP THE PAGE. */
    private int textHeight = Defaults.TEXT_HEIGHT;
    /** PRIVATE PRINTWRITER, PRINTS THE LINES IMMEDIATELY. */
    private PrintWriter _out;
    /** PRIVATE PAGECOUNT TO COUNT THE NUMBER OF PAGES. */
    private int pageCount = 0;
}
