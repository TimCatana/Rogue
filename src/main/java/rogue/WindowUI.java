package rogue;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.TerminalPosition;

import javax.swing.JFrame;
import java.awt.Container;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.io.IOException;

public class WindowUI extends JFrame {

    private SwingTerminal terminal;
    private TerminalScreen screen;
    public static final int WIDTH = 700;
    public static final int HEIGHT = 800;
    // Screen buffer dimensions are different than terminal dimensions
    public static final int COLS = 80;
    public static final int ROWS = 24;
    private final char startCol = 0;
    private final char msgRow = 1;
    private final char roomRow = 3;

    /**
     * Default Constructor.
     */
    public WindowUI() {
        super();
        setWindowDefaults(getContentPane());
        setTerminal(getContentPane());
        pack();
        start();
    }

    /**
     * Set window.
     * @param contentPane
     */
    private void setWindowDefaults(Container contentPane) {
        setTitle("Rogue!");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        contentPane.setLayout(new BorderLayout());
    }

    /**
     * Set terminal.
     * @param contentPane
     */
    private void setTerminal(Container contentPane) {
        terminal = new SwingTerminal();
        contentPane.add(terminal, BorderLayout.CENTER);
    }

    /**
     * start terminal.
     */
    private void start() {
        try {
            screen = new TerminalScreen(terminal);
            //screen = new VirtualScreen(baseScreen);
            screen.setCursorPosition(TerminalPosition.TOP_LEFT_CORNER);
            screen.startScreen();
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints a string to the screen starting at the indicated column and row.
     * @param toDisplay the string to be printed
     * @param column the column in which to start the display
     * @param row the row in which to start the display
     */
    public void putString(String toDisplay, int column, int row) {
        Terminal t = screen.getTerminal();
        try {
            t.setCursorPosition(column, row);

            for (char ch: toDisplay.toCharArray()) {
                t.putCharacter(ch);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Changes the message at the top of the screen for the user.
     * @param msg the message to be displayed
     */
    public void setMessage(String msg) {
        putString("                                                ", 1, 1);
        putString(msg, startCol, msgRow);
    }

    /**
     * clears the terminal.
     * used specifically to prevent overlapping rooms
     */
    public void clearScreen() {
        for (int i = 0; i < ROWS; i++) {
            putString("                                                                              ", 0, i);
        }
    }

    /**
     * Redraws the whole screen including the room and the message.
     * @param message the message to be displayed at the top of the room
     * @param room the room map to be drawn
     */
    public void draw(String message, String room) {

        try {
            clearScreen();
            setMessage(message);
            putString(room, startCol, roomRow);
            screen.refresh();
        } catch (IOException e) {

        }

    }

    /**
     * Obtains input from the user and returns it as a char. Converts arrow
     * keys to the equivalent movement keys in rogue.
     * @return the ascii value of the key pressed by the user
     */
    public char getInput() {
        KeyStroke keyStroke = null;
        char returnChar;

        while (keyStroke == null) {
            try {
                keyStroke = screen.pollInput();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (keyStroke.getKeyType() == KeyType.ArrowDown) {
            returnChar = Rogue.DOWN;
        } else if (keyStroke.getKeyType() == KeyType.ArrowUp) {
            returnChar = Rogue.UP;
        } else if (keyStroke.getKeyType() == KeyType.ArrowLeft) {
            returnChar = Rogue.LEFT;
        } else if (keyStroke.getKeyType() == KeyType.ArrowRight) {
            returnChar = Rogue.RIGHT;
        } else {
            returnChar = keyStroke.getCharacter();
        }
        return returnChar;
    }

    /**
     * The controller method for making the game logic work.
     * @param args command line parameters
     */
    public static void main(String[] args) {
        char userInput = 'h';
        String message;
        String configurationFileLocation = "fileLocations.json";
        String room;

        RogueParser rogueParser = new RogueParser(configurationFileLocation);

        WindowUI theGameUI = new WindowUI();

        Rogue rogue = new Rogue(rogueParser);
        rogue.createRooms();

        Player player = new Player("KraveXL");
        rogue.setPlayer(player);
        message = "Welcome to my Rogue game";

        theGameUI.draw(message, rogue.getNextDisplay());
        theGameUI.setVisible(true);

        while (userInput != 'q') {
            userInput = theGameUI.getInput();

            try {
                message = rogue.makeMove(userInput);
                theGameUI.draw(message, rogue.getNextDisplay());
            } catch (InvalidMoveException badMove) {
                theGameUI.setMessage("lol");
            }
        }

         message = "GoodBye";
         theGameUI.setMessage(message);
         System.exit(0);
    }

} // Class End
