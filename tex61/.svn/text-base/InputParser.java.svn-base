package tex61;

import static tex61.FormatException.reportError;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

/**
 * Reads commands and text from an input source and send the results to a
 * designated Controller. This essentially breaks the input down into
 * "tokens"---commands and pieces of text.
 *
 * @author Jesus Garcia
 */
class InputParser {

    /**
     * Matches text between { } in a command, including the last }, but not the
     * opening {. When matched, group 1 is the matched text. Always matches at
     * least one character against a non-empty string or input source. If it
     * matches and group 1 is null, the argument was not well-formed (the final
     * } was missing or the argument list was nested too deeply).
     */
    private static final Pattern BALANCED_TEXT = Pattern
        .compile("(?s)((?:\\\\.|[^\\\\{}]" + "|[{](?:\\\\.|[^\\\\{}])*[}])*)"
            + "\\}" + "|.");

    /**
     * Matches input to the text formatter. Always matches something in a
     * non-empty string or input source. After matching, one or more of the
     * groups described by *_TOKEN declarations will be non-null. See these
     * declarations for descriptions of what this pattern matches. To test
     * whether .group(*_TOKEN) is null quickly, check for .end(*_TOKEN) > -1).
     */
    private static final Pattern INPUT_PATTERN = Pattern
        .compile("(?s)(\\p{Blank}+)" + "|(\\r?\\n((?:\\r?\\n)+)?)"
            + "|\\\\([\\p{Blank}{}\\\\])" + "|\\\\(\\p{Alpha}+)([{]?)"
            + "|((?:[^\\p{Blank}\\r\\n\\\\{}]+))" + "|(.)");

    /** Symbolic names for the groups in INPUT_PATTERN. */
    private static final int
        /** Blank or tab. */
        BLANK_TOKEN = 1,
        /** End of line or paragraph. */
        EOL_TOKEN = 2,
        /**
         * End of paragraph (>1 newline). EOL_TOKEN group will also be present.
         */
        EOP_TOKEN = 3,
        /**
         * \{, \}, \\, or \ . .group(ESCAPED_CHAR_TOKEN) will be the
         * character after
         * the backslash.
         */
        ESCAPED_CHAR_TOKEN = 4,
        /**
         * Command (\<alphabetic characters>). .group(COMMAND_TOKEN) will be the
         * characters after the backslash.
         */
        COMMAND_TOKEN = 5,
        /**
         * A '{' immediately following a command. When this group is present,
         * .group(COMMAND_TOKEN) will also be present.
         */
        COMMAND_ARG_TOKEN = 6,
        /**
         * Segment of other text (none of the above, not including any of the
         * special characters \, {, or }).
         */
        TEXT_TOKEN = 7,
        /** A character that should not be here. */
        ERROR_TOKEN = 8;

    /**
     * A new InputParser taking input from READER and sending tokens to OUT.
     */
    InputParser(Reader reader, Controller out) {
        _input = new Scanner(reader);
        _out = out;
    }

    /**
     * A new InputParser whose input is TEXT and that sends tokens to OUT.
     */
    InputParser(String text, Controller out) {
        _input = new Scanner(text);
        _out = out;
    }

    /**
     * Break all input source text into tokens, and send them to our output
     * controller. Finishes by calling .close on the controller.
     */
    void process() {
        Pattern x = INPUT_PATTERN;
        while (_input.findWithinHorizon(x, 0) != null) {
            MatchResult result = _input.match();
            String real = result.toString();
            if (result.groupCount() > 0) {
                int i = 1;
                for (; i <= 8; i++) {
                    if (result.end(i) > -1) {
                        String string = result.group(i);
                        getWords().add(string);
                        if (i == 2 && result.end(EOP_TOKEN) > -1) {
                            handleToken(i + 1, result.group(i));
                        } else if (i == COMMAND_TOKEN
                            && !result.group(6).equals("")) {
                            handleToken(i + 1, result.group(i));
                        } else if (i == COMMAND_TOKEN) {
                            handleToken(i, result.group(i));
                        } else {
                            if (i != 5) {
                                int token = i;
                                handleToken(token, result.group(i));
                            }
                        }
                    }
                }
            }
        }
        this._out.close();
    }

    /**
     * THIS IS A METHOD THAT IS DESIGNED TO TAKE A TOKEN INPUT AND THE STRING.
     * INPUT CALLED TOKEN AND SEGMENT VOID.
     */
    private void handleToken(int token, String segment) {
        if (token == BLANK_TOKEN) {
            _out.endWord();
        }
        if (token == EOL_TOKEN) {
            _out.addNewline();
        }
        if (token == EOP_TOKEN) {
            _out.endParagraph();
        }
        if (token == ESCAPED_CHAR_TOKEN) {
            _out.addText(segment);
        }
        if (token == COMMAND_TOKEN) {
            processCommand(segment, "nothing");
        }
        if (token == COMMAND_ARG_TOKEN) {
            if (segment.length() > 1) {
                if (_input.findWithinHorizon(BALANCED_TEXT, 0) == null) {
                    throw new FormatException("notbalancedtext");
                }
                String argument = _input.match().group(1);
                processCommand(segment, argument);
            }
        }
        if (token == TEXT_TOKEN) {
            _out.addText(segment);
        }
        if (token == ERROR_TOKEN) {
            throw new FormatException("errorToken");
        }
    }

    /**
     * CHECKS THAT THE ARGUMENT IS A POSITIVE NUMBER, RETURNS BOOLEAN TAKES IN
     * STR STRING.
     */
    public static boolean isPositiveDigit(String str) {
        if (str == null) {
            reportError("error command has to be a number and positive: %s",
                "command");
            System.exit(1);
        }
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    /**
     * THIS METHOD IS TO HANDLE CHECKING IF ARG IS A POSITIVE INT, TAKES IN
     * STRING RETURNS INT TAKES IN STRING COMMAND.
     */
    private int intParse(String arg, String command) {
        int intArg = 0;
        if (isPositiveDigit(arg) || arg == null) {
            intArg = Integer.parseInt(arg);
            if ((!command.equals("parindent") && intArg < 0)) {
                reportError("error command has to be a number and positive: %s",
                    command);
                System.exit(1);
            }
        } else {
            reportError("error command has to be a number and positive: %s",
                command);
            System.exit(1);
        }
        return intArg;
    }

    /**
     * Process \COMMAND{ARG} or (if ARG is null) \COMMAND. Call the appropriate
     * methods in our Controller (_out).
     */
    private void processCommand(String command, String arg) {
        try {
            switch (command) {
            case "indent":
                _out.setIndentation(intParse(arg, command));
                break;
            case "parindent":
                _out.setParIndentation(intParse(arg, command));
                break;
            case "textwidth":
                _out.setTextWidth(intParse(arg, command));
                break;
            case "textheight":
                _out.setTextHeight(intParse(arg, command));
                break;
            case "parskip":
                _out.setParSkip(intParse(arg, command));
                break;
            case "nofill":
                _out.setFill(false);
                break;
            case "fill":
                _out.setFill(true);
                break;
            case "justify":
                _out.setJustify(true);
                break;
            case "nojustify":
                _out.setJustify(false);
                break;
            case "endnote":
                _out.formatEndnote(arg);
                break;
            case " ":
                _out.addText(" ");
                break;
            default:
                reportError("error unknown command: %s", command);
                System.exit(1);
                break;
            }
        } catch (FormatException e) {
            reportError("error Not balanced command", e);
        }
    }

    /** GETTERMETHOD FOR WORDS RETURNS ARRAYLIST<STRING>. */
    public ArrayList<String> getWords() {
        return words;
    }

    /** SETTERMETHOD FOR WORDS TAKES IN ARRAYLIST<STRING> WORDS1. */
    public void setWords(ArrayList<String> words1) {
        this.words = words1;
    }

    /** My input source. */
    private final Scanner _input;
    /** The Controller to which I send input tokens. */
    private Controller _out;
    /** ALL THE STRING THAT GETS MATCHED FROM PROCESS FOR TESTING. */
    private ArrayList<String> words = new ArrayList<String>();
}
