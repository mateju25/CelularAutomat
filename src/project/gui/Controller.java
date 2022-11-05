package project.gui;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
//import javafx.scene.paint.Color;

import project.model.*;
import project.model.gas.Fire;
import project.model.gas.Vapor;
import project.model.generators.MagmaGenerator;
import project.model.generators.VoidGenerator;
import project.model.generators.WaterGenerator;
import project.model.liquid.Magma;
import project.model.liquid.Oil;
import project.model.liquid.Water;
import project.model.solid.Glass;
import project.model.solid.Obsidian;
import project.model.solid.Sand;
import project.model.solid.Wall;

import java.util.Map;
import java.util.Optional;
import java.util.Random;

import static java.lang.Thread.sleep;
import static javafx.scene.paint.Color.hsb;
import static javafx.scene.paint.Color.rgb;

public class Controller {
    public Canvas canvas;
    public Pane pane;
    public Label textMode;
    public Label textNumElements;
    public Label textFps;

    private GraphicsContext gc;

    private int size = 2;
    private int load = 5;
    private int mode = 0;
    private Worker worker;

    private int fps = 30;

    public void initialize() {
        gc = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);
        gc.setFill(rgb(0, 0, 0));
        gc.fillRect(0, 0, canvas.getWidth(),  canvas.getHeight());

        worker = Worker.getInstance((int) canvas.getHeight(), (int) canvas.getWidth(), size);

        Thread thread = new Thread(() -> {
            while (true) {
                for (int i = 0; i < fps; i++) {
                    Platform.runLater(() -> refreshPoints());
                    try {
                        sleep(1000/fps);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.setDaemon(true);
        thread.start();

        Thread secondThread = new Thread(() -> {
            while (true) {
                worker.applyGravity();
                try {
                    sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        secondThread.setDaemon(true);
        secondThread.start();
    }

    public void refreshPoints() {
        gc.setFill(rgb(0, 0, 0));
        gc.fillRect(0, 0, canvas.getWidth(),  canvas.getHeight());
        var count = 0;
        for (Chunk chunk : worker.getChunks().values()) {
            if (chunk.isTobeRendered())
                count++;
                for (Element item : chunk.getItems().values()) {
                    synchronized (this) {
                        gc.setFill(item.getTexture());
                        gc.fillRect(item.getCoors().getX() - size / 2, item.getCoors().getY() - size / 2, size, size);
                    }
                }
//            });
        }
        textNumElements.setText(worker.getChunks().size() + " " + count);
    }

    private void createPoint(int x, int y) {
        for (int i = 0; i < load; i++) {
            for (int j = 0; j < load; j++) {
                x = x + i*size;
                y = y + j*size;
                switch (mode) {
                    case 0: worker.addPoint(new Sand(new Coordinates(x, y))); break;
                    case 1: worker.addPoint(new Water(new Coordinates(x, y))); break;
                    case 2: worker.addPoint(new Wall(new Coordinates(x, y))); break;
                    case 3: worker.addPoint(new Fire(new Coordinates((int) (x + new Random().nextDouble()*9 - 3), (int) (y+ new Random().nextDouble()*9 - 3)))); break;
                    case 4: worker.addPoint(new Vapor(new Coordinates((int) (x + new Random().nextDouble()*9 - 3), (int) (y+ new Random().nextDouble()*9 - 3)))); break;
                    case 5: worker.addPoint(new Magma(new Coordinates(x, y))); break;
                    case 6: worker.addPoint(new Oil(new Coordinates(x, y))); break;
                    case 7: worker.addPoint(new Glass(new Coordinates(x, y))); break;
                    case 8: worker.addPoint(new Obsidian(new Coordinates(x, y))); break;
                    case 9: worker.addPoint(new WaterGenerator(new Coordinates(x, y))); break;
                    case 10: worker.addPoint(new MagmaGenerator(new Coordinates(x, y))); break;
                    case 11: worker.addPoint(new VoidGenerator(new Coordinates(x, y))); break;
                }
                x = x - i*size;
                y = y - j*size;
            }
        }
    }

    public void addPoint(MouseEvent mouseEvent) throws InterruptedException {
        cancelTimer(mouseEvent);
//        System.out.println(mouseEvent.getButton().toString());

        int x = (int) ((mouseEvent.getX()) / size) * size;
        int y = (int) ((mouseEvent.getY()) / size) * size;
        if (mouseEvent.getButton().toString().equals("PRIMARY"))
            createPoint(x, y);
        if (mouseEvent.getButton().toString().equals("SECONDARY")) {
            for (int i = 0; i < load; i++) {
                for (int j = 0; j < load; j++) {
                    x = x + i*size;
                    y = y + j*size;
                    worker.removePoint(x, y);
                    x = x - i*size;
                    y = y - j*size;
                }
            }
        }
    }

    public void moreFunc(KeyEvent keyEvent) {
        if (keyEvent.getCode().getName().equals("Space")) {
            worker.clearPoints();
        }
        if (keyEvent.getCode().getName().equals("Add")) {
            if (mode < 11)
                mode = (mode + 1);
        }
        if (keyEvent.getCode().getName().equals("Subtract")) {
            if (mode > 0)
                mode = (mode - 1);
        }
        switch (mode) {
            case 0: textMode.setText("Sand"); break;
            case 1: textMode.setText("Water"); break;
            case 2: textMode.setText("Wall"); break;
            case 3: textMode.setText("Fire"); break;
            case 4: textMode.setText("Vapor"); break;
            case 5: textMode.setText("Magma"); break;
            case 6: textMode.setText("Oil");  break;
            case 7: textMode.setText("Glass"); break;
            case 8: textMode.setText("Obsidian"); break;
            case 9: textMode.setText("WaterGenerator"); break;
            case 10: textMode.setText("MagmaGenerator"); break;
            case 11: textMode.setText("VoidGenerator"); break;
        }
    }

    private Thread timer = null;
    private boolean stop = true;

    public void startTimer(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().toString().equals("PRIMARY")) {
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
    }

    public void cancelTimer(MouseEvent mouseEvent) throws InterruptedException {
        stop = false;
        if (timer != null)
            timer.join();
        timer = null;
    }
}
