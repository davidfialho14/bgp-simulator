package gui.partialdeployment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Just for testing the partial deployment form!
 */
public class PartialDeploymentApp extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("partial_deployment.fxml"));

        primaryStage.setTitle("Routing Simulator");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}
