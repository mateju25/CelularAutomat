package project.model.liquid;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import project.model.Coordinates;
import project.model.Element;
import project.model.Movable;
import project.model.Worker;
import project.model.gas.Vapor;
import project.model.solid.Glass;
import project.model.solid.Obsidian;
import project.model.solid.Sand;

import java.util.Random;

import static javafx.scene.paint.Color.rgb;

public class Magma extends Element implements Liquid, Movable {
    private int power = 3;

    public Magma(Coordinates coors) {
        super(coors);
    }

    public void decPower() {
        this.power--;
    }

    @Override
    public void applyGravity() {
        if (power <= 0) {
            Worker.getInstance().removeElement(getCoors());
            Element obs = new Obsidian(new Coordinates(getX(), getY()));
            Worker.getInstance().addElement(obs.getCoors(), obs);
            return;
        }

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
        return rgb(255, 0, 0);
    }

    @Override
    public boolean moveLeft() {
        int size = Worker.getInstance().getSize();
        if (checkCoors(getX() - size, getY())) {
            if (Worker.getInstance().getElement(new Coordinates(getX() - size, getY())) == null) {
                swap( getX() - size, getY());
                return true;
            }
            if (Worker.getInstance().getElement(new Coordinates(getX() - size, getY())) instanceof Water) {
                power--;
                swap(getX() - size, getY());
                return true;
            }
            if (Worker.getInstance().getElement(new Coordinates(getX() - size, getY())) instanceof Oil) {
                swap(getX() - size, getY());
                return true;
            }
            Coordinates coors = new Coordinates(getX() - size, getY());
            if (Worker.getInstance().getElement(coors) instanceof Sand) {
                Worker.getInstance().removeElement(coors);
                Element glass = new Glass(coors);
                Worker.getInstance().addElement(coors, glass);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean moveRight() {
        int size = Worker.getInstance().getSize();
        if (checkCoors(getX() + size, getY())) {
            if (Worker.getInstance().getElement(new Coordinates(getX() + size, getY())) == null) {
                swap(getX() + size, getY());
                return true;
            }
            if (Worker.getInstance().getElement(new Coordinates(getX() + size, getY())) instanceof Water) {
                power--;
                swap(getX() + size, getY());
                return true;
            }
            if (Worker.getInstance().getElement(new Coordinates(getX() + size, getY())) instanceof Oil) {
                swap(getX() + size, getY());
                return true;
            }
            Coordinates coors = new Coordinates(getX()  + size, getY());
            if (Worker.getInstance().getElement(coors) instanceof Sand) {
                Worker.getInstance().removeElement(coors);
                Element glass = new Glass(coors);
                Worker.getInstance().addElement(coors, glass);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean moveDown() {
        int size = Worker.getInstance().getSize();
        if (checkCoors(getX(), getY() + size)) {
            if (Worker.getInstance().getElement(new Coordinates(getX(), getY() + size)) == null) {
                swap(getX(), getY() + size);
                return true;
            }
            if (Worker.getInstance().getElement(new Coordinates(getX(), getY() + size)) instanceof Water) {
                power--;
                swap(getX(), getY() + size);
                return true;
            }
            if (Worker.getInstance().getElement(new Coordinates(getX(), getY() + size)) instanceof Oil) {
                swap(getX(), getY() + size);
                return true;
            }
            Coordinates coors = new Coordinates(getX(), getY() + size);
            if (Worker.getInstance().getElement(coors) instanceof Sand) {
                Worker.getInstance().removeElement(coors);
                Element glass = new Glass(coors);
                Worker.getInstance().addElement(coors, glass);
                return true;
            }
        }
        return false;
    }
}
