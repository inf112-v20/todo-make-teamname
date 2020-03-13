package inf112.skeleton.app.objects.cards;

import com.badlogic.gdx.graphics.Texture;

public interface ICard {

    /**
     * Get the name of the card
     * @return card name
     */
    String getName();

    /**
     *  Get the text of the card
     * @return  Card text
     */
    String getText();

    /**
     *  Get the image of the card
     * @return  Card image
     */
    Texture getImage();

    /**
     *  Get the value of the card
     * @return Card value
     */
    Integer getValue();

    
}
