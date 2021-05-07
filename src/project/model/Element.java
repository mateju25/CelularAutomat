package project.model;

import javafx.scene.paint.Paint;

import java.util.Map;

public abstract class Element {
    private Coordinates coors;

    public Element(Coordinates coors) {
        this.coors = coors;
    }

    public Coordinates getCoors() {
        return coors;
    }

    protected int getX() {
        return coors.getX();
    }

    protected void setX(int x) {
        this.coors.setX(x);
    }

    protected int getY() {
        return coors.getY();
    }

    protected void setY(int y) {
        this.coors.setY(y);
    }

    protected boolean checkCoors(int x, int y) {
        if (x >= 0 && x <= Worker.getInstance().getMaxWidth() && y >= 0 && y <= Worker.getInstance().getMaxHeight()-1)
            return true;
        else
            return false;
    }

    public void swap(int x, int y) {
        Element tmpStart = Worker.getInstance().removeElement(this.getCoors());
        Element tmpFinish = Worker.getInstance().removeElement(new Coordinates(x, y));

        if (tmpStart == null)
            return;

        if (tmpFinish != null) {
            tmpFinish.setX(tmpStart.getX());
            tmpFinish.setY(tmpStart.getY());
        }

        tmpStart.setX(x);
        tmpStart.setY(y);

        if (tmpFinish != null)
            Worker.getInstance().addElement(tmpFinish.getCoors(), tmpFinish);
        Worker.getInstance().addElement(tmpStart.getCoors(), tmpStart);
    }

    public abstract void applyGravity();

    public abstract Paint getTexture();
}
