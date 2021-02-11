package rogue;

import java.awt.Point;

public class Item  {

    private int id;
    private String name;
    private String desc;
    private String type;
    private Character displayChar;
    private Point location;
    private Room curRoom;

    /**
     * Default Constructor.
     */
    public Item() {

    }

    /**
     * Constructor used to create a new item with initialized id, name, type and location.
     * @param newId (int) the id of the item.
     * @param newName (String) the name of the item.
     * @param newType (String) the item type [eg 'weapon'].
     * @param newDescription (String) the description of the item.
     */
    public Item(int newId, String newName, String newType, String newDescription) {
        setId(newId);
        setName(newName);
        setType(newType);
        setDescription(newDescription);
    }

    /**
     * Returns the id of the item.
     * @return (int) the id of the item
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the item.
     * @param idNew (int) the id of the item
     */
    public void setId(int idNew) {
        id = idNew;
    }


    /**
     * Returns the full name of the item.
     * @return (String) the full name of this item
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the item.
     * @param nameNew (String) the full name of the item
     */
    public void setName(String nameNew) {
        name = nameNew;
    }


    /**
     * Returns the type name of the item [eg 'weapon'].
     * @return (String) the type name of the item [eg 'weapon']
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of this item [eg 'weapon'].
     * @param typeNew (String) the type of this [eg 'weapon']
     */
    public void setType(String typeNew) {
        type = typeNew;
    }


    /**
     * Returns the display character of the item [eg '*'].
     * @return (Character) the display character of the item [eg '*']
     */
    public Character getDisplayCharacter() {
        return displayChar;
    }

    /**
     * Sets the display character of this item [eg '*'].
     * @param newDisplayCharacter (Character) the display character of the item [eg '*']
     */
    public void setDisplayCharacter(Character newDisplayCharacter) {
        displayChar = newDisplayCharacter;
    }

    /**
     * Returns the description of the item [ie name, effects, etc...].
     * @return (String) the description of the item [ie name, effects, etc...]
     */
    public String getDescription() {
        return desc;
    }

    /**
     * Sets the description character of the item [ie name, effects, etc...].
     * @param newDescription (String) the description of the item [ie name, effects, etc...]
     */
    public void setDescription(String newDescription) {
        desc = newDescription;
    }


    /**
     * Returns the location of the of the item.
     * @return (Point) the location of this item [as a Point (x,y)]
     */
    public Point getXyLocation() {
        return location;
    }

    /**
     * Sets the location of the of the item.
     * @param newXyLocation (Point) the location of the of this item [as a Point (x,y)]
     */
    public void setXyLocation(Point newXyLocation) {
        location = newXyLocation;
    }


    /**
     * Returns the room in which the item is located.
     * @return (Room) the room in which the item is located
     */
    public Room getCurrentRoom() {
        return curRoom;
    }

    /**
     * Sets the room in which the item is located.
     * @param newCurrentRoom (Room)the room in which the item is located.
     */
    public void setCurrentRoom(Room newCurrentRoom) {
        curRoom = newCurrentRoom;
    }

} // Class End
