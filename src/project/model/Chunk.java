package project.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
public class Chunk {
    private Map<Coordinates, Element> items = new ConcurrentHashMap<>();
    private Coordinates coors;
    private boolean tobeRendered = true;
}
