package project.model;

import javafx.scene.paint.Paint;

import java.util.Map;
import java.util.Random;

import static javafx.scene.paint.Color.rgb;

public class Sand extends Element implements Solid, Movable {
    public Sand(Coordinates coors) {
        super(coors);
    }

    public boolean moveLeftDown(Map<Coordinates, Element> itemMap) {
        int size = Worker.getInstance().getSize();
        if (checkCoors(getX() - size, getY() + size)) {
            if (itemMap.get(new Coordinates(getX() - size, getY() + size)) == null) {
                swap(itemMap, getX() - size, getY() + size);
                return true;
            }
            if (itemMap.get(new Coordinates(getX() - size, getY() + size)) instanceof Water) {
                swap(itemMap, getX() - size, getY() + size);
                return true;
            }
        }
        return false;
    }

    public boolean moveRightDown(Map<Coordinates, Element> itemMap) {
        int size = Worker.getInstance().getSize();
        if (checkCoors(getX() + size, getY() + size)) {
            if (itemMap.get(new Coordinates(getX() + size, getY() + size)) == null) {
                swap(itemMap, getX() + size, getY() + size);
                return true;
            }
            if (itemMap.get(new Coordinates(getX() + size, getY() + size)) instanceof Water) {
                swap(itemMap, getX() + size, getY() + size);
                return true;
            }
        }
        return false;
    }

    public boolean moveDown(Map<Coordinates, Element> itemMap) {
        int size = Worker.getInstance().getSize();
        if (checkCoors(getX(), getY() + size)) {
            if (itemMap.get(new Coordinates(getX(), getY() + size)) == null) {
                swap(itemMap, getX(), getY() + size);
                return true;
            }
            if (itemMap.get(new Coordinates(getX(), getY() + size)) instanceof Water) {
                swap(itemMap, getX(), getY() + size);
                return true;
            }
        }
        return false;
    }


    public void applyGravity(Map<Coordinates, Element> itemMap) {
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
