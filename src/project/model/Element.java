package project.model;

import javafx.scene.paint.Paint;

import java.util.Map;

public abstract class Element {
    private Coordinates coors;
    protected boolean toRemove = false;

    public boolean isToRemove() {
        return toRemove;
    }

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
        if (x >= 0 && x <= Worker.getInstance().getMaxWidth() && y >= 0 && y <= Worker.getInstance().getMaxHeight())
            return true;
        else
            return false;
    }

    public void swap(Map<Coordinates, Element> itemMap, int x, int y) {
        Element tmpStart = itemMap.remove(this.getCoors());
        Element tmpFinish = itemMap.remove(new Coordinates(x, y));

        if (tmpStart == null)
            return;

        if (tmpFinish != null) {
            tmpFinish.setX(tmpStart.getX());
            tmpFinish.setY(tmpStart.getY());
        }

        tmpStart.setX(x);
        tmpStart.setY(y);

        if (tmpFinish != null)
            itemMap.put(tmpFinish.getCoors(), tmpFinish);
        itemMap.put(tmpStart.getCoors(), tmpStart);
    }

    public abstract void applyGravity(Map<Coordinates, Element> itemMap);

    public abstract Paint getTexture();
}
