package project.model;

import javafx.scene.paint.Paint;

import java.util.Random;

import static javafx.scene.paint.Color.rgb;

public class Water extends Element implements Liquid, Movable{
    public Water(Coordinates coors) {
        super(coors);
    }

    public boolean moveLeft(Element[][] itemMap) {
        int size = Worker.getInstance().getSize();
        if (checkCoors(getX() - size, getY()) && itemMap[getX() - size][getY()] == null) {
            swap(itemMap, getX() - size, getY());
            return true;
        }
        return false;
    }

    public boolean moveRight(Element[][] itemMap) {
        int size = Worker.getInstance().getSize();
        if (checkCoors(getX() + size, getY()) && itemMap[getX() + size][getY()] == null) {
            swap(itemMap, getX() + size, getY());
            return true;
        }
        return false;
    }

    public boolean moveDown(Element[][] itemMap) {
        int size = Worker.getInstance().getSize();
        if (checkCoors(getX(), getY() + size) && itemMap[getX()][getY() + size] == null) {
            swap(itemMap, getX(), getY() + size);
            return true;
        }
        return false;
    }

    public boolean applyGravity(Element[][] itemMap) {
        if (!moveDown(itemMap)) {
            if (new Random().nextBoolean()) {
                if (!moveLeft(itemMap))
                    moveRight(itemMap);
            } else {
                if (!moveRight(itemMap))
                    moveLeft(itemMap);
            }
        }
        return true;
    }

    @Override
    public Paint getTexture() {
        return rgb(0, 55, 255);
    }
}
