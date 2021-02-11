package rogue;

public class NoSuchItemException extends Exception {

    /**
     * Default Constructor.
     */
    public NoSuchItemException() {
        super("Item does not exist");
    }

} // Class End
