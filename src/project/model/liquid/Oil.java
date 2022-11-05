package project.model.liquid;

import javafx.scene.paint.Paint;
import project.model.Coordinates;
import project.model.Element;
import project.model.Movable;
import project.model.Worker;
import project.model.gas.Fire;
import project.model.gas.Vapor;

import java.util.Random;

import static javafx.scene.paint.Color.rgb;

public class Oil extends Element implements Liquid, Movable {
    private int energy = 300;

    public Oil(Coordinates coors) {
        super(coors);
    }

    public void decEnergy() {
        energy--;
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
                energy--;
                return true;
            }
            if (Worker.getInstance().getElement(new Coordinates(getX() - size, getY())) instanceof Oil) {
                if (this.energy != 300)
                    ((Oil) Worker.getInstance().getElement(new Coordinates(getX() - size, getY()))).energy--;
                return false;
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
                energy--;
                return true;
            }
            if (Worker.getInstance().getElement(new Coordinates(getX() + size, getY())) instanceof Oil) {
                if (this.energy != 300)
                    ((Oil) Worker.getInstance().getElement(new Coordinates(getX() + size, getY()))).energy--;
                return false;
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
                energy--;
                return true;
            }
            if (Worker.getInstance().getElement(new Coordinates(getX() , getY() + size)) instanceof Oil) {
                if (this.energy != 300)
                    ((Oil) Worker.getInstance().getElement(new Coordinates(getX() , getY() + size))).energy--;
                return false;
            }
        }
        return false;
    }

    @Override
    public void applyGravity() {
        if (energy <= 0) {
            Worker.getInstance().removeElement(getCoors());
            return;
        }
        int size = Worker.getInstance().getSize();
        if (energy != 300) {
            if (checkCoors(getX(), getY() - size)) {
                var tmp = Worker.getInstance().getElement(new Coordinates(getX() , getY() - size));
                if (tmp instanceof Oil) {
                    this.energy = 300;
                    ((Oil) tmp).energy--;
                }
            }
            Element vapor = new Fire(new Coordinates(getX(), getY() - size));
            Worker.getInstance().addElement(vapor.getCoors(), vapor);
            energy--;
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
        return rgb(236, 190, 70);
    }
}
