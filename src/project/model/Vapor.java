package project.model;

import javafx.scene.paint.Paint;

import java.util.Random;

import static javafx.scene.paint.Color.rgb;

public class Vapor extends Element implements Movable, Gas{
    private int energy = 40;
    public Vapor(Coordinates coors) {
        super(coors);
    }

    @Override
    public void applyGravity(Element[][] itemMap) {
        if (energy <= 0) {
            this.toRemove= true;
            itemMap[getX()][getY()] = null;
        }
        if (!moveUp(itemMap)) {
            if (new Random().nextBoolean()) {
                if (!moveLeft(itemMap))
                    moveRight(itemMap);
            } else {
                if (!moveRight(itemMap))
                    moveLeft(itemMap);
            }
        }
    }

    @Override
    public Paint getTexture() {
        return rgb(200, 200, 255);
    }

    @Override
    public boolean moveLeft(Element[][] itemMap) {
        int size = Worker.getInstance().getSize();
        if (checkCoors(getX() - size, getY())) {
            energy--;
            if (itemMap[getX() - size][getY()] == null) {
                swap(itemMap, getX() - size, getY());
                return true;
            }
            if (itemMap[getX() - size][getY()] instanceof Water) {
                swap(itemMap, getX() - size, getY());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean moveRight(Element[][] itemMap) {
        int size = Worker.getInstance().getSize();
        if (checkCoors(getX() + size, getY())) {
            energy--;
            if (itemMap[getX() + size][getY() ] == null) {
                swap(itemMap, getX() + size, getY());
                return true;
            }
            if (itemMap[getX() + size][getY()] instanceof Water) {
                swap(itemMap, getX() + size, getY());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean moveUp(Element[][] itemMap) {
        int size = Worker.getInstance().getSize();
        if (checkCoors(getX(), getY() - size)) {
            energy--;
            if (itemMap[getX()][getY() - size] == null) {
                swap(itemMap, getX(), getY() - size);
                return true;
            }
            if (itemMap[getX()][getY() - size] instanceof Water) {
                swap(itemMap, getX(), getY() - size);
                return true;
            }
        }
        return false;
    }
}
