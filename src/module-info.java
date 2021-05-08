module JavaFxApplication {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.desktop;
    requires javafx.swing;

    opens project.gui;
}
