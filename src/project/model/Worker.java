package project.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Worker {
    private int maxHeight;
    private int maxWidth;
    private int size;

    private static Worker single_instance = null;

    private Element[][] itemMap = new Element[10000][10000];
    private  Map<Coordinates, Element> items = new ConcurrentHashMap<>();
    private  Map<Coordinates, Element> createdItems = new ConcurrentHashMap<>();

    private  Worker(int maxHeight, int maxWidth, int size) {
        this.maxHeight = maxHeight;
        this.maxWidth = maxWidth;
        this.size = size;
    }

    private  Worker() {}

    public static Worker getInstance(int maxHeight, int maxWidth, int size) {
        if (single_instance == null)
            single_instance = new Worker(maxHeight, maxWidth, size);

        return single_instance;
    }

    public static Worker getInstance() {
        if (single_instance == null)
            single_instance = new Worker();

        return single_instance;
    }

    public void addPoint(Element item) {
        synchronized (this) {
            if (item.getX() >= 0 && item.getX() <= Worker.getInstance().getMaxWidth() && item.getY() >= 0 && item.getY() <= Worker.getInstance().getMaxHeight()) {
                if (!items.containsKey(item.getCoors())) {
                    items.put(item.getCoors(), item);
                    itemMap[item.getCoors().getX()][item.getCoors().getY()] = item;
                }
            }
        }
    }

    public void removePoint(int x, int y) {
        synchronized (this) {
            if (x>= 0 && x <= Worker.getInstance().getMaxWidth() && y >= 0 && y <= Worker.getInstance().getMaxHeight()) {
                if (items.containsKey(new Coordinates(x, y))) {
                    items.remove(new Coordinates(x, y));
                }
                itemMap[x][y] = null;
            }
        }
    }

    public Map<Coordinates, Element> getItems() {
        return items;
    }

    public Map<Coordinates, Element> getCreatedItems() {
        return createdItems;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public int getSize() {
        return size;
    }

    public void applyGravity() {
        synchronized (this) {
            createdItems = new ConcurrentHashMap<>();
            Map<Coordinates, Element> tmp = new ConcurrentHashMap<>();
            for (Element item : items.values()) {
                if (item instanceof Movable) {
                    if (!item.isToRemove()) {
                        item.applyGravity(itemMap);
                        tmp.put(item.getCoors(), item);
                    }
                } else {
                    tmp.put(item.getCoors(), item);
                }
            }
            items = tmp;
            items.putAll(createdItems);
        }
    }

    public void clearPoints() {
        itemMap = new Element[10000][10000];
        items.clear();
    }
}
