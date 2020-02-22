package inf112.skeleton.app.objects;

import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.app.Direction;

public class RepairSite implements IBoardObject{
    private boolean hammer;

    public RepairSite(boolean hammer){
        //if it is a repair site with wrench and hammer, hammer = true, if not hammer = false.
        this.hammer = hammer;
    }

    @Override
    public Texture getTexture() {
        return null;
    }

    @Override
    public void setTexture() {

    }

    @Override
    public Direction getDirection() {
        return null;
    }

    @Override
    public void setDirection(Direction d) {

    }

    @Override
    public int getTileX() {
        return 0;
    }

    @Override
    public int getTileY() {
        return 0;
    }

    @Override
    public void setTileX(int x) {

    }

    @Override
    public void setTileY(int y) {

    }

    public boolean getHammer() {
        return hammer;
    }
}
