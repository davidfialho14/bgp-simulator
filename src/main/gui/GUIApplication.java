package main.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUIApplication extends Application {

    /**
     * Launch method to avoid RuntimeException. If we call the launch method of the application from another class
     * (for instance the Main class) it throws a RuntimeException. This is a clean hack to prevent that.
     */
    public static void launch(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gui.fxml"));
        Parent root = fxmlLoader.load();

        primaryStage.setTitle("Routing Simulator");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
