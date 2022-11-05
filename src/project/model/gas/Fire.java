package project.model.gas;

import javafx.scene.paint.Paint;
import project.model.Coordinates;
import project.model.Element;
import project.model.Movable;
import project.model.Worker;
import project.model.liquid.Liquid;
import project.model.liquid.Oil;
import project.model.liquid.Water;

import java.util.Random;

import static javafx.scene.paint.Color.rgb;

public class Fire extends Element implements Movable, Gas {
    private int energy = 5 + new Random().nextInt() % 20;
    public Fire(Coordinates coors) {
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
        if (new Random().nextBoolean())
            return rgb(255, 51, 0);
        else
            return rgb(255, 145, 0);
    }

    @Override
    public boolean moveLeft() {
        int size = Worker.getInstance().getSize();
        if (checkCoors(getX() - size, getY())) {
            energy--;
            var tmp = Worker.getInstance().getElement(new Coordinates(getX() - size, getY()));
            if (tmp instanceof Oil) {
                ((Oil) tmp).decEnergy();
            }
            if (Worker.getInstance().getElement(new Coordinates(getX() - size, getY())) == null) {
                swap( getX() - size, getY());
                return true;
            }
            if (Worker.getInstance().getElement(new Coordinates(getX() - size, getY())) instanceof Liquid) {
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
            var tmp = Worker.getInstance().getElement(new Coordinates(getX() + size, getY()));
            if (tmp instanceof Oil) {
                ((Oil) tmp).decEnergy();
            }
            if (Worker.getInstance().getElement(new Coordinates(getX() +  size, getY())) == null) {
                swap( getX() + size, getY());
                return true;
            }
            if (Worker.getInstance().getElement(new Coordinates(getX() + size, getY())) instanceof Liquid) {
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
            var tmp = Worker.getInstance().getElement(new Coordinates(getX(), getY() - size));
            if (tmp instanceof Oil) {
                ((Oil) tmp).decEnergy();
            }
            if (Worker.getInstance().getElement(new Coordinates(getX() , getY()- size)) == null) {
                swap( getX(), getY() - size);
                return true;
            }
            if (Worker.getInstance().getElement(new Coordinates(getX() , getY()- size)) instanceof Liquid) {
                swap( getX(), getY() - size);
                return true;
            }
        }
        return false;
    }
}
