package project.gui;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
//import javafx.scene.paint.Color;
import java.awt.Color;
import project.model.*;
import project.model.gas.Vapor;
import project.model.generators.MagmaGenerator;
import project.model.generators.WaterGenerator;
import project.model.liquid.Magma;
import project.model.liquid.Water;
import project.model.solid.Sand;
import project.model.solid.Wall;

import javax.imageio.ImageIO;
import java.util.Random;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import static java.lang.Thread.sleep;
import static javafx.scene.paint.Color.rgb;

public class Controller {
    public Canvas canvas;
    public Pane pane;
    public Label textMode;
    public Label textNumElements;

    private GraphicsContext gc;

    private int size = 2;
    private int load = 2;
    private int mode = 1;
    private Worker worker;
    private javafx.embed.swing.SwingFXUtils SwingFXUtils;

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
//        for (Chunk chunk : worker.getChunks().values()) {
//            gc.setFill(rgb(25, 0, 0));
//            Coordinates item = chunk.getItems().entrySet().stream().findFirst().get().getValue().getCoors();
//            gc.fillRect(item.getX()/worker.getChunkSize() * worker.getChunkSize(), item.getY()/worker.getChunkSize() * worker.getChunkSize(), worker.getChunkSize(),  worker.getChunkSize());
//        }

//        ExecutorService executor = Executors.newWorkStealingPool(2);
        BufferedImage img = new BufferedImage((int) canvas.getWidth() + 20, (int) canvas.getHeight() + 20, TYPE_INT_RGB);

        for (Chunk chunk : worker.getChunks().values()) {
//            executor.execute(() -> {
                for (Element item : chunk.getItems().values()) {
                    Color clr = Color.getHSBColor(item.getTexture()[0],item.getTexture()[1],item.getTexture()[2]);
                    for (int i = 0; i < size; i++) {
                        for (int j = 0; j < size; j++) {
                            img.setRGB(item.getCoors().getX()+i, item.getCoors().getY()+j, clr.getRGB());
                        }
                    }
//                    synchronized (this) {
//                        gc.setFill(item.getTexture());
//                        gc.fillRect(item.getCoors().getX() - size / 2, item.getCoors().getY() - size / 2, size, size);
//                    }
                }
//            });
        }
        Image tempImg = SwingFXUtils.toFXImage(img, null);
        gc.drawImage(tempImg, 0, 0);
//        executor.shutdown();
    }

    private void createPoint(int x, int y) {
        for (int i = 0; i < load; i++) {
            for (int j = 0; j < load; j++) {
                x = x + i*size;
                y = y + j*size;
                switch (mode) {
                    case 1: worker.addPoint(new Sand(new Coordinates(x, y))); break;
                    case 2: worker.addPoint(new Water(new Coordinates(x, y))); break;
                    case 3: worker.addPoint(new Wall(new Coordinates(x, y))); break;
                    case 4: worker.addPoint(new Vapor(new Coordinates((int) (x + new Random().nextDouble()*9 - 3), (int) (y+ new Random().nextDouble()*9 - 3)))); break;
                    case 5: worker.addPoint(new Magma(new Coordinates(x, y))); break;
                    case 8: worker.addPoint(new MagmaGenerator(new Coordinates(x, y))); break;
                    case 9: worker.addPoint(new WaterGenerator(new Coordinates(x, y))); break;
                }
                x = x - i*size;
                y = y - j*size;
            }
        }
//        switch (mode) {
//            case 1: worker.addPoint(new Sand(new Coordinates(x, y))); break;
//            case 2: worker.addPoint(new Water(new Coordinates(x, y))); break;
//            case 3: worker.addPoint(new Wall(new Coordinates(x, y))); break;
//            case 4: worker.addPoint(new Vapor(new Coordinates((int) (x + new Random().nextDouble()*9 - 3), (int) (y+ new Random().nextDouble()*9 - 3)))); break;
//            case 5: worker.addPoint(new Magma(new Coordinates(x, y))); break;
//            case 8: worker.addPoint(new MagmaGenerator(new Coordinates(x, y))); break;
//            case 9: worker.addPoint(new WaterGenerator(new Coordinates(x, y))); break;
//        }
    }

    public void addPoint(MouseEvent mouseEvent) throws InterruptedException {
        cancelTimer(mouseEvent);
//        System.out.println(mouseEvent.getButton().toString());

        int x = (int) ((mouseEvent.getX()) / size) * size;
        int y = (int) ((mouseEvent.getY()) / size) * size;
        if (mouseEvent.getButton().toString().equals("PRIMARY"))
            createPoint(x, y);
        if (mouseEvent.getButton().toString().equals("SECONDARY"))
            worker.removePoint(x, y);
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
        if (keyEvent.getCode().getName().equals("Numpad 5")) {
            mode = 5;
            textMode.setText("5");
        }
        if (keyEvent.getCode().getName().equals("Numpad 9")) {
            mode = 9;
            textMode.setText("9");
        }
        if (keyEvent.getCode().getName().equals("Numpad 8")) {
            mode = 8;
            textMode.setText("8");
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
