package project.model.generators;

import javafx.scene.paint.Paint;
import project.model.Coordinates;
import project.model.Element;
import project.model.Immovable;
import project.model.Worker;
import project.model.liquid.Water;

import static javafx.scene.paint.Color.rgb;

public class VoidGenerator extends Element implements Immovable, Generator {
    public VoidGenerator(Coordinates coors) {
        super(coors);
    }

    @Override
    public void applyGravity() {
    }

    @Override
    public Paint getTexture() {
        return rgb(0, 54, 76);
    }

    @Override
    public void generateElements() {
        int size = Worker.getInstance().getSize();
        for (int i = getX() - size; i < getX() + size; i++) {
            for (int j = getY() - size; j < getY() + size; j++) {
                if (!(Worker.getInstance().getElement(new Coordinates(i, j)) instanceof VoidGenerator)) {
                    Worker.getInstance().removeElement(new Coordinates(i, j));
                }
            }
        }
    }
}
