package project.model;

import javafx.scene.paint.Paint;

import java.util.Map;

import static javafx.scene.paint.Color.rgb;

public class WaterGenerator extends Element implements Immovable, Generator {
    public WaterGenerator(Coordinates coors) {
        super(coors);
    }

    @Override
    public void applyGravity(Map<Coordinates, Element> itemMap) {
    }

    @Override
    public Paint getTexture() {
        return rgb(255, 0, 255);
    }

    @Override
    public void generateElements(Map<Coordinates, Element> itemMap) {
        int size = Worker.getInstance().getSize();
        var tmp = new Coordinates(getX(), getY() + size);
        itemMap.put(tmp, new Water(tmp));
    }
}
