package project.model;

import javafx.scene.paint.Paint;

import java.util.Random;

import static javafx.scene.paint.Color.rgb;

public class Magma extends Element implements Liquid, Movable{
    public Magma(Coordinates coors) {
        super(coors);
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
        return rgb(255, 0, 0);
    }

    @Override
    public boolean moveLeft(Element[][] itemMap) {
        int size = Worker.getInstance().getSize();
        if (checkCoors(getX() - size, getY())) {
            if (itemMap[getX() - size][getY()] == null) {
                swap(itemMap, getX() - size, getY());
                return true;
            }
            if (itemMap[getX() - size][getY()] instanceof Water) {
                int x = getX();
                int y = getY();
                swap(itemMap, getX() - size, getY());
                if (Worker.getInstance().getItems().get(new Coordinates(x, y)) != null) {
                    Worker.getInstance().getItems().get(new Coordinates(x, y)).toRemove = true;
                    Element vapor = new Vapor(new Coordinates(x, y));
                    Worker.getInstance().getCreatedItems().put(vapor.getCoors(), vapor);
                    itemMap[x][y] = vapor;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean moveRight(Element[][] itemMap) {
        int size = Worker.getInstance().getSize();
        if (checkCoors(getX() + size, getY())) {
            if (itemMap[getX() + size][getY()] == null) {
                swap(itemMap, getX() + size, getY());
                return true;
            }
            if (itemMap[getX() + size][getY()] instanceof Water) {
                int x = getX();
                int y = getY();
                swap(itemMap, getX() + size, getY());
                if (Worker.getInstance().getItems().get(new Coordinates(x, y)) != null) {
                    Worker.getInstance().getItems().get(new Coordinates(x, y)).toRemove = true;
                    Element vapor = new Vapor(new Coordinates(x, y));
                    Worker.getInstance().getCreatedItems().put(vapor.getCoors(), vapor);
                    itemMap[x][y] = vapor;
                }
                return true;
            }

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
            if (itemMap[getX()][getY() + size] instanceof Water) {
                int x = getX();
                int y = getY();
                swap(itemMap, getX(), getY() + size);
                if (Worker.getInstance().getItems().get(new Coordinates(x, y)) != null) {
                    Worker.getInstance().getItems().get(new Coordinates(x, y)).toRemove = true;
                    Element vapor = new Vapor(new Coordinates(x, y));
                    Worker.getInstance().getCreatedItems().put(vapor.getCoors(), vapor);
                    itemMap[x][y] = vapor;
                }
                return true;
            }
        }
        return false;
    }
}
