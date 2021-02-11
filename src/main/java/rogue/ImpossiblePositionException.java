package rogue;

public class ImpossiblePositionException extends Exception {

    /**
     * Default Constructor.
     */
    public ImpossiblePositionException() {
        super("Item not in a valid position");
    }

} // Class End
