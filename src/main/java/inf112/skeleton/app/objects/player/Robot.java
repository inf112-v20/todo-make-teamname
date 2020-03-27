package inf112.skeleton.app.objects.player;

import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.objects.boardObjects.IBoardObject;

public class Robot implements IBoardObject {

    private final Texture[] textures;
    private int x, y;
    private Direction dir;
    private Texture sprite;
    private int health = 9;
    private int respawnX, respawnY;
    private boolean isDestroyed = false;
    private int life = 3;


    public Robot(Direction newDir, Texture[] textures) {
        dir = newDir;
        this.textures = textures;
    }
    public Robot(Texture[] textures){
        this.dir = Direction.EAST;
        this.textures = textures;
    }

    public Texture getTexture() {
        switch (dir){
            case NORTH:
                return textures[0];
            case SOUTH:
                return textures[2];
            case WEST:
                return textures[3];
            default:
                return textures[1];
        }

    }




    @Override
    public Direction getDirection() {
        return dir;
    }

    @Override
    public void setDirection(Direction newDir) {
        dir = newDir;
    }

    @Override
    public int getTileX() {
        return x;
    }

    @Override
    public int getTileY() {
        return y;
    }

    @Override
    public void setTileX(int newX) {
        x = newX;
    }

    @Override
    public void setTileY(int newY) {
        y = newY;
    }

    public int getHealth(){
        return health;
    }

    public void takeDamage(){
        health--;
        if(health <= 0){
            destroy();
        }
    }

    public void healDamage(){
        health++;
    }
    public void fullHealth(){
        health = 9;
    }
    public void setRespawn(int x, int y){
        respawnX = x;
        respawnY = y;
    }
    public int getRespawnX(){
        return respawnX;
    }
    public int getRespawnY(){
        return respawnY;
    }

    public void rotateRight() {
        switch (dir){
            case WEST:
                dir = Direction.NORTH;
                break;
            case SOUTH:
                dir = Direction.WEST;
                break;
            case EAST:
                dir = Direction.SOUTH;
                break;
            case NORTH:
                dir = Direction.EAST;
                break;
            default:
                System.out.println("Wrong input");
        }
    }

    public void rotateLeft() {
        switch (dir){
            case WEST:
                dir = Direction.SOUTH;
                break;
            case SOUTH:
                dir = Direction.EAST;
                break;
            case EAST:
                dir = Direction.NORTH;
                break;
            case NORTH:
                dir = Direction.WEST;
                break;
            default:
                System.out.println("Wrong input");

        }
    }

    public void destroy() {
        isDestroyed = true;
        health = 0;
        life--;
        setTileX(-1);
        setTileY(-1);
    }

    public boolean isDestroyed() {
        return isDestroyed || getTileX() == -1 || getTileY() == -1;
    }

    public void respawn(){
        isDestroyed = false;
        fullHealth();
        setTileY(respawnY);
        setTileX(respawnX);
    }
    public int getLife(){
        return life;
    }

    public float getRotation() {
        switch (this.getDirection()){
            case NORTH:
                return 90;
            case WEST:
                return 180;
            case EAST:
                return 0;
            case SOUTH:
                return 270;
        }
        return 0;
    }
}
