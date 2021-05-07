package project.model;

public interface Liquid {
    boolean moveLeft(Element[][] itemMap);

    boolean moveRight(Element[][] itemMap);

    boolean moveDown(Element[][] itemMap);
}
