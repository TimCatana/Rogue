package rogue;

import java.awt.Point;

public class Player {

    private String name;
    private Point location;
    private Room curRoom;

    /**
     * Default Constructor.
     */
    public Player() {

    }

    /**
     * Constructor used to create a new player with a name.
     * @param newName (String) the name of the player
     */
    public Player(String newName) {
        setName(newName);
    }

    /**
     * Returns the full name of the player.
     * @return (String) the full name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the player.
     * @param newName (String) the full name of the player
     */
    public void setName(String newName) {
        name = newName;
    }

    /**
     * Returns the location of the player.
     * @return (Point) the location of the player
     */
    public Point getXyLocation() {
        return location;
    }

    /**
     * Sets the location of the of the player.
     * @param newXyLocation (Point) the location of the of the player
     */
    public void setXyLocation(Point newXyLocation) {
        location = newXyLocation;
    }

    /**
     * Gets the room in which the player is currently located.
     * @return (Room) the room in which the player is currently located
     */
    public Room getCurrentRoom() {
        return curRoom;
    }

    /**
     * Sets the room in which the player is currently located.
     * @param newRoom (Room) the room in which the player is currently located.
     */
    public void setCurrentRoom(Room newRoom) {
        curRoom = newRoom;
    }

} // Class End
