package project.model;

import java.util.Map;

public interface Gas {
    boolean moveLeft(Map<Coordinates, Element> itemMap);

    boolean moveRight(Map<Coordinates, Element> itemMap);

    boolean moveUp(Map<Coordinates, Element> itemMap);
}
