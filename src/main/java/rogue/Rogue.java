package rogue;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.awt.Point;

public class Rogue {

    public static final char UP = 'w';
    public static final char DOWN = 's';
    public static final char LEFT = 'a';
    public static final char RIGHT = 'd';

    private RogueParser rogueParser = new RogueParser();
    private ArrayList<Room> rogueRoomsArr = new ArrayList<Room>();
    private ArrayList<Item> rogueItemsArr = new ArrayList<Item>();

    private Player player = new Player("KraveXL");
    private int plrX = 1;
    private int plrY = 1;

    private Room nextDisplay = new Room();

    /**
     * Default ConStructor.
     */
    public Rogue() {

    }

    /**
     * Constructor with rogue contents.
     * @param theDungeonInfo the rogue parser containing the parsed JSON contents
     */
    public Rogue(RogueParser theDungeonInfo) {
        rogueParser = theDungeonInfo;
    }

    /**
     * Sets the player.
     * @param thePlayer (Player) the player
     */
    public void setPlayer(Player thePlayer) {
        player = thePlayer;
    }

    /**
     * Returns the player.
     * @return (Player) the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * set the player location.
     * @param x (int) player x coordinate
     * @param y (int) player y coordinate
     */
    public void setPlayerLoc(int x, int y) {
        Point plrLoc = new Point();
        plrLoc.setLocation(x, y);
        player.setXyLocation(plrLoc);
    }

    /**
     * Returns the array list containing all of the rooms.
     * Used mostly for testing purposes.
     * @return (ArrayList<Room>) the array list containing all of the rooms
     */
    public ArrayList<Room> getRooms() {
        return rogueRoomsArr;
    }

      /**
     * Returns the array list containing all of the items.
     * Used mostly for testing purposes.
     * @return (ArrayList<Item>) the array list containing all of the items
     */
    public ArrayList<Item> getItems() {
        return rogueItemsArr;
    }

    /**
     * Sets the next room to be displayed.
     * @param newNextDisplay (Room) the next room to be displayed
     */
    public void setNextDisplay(Room newNextDisplay) {
        nextDisplay = newNextDisplay;
    }

    /**
     * Determines the next move of the player.
     * Likewise determines the new room to display.
     * @param input (char) a character inputted by the user.
     * @throws InvalidMoveException the player is attempting to move to an invalid
     *                              location in the room.
     * @return (String) the next message to print out into the terminal
     */
    public String makeMove(char input) throws InvalidMoveException {

        if (input == UP) {
            if (plrY - 1 <= 0) {
                moveRoomsNS();
            }
            plrY -= 1;
            setPlayerLoc(plrX, plrY);
        } else if (input == DOWN) {
            if (plrY + 1 >= (nextDisplay.getHeight() - 1)) {
                moveRoomsSN();
            } else {
                plrY += 1;
            }
            setPlayerLoc(plrX, plrY);
        } else if (input == LEFT) {
            if (plrX - 1 <= 0) {
                moveRoomsWE();
            } else {
               plrX -= 1;
            }
            setPlayerLoc(plrX, plrY);
        } else if (input == RIGHT) {
            if (plrX + 1 >= (nextDisplay.getWidth() - 1)) {
                moveRoomsEW();
            } else {
                plrX += 1;
            }
            setPlayerLoc(plrX, plrY);
        } else {
            throw new InvalidMoveException();
        }

        nextDisplay.setPlayer(player);
        return "you moved";
    }

    /**
     * Moves the player into the next room.
     * transports player from room A door "N" to room B door "S"
     * (North to South door)
     * @throws InvalidMoveException the player is attempting to move to an invalid
     *                              location in the room.
     */
    public void moveRoomsNS() throws InvalidMoveException {
        if (nextDisplay.getDoor("N") != null) {
            if (plrX == nextDisplay.getDoor("N").getPosition()) {
                nextDisplay.setIsPlayerInRoom(false);
                nextDisplay = nextDisplay.getDoor("N").getOtherRoom(nextDisplay);
                nextDisplay.setIsPlayerInRoom(true);
                plrY = nextDisplay.getHeight() - 1;
                plrX = nextDisplay.getDoor("S").getPosition();
            } else {
                throw new InvalidMoveException();
            }
        } else {
            throw new InvalidMoveException();
        }

    }

    /**
     * Moves the player into the next room.
     * transports player from room A door "S" to room B door "N".
     * (South to North door)
     * @throws InvalidMoveException the player is attempting to move to an invalid
     *                              location in the room.
     */
    public void moveRoomsSN() throws InvalidMoveException {
        if (nextDisplay.getDoor("S") != null) {
            if (plrX == nextDisplay.getDoor("S").getPosition()) {
                nextDisplay.setIsPlayerInRoom(false);
                nextDisplay = nextDisplay.getDoor("S").getOtherRoom(nextDisplay);
                nextDisplay.setIsPlayerInRoom(true);
                plrY = 1;
                plrX = nextDisplay.getDoor("N").getPosition();
            } else {
                throw new InvalidMoveException();
            }
        } else {
            throw new InvalidMoveException();
        }

    }

    /**
     * Moves the player into the next room.
     * transports player from room A door "E" to room B door "W".
     * (East to West door)
     * @throws InvalidMoveException the player is attempting to move to an invalid
     *                              location in the room.
     */
    public void moveRoomsEW() throws InvalidMoveException {
        if (nextDisplay.getDoor("E") != null) {
            if (plrY == nextDisplay.getDoor("E").getPosition()) {
                nextDisplay.setIsPlayerInRoom(false);
                nextDisplay = nextDisplay.getDoor("E").getOtherRoom(nextDisplay);
                nextDisplay.setIsPlayerInRoom(true);
                plrY = nextDisplay.getDoor("W").getPosition();
                plrX = 1;
            } else {
                throw new InvalidMoveException();
            }
        } else {
            throw new InvalidMoveException();
        }

    }

    /**
     * Moves the player into the next room.
     * transports player from room A door "W" to room B door "E".
     * (West to East door)
     * @throws InvalidMoveException the player is attempting to move to an invalid
     *                              location in the room.
     */
    public void moveRoomsWE() throws InvalidMoveException {
        // North to South
        if (nextDisplay.getDoor("W") != null) {
            if (plrY == nextDisplay.getDoor("W").getPosition()) {
                nextDisplay.setIsPlayerInRoom(false);
                nextDisplay = nextDisplay.getDoor("W").getOtherRoom(nextDisplay);
                nextDisplay.setIsPlayerInRoom(true);
                plrY = nextDisplay.getDoor("E").getPosition();
                plrX = nextDisplay.getWidth() - 2;
            } else {
                throw new InvalidMoveException();
            }
        } else {
            throw new InvalidMoveException();
        }

    }

    /**
     * Creates the rogue rooms ONE AT A TIME.
     * Creates room layout first
     * Then places items into room after
     * FInally add doors to the rooms
     * Checks if rooms are valid, and fixes them if they are not
     */
    public void createRooms() {
        setPlayerLoc(plrX, plrY);

        // Rooms
        Map<String, String> curRoomContents = rogueParser.nextRoom();
        while (curRoomContents != null) {
            addRoom(curRoomContents);
            curRoomContents = rogueParser.nextRoom();
        }

        // Items
        Map<String, String> curLootContents = rogueParser.nextLoot();
        while (curLootContents != null) {
            addItem(curLootContents);
            curLootContents = rogueParser.nextLoot();
        }

        // Doors
        Map<String, String> curRoomDoorsContents = rogueParser.nextRoomDoors();
        while (curRoomDoorsContents != null) {
            addDoors(curRoomDoorsContents);
            curRoomDoorsContents = rogueParser.nextRoomDoors();
        }

        // Verification
        for (Room aRoom : rogueRoomsArr) {
            if (!verifyRogueRoom(aRoom)) {
                rogueRoomsArr.remove(aRoom);
            }
        }

        // Set player location
        for (Room curRoom : rogueRoomsArr) {
            if (curRoom.isPlayerInRoom()) {
                nextDisplay = curRoom;
                break;
            }
        }

    }

    /**
     *  Verifies a room to make sure it has a valid setup.
     *  If the room is not valid, attempt to fix it
     *  NotEnoughDoorsException: implement a door if possible, else exit game
     *  UnconnectedDoorException: Remove the unconnected door from the room
     *  @param aRoom (Room) the room that is being verified
     *  @return (boolean) whether the room is valid or not
     *          (it should always return true)
     */
    public boolean verifyRogueRoom(Room aRoom) {
        try {
            aRoom.verifyRoom(aRoom);
        } catch (NotEnoughDoorsException e) {
            boolean fixed = setNewDoors(aRoom);

            if (!fixed) {
                System.out.println(e.getMessage());
                System.exit(0);
            }
        } catch (UnconnectedDoorException e) {
            String[] directions = {"N", "S", "E", "W"};

            for (String curDir : directions) {
                if (aRoom.getDoor(curDir) != null) {
                    String oppDir = aRoom.getDoor(curDir).getOppositeDirection(curDir);
                    Room oppRoom = aRoom.getDoor(curDir).getOtherRoom(aRoom);

                    if (oppRoom.getDoor(oppDir) == null) {
                        aRoom.setDoor(curDir, null);
                        break;
                    }
                }
            }
        }

        return true;
    }

    /**
     *  Sets the new doors in an attempt to fix NotEnoughDoorsException .
     *  @param aRoom (Room) the room that has no doors
     *  @return (boolean) a boolean indicating whether the room was fixed or not
    */
    public boolean setNewDoors(Room aRoom) {
        boolean fixed = false;
        String[] directions = {"N", "S", "W", "E"};

            for (Room curRoom : rogueRoomsArr) {
                if (aRoom != curRoom) {
                    for (String curDir : directions) {
                        if (curRoom.getDoor(curDir) == null) {
                            String oppDir = getOppositeDirection(curDir);
                            implmentNewDoors(curRoom, aRoom, curDir, oppDir);
                            fixed = true;
                            break;
                        }
                    }
                }

                if (fixed) {
                    return fixed;
                }
            }

        return fixed;
    }

    /**
     * Returns the wall which the connected door is located.
     * @param dir (String) one of "N", "S", "E", "W"
     * @return (String) opposite direction of dir param (e.g. if "N", then "S")
     */
    public String getOppositeDirection(String dir) {
        if (dir == "N") {
            return "S";
        } else if (dir == "S") {
            return "N";
        } else if (dir == "E") {
            return "W";
        } else if (dir == "W") {
            return "E";
        } else {
            return null;
        }
    }

    /**
     *  Create and implement two randomly located rooms into the designated rooms and walls.
     *  @param roomA (Room) the room with an extra wall available
     *  @param roomB (Room) the room with currently no doors
     *  @param dirA (String) the wall in which the roomA door will be placed (opposite dirB)
     *  @param dirB (String) the wall in which the rooB door will be placed (opposite dirA)
     */
    public void implmentNewDoors(Room roomA, Room roomB, String dirA, String dirB) {
        Random randomLoc = new Random();
        int doorALoc = 0;
        int doorBLoc = 0;
        Door roomADoor = new Door();
        Door roomBDoor = new Door();

        roomADoor.connectRoom(roomA);
        roomADoor.connectRoom(roomB);
        roomBDoor.connectRoom(roomA);
        roomBDoor.connectRoom(roomB);

        roomADoor.setDirection(dirA);
        roomBDoor.setDirection(dirB);

        if (dirA.equals("N") || dirA.equals("S")) { // it's either NS or EW
            while (doorALoc <= 0 || doorBLoc <= 0 || doorALoc >= roomA.getWidth() || doorBLoc >= roomB.getWidth()) {
                doorALoc = randomLoc.nextInt(roomA.getWidth() - 1);
                doorBLoc = randomLoc.nextInt(roomB.getWidth() - 1);
            }
        } else {
            while (doorALoc <= 0 || doorBLoc <= 0 || doorALoc >= roomA.getHeight() || doorBLoc >= roomB.getHeight()) {
                doorALoc = randomLoc.nextInt(roomA.getHeight() - 1);
                doorBLoc = randomLoc.nextInt(roomB.getHeight() - 1);
            }
        }

        roomADoor.setPosition(doorALoc);
        roomBDoor.setPosition(doorBLoc);

        roomA.setDoor(dirA, roomADoor);
        roomB.setDoor(dirB, roomBDoor);
    }

    /**
     * Creates a single room.
     * @param toAdd (Map<String, String>) contains the contents/description of the room to be added
     */
    public void addRoom(Map<String, String> toAdd) {
        Room aRoom = new Room();

        aRoom.setSymbolMap(rogueParser.getSymbolMap());

        Integer aRoomId = Integer.decode(toAdd.get("id").toString());
        Integer aRoomwidth = Integer.decode(toAdd.get("width").toString());
        Integer aRoomHeight = Integer.decode(toAdd.get("height").toString());
        Boolean aRoomStart = Boolean.valueOf(toAdd.get("start").toString());

        aRoom.setId(aRoomId);
        aRoom.setWidth(aRoomwidth);
        aRoom.setHeight(aRoomHeight);
        aRoom.setIsPlayerInRoom(aRoomStart);

        if (aRoomStart) {
            aRoom.setPlayer(player);
        }

        rogueRoomsArr.add(aRoom);
    }

    /**
     * Creates a single item.
     * @param toAdd (Map<String, String>) contains the contents/description of the item to be added
     */
    public void addItem(Map<String, String> toAdd) {
        boolean added = false;

        Integer itemRoom = Integer.decode(toAdd.get("room"));
        Integer itemId = Integer.decode(toAdd.get("id"));
        String itemName = toAdd.get("name");
        String itemType = toAdd.get("type");
        String itemDesc = toAdd.get("description");

        Integer itemX = Integer.decode(toAdd.get("x").toString());
        Integer itemY = Integer.decode(toAdd.get("y").toString());
        Point anItemLoc = new Point();

        Item anItem = new Item(itemId, itemName, itemType, itemDesc);

        while (!added) {
            try {
                anItemLoc.setLocation(itemX, itemY);
                anItem.setXyLocation(anItemLoc);
                anItem.setCurrentRoom(rogueRoomsArr.get(itemRoom - 1));
                rogueRoomsArr.get(itemRoom - 1).addItem(anItem);
                added = true;
                rogueItemsArr.add(anItem); // For A2 submission... but I don't use it
            } catch (ImpossiblePositionException e) {
                Random randomLoc = new Random();
                itemX = randomLoc.nextInt(rogueRoomsArr.get(itemRoom - 1).getWidth() - 1);
                itemY = randomLoc.nextInt(rogueRoomsArr.get(itemRoom - 1).getHeight() - 1);
            } catch (NoSuchItemException e) {
                added = true;
            }
        }
    }

    /** Creates doors for a single room.
     *  @param toAdd (Map<String, String>) contains the contents/description of the doors in from a room
     *               including door positions and door connections
     */
    public void addDoors(Map<String, String> toAdd) {
        Integer room = Integer.decode(toAdd.get("room"));
        String[] directions = {"N", "S", "E", "W"};

         for (String curDir : directions) {
            Integer doorPos = Integer.decode(toAdd.get(curDir));
            if (curDir == "N" || curDir == "S") {
                if ((doorPos != -1) && (doorPos <= 0 || doorPos >= rogueRoomsArr.get(room - 1).getWidth())) {
                    doorPos = rogueRoomsArr.get(room - 1).getWidth() / 2;
                }
            }

             if (curDir == "E" || curDir == "W") {
                if ((doorPos != -1) && (doorPos <= 0 || doorPos >= rogueRoomsArr.get(room - 1).getHeight())) {
                    doorPos = rogueRoomsArr.get(room - 1).getHeight() / 2;
                }
            }

            if (doorPos != -1) {
                Integer conRoom = Integer.decode(toAdd.get(curDir + "con").toString());
                Door curDoor = new Door(curDir, doorPos);
                curDoor.connectRoom(rogueRoomsArr.get(room - 1)); // -1 for correct index
                curDoor.connectRoom(rogueRoomsArr.get(conRoom - 1)); // -1 for correct index
                rogueRoomsArr.get(room - 1).setDoor(curDir, curDoor);
            } else {
                rogueRoomsArr.get(room - 1).setDoor(curDir, null); // no door on this wall
            }
         }

    }

    /**
     * returns a string that represents the next room that is to be displayed.
     * @return (String) the next room that is to be displayed.
     */
    public String getNextDisplay() {
        return nextDisplay.displayRoom();
    }

} // Class End
