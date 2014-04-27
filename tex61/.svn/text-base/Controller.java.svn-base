package tex61;

import java.io.PrintWriter;
import java.util.ArrayList;

/* import static tex61.FormatException.reportError; */

/** Receives (partial) words and commands, performs commands, and
 *  accumulates and formats words into lines of text, which are sent to a
 *  designated PageAssembler.  At any given time, a Controller has a
 *  current word, which may be added to by addText, a current list of
 *  words that are being accumulated into a line of text, and a list of
 *  lines of endnotes.
 *  @author Jesus Garcia
 */
class Controller {

    /** PRIVATE PAGEPRINTER PRINTS PAGES IMMEDIATELY, TAKES IN A PRINTWRITER. */
    private PagePrinter _pagePrinter;
    /** PAGE COLLECTOR PAGECOLLECTOR THAT COLLECTS LINES AND PAGES. */
    private PageCollector _pageCollector;
    /** THIS REMEMBERS THE AMOUNT OF SPACE THAT IS NEEDED TO BE ADDED BEFORE
     * THE NEXT PARAGRAPH. */
    private int parSave = 0;
    /** BOOLEAN TRUE IF SPACE NEEDS TO BE ADDED BEFORE NEXT PAR, WILL ACTIVATE
     * WHEN FIRST WORD IS BEING DETECTED. */
    private boolean isParSave = false;
     /** THIS IS THE PRINTWRITER FIELD, MIGHT NOT NEED IT. */
    private PrintWriter printWriter;
    /** PREPARES TO ADD REFERENCE TO ENDNOTE. PREPAREREFERENCE. */
    private boolean prepareReference;
    /** SAVEINDENTATION WILL SAVE THE CHANGE IN
     * INDENTATION UNTIL THE NEXT PARAGRAPH. */
    private boolean saveIndentation;
    /** SAVE INDENTATIONVAL VAL INT. */
    private int saveIndentationVal;
    /** PRIVATE BOOLEAN TELLS IF LAST SENTENCE IN PARAGRAPH. */
    private boolean lastSentence;
    /** SAVES INT VALUE OF ENDNOTE PARAGRAPH SKIP. */
    private int endParSave;

    /** A new Controller that sends formatted output to OUT. */
    Controller(PrintWriter out) {
        ArrayList<String> array = new ArrayList<String>();
        PageCollector pageCollector = new PageCollector(array, this);
        PagePrinter pagePrinter = new PagePrinter(out);
        this._pageCollector = pageCollector;
        this._pagePrinter = pagePrinter;
        printWriter = out;
    }

    /** Add TEXT to the end of the word of formatted text currently
     *  being accumulated. */
    void addText(String text) {
        if (currentStringArray.size() == 0) {
            charCount = 0;
        }
        if (isParSave && !_endnoteMode) {
            int par = parSkip;
            while (par > 0) {
                _pagePrinter.write((String) null);
                par--;
            }
            isParSave = false;
        }
        if ((isParSave && _endnoteMode)) {
            int par = endParSkip;
            while (par > 0) {
                _pageCollector.write((String) null);
                par--;
            }
            isParSave = false;
        }
        boolean endNoteWillHappen = false;
        if (prepareReference) {
            endNoteWillHappen = true;
            current += "[" + _refNum + "]" + " ";
            prepareReference = false;
        }
        current += text;
        if (endNoteWillHappen) {
            endWord();
            endNoteWillHappen = false;
        }

    }

    /** Finish any current word of text and, if present, add to the
     *  list of words for the next line.  Has no effect if no unfinished
     *  word is being accumulated. */
    void endWord() {
        if (!(current.equals(""))) {
            if (fill) {
                filler();
            } else {
                currentStringArray.add(current);
                charCount += current.length() + 1;
            }
        }
        current = "";
    }

    /** FILLER METHOD, ADDS CURRENT TO ARRAY LIST IF IT CAN OTHERWISE
     * SENDS IT TO GET JUSTIFIED OR NOT. */
    void filler() {
        int lineWidth = getLineWidth();
        if (!current.equals("")) {
            if ((currentStringArray.size() == 0)
                    && (current.length() >= lineWidth)) {
                _pagePrinter.write(current);
                charCount = 0;
            } else if (charCount + current.length() > lineWidth) {
                if (justify) {
                    justifier();
                } else {
                    noJustify();
                }
                if (!_endnoteMode) {
                    _pagePrinter.write(arrayToString(currentStringArray));
                } else {
                    _pageCollector.write(arrayToString(currentStringArray));
                }
                currentStringArray = new ArrayList<String>();
                charCount = current.length() + 1;
                currentStringArray.add(current);
                current = "";
            } else {
                currentStringArray.add(current);
                charCount += current.length() + 1;
                wordCount++;
            }
        }
    }

    /** NOJUSTIFY CHANGES THE CURRENT STRING LINE ARRAY TO
     *  A NOT JUSTIFIED VERSION OF IT.
     * SINGLE SPACE AFTER EACH WORD EXCEPT LAST ONE */
    void noJustify() {
        int k = 0;
        while (k < currentStringArray.size() - 1) {
            currentStringArray.set(k, currentStringArray.get(k)
                    + indentMaker("space", 1));
            k++;
        }
        currentStringArray.add(0, indentMaker("total", 1));
        if (paragraphOn) {
            setParagraph();
        }
    }

    /** CREATES INDENTS SO YOU DONT HAVE TO, FOR PARAGRAPH,
     * RETURNS STRING TAKES IN A STRING SETTING AND INT SPACES. */
    String indentMaker(String setting, double spaces) {
        String list = "";
        if (setting.equals("total")) {
            int x = endInd;
            if (paragraphOn && !_endnoteMode) {
                x = parIndent + indent;
            } else if (paragraphOn && _endnoteMode) {
                x = endParInd + endInd;
            } else if (!_endnoteMode) {
                x = indent;
            }
            while (x > 0) {
                list += " ";
                x--;
            }
        }
        if (setting.equals("space")) {
            while (spaces != 0) {
                list += " ";
                spaces--;
            }
        }
        return list;
    }

    /** Finish any current word of formatted text and process an end-of-line
     *  according to the current formatting parameters. */
    void addNewline() {
        if (!fill) {
            endWord();
            if (currentStringArray.size() > 0) {
                noJustify();
                if (!_endnoteMode) {
                    _pagePrinter.write(arrayToString(currentStringArray));
                } else {
                    _pageCollector.write(arrayToString(currentStringArray));
                }
            }
            wordCount = 0;
            currentStringArray = new ArrayList<String>();
        }
        endWord();
    }

    /** Finish any current word of formatted text, format and output any
     *  current line of text, and start a new paragraph. */
    void endParagraph() {
        endWord();
        if (currentStringArray.size() > 0) {
            noJustify();
            if (!_endnoteMode) {
                _pagePrinter.write(arrayToString(currentStringArray));
            } else {
                _pageCollector.write(arrayToString(currentStringArray));
            }
            currentStringArray = new ArrayList<String>();
            charCount = 0;
        }
        parSave = parSkip;
        if (_endnoteMode) {
            endParSave = endParSkip;
        }
        if (!_endnoteMode) {
            paragraphOn = true;
            isParSave = true;
            if (saveIndentation) {
                setIndentation(saveIndentationVal);
            }
        }
    }

    /** FINDLINEWIDTH RETURNS INT. */
    int getLineWidth() {
        int lineWidth = 0;
        if (_endnoteMode && paragraphOn) {
            lineWidth = endTextWidth - endParInd - endInd;
        } else if (_endnoteMode && !paragraphOn) {
            lineWidth = endTextWidth - endInd;
        } else if (!_endnoteMode && paragraphOn) {
            lineWidth = textWidth - parIndent - indent;
        } else if (!_endnoteMode && !paragraphOn) {
            lineWidth = textWidth - indent;
        }
        return lineWidth;
    }

    /** JUSTIFIER TAKES ARRAYLIST STRING TURNS IT TO CORRECTLY
     * FILLED ARRAY LIST. */
    void justifier() {
        int lineWidth = getLineWidth();
        int k = 1;
        int n = currentStringArray.size();
        charCount -= currentStringArray.size();
        int b = lineWidth - charCount;
        double thisOne = 0;
        double soFar = 0;
        if (b >= 3 * (n - 1)) {
            if (currentStringArray.size() != 1) {
                thrice();
            }
            currentStringArray.add(0, indentMaker("total", 0));
            if (paragraphOn) {
                setParagraph();
            }
            return;
        }
        while (charCount < lineWidth) {
            double total = Math.floor(.5 + (((double) b
                    * (double) k) / (n - 1)));
            thisOne = total - soFar;
            soFar += thisOne;
            currentStringArray.set(k - 1, currentStringArray.get(k - 1)
                    + indentMaker("space", thisOne));
            charCount += thisOne;
            k++;
        }
        currentStringArray.add(0, indentMaker("total", 0));
        if (paragraphOn) {
            setParagraph();
        }
    }

    /** THIS METHOD WILL TAKE CARE OF THE THREE SPACES AFTER EACH
     * WORD CASE MAX IT WILL BE CALLED THRICE. */
    void thrice() {
        int index = 0;
        for (String x: currentStringArray) {
            if (index != currentStringArray.size() - 1) {
                currentStringArray.set(index, currentStringArray.get(index)
                        + indentMaker("space", 3));
                index++;
            }
        }
    }
    /**ADDLINE ADDS THE ARRAYLIST TO STRING FROM FILLER TO
     * THE PAGECOLLECTOR TAKES IN ARRAYLIST<STRING> LIST RETURNS STRING . */
    String arrayToString(ArrayList<String> list) {
        String line = "";
        for (String d: list) {
            line += d;
        }
        return line;
    }

    /** If valid, process TEXT into an endnote, first appending a reference
     *  to it to the line currently being accumulated. */
    void formatEndnote(String text) {
        boolean saveEndNoteMode = paragraphOn;
        paragraphOn = true;
        _refNum++;
        addText("[" + Integer.toString(_refNum) + "]");
        String savedCurrent = current;
        ArrayList<String> saved = currentStringArray;
        int savedCount = charCount;
        current = "";
        currentStringArray = new ArrayList<String>();
        setEndnoteMode();
        prepareReference = true;
        InputParser parser = new InputParser(text, this);
        parser.process();
        setNormalMode();
        paragraphOn = saveEndNoteMode;
        currentStringArray = saved;
        charCount = savedCount;
        current = savedCurrent;
    }

    /** Set the current text height (number of lines per page) to VAL, if
     *  it is a valid setting.  Ignored when accumulating an endnote. */
    void setTextHeight(int val) {
        _pageCollector.setTextHeight(val);
    }

    /** Set the current text width (width of lines including indentation)
     *  to VAL, if it is a valid setting. */
    void setTextWidth(int val) {
        if (_endnoteMode) {
            endTextWidth = val;
        } else {
            textWidth = val;
        }
    }

    /** Set the current text indentation (number of spaces inserted before
     *  each line of formatted text) to VAL, if it is a valid setting. */
    void setIndentation(int val) {
        if (paragraphOn) {
            if (_endnoteMode) {
                endInd = val;
            } else {
                indent = val;
            }
        } else {
            saveIndentation = true;
            saveIndentationVal = val;
        }
    }

    /** Set the current paragraph indentation (number of spaces inserted before
     *  first line of a paragraph in addition to indentation) to VAL, if it is
     *  a valid setting. */
    void setParIndentation(int val) {
        if (_endnoteMode) {
            endParInd = val;
        } else {
            parIndent = val;
        }
    }

    /** Set the current paragraph skip (number of blank lines inserted before
     *  a new paragraph, if it is not the first on a page) to VAL, if it is
     *  a valid setting. */
    void setParSkip(int val) {
        if (_endnoteMode) {
            endParSkip = val;
        } else {
            parSkip = val;
            parSave = val;
        }
    }

    /** Iff ON, begin filling lines of formatted text. */
    void setFill(boolean on) {
        if (on) {
            this.fill = true;
        } else {
            this.fill = false;
        }
    }

    /** Iff ON, begin justifying lines of formatted text whenever filling is
     *  also on. */
    void setJustify(boolean on) {
        if (on) {
            this.justify = true;
        } else {
            this.justify = false;
        }
    }

    /** GETPAGEPRINTER RETURNS PAGEPRINTER. */
    PagePrinter getPagePrinter() {
        return _pagePrinter;
    }

    /** Finish the current formatted document or endnote (depending on mode).
     *  Formats and outputs all pending text. */
    void close() {
        endParagraph();
        if (!_endnoteMode) {
            writeEndnotes();
        }
    }

    /** Start directing all formatted text to the endnote assembler. */
    private void setEndnoteMode() {
        _endnoteMode = true;
    }

    /** Return to directing all formatted text to _mainText. */
    private void setNormalMode() {
        _endnoteMode = false;
    }

    /** Write all accumulated endnotes to _mainText. */
    private void writeEndnotes() {
        for (String x: _pageCollector.getArray()) {
            _pagePrinter.write(x);
        }
    }

    /** TURNS SET PARAGRAPH ON OR OFF, TRUE OR FALSE, returns BOOLEAN. */
    private boolean setParagraph() {
        if (paragraphOn) {
            paragraphOn = false;
            return true;
        }
        return false;
    }

    /** PARAGRAPGH MODE IS ON, INDENT THE LINE WITH PARAGRAPH INDENTATION. */
    private boolean paragraphOn = true;

    /** PRIVATE CLASS VARIABLE, BOOLEAN JUSTIFY, IF JUSTIFY ON OR OFF. */
    private boolean justify = true;

    /** PRIVATE CLASS VARIABLE, BOOLEAN FILL, IF FILL ON OR OFF. */
    private boolean fill = true;

    /** True iff we are currently processing an endnote. */
    private boolean _endnoteMode = false;

    /** Number of next endnote. */
    private int _refNum = 0;

    /** CURRENT WORD THAT WE ARE ON STRING. */
    private String current = "";

    /** Current string that you are on. CALLED CURRENT, STRING TYPE. */
    private ArrayList<String> currentStringArray = new ArrayList<String>();

    /** CURRENT LINE'S NUMBER OF WORDS INT WORDCOUNT. */
    private int wordCount = 0;

    /** CURRENT LINE'S CHARACTER COUNT FOR USE WITH TEXTWIDTH, INT. */
    private int charCount = 0;
    /** PRIVATE CLASS VARIABLE TEXTWIDTH THAT DETERMINES THE WIDTH OF A LINE. */
    private int textWidth = Defaults.TEXT_WIDTH;
    /** PRIVATE CLASS VARIABLE INDENT, THE INDENT OF A LINE, INT. */
    private int indent = Defaults.INDENTATION;
    /** PRIVATE CLASS VARIABLE PARINDENT, PARAGRAPH INT INDENT. */
    private int parIndent = Defaults.PARAGRAPH_INDENTATION;
    /** PRIVATE CLASS VARIABLE, INT PARSKIP, NUM OF LINES TO
     * SKIP PER PARAGRAPH. */
    private int parSkip = Defaults.PARAGRAPH_SKIP;


    /** Default setting for \parskip in endnotes. */
    private int endParSkip = Defaults.ENDNOTE_PARAGRAPH_SKIP;
    /** Setting for \indent in endnotes. */
    private int endInd = Defaults.ENDNOTE_INDENTATION;
    /** Setting for \parindent in endnotes. */
    private int endParInd =
            Defaults.ENDNOTE_PARAGRAPH_INDENTATION;
    /** Setting for \textwidth in endnotes. */
    private int endTextWidth = Defaults.ENDNOTE_TEXT_WIDTH;
}
