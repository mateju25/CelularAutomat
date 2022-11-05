package project.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import project.model.Coordinates;
import project.model.gas.Fire;

public class Main extends Application {
    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Main.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/project/gui/mainscreen.fxml"));
        Main.primaryStage.setScene(new Scene(root));
//        Main.primaryStage.setMaximized(true);
        Main.primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
