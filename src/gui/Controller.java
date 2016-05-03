package gui;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.io.File;
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

        // enable destination spinner only when the one node radio button is selected
        oneNodeRadioButton.selectedProperty().addListener((observable, oldState, newState) -> {
            destinationIdSpinner.setDisable(!newState);
        });
    }

    public void handleClickedBrowseButton(ActionEvent actionEvent) {
        File file = fileChooser.showOpenDialog(pane.getScene().getWindow());

        if (file != null && file.exists()) {
            networkTextField.setText(file.getAbsolutePath());
        }
    }
}
