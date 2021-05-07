package project.model;

import javafx.scene.paint.Paint;

import java.util.Random;

import static javafx.scene.paint.Color.rgb;

public class Water extends Element implements Liquid, Movable{
    public Water(Coordinates coors) {
        super(coors);
    }

    @Override
    public boolean moveLeft(Element[][] itemMap) {
        int size = Worker.getInstance().getSize();
        if (checkCoors(getX() - size, getY()) && itemMap[getX() - size][getY()] == null) {
            swap(itemMap, getX() - size, getY());
            return true;
        }
        return false;
    }

    @Override
    public boolean moveRight(Element[][] itemMap) {
        int size = Worker.getInstance().getSize();
        if (checkCoors(getX() + size, getY()) && itemMap[getX() + size][getY()] == null) {
            swap(itemMap, getX() + size, getY());
            return true;
        }
        return false;
    }

    @Override
    public boolean moveDown(Element[][] itemMap) {
        int size = Worker.getInstance().getSize();
        if (checkCoors(getX(), getY() + size)) {
            if (itemMap[getX()][getY() + size] == null) {
                swap(itemMap, getX(), getY() + size);
                return true;
            }
            if (itemMap[getX()][getY() + size] instanceof Magma) {
                this.toRemove = true;
                Element vapor = new Vapor(new Coordinates(getX(), getY()));
                Worker.getInstance().getCreatedItems().put(vapor.getCoors(), vapor);
                itemMap[getX()][getY()] = vapor;
                return true;
            }
        }
        return false;
    }

    @Override
    public void applyGravity(Element[][] itemMap) {
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
        return rgb(0, 55, 255);
    }
}
