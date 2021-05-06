package project.model;

import java.util.Random;

public class Sand extends Element{
    public Sand(Coordinates coors) {
        super(coors);
    }

    private boolean moveLeft(Element[][] itemMap) {
        int size = Worker.getInstance().getSize();
        if (checkCoors(getX() - size, getY() + size) && (itemMap[getX() - size][getY() + size] == null || itemMap[getX() - size][getY() + size] instanceof Water)) {
            if (itemMap[getX() - size][getY() + size] == null) {
                itemMap[getX()][getY()] = null;
                setX(getX() - size);
                setY(getY() + size);
                itemMap[getX()][getY()] = this;
            } else {
                Element tmp = itemMap[getX() - size][getY() + size];
                itemMap[getX()][getY()] = tmp;
                tmp.setX(tmp.getX() + size);
                tmp.setY(tmp.getY() - size);
                setX(getX() - size);
                setY(getY() + size);
                itemMap[getX()][getY()] = this;
            }
            return true;
        }
        return false;
    }

    private boolean moveRight(Element[][] itemMap) {
        int size = Worker.getInstance().getSize();
        if (checkCoors(getX() + size, getY() + size) && (itemMap[getX() + size][getY() + size] == null || itemMap[getX() + size][getY() + size] instanceof Water)) {
            if (itemMap[getX() + size][getY() + size] == null) {
                itemMap[getX()][getY()] = null;
                setX(getX() + size);
                setY(getY() + size);
                itemMap[getX()][getY()] = this;
            } else {
                Element tmp = itemMap[getX() + size][getY() + size];
                itemMap[getX()][getY()] = tmp;
                tmp.setX(tmp.getX() - size);
                tmp.setY(tmp.getY() - size);
                setX(getX() + size);
                setY(getY() + size);
                itemMap[getX()][getY()] = this;
            }
            return true;
        }
        return false;
    }

    public void applyGravity(Element[][] itemMap) {
        int size = Worker.getInstance().getSize();
        if (!checkCoors(getX(), getY() + size))
            return;
        if (itemMap[getX()][getY() + size] == null || itemMap[getX()][getY() + size] instanceof Water) {
            if (itemMap[getX()][getY() + size] == null) {
                itemMap[getX()][getY()] = null;
                setY(getY() + size);
                itemMap[getX()][getY()] = this;
            } else {
                Element tmp = itemMap[getX()][getY() + size];
                itemMap[getX()][getY()] = tmp;
                tmp.setY(tmp.getY() - size);
                setY(getY() + size);
                itemMap[getX()][getY()] = this;
            }
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
