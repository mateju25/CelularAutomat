package project.model;

import javafx.scene.paint.Paint;

import java.util.Random;

import static javafx.scene.paint.Color.rgb;

public class Sand extends Element implements Solid{
    public Sand(Coordinates coors) {
        super(coors);
    }

    public boolean moveLeftDown(Element[][] itemMap) {
        int size = Worker.getInstance().getSize();
        if (checkCoors(getX() - size, getY() + size) && itemMap[getX() - size][getY() + size] == null) {
                itemMap[getX()][getY()] = null;
                setX(getX() - size);
                setY(getY() + size);
                itemMap[getX()][getY()] = this;
            return true;
        }
        return false;
    }

    public boolean moveRightDown(Element[][] itemMap) {
        int size = Worker.getInstance().getSize();
        if (checkCoors(getX() + size, getY() + size) && itemMap[getX() + size][getY() + size] == null) {
                itemMap[getX()][getY()] = null;
                setX(getX() + size);
                setY(getY() + size);
                itemMap[getX()][getY()] = this;
            return true;
        }
        return false;
    }

    public boolean moveDown(Element[][] itemMap) {
        int size = Worker.getInstance().getSize();
        if (checkCoors(getX(), getY() + size) && itemMap[getX()][getY() + size] == null) {
            itemMap[getX()][getY()] = null;
            setY(getY() + size);
            itemMap[getX()][getY()] = this;
            return true;
        }
        return false;
    }


    public void applyGravity(Element[][] itemMap) {
        int size = Worker.getInstance().getSize();
        if (!checkCoors(getX(), getY() + size))
            return;
        if (!moveDown(itemMap)) {
            if (new Random().nextBoolean()) {
                if (!moveLeftDown(itemMap))
                    moveRightDown(itemMap);
            } else {
                if (!moveRightDown(itemMap))
                    moveLeftDown(itemMap);
            }
        }
    }

    @Override
    public Paint getTexture() {
        return rgb(255, 191, 0);
    }
}
