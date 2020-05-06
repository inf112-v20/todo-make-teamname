package inf112.skeleton.app.objects.player;

import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.objects.boardObjects.IBoardObject;

/**
 * The Robot class is an object that simulates the robot on the board. <BR>
 * It moves and acts on the board.
 */
public class Robot implements IBoardObject {

    private final Texture[] textures;
    private int x, y;
    private Direction dir;
    private int health = 9;
    private int respawnX, respawnY;
    private boolean isDestroyed = false;
    private int life = 3;
    private int id;

    /**
     * This constructor takes a {@link Direction} and a list of {@link Texture},<BR>
     * then it creates a robot facing that direction and looking like those textures.
     * @param newDir {@link Direction} Robot is going to face
     * @param textures {@link Texture} How the robot is going to look. The list needs 4 textures,<BR>
     * the first is the the {@link Direction#NORTH} facing texture, second the {@link Direction#EAST},<BR>
     * third {@link Direction#SOUTH} and fourth {@link Direction#WEST}
     */
    public Robot(Direction newDir, Texture[] textures) {
        dir = newDir;
        this.textures = textures;
    }

    /**
     * This constructor takes a list of {@link Texture},<BR>
     * then it creates a robot facing {@link Direction#EAST} and looking like those textures.
     * @param textures {@link Texture} How the robot is going to look. The list needs 4 textures,<BR>
     * the first is the the {@link Direction#NORTH} facing texture, second the {@link Direction#EAST},<BR>
     * third {@link Direction#SOUTH} and fourth {@link Direction#WEST}
     */
    public Robot(Texture[] textures){
        this.dir = Direction.EAST;
        this.textures = textures;
    }

    /**
     *
     * @return Returns the {@link Texture} of the robot depending on what direction it is facing {@link Direction}
     */
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

    /**
     *
     * @return Returns the {@link Direction} of the Robot
     */
    @Override
    public Direction getDirection() {
        return dir;
    }

    /**
     *
     * @param newDir The new {@link Direction} of the Robot
     */
    @Override
    public void setDirection(Direction newDir) {
        dir = newDir;
    }

    /**
     *
     * @return Returns the width position of the Robot on the board, returns -1 if destroyed.
     */
    @Override
    public int getTileX() {
        return x;
    }

    /**
     *
     * @return Returns the height position of the Robot on the board, returns -1 if destroyed.
     */
    @Override
    public int getTileY() {
        return y;
    }

    /**
     *
     * @param newX New width position of the Robot
     */
    @Override
    public void setTileX(int newX) {
        x = newX;
    }

    /**
     *
     * @param newY New height position of the Robot
     */
    @Override
    public void setTileY(int newY) {
        y = newY;
    }

    /**
     *
     * @return Returns the health of the Robot
     */
    public int getHealth(){
        return health;
    }

    /**
     * The Robot looses 1 health
     */
    public void takeDamage(){
        health--;
        if(health <= 0){
            destroy();
        }
    }

    /**
     * The Robot gains one health, as long as it is not on full health
     */
    public void healDamage(){
        if(health < 9) health++;
    }

    /**
     * Sets the robot to full health
     */
    public void fullHealth(){
        health = 9;
    }

    /**
     * Sets the new respawn point
     * @param x The new width coordinate
     * @param y The new height coordinate
     */
    public void setRespawn(int x, int y){
        respawnX = x;
        respawnY = y;
    }

    /**
     *
     * @return Returns the width respawn coordinate
     */
    public int getRespawnX(){
        return respawnX;
    }

    /**
     *
     * @return Returns the height coordinate
     */
    public int getRespawnY(){
        return respawnY;
    }

    /**
     * Rotates the Robot to the right
     */
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

    /**
     * Rotates the Robot to the left
     */
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

    /**
     * Destroys the Robot, sets the {@link #isDestroyed} to true, {@link #health} to 0,<BR>
     *  decreases {@link #life} by one, and removes the robot from the board.
     */
    public void destroy() {
        isDestroyed = true;
        health = 0;
        life--;
        setTileX(-1);
        setTileY(-1);
    }

    /**
     *
     * @return Returns true if the Robot is destroyed
     */
    public boolean isDestroyed() {
        return isDestroyed || getTileX() == -1 || getTileY() == -1;
    }

    /**
     * Respawn the Robot, sets {@link #isDestroyed} to false, heals the Robot to full health, <BR>
     * sets the location coordinates to {@link #respawnX} and {@link #respawnY}.
     */
    public void respawn(){
        isDestroyed = false;
        fullHealth();
        takeDamage();
        takeDamage();
        setTileY(respawnY);
        setTileX(respawnX);
    }

    /**
     *
     * @return Returns the life of the player
     */
    public int getLife(){
        return life;
    }

    /**
     *
     * @return Returns the {@link Direction#EAST} {@link Texture} of the Robot.
     */
    public Texture getNonRotatingTexture() {
        return textures[1];
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId(){
        return id;
    }
}
