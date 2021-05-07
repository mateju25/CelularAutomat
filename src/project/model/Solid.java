package project.model;

import java.util.Map;

public interface Solid {
    boolean moveLeftDown(Map<Coordinates, Element> itemMap);

    boolean moveRightDown(Map<Coordinates, Element> itemMap);

    boolean moveDown(Map<Coordinates, Element> itemMap);
}
