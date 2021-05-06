package project.model;

import java.util.Random;

public class Water extends Element{
    public Water(Coordinates coors) {
        super(coors);
    }

    private boolean moveLeft(Element[][] itemMap) {
        int size = Worker.getInstance().getSize();
        if (checkCoors(getX() - size, getY()) && itemMap[getX() - size][getY()] == null) {
            itemMap[getX()][getY()] = null;
            setX(getX() - size);
            itemMap[getX()][getY()] = this;
            return true;
        }
        return false;
    }

    private boolean moveRight(Element[][] itemMap) {
        int size = Worker.getInstance().getSize();
        if (checkCoors(getX() + size, getY()) && itemMap[getX() + size][getY()] == null) {
            itemMap[getX()][getY()] = null;
            setX(getX() + size);
            itemMap[getX()][getY()] = this;
            return true;
        }
        return false;
    }

    public void applyGravity(Element[][] itemMap) {
        int size = Worker.getInstance().getSize();
        if (!checkCoors(getX(), getY() + size))
            return;
        if (itemMap[getX()][getY() + size] == null) {
            itemMap[getX()][getY()] = null;
            setY(getY() + size);
            itemMap[getX()][getY()] = this;
        } else {
            if (new Random().nextBoolean()) {
                if (!moveLeft(itemMap))
                    moveRight(itemMap);
            } else {
                if (!moveRight(itemMap))
                    moveLeft(itemMap);
            }
        }
    }
}
