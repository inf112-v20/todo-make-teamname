package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Texture;

public class BoardTile {
    private int x, y;
    private int itemSize = 0;
    private Texture sprite;

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


    public void add(IBoardObject object) {
        items[itemSize] = object;
        itemSize++;
        sort();
    }

    private void sort() {
        int n = itemSize;
        for (int i = 1; i < n; ++i) {
            IBoardObject key = items[i];
            int j = i - 1;

            /* Move elements of items[0..i-1], that are
               greater than key, to one position ahead
               of their current position */
            while (j >= 0 && items[j].getSize() > key.getSize()) {
                items[j + 1] = items[j];
                j = j - 1;
            }
            items[j + 1] = key;
        }
    }

    public IBoardObject remove() {
        if(itemSize == 0) return null;
        IBoardObject object = items[--itemSize];
        items[itemSize] = null;
        return object;
    }
}
