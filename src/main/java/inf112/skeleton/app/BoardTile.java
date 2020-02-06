package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class BoardTile {
    private int x, y;
    private int itemSize = 0;
    private Texture sprite;

    //
    private IBoardObject [] items = {null, null, null, null}; //Change for pq?


    public BoardTile(int x, int y) {
        this.x = x;
        this.y = y;
        sprite = new Texture("assets/Cell_Empty.png");

    }
    //TODO Make a version that doesn't give null pointer when testing
    public Texture getSprite() {
        return sprite;
    }

    public ArrayList<Texture> getTextures(){
        ArrayList<Texture> textures = new ArrayList<>();
        textures.add(getSprite());
        for (IBoardObject i : items) if (i != null) textures.add(i.getTexture());
        return textures;
    }



    public void add(IBoardObject object) {
        items[itemSize] = object;
        itemSize++;
    }




    public IBoardObject remove() {
        if(itemSize == 0) return null;
        IBoardObject object = items[--itemSize];
        items[itemSize] = null;
        return object;
    }
}
