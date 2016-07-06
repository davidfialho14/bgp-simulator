package gui.gradualdeployment;

import gui.basics.NumberSpinner;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class GradualDeploymentController implements Initializable {

    public GridPane controlsPane;
    public ToggleButton activateToggle;
    public Spinner<Integer> deployPeriodSpinner;
    public Spinner<Integer> deployPercentageSpinner;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        NumberSpinner.setupNumberSpinner(deployPeriodSpinner, 0, Integer.MAX_VALUE, 10);
        NumberSpinner.setupNumberSpinner(deployPercentageSpinner, 0, 100, 10, 10);
    }

    public void enableGradualDeployment(ActionEvent actionEvent) {

        if (activateToggle.isSelected()) {
            activateToggle.setText("On");
            controlsPane.setDisable(false);
        } else {
            activateToggle.setText("Off");
            controlsPane.setDisable(true);
        }
    }
}
