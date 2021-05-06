package project.model;

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
        if (x >= 0 && x <= Worker.getInstance().getMaxWidth() && y >= 0 && y <= Worker.getInstance().getMaxHeight())
            return true;
        else
            return false;
    }

    public abstract void applyGravity(Element[][] itemMap);
}
