package gui;

import com.alexmerz.graphviz.ParseException;
import com.alexmerz.graphviz.TokenMgrError;
import io.HTMLReportGenerator;
import io.InvalidTagException;
import io.TopologyParser;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import network.exceptions.NodeExistsException;
import network.exceptions.NodeNotFoundException;
import simulation.Engine;
import simulation.implementations.handlers.MessageAndDetectionCountHandler;
import simulation.implementations.schedulers.RandomScheduler;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public GridPane pane;
    public TextField networkTextField;
    public Button startButton;
    public Spinner<Integer> destinationIdSpinner;
    public Spinner<Integer> repetitionsSpinner;
    public RadioButton oneNodeRadioButton;

    private FileChooser fileChooser = new FileChooser();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // ensure the start button is disabled when the text field is empty
        networkTextField.textProperty().addListener((observable, oldText, newText) -> {
            startButton.setDisable(newText.isEmpty());
        });

        // accept only positive integers for the destination ID
        destinationIdSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));

        // accept only 1 or more repetitions
        repetitionsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE));

        // commit values when the spinners are unfocused
        repetitionsSpinner.focusedProperty().addListener((observable, oldState, newState) -> {
            if (!newState)
                commitValue(repetitionsSpinner);
        });
        destinationIdSpinner.focusedProperty().addListener((observable, oldState, newState) -> {
            if (!newState)
                commitValue(destinationIdSpinner);
        });

        // enable destination spinner only when the one node radio button is selected
        oneNodeRadioButton.selectedProperty().addListener((observable, oldState, newState) -> {
            destinationIdSpinner.setDisable(!newState);
        });
    }

    private static void commitValue(Spinner<Integer> spinner) {
        String text = spinner.getEditor().getText();
        Integer value = spinner.getValueFactory().getConverter().fromString(text);
        spinner.getValueFactory().setValue(value);
    }

    public void handleClickedBrowseButton(ActionEvent actionEvent) {
        File file = fileChooser.showOpenDialog(pane.getScene().getWindow());

        if (file != null && file.exists()) {
            networkTextField.setText(file.getAbsolutePath());
        }
    }

    public void handleClickedStartButton(ActionEvent actionEvent) {
        TopologyParser parser = parse(new File(networkTextField.getText()));

        if (parser != null) {
            Engine engine = new Engine.Builder(parser.getProtocol(), parser.getPolicy(), new RandomScheduler()).build();
            HTMLReportGenerator reportGenerator = new HTMLReportGenerator();

            for (int i = 0; i < repetitionsSpinner.getValue(); i++) {
                MessageAndDetectionCountHandler handler = new MessageAndDetectionCountHandler();
                engine.setEventHandler(handler);

                if (oneNodeRadioButton.isSelected()) {
                    engine.simulate(parser.getParsedNetwork(), destinationIdSpinner.getValue());
                } else {
                    engine.simulate(parser.getParsedNetwork());
                }

                reportGenerator.addMessageCount(handler.getMessageCount());
                reportGenerator.addDetectionCount(handler.getDetectionCount());

                engine.reset();
            }

            try {
                reportGenerator.generate(new File("data/report.html"));
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "could not generate report", ButtonType.OK);
                alert.setHeaderText("Report File Error");
                alert.showAndWait();
            }
        }
    }

    private static TopologyParser parse(File networkFile) {
        try {
            TopologyParser parser = new TopologyParser();
            parser.parse(networkFile);

            return parser;

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "can't open the file", ButtonType.OK);
            alert.setHeaderText("Network File Error");
            alert.showAndWait();
        } catch (TokenMgrError | ParseException | NodeExistsException | NodeNotFoundException | InvalidTagException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "network file is corrupted", ButtonType.OK);
            alert.setHeaderText("Invalid File Error");
            alert.showAndWait();
        }

        return null;    // there was an error
    }

}
