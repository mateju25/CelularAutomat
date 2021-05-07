package project.model;

import java.util.Map;

public interface Liquid {
    boolean moveLeft(Map<Coordinates, Element> itemMap);

    boolean moveRight(Map<Coordinates, Element> itemMap);

    boolean moveDown(Map<Coordinates, Element> itemMap);
}
