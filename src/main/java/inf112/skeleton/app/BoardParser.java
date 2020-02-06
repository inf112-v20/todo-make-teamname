package inf112.skeleton.app;

import inf112.skeleton.app.objects.GearClockwise;
import inf112.skeleton.app.objects.GearCounterClockwise;
import inf112.skeleton.app.objects.IBoardObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BoardParser {

    public static Board parse(String boardName) {
        try {
            File map = new File("assets/maps/" + boardName.toLowerCase() + ".txt");
            Scanner sc = new Scanner(map);
            while (sc.hasNextLine()) {
                String size = sc.nextLine();
                String [] dimensions = size.split(",");
                int height = Integer.parseInt(dimensions[0]);
                int width = Integer.parseInt(dimensions[1]);
                Board board = new Board(width, height);

                String [] itemLayer = new String [height];

                for (int i=0; i < height; i++) {
                    String row = sc.nextLine();
                    itemLayer[i] = row;
                }
                addLayer(board, itemLayer, 0);
                return board;
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("ERROR: File " + boardName + " not found");
            e.printStackTrace();
        }
        return null;
    }

    private static void addLayer(Board board, String [] map, int layer) {
        for (int y=0; y < map.length; y++) {
            for (int x=0; x < map[0].length(); x++) {
                IBoardObject object = factory(map[y].charAt(x));
                if (object != null) {
                    board.addObject(object, x, y);
                }
            }
        }
    }

    private static IBoardObject factory(char object) {
        switch(object) {
            case 'c':
                return new GearCounterClockwise();
            case 'C':
                return new GearClockwise();
            default:
                return null;
        }
    }
}
