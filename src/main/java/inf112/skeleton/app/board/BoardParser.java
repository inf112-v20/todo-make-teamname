package inf112.skeleton.app.board;

import inf112.skeleton.app.objects.boardObjects.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BoardParser {

    /**
     * Parses a text file name given into a Board object with
     * the values in the text file.
     * @param boardName Name of the textfile (without .txt)
     * @return The Board object that corresponts to the textfile
     */
    public static Board parse(String boardName) {
        try {
            File map = new File("assets/maps/" + boardName.toLowerCase() + ".txt");
            Scanner sc = new Scanner(map);
            while (sc.hasNextLine()) {
                String size = sc.nextLine();
                String [] dimensions = size.split(",");
                int height = Integer.parseInt(dimensions[0]);
                int width = Integer.parseInt(dimensions[1]);
                int flags = Integer.parseInt(dimensions[2]);
                Board board = new Board(width, height, flags);

                String [] itemLayer = new String [height];

                for (int i=0; i < height; i++) {
                    String row = sc.nextLine();
                    itemLayer[i] = row;
                }
                addLayer(board, itemLayer);

                String skip = sc.nextLine();

                for (int i=0; i < height; i++) {
                    String row = sc.nextLine();
                    itemLayer[i] = row;
                }

                addLayer(board, itemLayer);

                skip = sc.nextLine();

                for (int i=0; i < height; i++) {
                    String row = sc.nextLine();
                    itemLayer[i] = row;
                }
                addLayer(board, itemLayer);

                return board;
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("ERROR: File " + boardName + " not found");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Adds a layer of objects. Puts at most 1 item at each tile.
     * @param board The board to add items to
     * @param map The string array representing the layer
     */
    private static void addLayer(Board board, String [] map) {
        for (int y=0; y < map.length; y++) {
            for (int x=0; x < map[y].length(); x++) {
                IBoardObject object = factory(map[y].charAt(x));
                if (object != null) {
                    board.addObject(object, x, map.length - y -1);
                }
            }
        }
    }

    /**
     * A factory that parses characters into corresponding
     * IBoardObjects.
     * @param object what char to parse
     * @return the object requested
     */
    public static IBoardObject factory(char object) {
        switch(object) {
            case 'c':
                return new GearCounterClockwise();
            case 'C':
                return new GearClockwise();
            case 'w':
                return new ConveyorBelt(false, Direction.WEST);
            case 'W':
                return new ConveyorBelt(true, Direction.WEST);
            case 'n':
                return new ConveyorBelt(false, Direction.NORTH);
            case 'N':
                return new ConveyorBelt(true, Direction.NORTH);
            case 'e':
                return new ConveyorBelt(false, Direction.EAST);
            case 'E':
                return new ConveyorBelt(true, Direction.EAST);
            case 's':
                return new ConveyorBelt(false, Direction.SOUTH);
            case 'S':
                return new ConveyorBelt(true, Direction.SOUTH);
            case 'R':
                return new RepairSite(true);
            case 'r':
                return new RepairSite(false);
            case 'P':
                return new Pit();
            case 'y':
                return new Wall(Direction.NORTH);
            case 'h':
                return new Wall(Direction.SOUTH);
            case 'j':
                return new Wall(Direction.EAST);
            case 'g':
                return new Wall(Direction.WEST);
            case 't':
                return new Wall(Direction.NORTHWEST);
            case 'u':
                return new Wall(Direction.NORTHEAST);
            case 'b':
                return new Wall(Direction.SOUTHWEST);
            case 'm':
                return new Wall(Direction.SOUTHEAST);
            case 'L':
                return new BoardLaser();
            case '-':
                return null;
            default:
                return new Flag(Character.getNumericValue(object));
        }
    }
}