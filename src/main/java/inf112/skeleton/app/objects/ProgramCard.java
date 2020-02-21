package inf112.skeleton.app.objects;

import com.badlogic.gdx.graphics.Texture;

public class ProgramCard implements ICard{
    String name;
    String text;
    Texture image;

    public ProgramCard(){

    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public Texture getImage() {
        return this.image;
    }

    @Override
    public Integer getValue() {
        return null;
    }
    // Flips the texture of the card between front and back
    public void flip(){

    }
}
