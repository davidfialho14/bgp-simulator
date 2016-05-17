package gui.partialdeployment;

import javafx.event.ActionEvent;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;

public class PartialDeploymentController {

    public GridPane statsPane;
    public ToggleButton activateToggle;

    public void enablePartialDeployment(ActionEvent actionEvent) {

        if (activateToggle.isSelected()) {
            activateToggle.setText("On");
            statsPane.setDisable(false);
        } else {
            activateToggle.setText("Off");
            statsPane.setDisable(true);
        }
    }
}
