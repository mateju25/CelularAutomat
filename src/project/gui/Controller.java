package project.gui;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import project.model.*;

import static java.lang.Thread.sleep;
import static javafx.scene.paint.Color.rgb;

public class Controller {
    public Canvas canvas;
    public StackPane pane;

    private GraphicsContext gc;

    private int size = 4;
    private Worker worker;

    public void initialize() {
        gc = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);
        gc.setFill(rgb(0, 0, 0));
        gc.fillRect(0, 0, canvas.getWidth(),  canvas.getHeight());

        worker = Worker.getInstance((int) canvas.getHeight(), (int) canvas.getWidth(), size);

        Thread thread = new Thread(() -> {
            while (true) {
                worker.applyGravity();
                Platform.runLater(() -> refreshPoints());
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public void refreshPoints() {
        gc.setFill(rgb(0, 0, 0));
        gc.fillRect(0, 0, canvas.getWidth(),  canvas.getHeight());
        for (Element item :
                worker.getItems().values()) {
            if (item instanceof Sand)
                gc.setFill(rgb(255, 191, 0));
            if (item instanceof Water)
                gc.setFill(rgb(0, 55, 255));
            gc.fillRect(item.getCoors().getX()-size/2, item.getCoors().getY()-size/2, size, size);
        }
    }

    public void addPoint(MouseEvent mouseEvent) {
        int x = (int) ((mouseEvent.getX()) / size) * size;
        int y = (int) ((mouseEvent.getY()) / size) * size;
        if (mouseEvent.getButton().name().equals("PRIMARY"))
            worker.addPoint(new Sand(new Coordinates(x, y)));
        if (mouseEvent.getButton().name().equals("SECONDARY"))
            worker.addPoint(new Water(new Coordinates(x, y)));
    }

    public void moreFunc(KeyEvent keyEvent) {
        if (keyEvent.getCode().getName().equals("Space")) {
            worker.clearPoints();
        }
    }
}
