package gui;

import com.alexmerz.graphviz.ParseException;
import gui.basics.NumberSpinner;
import gui.partialdeployment.PartialDeploymentController;
import io.CSVReporter;
import io.Reporter;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import simulation.simulators.PartialDeploymentSimulator;
import simulation.simulators.Simulator;
import simulation.simulators.StandardSimulator;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Controller implements Initializable {

    public GridPane pane;
    public TextField networkTextField;
    public Button startButton;
    public Spinner<Integer> destinationIdSpinner;
    public Spinner<Integer> repetitionsSpinner;
    public PartialDeploymentController partialDeploymentFormController;

    private FileChooser fileChooser = new FileChooser();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // ensure the start button is disabled when the text field is empty
        networkTextField.textProperty().addListener((observable, oldText, newText) -> {
            startButton.setDisable(newText.isEmpty());
        });

        NumberSpinner.setupNumberSpinner(destinationIdSpinner, 0, Integer.MAX_VALUE, 0);
        NumberSpinner.setupNumberSpinner(repetitionsSpinner, 1, Integer.MAX_VALUE, 1);

        // accept only *.gv file in the file chooser
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Network files (*.gv)", "*.gv"));
    }

    /**
     * Handles browsing for network files. Opens up a file chooser for the user to select the network files to
     * simulate.
     */
    public void handleClickedBrowseButton(ActionEvent actionEvent) {
        List<File> files = fileChooser.showOpenMultipleDialog(pane.getScene().getWindow());

        if (files != null) {
            List<String> filePaths = files.stream()
                                             .filter(File::exists)
                                             .map(File::getAbsolutePath)
                                             .collect(Collectors.toList());

            networkTextField.setText(String.join(" ; ", filePaths));
        }
    }

    public void handleClickedStartButton(ActionEvent actionEvent) {

        for (String networkFilePath : networkTextField.getText().split(" ; ")) {
            simulate(new File(networkFilePath));
        }

    }

    /**
     * Simulates each network file.
     *
     * @param networkFile network file to be simulated.
     */
    private void simulate(File networkFile) {
        int destinationId = destinationIdSpinner.getValue();
        int repetitionCount = repetitionsSpinner.getValue();

        // simulator that will be used to simulate
        Simulator simulator;

        if (!partialDeploymentFormController.activateToggle.isSelected()) {
            simulator = new StandardSimulator(networkFile, destinationId, repetitionCount);
        } else {
            int timeToChange = partialDeploymentFormController.detectingTimeSpinner.getValue();
            simulator = new PartialDeploymentSimulator(networkFile, destinationId, repetitionCount, timeToChange);
        }

        Reporter reporter = new CSVReporter();

        try {
            simulator.simulate(reporter);

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "can't open the file", ButtonType.OK);
            alert.setHeaderText("Network File Error");
            alert.showAndWait();
        } catch (ParseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "network file is corrupted", ButtonType.OK);
            alert.setHeaderText("Invalid File Error");
            alert.showAndWait();
        }

        try {
            // store the report file in the same directory as the network file and with the same name bu with
            // different extension
            String reportFileName = networkFile.getName().replaceFirst("\\.gv", ".csv");
            reporter.generate(new File(networkFile.getParent(), reportFileName));

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "could not generate report", ButtonType.OK);
            alert.setHeaderText("Report File Error");
            alert.showAndWait();
        }
    }

}
