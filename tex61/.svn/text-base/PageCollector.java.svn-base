package tex61;

import java.util.ArrayList;

/** A PageAssembler that collects its lines into a designated List.
 *  @author Jesus Garcia
 */
class PageCollector {

    /** PRIVATE CONTROLLER CONTROLLER TO GET PAGE PRINTER. */
    private Controller controller;
    /** A new PageCollector that stores lines in OUT and the controller
     * FINE. */
    PageCollector(ArrayList<String> out, Controller fine) {
        page = out;
        controller = fine;
    }

    /** Add NONBLANK LINE to my List. */
    void write(String line) {
        if (page.size() < textHeight) {
            page.add(line);
        } else {
            controller.getPagePrinter().write(page);
            page = new ArrayList<String>();
        }
    }

    /** Add LINE to the current page, starting a new page with it if
     *  the previous page is full. A null LINE indicates a skipped line,
     *  and has no effect at the top of a page. */
    void addBlankLine(String line) {
        if (line == null) {
            if (!(page.size() == 0) && (page.size() < textHeight)) {
                page.add("");
            } else if (page.size() == textHeight) {
                controller.getPagePrinter().write(page);
                page = new ArrayList<String>();
            } else {
                page.add(line);
            }
        }
    }

    /** PUBLIC RETURNS ARRAYLIST<STRING> GETARRAY. */
    public ArrayList<String> getArray() {
        return page;
    }
/** SETTEXTHEIGHT TO SET THE TEXTHEIGHT, MAY ONLY BE NEEDED
 * IN PAGEPRINTER TAKES IN TEXTH. */
    void setTextHeight(int textH) {
        textHeight = textH;
    }

    /** PRIVATE ARRAY FOR ENDNOTES HOLDS ALL LINES,
     * THEN SENDS TO PRINTER CLOSING. */
    private ArrayList<String> page;
    /** TEXT HEIGHT VARIABLE TO DETERMINE WHEN TO STOP THE PAGE. */
    private int textHeight = Defaults.TEXT_HEIGHT;
}
