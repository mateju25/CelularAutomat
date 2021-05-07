package project.model.gas;

import javafx.scene.paint.Paint;
import project.model.Coordinates;
import project.model.Element;
import project.model.Movable;
import project.model.Worker;
import project.model.liquid.Water;

import java.util.Random;

import static javafx.scene.paint.Color.rgb;

public class Vapor extends Element implements Movable, Gas{
    private int energy = 35 + new Random().nextInt() % 20;
    public Vapor(Coordinates coors) {
        super(coors);
    }

    @Override
    public void applyGravity() {
        if (energy <= 0) {
            Worker.getInstance().removeElement(getCoors());
        }
        if (!moveUp()) {
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
        return rgb(200, 200, 255);
    }

    @Override
    public boolean moveLeft() {
        int size = Worker.getInstance().getSize();
        if (checkCoors(getX() - size, getY())) {
            energy--;
            if (Worker.getInstance().getElement(new Coordinates(getX() - size, getY())) == null) {
                swap( getX() - size, getY());
                return true;
            }
            if (Worker.getInstance().getElement(new Coordinates(getX() - size, getY())) instanceof Water) {
                swap( getX() - size, getY());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean moveRight() {
        int size = Worker.getInstance().getSize();
        if (checkCoors(getX() + size, getY())) {
            energy--;
            if (Worker.getInstance().getElement(new Coordinates(getX() +  size, getY())) == null) {
                swap( getX() + size, getY());
                return true;
            }
            if (Worker.getInstance().getElement(new Coordinates(getX() + size, getY())) instanceof Water) {
                swap( getX() + size, getY());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean moveUp() {
        int size = Worker.getInstance().getSize();
        if (checkCoors(getX(), getY() - size)) {
            energy--;
            if (Worker.getInstance().getElement(new Coordinates(getX() , getY()- size)) == null) {
                swap( getX(), getY() - size);
                return true;
            }
            if (Worker.getInstance().getElement(new Coordinates(getX() , getY()- size)) instanceof Water) {
                swap( getX(), getY() - size);
                return true;
            }
        }
        return false;
    }
}
