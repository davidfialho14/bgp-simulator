package main.gui.fulldeployment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Just for testing the full deployment form!
 */
public class FullDeploymentApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("full_deployment.fxml"));

        primaryStage.setTitle("Routing Simulator");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}
