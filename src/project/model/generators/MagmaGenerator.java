package project.model.generators;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import project.model.*;
import project.model.liquid.Magma;

import static javafx.scene.paint.Color.rgb;

public class MagmaGenerator extends Element implements Immovable, Generator {
    public MagmaGenerator(Coordinates coors) {
        super(coors);
    }

    @Override
    public void applyGravity() {
    }

    @Override
    public Paint getTexture() {
        return rgb(255, 0, 255);
    }

    @Override
    public void generateElements() {
        int size = Worker.getInstance().getSize();
        var tmp = new Coordinates(getX(), getY() + size);
        Worker.getInstance().addElement(tmp, new Magma(tmp));
    }
}