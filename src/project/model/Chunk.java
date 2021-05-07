package project.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Chunk {
    private Map<Coordinates, Element> items = new ConcurrentHashMap<>();

    public Map<Coordinates, Element> getItems() {
        return items;
    }
}
