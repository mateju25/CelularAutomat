package project.model;

import project.model.generators.Generator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Worker {
    private int maxHeight;
    private int maxWidth;
    private int size;
    private int chunkSize = 50;

    private static Worker single_instance = null;

    private  Map<Coordinates, Chunk> chunks = new ConcurrentHashMap<>();

    public Element getElement(Coordinates coors) {
        if (chunks.containsKey(new Coordinates(coors.getX()/chunkSize, coors.getY()/chunkSize)))
            return chunks.get(new Coordinates(coors.getX()/chunkSize, coors.getY()/chunkSize)).getItems().get(coors);
        return null;
    }

    public Element removeElement(Coordinates coors) {
        if (chunks.containsKey(new Coordinates(coors.getX()/chunkSize, coors.getY()/chunkSize))) {
            Element tmp = chunks.get(new Coordinates(coors.getX() / chunkSize, coors.getY() / chunkSize)).getItems().remove(coors);
            if (chunks.get(new Coordinates(coors.getX() / chunkSize, coors.getY() / chunkSize)).getItems().size() == 0)
                chunks.remove(new Coordinates(coors.getX() / chunkSize, coors.getY() / chunkSize));
            return tmp;
        }
        return null;
    }

    public void addElement(Coordinates coors, Element newElement) {
        if (chunks.containsKey(new Coordinates(coors.getX()/chunkSize, coors.getY() / chunkSize))) {
            if (!chunks.get(new Coordinates(coors.getX() / chunkSize, coors.getY() / chunkSize)).getItems().containsKey(coors))
                chunks.get(new Coordinates(coors.getX() / chunkSize, coors.getY() / chunkSize)).getItems().put(coors, newElement);
        } else {
            chunks.put(new Coordinates(coors.getX() / chunkSize, coors.getY() / chunkSize), new Chunk());
            chunks.get(new Coordinates(coors.getX() / chunkSize, coors.getY() / chunkSize)).getItems().put(coors, newElement);
        }
    }

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
                addElement(item.getCoors(), item);
            }
        }
    }

    public void removePoint(int x, int y) {
        synchronized (this) {
            if (x>= 0 && x <= Worker.getInstance().getMaxWidth() && y >= 0 && y <= Worker.getInstance().getMaxHeight()) {
                removeElement(new Coordinates(x, y));
            }
        }
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

    public Map<Coordinates, Chunk> getChunks() {
        return chunks;
    }

    public int getChunkSize() {
        return chunkSize;
    }

    public void applyGravity() {
        synchronized (this) {
            for (Chunk chunk : new ConcurrentHashMap<>(chunks).values()) {
                for (Element item : new ConcurrentHashMap<>(chunk.getItems()).values()) {
                    if (item instanceof Movable) {
                        item.applyGravity();
                    } else {
                        if (item instanceof Generator)
                            ((Generator) item).generateElements();
                    }
                }
            }
        }
    }

    public void clearPoints() {
        chunks.clear();
    }
}
