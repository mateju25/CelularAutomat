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
//        if (checkCoors(getX() - 3*size, getY() )) {
//            if (Worker.getInstance().getElement(new Coordinates(getX() - 3*size, getY())) == null) {
//                swap( getX() - 3*size, getY() );
//                return true;
//            }
//        }
//        if (checkCoors(getX() - 2*size, getY() )) {
//            if (Worker.getInstance().getElement(new Coordinates(getX() - 2*size, getY())) == null) {
//                swap( getX() - 2*size, getY() );
//                return true;
//            }
//        }
        if (checkCoors(getX() - size, getY())) {
            if (Worker.getInstance().getElement(new Coordinates(getX() - size, getY())) == null) {
                swap( getX() - size, getY());
                return true;
            }
            if (Worker.getInstance().getElement(new Coordinates(getX() - size, getY())) instanceof Oil) {
                swap( getX() - size, getY());
                return true;
            }
            if (Worker.getInstance().getElement(new Coordinates(getX() - size, getY())) instanceof Magma) {
                ((Magma) Worker.getInstance().getElement(new Coordinates(getX() - size, getY()))).decPower();
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
//        if (checkCoors(getX()+ 3*size, getY() )) {
//            if (Worker.getInstance().getElement(new Coordinates(getX() + 3*size, getY())) == null) {
//                swap( getX()+ 3*size, getY() );
//                return true;
//            }
//        }
//        if (checkCoors(getX()+ 2*size, getY() )) {
//            if (Worker.getInstance().getElement(new Coordinates(getX() + 2*size, getY())) == null) {
//                swap( getX()+ 2*size, getY() );
//                return true;
//            }
//        }
        if (checkCoors(getX() + size, getY()) ) {
            if (Worker.getInstance().getElement(new Coordinates(getX() + size, getY())) == null) {
                swap( getX() + size, getY());
                return true;
            }
            if (Worker.getInstance().getElement(new Coordinates(getX() + size, getY())) instanceof Oil) {
                swap( getX() + size, getY());
                return true;
            }
            if (Worker.getInstance().getElement(new Coordinates(getX() + size, getY())) instanceof Magma) {
                ((Magma) Worker.getInstance().getElement(new Coordinates(getX() + size, getY()))).decPower();
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
            if (Worker.getInstance().getElement(new Coordinates(getX() , getY() + size)) instanceof Oil) {
                swap( getX(), getY() + size);
                return true;
            }
            if (Worker.getInstance().getElement(new Coordinates(getX() , getY() + size)) instanceof Magma) {
                var element = Worker.getInstance().getElement(new Coordinates(getX() , getY() + size));
                if (element == null)
                    return false;
                ((Magma) element).decPower();
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
//        switch (new Random().nextInt() % 3) {
//            case 0: moveDown(); break;
//            case 1: if (!moveLeft())
//                        moveRight();
//                    break;
//            case 2: if (!moveRight())
//                        moveLeft();
//        }
//        moveDown();
        if (!moveDown()) {
            for (int i = 0; i < 5; i++) {
                if (new Random().nextBoolean()) {
                    if (!moveLeft())
                        moveRight();
                } else {
                    if (!moveRight())
                        moveLeft();
                }
            }
        }
    }

    @Override
    public Paint getTexture() {
        return rgb(0, 55, 255);
    }
}
