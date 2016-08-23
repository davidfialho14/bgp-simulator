package main.gui;

import io.networkreaders.GraphvizReaderFactory;
import io.networkreaders.exceptions.ParseException;
import io.reporters.CSVReporterFactory;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import main.ErrorHandler;
import main.SimulatorLauncher;
import main.gui.basics.NumberSpinner;
import main.gui.fulldeployment.FullDeploymentController;
import main.gui.gradualdeployment.GradualDeploymentController;
import main.gui.radiobuttons.ProtocolToggleGroup;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Controller implements Initializable {

    private final SimulatorLauncher simulatorLauncher = new SimulatorLauncher(new ErrorHandler() {
        @Override
        public void onTopologyLoadIOException(IOException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "can't open the file");
            alert.setHeaderText("Network File Error");
            alert.showAndWait();
        }

        @Override
        public void onTopologyLoadParseException(ParseException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "initialTopology file is corrupted");
            alert.setHeaderText("Invalid File Error");
            alert.showAndWait();
        }

        @Override
        public void onReportingIOException(IOException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "failed to open/create/write report file");
            alert.setHeaderText("IO Error");
            alert.showAndWait();
        }
    });
    public GridPane pane;
    public TextField networkTextField;
    public Button startButton;
    public Spinner<Integer> destinationIdSpinner;
    public Spinner<Integer> repetitionsSpinner;
    public Spinner<Integer> minDelaySpinner;
    public Spinner<Integer> maxDelaySpinner;
    public FullDeploymentController fullDeploymentFormController;
    public GradualDeploymentController gradualDeploymentFormController;
    public CheckBox debugCheckBox;
    public ProtocolToggleGroup detectionGroup;
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
     * Updates the networkTextField with the default topology file.
     *
     * @param networkFile default topology file to set.
     */
    public void setDefaultNetworkFile(File networkFile) {
        networkTextField.setText(networkFile.getAbsolutePath());
    }

    /**
     * Handles browsing for topology files. Opens up a file chooser for the user to select the topology files to
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

    /**
     * Starts the simulation according to the current configuration inputted by the user.
     */
    public void handleClickedStartButton(ActionEvent actionEvent) {

        for (String networkFilePath : networkTextField.getText().split(" ; ")) {
            simulate(new File(networkFilePath));
        }

    }

    /**
     * Simulates each topology file.
     *
     * @param topologyFile topology file to be simulated.
     */
    private void simulate(File topologyFile) {

        simulatorLauncher.configure()
                .readerFactory(new GraphvizReaderFactory())
                .minDelay(minDelaySpinner.getValue())
                .maxDelay(maxDelaySpinner.getValue())
                .destinationId(destinationIdSpinner.getValue())
                .repetitionCount(repetitionsSpinner.getValue())
                .protocol(detectionGroup.getSelectedProtocol())
                .reporterFactory(new CSVReporterFactory())
                .enableFullDeployment(fullDeploymentFormController.activateToggle.isSelected())
                    .deployTime(fullDeploymentFormController.detectingTimeSpinner.getValue())
                .enableGradualDeployment(gradualDeploymentFormController.activateToggle.isSelected())
                    .deployPeriod(gradualDeploymentFormController.deployPeriodSpinner.getValue())
                    .deployPercentage(gradualDeploymentFormController.deployPercentageSpinner.getValue())
                .commit();

        String reportFileName = topologyFile.getName().replaceFirst("\\.gv", ".csv");
        File reportFile = new File(topologyFile.getParent(), reportFileName);

        simulatorLauncher.launch(topologyFile, reportFile);

    }

}
