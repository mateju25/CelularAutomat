package project.gui;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import project.model.*;

import java.util.Random;

import static java.lang.Thread.sleep;
import static javafx.scene.paint.Color.rgb;

public class Controller {
    public Canvas canvas;
    public Pane pane;
    public Label textMode;

    private GraphicsContext gc;

    private int size = 3;
    private int mode = 1;
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
        for (Element item : worker.getItems().values()) {
            gc.setFill(item.getTexture());
            gc.fillRect(item.getCoors().getX()-size/2, item.getCoors().getY()-size/2, size, size);
        }
    }

    private void createPoint(int x, int y) {
        switch (mode) {
            case 1: worker.addPoint(new Sand(new Coordinates(x, y))); break;
            case 2: worker.addPoint(new Water(new Coordinates(x, y))); break;
            case 3: worker.addPoint(new Wall(new Coordinates(x, y))); break;
            case 4: worker.addPoint(new Vapor(new Coordinates((int) (x + new Random().nextDouble()*9 - 3), (int) (y+ new Random().nextDouble()*9 - 3)))); break;
        }
    }

    public void addPoint(MouseEvent mouseEvent) throws InterruptedException {
        cancelTimer(mouseEvent);

        int x = (int) ((mouseEvent.getX()) / size) * size;
        int y = (int) ((mouseEvent.getY()) / size) * size;
        createPoint(x, y);
    }

    public void moreFunc(KeyEvent keyEvent) {
        if (keyEvent.getCode().getName().equals("Space")) {
            worker.clearPoints();
        }
        if (keyEvent.getCode().getName().equals("Numpad 1")) {
            mode = 1;
            textMode.setText("1");
        }
        if (keyEvent.getCode().getName().equals("Numpad 2")) {
            mode = 2;
            textMode.setText("2");
        }
        if (keyEvent.getCode().getName().equals("Numpad 3")) {
            mode = 3;
            textMode.setText("3");
        }
        if (keyEvent.getCode().getName().equals("Numpad 4")) {
            mode = 4;
            textMode.setText("4");
        }
    }

    private Thread timer = null;
    private boolean stop = true;

    public void startTimer(MouseEvent mouseEvent) {
        if (timer == null) {
            stop = true;
            timer = new Thread(() -> {
                while (stop) {
                    int x = (int) ((mouseEvent.getX()) / size) * size;
                    int y = (int) ((mouseEvent.getY()) / size) * size;
                    createPoint(x, y);
                    try {
                        sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            timer.setDaemon(true);
            timer.start();
        }
    }

    public void cancelTimer(MouseEvent mouseEvent) throws InterruptedException {
        stop = false;
        if (timer != null)
            timer.join();
        timer = null;
    }
}
