package project.model;

import project.model.generators.Generator;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Worker {
    private int maxHeight;
    private int maxWidth;
    private int size;
    private int chunkSize = 35;

    private static Worker single_instance = null;

    private  Map<Coordinates, Chunk> chunks = new ConcurrentHashMap<>();

    public Element getElement(Coordinates coors) {
        var keyCoors = new Coordinates(coors.getX()/chunkSize, coors.getY()/chunkSize);
        var chunk = chunks.get(keyCoors);
        if (chunk != null) {
            Element tmp = chunk.getItems().get(coors);
            return tmp;
        }
        return null;
    }

    public Element removeElement(Coordinates coors) {
        var keyCoors = new Coordinates(coors.getX()/chunkSize, coors.getY()/chunkSize);
        var chunk = chunks.get(keyCoors);
        if (chunk != null) {
            Element tmp = chunk.getItems().remove(coors);
            if (chunk.getItems().size() == 0)
                chunks.remove(keyCoors);
            else
                notifyNeighboursChunks(keyCoors);
            return tmp;
        }
        return null;
    }

    public void addElement(Coordinates coors, Element newElement) {
        var keyCoors = new Coordinates(coors.getX()/chunkSize, coors.getY()/chunkSize);
        var chunk = chunks.get(keyCoors);
        if (chunk != null) {
            if (!chunk.getItems().containsKey(coors))
                chunk.getItems().put(coors, newElement);
        } else {
            Chunk tmp = new Chunk();
            chunks.put(keyCoors,tmp);
            tmp.getItems().put(coors, newElement);
        }
        notifyNeighboursChunks(keyCoors);
    }

    public void notifyNeighboursChunks(Coordinates coors) {
        var chunk = chunks.get(coors);
        if (chunk != null)
            chunk.setTobeRendered(true);
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                var tmp = chunks.get(new Coordinates(coors.getX() + i, coors.getY() + j));
                if (tmp == null)
                    continue;
                tmp.setTobeRendered(true);
            }
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
            ExecutorService executor = Executors.newWorkStealingPool(4);
            for (Chunk chunk : new ConcurrentHashMap<>(chunks).values()) {
                if (!chunk.getTobeRendered())
                    continue;
                chunk.setTobeRendered(false);
                executor.execute(() -> {
                    for (Element item : new ConcurrentHashMap<>(chunk.getItems()).values()) {
                        if (item instanceof Movable) {
                            item.applyGravity();
                        } else {
                            if (item instanceof Generator)
                                ((Generator) item).generateElements();
                        }
                    }
                });
            }
            executor.shutdown();
        }
    }

    public void clearPoints() {
        chunks.clear();
    }
}
