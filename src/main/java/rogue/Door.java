
package rogue;

import java.util.ArrayList;

public class Door  {

    private ArrayList<Room> connectedRooms = new ArrayList<Room>();
    private int position;
    private String direction;

    /**
     * Default Constructor.
     */
    public Door() {

    }

    /**
     * Constructor that sets direction and position.
     * @param newDirection (String) one of "N", "S", "E", "W"
     * @param newPosition (int) a number between 0 and current room width for "N" and
     *                    "S" and current room height for "W" and "E"
     */
    public Door(String newDirection, int newPosition) {
        direction = newDirection;
        position = newPosition;
    }

    /**
     * Set the wall that the door is located on.
     * @param newDirection (String) one of "N", "S", "E", "W"
     */
    public void setDirection(String newDirection) {
        direction = newDirection;
    }

    /**
     * Returns the wall that the door is located o.
     * @return (String) one of "N", "S", "E", "W"
     */
    public String getDirection() {
        return direction;
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
     * Sets the position of the door on the wall.
     * @param newPosition (int) a number between 0 and current room width for "N" and "S"
     *                    and current room height for "W" and "E"
     */
    public void setPosition(int newPosition) {
        position = newPosition;
    }

    /**
     * Returns the position of the door on the wall.
     * @return (int) a number between 0 and current room width for "N" and "S"
     *         and current room height for "W" and "E"
     */
     public int getPosition() {
        return position;
    }

    /**
     * Connect a room to the door.
     * @param r (Room) a room to be connected to the door
     */
    public void connectRoom(Room r) {
        connectedRooms.add(r);
    }

    /**
     * returns the arrayList containing the TWO rooms that are connected to this door.
     * @return (ArrayList<Room>) The arrayList of TWO rooms that are connected to this door
     */
     public ArrayList<Room> getConnectedRooms() {
         return connectedRooms;
     }

    /**
     * Returns the other room that is connected to this door.
     * i.e. I am in the kitchen, this returns the dining room
     * @param currentRoom (Room) one of the rooms connected to this door
     * @return (Room) the other room that is attached to this door or
     *         null if no other room is connected (indicating a possible
     *         unconnected door which is dealt with in another place)
     */
      public Room getOtherRoom(Room currentRoom) {
          for (Room room : connectedRooms) {
              if (room != currentRoom) {
                  return room;
              }
          }
          return null;
      }

} // Class End
