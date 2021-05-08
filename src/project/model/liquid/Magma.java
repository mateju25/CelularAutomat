package project.model.liquid;

import javafx.scene.paint.Color;
import project.model.Coordinates;
import project.model.Element;
import project.model.Movable;
import project.model.Worker;

import java.util.Random;

import static javafx.scene.paint.Color.rgb;

public class Magma extends Element implements Liquid, Movable {
    public Magma(Coordinates coors) {
        super(coors);
    }

    @Override
    public void applyGravity() {
        if (!moveDown()) {
            if (new Random().nextBoolean()) {
                if (!moveLeft())
                    moveRight();
            } else {
                if (!moveRight())
                    moveLeft();
            }
        }
    }

    @Override
    public float[] getTexture() {
        return new float[]{0, 1, 1};
    }

    @Override
    public boolean moveLeft() {
        int size = Worker.getInstance().getSize();
        if (checkCoors(getX() - size, getY())) {
            if (Worker.getInstance().getElement(new Coordinates(getX() - size, getY())) == null) {
                swap( getX() - size, getY());
                return true;
            }
            if (Worker.getInstance().getElement(new Coordinates(getX() - size, getY())) instanceof Water) {
                swap(getX() - size, getY());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean moveRight() {
        int size = Worker.getInstance().getSize();
        if (checkCoors(getX() + size, getY())) {
            if (Worker.getInstance().getElement(new Coordinates(getX() + size, getY())) == null) {
                swap(getX() + size, getY());
                return true;
            }
            if (Worker.getInstance().getElement(new Coordinates(getX() + size, getY())) instanceof Water) {
                swap(getX() + size, getY());
                return true;
            }

        }
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
            if (Worker.getInstance().getElement(new Coordinates(getX(), getY() + size)) instanceof Water) {
                swap(getX(), getY() + size);
                return true;
            }
        }
        return false;
    }
}
