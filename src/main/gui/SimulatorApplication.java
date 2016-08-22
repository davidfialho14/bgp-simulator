package main.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class SimulatorApplication extends Application {

    private static File defaultNetworkFile = null;

    public static void launch(String[] args, File networkFile) {
        defaultNetworkFile = networkFile;
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gui.fxml"));
        Parent root = fxmlLoader.load();

        if (defaultNetworkFile != null) {
            Controller controller = fxmlLoader.getController();
            controller.setDefaultNetworkFile(defaultNetworkFile);
        }

        primaryStage.setTitle("Routing Simulator");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
