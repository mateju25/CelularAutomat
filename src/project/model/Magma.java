package project.model;

import javafx.scene.paint.Paint;

import java.util.Map;
import java.util.Random;

import static javafx.scene.paint.Color.rgb;

public class Magma extends Element implements Liquid, Movable{
    public Magma(Coordinates coors) {
        super(coors);
    }

    @Override
    public void applyGravity(Map<Coordinates, Element> itemMap) {
        if (!moveDown(itemMap)) {
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
        return rgb(255, 0, 0);
    }

    @Override
    public boolean moveLeft(Map<Coordinates, Element> itemMap) {
        int size = Worker.getInstance().getSize();
        if (checkCoors(getX() - size, getY())) {
            if (itemMap.get(new Coordinates(getX() - size, getY())) == null) {
                swap(itemMap, getX() - size, getY());
                return true;
            }
            if (itemMap.get(new Coordinates(getX() - size, getY())) instanceof Water) {
                swap(itemMap, getX() - size, getY());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean moveRight(Map<Coordinates, Element> itemMap) {
        int size = Worker.getInstance().getSize();
        if (checkCoors(getX() + size, getY())) {
            if (itemMap.get(new Coordinates(getX() + size, getY())) == null) {
                swap(itemMap, getX() + size, getY());
                return true;
            }
            if (itemMap.get(new Coordinates(getX() + size, getY())) instanceof Water) {
                swap(itemMap, getX() + size, getY());
                return true;
            }

        }
        return false;
    }

    @Override
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
}
