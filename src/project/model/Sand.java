package project.model;

import javafx.scene.paint.Paint;

import java.util.Map;
import java.util.Random;

import static javafx.scene.paint.Color.rgb;

public class Sand extends Element implements Solid, Movable {
    public Sand(Coordinates coors) {
        super(coors);
    }

    public boolean moveLeftDown() {
        int size = Worker.getInstance().getSize();
        if (checkCoors(getX() - size, getY() + size)) {
            if (Worker.getInstance().getElement(new Coordinates(getX() - size, getY() + size)) == null) {
                swap(getX() - size, getY() + size);
                return true;
            }
            if (Worker.getInstance().getElement(new Coordinates(getX() - size, getY() + size)) instanceof Water) {
                swap(getX() - size, getY() + size);
                return true;
            }
        }
        return false;
    }

    public boolean moveRightDown() {
        int size = Worker.getInstance().getSize();
        if (checkCoors(getX() + size, getY() + size)) {
            if (Worker.getInstance().getElement(new Coordinates(getX() + size, getY() + size)) == null) {
                swap(getX() + size, getY() + size);
                return true;
            }
            if (Worker.getInstance().getElement(new Coordinates(getX() + size, getY() + size)) instanceof Water) {
                swap(getX() + size, getY() + size);
                return true;
            }
        }
        return false;
    }

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


    public void applyGravity() {
        if (!moveDown()) {
            if (new Random().nextBoolean()) {
                if (!moveLeftDown())
                    moveRightDown();
            } else {
                if (!moveRightDown())
                    moveLeftDown();
            }
        }
    }

    @Override
    public Paint getTexture() {
        return rgb(255, 191, 0);
    }
}
