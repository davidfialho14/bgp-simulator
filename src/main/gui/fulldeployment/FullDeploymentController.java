package main.gui.fulldeployment;

import main.gui.basics.NumberSpinner;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class FullDeploymentController implements Initializable {

    public GridPane statsPane;
    public ToggleButton activateToggle;
    public Spinner<Integer> detectingTimeSpinner;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        NumberSpinner.setupNumberSpinner(detectingTimeSpinner, 0, Integer.MAX_VALUE, 0);
    }

    public void enableFullDeployment(ActionEvent actionEvent) {

        if (activateToggle.isSelected()) {
            activateToggle.setText("On");
            statsPane.setDisable(false);
        } else {
            activateToggle.setText("Off");
            statsPane.setDisable(true);
        }
    }
}
