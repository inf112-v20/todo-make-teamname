package inf112.skeleton.app.objects.cards;

public class Hitbox {
    /*a hitbox will look like this, where numbers 0-3 represents their location in bounds
    * 3------2
    * |      |
    * 0------1
    * */
    private int[][] bounds = new int[4][2];
    public void setBound(int i, int x, int y){
         bounds[i] = new int[]{x, y};
    }

    public int[] getBound(int i){
        return bounds[i];
    }
    public static void main(String[] args) {
        Hitbox h = new Hitbox();
        h.setBound(0, 1,0);
    }
}
