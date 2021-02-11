package rogue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RogueParser {

    private ArrayList<Map<String, String>> items = new ArrayList<>();
    private ArrayList<Map<String, String>> loot = new ArrayList<>();
    private ArrayList<Map<String, String>> rooms = new ArrayList<>();
    private ArrayList<Map<String, String>> doors = new ArrayList<>();
    private HashMap<String, Character> symbolsMap = new HashMap<>();

    private Iterator<Map<String, String>> roomIterator;
    private Iterator<Map<String, String>> lootIterator;
    private Iterator<Map<String, String>> doorsIterator;

    private int numOfRooms = -1;
    private int numOfItems = -1;

    /**
     * Default constructor.
     */
    public RogueParser() {

    }

    /**
     * Constructor that takes filename and parses everything.
     * @param filename (String) name of file that contains file location for rooms and symbols
     */
    public RogueParser(String filename) {
        parse(filename);
    }

    /**
     * Return the next room.
     * @return (Map) Information about a room
     */
    public Map nextRoom() {
        if (roomIterator.hasNext()) {
            return roomIterator.next();
        } else {
            return null;
        }
    }

    /**
     * Return the next rooms doors.
     * @return (Map) Information about a room's doors
     */
    public Map nextRoomDoors() {
        if (doorsIterator.hasNext()) {
            return doorsIterator.next();
        } else {
            return null;
        }
    }

    /**
     * Returns the next item.
     * @return (Map) Information about an item
     */
    public Map nextLoot() {
        if (lootIterator.hasNext()) {
            return lootIterator.next();
        } else {
            return null;
        }
    }

    /**
     *  Returns the symbols map.
     *  @return (HashMap) Display characters for all objects that are displayed
     */
    public HashMap<String, Character> getSymbolMap() {
        return symbolsMap;
    }

    /**
     * Read the file containing the file locations.
     * @param filename (String) Name of the file
     */
    private void parse(String filename) {
        JSONParser parser = new JSONParser();
        JSONObject roomsJSON;
        JSONObject symbolsJSON;
        try {
            Object obj = parser.parse(new FileReader(filename));
            JSONObject configurationJSON = (JSONObject) obj;

            String roomsFileLocation = (String) configurationJSON.get("Rooms");
            String symbolsFileLocation = (String) configurationJSON.get("Symbols");

            Object roomsObj = parser.parse(new FileReader(roomsFileLocation));
            roomsJSON = (JSONObject) roomsObj;
            Object symbolsObj = parser.parse(new FileReader(symbolsFileLocation));
            symbolsJSON = (JSONObject) symbolsObj;

            extractItemInfo(roomsJSON);
            extractSymbolInfo(symbolsJSON);
            extractRoomInfo(roomsJSON); // Includes setting up loot
            extractDoorInfo(roomsJSON);

            roomIterator = rooms.iterator();
            lootIterator = loot.iterator();
            doorsIterator = doors.iterator();

        } catch (FileNotFoundException e) {
            System.out.println("Cannot find file named: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("Error parsing JSON file");
        }
    }

    /**
     * Get the Item information from the Item key.
     * @param roomsJSON (JSONObject) The entire JSON file that contains keys for room and items
     */
    private void extractItemInfo(JSONObject roomsJSON) {
        JSONArray itemsJSONArray = (JSONArray) roomsJSON.get("items");

        for (int i = 0; i < itemsJSONArray.size(); i++) {
            items.add(singleItem((JSONObject) itemsJSONArray.get(i)));
            numOfItems += 1;
        }
    }

    /**
     * Get a single item from its JSON object.
     * @param itemsJSON (JSONObject) JSON version of an item
     * @return (Map<String, String>) Contains information about a single item
     */
    private Map<String, String> singleItem(JSONObject itemsJSON) {
        HashMap<String, String> item = new HashMap<>();
        item.put("id", itemsJSON.get("id").toString());
        item.put("name", itemsJSON.get("name").toString());
        item.put("type", itemsJSON.get("type").toString());
        item.put("description", itemsJSON.get("description").toString());

        return item;
    }

    /**
     * Get the symbol information.
     * @param symbolsJSON (JSONObject) Contains information about the symbols
     */
    private void extractSymbolInfo(JSONObject symbolsJSON) {
        JSONArray symbolsJSONArray = (JSONArray) symbolsJSON.get("symbols");

        for (Object curSymbol : symbolsJSONArray) {
            JSONObject symbolObj = (JSONObject) curSymbol;
            symbolsMap.put(symbolObj.get("name").toString(), String.valueOf(symbolObj.get("symbol")).charAt(0));
        }
    }

    /**
     * Get the room information.
     * @param roomsJSON (JSONObject) Contains information about the rooms
     */
    private void extractRoomInfo(JSONObject roomsJSON) {
        JSONArray roomsJSONArray = (JSONArray) roomsJSON.get("room");

        for (Object curRoom : roomsJSONArray) {
            rooms.add(singleRoom((JSONObject) curRoom));
            numOfRooms += 1;
        }
    }

    /**
     * Get a room's information.
     * @param roomJSON (JSONObject) Contains information about one room
     * @return (Map<String, String>) Contains key/values that has information about the room
     */
    private Map<String, String> singleRoom(JSONObject roomJSON) {
        HashMap<String, String> room = new HashMap<>();
        room.put("id", roomJSON.get("id").toString());
        room.put("start", roomJSON.get("start").toString());
        room.put("height", roomJSON.get("height").toString());
        room.put("width", roomJSON.get("width").toString());

        JSONArray lootArray = (JSONArray) roomJSON.get("loot");
        for (int j = 0; j < lootArray.size(); j++) {
            loot.add(singleLoot((JSONObject) lootArray.get(j), roomJSON.get("id").toString()));
        }

        return room;
    }

    /**
     * Create a map for information about an item in a room.
     * @param lootJSON (JSONObject) Loot key from the rooms file
     * @param roomID (String) Room id value
     * @return (Map<String, String>) Contains information about the item, where it is and what room
     */
    private Map<String, String> singleLoot(JSONObject lootJSON, String roomID) {
        HashMap<String, String> lootInfo = new HashMap<>();

        lootInfo.put("room", roomID);
        lootInfo.put("id", lootJSON.get("id").toString());
        lootInfo.put("x", lootJSON.get("x").toString());
        lootInfo.put("y", lootJSON.get("y").toString());

        for (Map<String, String> item : items) {
            if (lootInfo.get("id").toString().equals(item.get("id").toString())) {
                lootInfo.put("name", item.get("name"));
                lootInfo.put("type", item.get("type"));
                lootInfo.put("description", item.get("description"));
                break;
            } else {
                lootInfo.put("name", "invalid");
                lootInfo.put("type", "invalid");
                lootInfo.put("description", "invalid");
            }
        }

        return lootInfo;
    }

    /**
     * Get the doors information.
     * @param roomsJSON (JSONObject) Contains information about the rooms
     */
    private void extractDoorInfo(JSONObject roomsJSON) {
        JSONArray roomsJSONArray = (JSONArray) roomsJSON.get("room");

        for (int i = 0; i < roomsJSONArray.size(); i++) {
            doors.add(singleRoomDoors((JSONObject) roomsJSONArray.get(i)));
        }
    }

    /**
     * Get a information about a room's doors.
     * @param roomJSON (JSONObject) Contains information about one room
     * @return (Map<String, String>) Contains key/values that has information about the a room's doors
     */
    private Map<String, String> singleRoomDoors(JSONObject roomJSON) {
        HashMap<String, String> roomDoors = new HashMap<>();

        roomDoors.put("room", roomJSON.get("id").toString());

        // Cheap way of making sure all 4 directions have a sentinel value in the map
        roomDoors.put("E", "-1");
        roomDoors.put("N", "-1");
        roomDoors.put("S", "-1");
        roomDoors.put("W", "-1");

        roomDoors.put("Econ", "-1");
        roomDoors.put("Ncon", "-1");
        roomDoors.put("Scon", "-1");
        roomDoors.put("Wcon", "-1");

        // Update the map with any doors in the room
        JSONArray doorArray = (JSONArray) roomJSON.get("doors");
        for (int j = 0; j < doorArray.size(); j++) {
            JSONObject doorObj = (JSONObject) doorArray.get(j);
            String dir = String.valueOf(doorObj.get("dir"));
            roomDoors.replace(dir, doorObj.get("wall_pos").toString());
            roomDoors.replace(dir + "con", doorObj.get("con_room").toString());
        }

        return roomDoors;
    }

} // Class End
