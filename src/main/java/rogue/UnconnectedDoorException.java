package rogue;

public class UnconnectedDoorException extends Exception {

    /**
     * Default Constructor.
     */
    public UnconnectedDoorException() {
        super("Error - dungeon file could not be used (invalid doors)");
    }

} // Class End
