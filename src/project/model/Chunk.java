package project.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Chunk {
    private Map<Coordinates, Element> items = new ConcurrentHashMap<>();
    private boolean tobeRendered = true;

    public boolean getTobeRendered() {
        return tobeRendered;
    }

    public void setTobeRendered(boolean tobeRendered) {
        this.tobeRendered = tobeRendered;
    }

    public Map<Coordinates, Element> getItems() {
        return items;
    }
}
