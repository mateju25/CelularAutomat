package project.model;

import javafx.scene.paint.Paint;

import java.util.Map;
import java.util.Random;

import static javafx.scene.paint.Color.rgb;

public class Vapor extends Element implements Movable, Gas{
    private int energy = 35 + new Random().nextInt() % 10;
    public Vapor(Coordinates coors) {
        super(coors);
    }

    @Override
    public void applyGravity(Map<Coordinates, Element> itemMap) {
        if (energy <= 0) {
            this.toRemove= true;
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
    public boolean moveLeft(Map<Coordinates, Element> itemMap) {
        int size = Worker.getInstance().getSize();
        if (checkCoors(getX() - size, getY())) {
            energy--;
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
            energy--;
            if (itemMap.get(new Coordinates(getX() +  size, getY())) == null) {
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
    public boolean moveUp(Map<Coordinates, Element> itemMap) {
        int size = Worker.getInstance().getSize();
        if (checkCoors(getX(), getY() - size)) {
            energy--;
            if (itemMap.get(new Coordinates(getX() , getY()- size)) == null) {
                swap(itemMap, getX(), getY() - size);
                return true;
            }
            if (itemMap.get(new Coordinates(getX() , getY()- size)) instanceof Water) {
                swap(itemMap, getX(), getY() - size);
                return true;
            }
        }
        return false;
    }
}
