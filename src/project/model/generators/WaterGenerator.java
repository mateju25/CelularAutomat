package project.model.generators;

import javafx.scene.paint.Color;
import project.model.*;
import project.model.liquid.Water;

import static javafx.scene.paint.Color.rgb;

public class WaterGenerator extends Element implements Immovable, Generator {
    public WaterGenerator(Coordinates coors) {
        super(coors);
    }

    @Override
    public void applyGravity() {
    }

    @Override
    public float[] getTexture() {
        return new float[]{0.83f, 1, 1};
    }

    @Override
    public void generateElements() {
        int size = Worker.getInstance().getSize();
        var tmp = new Coordinates(getX(), getY() + size);
        Worker.getInstance().addElement(tmp, new Water(tmp));
    }
}
