package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.app.objects.IBoardObject;
import inf112.skeleton.app.objects.Robot;

import java.util.ArrayList;

public class BoardTile {
    private int x, y;
    private int itemSize = 0;
    private Texture sprite;

    //Array for the items on this tile
    private IBoardObject[] items = {null, null, null, null};


    public BoardTile(int x, int y) {
        this.x = x;
        this.y = y;
        sprite = new Texture("assets/Cell_Empty.png");

    }

    //TODO Make a version that doesn't give null pointer when testing
    public Texture getSprite() {
        return sprite;
    }

    //Returns all the textures for all the objects on this tile
    public ArrayList<Texture> getTextures(){
        ArrayList<Texture> textures = new ArrayList<>();
        textures.add(getSprite());
        for (IBoardObject i : items) if (i != null) textures.add(i.getTexture());
        return textures;
    }


    //Adds a  object on this tile, only works for robots atm
    public void add(IBoardObject object) {
        //TODO fix for all IBoardObjects
        if(object instanceof Robot) items[1] = object;
    }



    //Removes a object
    public IBoardObject remove(int objectNr) {
        IBoardObject object = items[objectNr];
        items[objectNr] = null;
        return object;
    }
}
