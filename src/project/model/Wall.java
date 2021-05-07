package project.model;

import javafx.scene.paint.Paint;

import static javafx.scene.paint.Color.rgb;

public class Wall extends Element implements Immovable{
    public Wall(Coordinates coors) {
        super(coors);
    }

    @Override
    public void applyGravity(Element[][] itemMap) {
    }

    @Override
    public Paint getTexture() {
        return rgb(100, 100, 100);
    }
}
