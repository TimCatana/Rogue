package rogue;

public class NotEnoughDoorsException extends Exception {

    /**
     * Default Constructor.
     */
    public NotEnoughDoorsException() {
        super("Error - dungeon file could not be used (invalid doors)");
    }

} // Class End
