package inf112.skeleton.app.board;

import inf112.skeleton.app.objects.boardObjects.BoardLaser;
import inf112.skeleton.app.objects.boardObjects.IBoardObject;
import inf112.skeleton.app.objects.boardObjects.Wall;
import inf112.skeleton.app.objects.player.Robot;


public class BoardTile {

    public final static int BOTTOMLAYER = 0; //CONVEYORBELT
    public final static int WALLLAYER = 1; //CONVEYORBELT
    public final static int ROBOT = 2;
    public final static int LASERLAYER = 3;
    private int x, y;

    //Array for the items on this tile
    private IBoardObject[] items = {null, null, null, null};


    public BoardTile() {
    }


    public BoardTile(int x, int y) {
        this.x = x;
        this.y = y;
    }
    //Adds a  object on this tile, only works for robots atm
    public void add(IBoardObject object) {
        if(object instanceof Robot) items[ROBOT] = object;
        else if(object instanceof Wall) items[WALLLAYER] = object;
        else if(object instanceof BoardLaser) items[LASERLAYER] = object;
        else items[BOTTOMLAYER] = object;
    }

    //Removes a object
    public IBoardObject remove(int objectNr) {
        IBoardObject object = items[objectNr];
        items[objectNr] = null;
        return object;
    }

    //returns list of all objects on a tile
    public IBoardObject[] getObjects(){return this.items;}
}
