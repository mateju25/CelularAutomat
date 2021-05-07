package project.model;

import java.util.Map;

public interface Generator {
    void generateElements(Map<Coordinates, Element> itemMap);
}
