package rogue;

import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Point;

public class Room  {

    private Player player;
    private boolean isPlayerInRoom;
    private int width;
    private int height;
    private int id;

    private ArrayList<Item> items = new ArrayList<Item>();
    private HashMap<String, Door> doorMap = new HashMap<String, Door>(); // 1 door per direction NSEW
    private HashMap<String, Character> symbols = new HashMap<String, Character>(); // the symbols from symbols.json

    /**
     * Default Constructor.
     */
    public Room() {

    }

    /**
     * Returns the player in the room.
     * @return (Player) the player in room if player is in room
     *         (null) null if player is not in room
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the player in the room (only if player is currently in room).
     * @param newPlayer (Player) the player in the room if player is in room
     *                  (null) null if player is not in room
     */
    public void setPlayer(Player newPlayer) {
        player = newPlayer;
    }

    /**
     * Populates the symbols map with display characters.
     * @param newSymbols (HashMap<String, Character>) the symbols map containing the
     *                   display characters parsed from symbols json
     */
    public void setSymbolMap(HashMap<String, Character> newSymbols) {
        symbols = newSymbols;
    }

    /**
     * Returns the symbol map.
     * @return (HashMap<String, Character>) the symbols map containing the
     *         display characters parsed from symbols json
     */
    public HashMap<String, Character> getSymbolMap() {
        return symbols;
    }

    /**
     * Returns the display symbol of specified object.
     * @param name (String) the key of the symbols hashmap
     * @return (Character) value of the hashmap at given key [ie name]
     */
    public Character getSymbol(String name) {
        return symbols.get(name);
    }

    /**
     * Returns the width of the room.
     * @return (int) the width of the room
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the width of the room.
     * @param newWidth (int) the width of the room
     */
    public void setWidth(int newWidth) {
        width = newWidth;
    }

    /**
     * Returns the height of the room.
     * @return (int) the height of the room
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the height of the room.
     * @param newHeight (int) the height of the room
     */
    public void setHeight(int newHeight) {
        height = newHeight;
    }

    /**
     * Returns the id of the room.
     * @return (int) the id of the room
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the room.
     * @param newId (int) the id of the room
     */
    public void setId(int newId) {
        id = newId;
    }

    /**
     * Return a door of a speicfic wall in this room.
     * @param direction (String) one of "N", "S", "E", or "W"
     *                  signifying Top, Bottom, Right and Left walls respectively
     * @return (door) integer signifying its position on the desired wall
     *         integer signifies number of columns from left for "N" and "S"
     *         integer signifies number of rows from top for "E" and "W"
     */
    public Door getDoor(String direction) {
        return doorMap.get(direction);
    }

    /**
     * Add a new door to the hashmap of doors.
     * @param direction (String) one of "N", "S", "E", or "W"
     *                  signifying Top, Bottom, Right and Left walls respectively
     * @param door (door) the door at that given direction
     */
    public void setDoor(String direction, Door door) {
        doorMap.put(direction, door);
    }

    /**
     * Sets a boolean value to determine whether the player is in the room or not.
     * @param newIsPlayerInRoom (boolean) true = player is in room
     *                           false = player is not in room
     */
    public void setIsPlayerInRoom(boolean newIsPlayerInRoom) {
        isPlayerInRoom = newIsPlayerInRoom;
    }

    /**
     * Returns whether the player is in the room or not.
     * @return (boolean) true = player is in room
     *                   false = player is not in room
     */
    public boolean isPlayerInRoom() {
        return isPlayerInRoom;
    }

    /**
     * Returns the array list of items in the room.
     * @return (ArrayList<Item>) the array list of items in the room.
     */
    public ArrayList<Item> getRoomItems() {
        return items;
    }

    /**
     * Adds an item to the list of items.
     * @param toAdd (Item) the item to add into the room
     * @throws NoSuchItemException the item id is invalid
     * @throws ImpossiblePositionException the item is not within a valid position in the room
     */
    public void addItem(Item toAdd) throws NoSuchItemException, ImpossiblePositionException {
        validateItemLoc(toAdd);

        if (toAdd.getType().equals("invalid")) { // Remember, type is capitalized due to symbols map
            throw new NoSuchItemException();
        }

        items.add(toAdd);
    }

    /**
     * Validates the item location.
     * checks if item is in a wall or out of the room
     * checks to see if two items have the same location in the room
     * @param anItem (Item) the item we are verifying
     * @throws ImpossiblePositionException the item is not in a valid location in the room
     */
    public void validateItemLoc(Item anItem) throws ImpossiblePositionException {
        if (anItem.getXyLocation().getX() <= 0 || anItem.getXyLocation().getY() <= 0) {
            throw new ImpossiblePositionException();
        }

        if (anItem.getXyLocation().getX() >= width - 1 || anItem.getXyLocation().getY() >= height) {
            throw new ImpossiblePositionException();
        }

        if (isPlayerInRoom()) {
            if (anItem.getXyLocation().equals(player.getXyLocation())) {
                throw new ImpossiblePositionException();
            }
        }

        for (Item curItem : items) {
            if (anItem.getXyLocation().equals(curItem.getXyLocation())) {
                throw new ImpossiblePositionException();
            }
        }
    }

    /**
     * Checks to see if a room is set up correctly.
     * Will always return true since the program will exit the program if room is unfixable
     * this method is used mostly for fixing up a room rather than "verifying"
     * @param room (Room) the room that is to be verified
     * @throws NotEnoughDoorsException the current room has no doors
     * @throws UnconnectedDoorException to current room has a door(s) that is not connected to another room
     * @return true = room is correctly set up
     *         false = room is incorrectly setup
     */
    public boolean verifyRoom(Room room) throws NotEnoughDoorsException, UnconnectedDoorException {
        // Items are by necessity not in incorrect positions (see addItem method)
        // Player is by necessity not in incorrect position (see MakeMove method)
        int doorCount = 0;
        String[] directions = {"N", "S", "E", "W"};

        for (String curDir : directions) {
            if (doorMap.get(curDir) != null) {
                doorCount++;

                String oppDir = doorMap.get(curDir).getOppositeDirection(curDir);
                Room oppRoom = doorMap.get(curDir).getOtherRoom(room);

                if (oppRoom.getDoor(oppDir) == null) {
                    throw new UnconnectedDoorException();
                }
            }
        }

        if (doorCount == 0) {
            throw new NotEnoughDoorsException();
        }

        return true;
    }

    /**
     * Build the basic layout of the room that consists only of the floor and walls.
     * @param buildRoom (StringBuilder) an EMPTY string
     * @return (StringBuilder) buildRoom
     */
    public StringBuilder buildRoomSkel(StringBuilder buildRoom) {
        Point curPoint = new Point();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                curPoint.setLocation(i, j);

                if (i == 0 || i == height - 1) { //if top or bottom
                    buildRoom.append(getSymbol("NS_WALL"));
                } else if (j == 0 || j == width - 1) { //if left or right
                    buildRoom.append(getSymbol("EW_WALL"));
                } else { //anything in the middle
                    buildRoom.append(getSymbol("FLOOR"));
                }
            }

            buildRoom.append("\n");
        }

        return buildRoom;
    }

    /**
     * Build the doors of the room.
     * @param buildDoors (StringBuilder) the stringBuilder that CONTAINS THE CONTENTS RETURNED FROM buildRoomSkel
     * @return (StringBuilder) buildDoors
     */
    public StringBuilder buildDoors(StringBuilder buildDoors) { // TODO - might need to check if door is null
        int position;

        if (doorMap.get("N") != null) {
            position = getDoor("N").getPosition();
            buildDoors.setCharAt(position, getSymbol("DOOR"));
        }
        if (doorMap.get("S") != null) {
            position = ((height - 1) * (width + 1)) + (getDoor("S").getPosition());
            buildDoors.setCharAt(position, getSymbol("DOOR"));
        }
        if (doorMap.get("W") != null) {
            position = (getDoor("W").getPosition() * (width + 1));
            buildDoors.setCharAt(position, getSymbol("DOOR"));
        }
        if (doorMap.get("E") != null) {
            position = ((getDoor("E").getPosition() + 1) * (width + 1)) - (2);
            buildDoors.setCharAt(position, getSymbol("DOOR"));
        }

        return (buildDoors);

    }

    /**
     * Place the player in the room if he is currently in the room.
     * @param buildPlayer (StringBuilder) the stringBuilder that CONTAINS THE CONTENTS RETURNED FROM buildDoors
     * @return (StringBuilder) buildPlayer
     */
    public StringBuilder buildPlayer(StringBuilder buildPlayer) {
        int x = (int) player.getXyLocation().getX();
        int y = (int) player.getXyLocation().getY();
        buildPlayer.setCharAt((y * (width + 1)) + x, getSymbol("PLAYER"));

        return (buildPlayer);
    }

    /**
     * Build the items of the room.
     * @param buildItems (StringBuilder) the stringBuilder that CONTAINS THE CONTENTS RETURNED FROM buildPlayer
     * @return (StringBuilder) buildItems
     */
    public StringBuilder buildItems(StringBuilder buildItems) {
        int plrX = (int) player.getXyLocation().getX();
        int plrY = (int) player.getXyLocation().getY();
        Item remItem = null;

        for (Item curItem : items) {
            if (curItem.getXyLocation().equals(player.getXyLocation())) {
                remItem = curItem;
            } else {
                int x = (int) curItem.getXyLocation().getX();
                int y = (int) curItem.getXyLocation().getY();
                String type = curItem.getType().toUpperCase();
                buildItems.setCharAt((y * (width + 1)) + x, getSymbol(type)); // width + 1 to account for the \n's
            }
        }

        items.remove(remItem);
        return (buildItems);
    }

    /**
     * Creates a string that represents the room.
     * @return (String) the string representing this room
     */
    public String displayRoom() {
        String finalRoomString = "";
        StringBuilder buildRoom = new StringBuilder();

        buildRoom = buildRoomSkel(buildRoom);
        buildRoom = buildDoors(buildRoom);
        if (isPlayerInRoom()) {
            buildRoom = buildPlayer(buildRoom);
        }
        buildRoom = buildItems(buildRoom);

        finalRoomString += buildRoom;
        return finalRoomString;
    }

} //Class End
