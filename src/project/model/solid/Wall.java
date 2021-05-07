package project.model.solid;

import javafx.scene.paint.Paint;
import project.model.Coordinates;
import project.model.Element;
import project.model.Immovable;

import java.util.Map;

import static javafx.scene.paint.Color.rgb;

public class Wall extends Element implements Immovable {
    public Wall(Coordinates coors) {
        super(coors);
    }

    @Override
    public void applyGravity() {
    }

    @Override
    public Paint getTexture() {
        return rgb(100, 100, 100);
    }
}
