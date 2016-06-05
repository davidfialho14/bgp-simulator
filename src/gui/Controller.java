package gui;

import com.alexmerz.graphviz.ParseException;
import com.alexmerz.graphviz.TokenMgrError;
import gui.basics.NumberSpinner;
import gui.partialdeployment.PartialDeploymentController;
import io.InvalidTagException;
import io.NetworkParser;
import io.reporters.DebugReporter;
import io.reporters.Reporter;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import network.exceptions.NodeExistsException;
import network.exceptions.NodeNotFoundException;
import simulation.Engine;
import simulation.schedulers.RandomScheduler;
import simulators.Simulator;

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
    public Spinner<Integer> minDelaySpinner;
    public Spinner<Integer> maxDelaySpinner;
    public PartialDeploymentController partialDeploymentFormController;
    public CheckBox debugCheckBox;
    public RadioButton htmlRadioButton;

    private FileChooser fileChooser = new FileChooser();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // ensure the start button is disabled when the text field is empty
        networkTextField.textProperty().addListener((observable, oldText, newText) -> {
            startButton.setDisable(newText.isEmpty());
        });

        NumberSpinner.setupNumberSpinner(destinationIdSpinner, 0, Integer.MAX_VALUE, 0);
        NumberSpinner.setupNumberSpinner(repetitionsSpinner, 1, Integer.MAX_VALUE, 1);
        NumberSpinner.setupNumberSpinner(minDelaySpinner, 0, Integer.MAX_VALUE, 0);
        NumberSpinner.setupNumberSpinner(maxDelaySpinner, 0, Integer.MAX_VALUE, 10);

        // enforces the minimum delay is never higher than the maximum delay
        minDelaySpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue > maxDelaySpinner.getValue()) {
                maxDelaySpinner.getValueFactory().setValue(newValue);
            }
        });

        // enforces the maximum delay is never lower than the minimum delay
        maxDelaySpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue < minDelaySpinner.getValue()) {
                minDelaySpinner.getValueFactory().setValue(newValue);
            }
        });

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
        int minDelay = minDelaySpinner.getValue();
        int maxDelay = maxDelaySpinner.getValue();

        try {
            NetworkParser parser = new NetworkParser();
            parser.parse(networkFile);

            Engine engine = new Engine(new RandomScheduler(minDelay, maxDelay));

            // simulator that will be used to simulate
            Simulator simulator = null; // FIXME include simulators

            String debugFilePath = networkFile.getPath().replaceFirst("\\.gv", ".debug");
            simulator.enableDebugReport(debugCheckBox.isSelected(), new File(debugFilePath));

            String reportFileName = networkFile.getName().replaceFirst("\\.gv", ".csv");
            File reportFile = new File(networkFile.getParent(), reportFileName);

            try (Reporter reporter = new DebugReporter(reportFile, parser.getNetwork())) {
                for (int i = 0; i < repetitionCount; i++) {
                    simulator.simulate();
                    reportData(simulator, reporter);
                }
            }

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "can't open the file", ButtonType.OK);
            alert.setHeaderText("Network File Error");
            alert.showAndWait();

        } catch (TokenMgrError | ParseException | NodeExistsException | NodeNotFoundException | InvalidTagException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "network file is corrupted", ButtonType.OK);
            alert.setHeaderText("Invalid File Error");
            alert.showAndWait();
        }
    }

    private void reportData(Simulator simulator, Reporter reporter) {
        try {
            simulator.report(reporter);

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "could not generate report", ButtonType.OK);
            alert.setHeaderText("Report File Error");
            alert.showAndWait();
        }
    }

}
