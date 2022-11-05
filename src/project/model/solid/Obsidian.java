package project.model.solid;

import javafx.scene.paint.Paint;
import project.model.Coordinates;
import project.model.Element;
import project.model.Movable;
import project.model.Worker;
import project.model.liquid.Liquid;
import project.model.liquid.Water;

import static javafx.scene.paint.Color.rgb;

public class Obsidian extends Element implements Solid, Movable {
    public Obsidian(Coordinates coors) {
        super(coors);
    }

    @Override
    public void applyGravity() {
        moveDown();
    }

    @Override
    public Paint getTexture() {
        return rgb(41, 2, 97);
    }

    @Override
    public boolean moveLeftDown() {
        return false;
    }

    @Override
    public boolean moveRightDown() {
        return false;
    }

    @Override
    public boolean moveDown() {
        int size = Worker.getInstance().getSize();
        if (checkCoors(getX(), getY() + size)) {
            if (Worker.getInstance().getElement(new Coordinates(getX(), getY() + size)) == null) {
                swap(getX(), getY() + size);
                return true;
            }
            if (Worker.getInstance().getElement(new Coordinates(getX(), getY() + size)) instanceof Liquid) {
                swap(getX(), getY() + size);
                return true;
            }
        }
        return false;
    }
}
