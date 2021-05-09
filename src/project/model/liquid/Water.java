package project.model.liquid;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import project.model.*;
import project.model.gas.Vapor;

import java.util.Random;

import static javafx.scene.paint.Color.rgb;

public class Water extends Element implements Liquid, Movable {
    public Water(Coordinates coors) {
        super(coors);
    }

    @Override
    public boolean moveLeft() {
        int size = Worker.getInstance().getSize();
        if (checkCoors(getX() - size, getY())) {
            if (Worker.getInstance().getElement(new Coordinates(getX() - size, getY())) == null) {
                swap( getX() - size, getY());
                return true;
            }
            if (Worker.getInstance().getElement(new Coordinates(getX() - size, getY())) instanceof Magma) {
                Worker.getInstance().removeElement(getCoors());
                Element vapor = new Vapor(new Coordinates(getX(), getY()));
                Worker.getInstance().addElement(vapor.getCoors(), vapor);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean moveRight() {
        int size = Worker.getInstance().getSize();
        if (checkCoors(getX() + size, getY()) ) {
            if (Worker.getInstance().getElement(new Coordinates(getX() + size, getY())) == null) {
                swap( getX() + size, getY());
                return true;
            }

            if (Worker.getInstance().getElement(new Coordinates(getX() + size, getY())) instanceof Magma) {
                Worker.getInstance().removeElement(getCoors());
                Element vapor = new Vapor(new Coordinates(getX(), getY()));
                Worker.getInstance().addElement(vapor.getCoors(), vapor);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean moveDown() {
        int size = Worker.getInstance().getSize();
        if (checkCoors(getX(), getY() + size)) {
            if (Worker.getInstance().getElement(new Coordinates(getX() , getY() + size)) == null) {
                swap( getX(), getY() + size);
                return true;
            }
            if (Worker.getInstance().getElement(new Coordinates(getX() , getY() + size)) instanceof Magma) {
                Worker.getInstance().removeElement(getCoors());
                Element vapor = new Vapor(new Coordinates(getX(), getY()));
                Worker.getInstance().addElement(vapor.getCoors(), vapor);
                return true;
            }
        }
        return false;
    }

    @Override
    public void applyGravity() {
        if (!moveDown()) {
            if (new Random().nextBoolean()) {
                if (!moveLeft())
                    moveRight();
            } else {
                if (!moveRight())
                    moveLeft();
            }
        }
    }

    @Override
    public Paint getTexture() {
        return rgb(0, 55, 255);
    }
}
