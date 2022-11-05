package project.model.solid;

import javafx.scene.paint.Paint;
import project.model.Coordinates;
import project.model.Element;
import project.model.Movable;
import project.model.Worker;
import project.model.liquid.Liquid;

import static javafx.scene.paint.Color.rgb;

public class Glass extends Element implements Solid, Movable {
    public Glass(Coordinates coors) {
        super(coors);
    }

    @Override
    public void applyGravity() {
        moveDown();
    }

    @Override
    public Paint getTexture() {
        return rgb(255, 255, 255, 0.8);
    }

    @Override
    public boolean moveLeftDown() {
        return false;
    }

    @Override
    public boolean moveRightDown() {
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
            if (Worker.getInstance().getElement(new Coordinates(getX(), getY() + size)) instanceof Liquid) {
                swap(getX(), getY() + size);
                return true;
            }
        }
        return false;
    }
}
