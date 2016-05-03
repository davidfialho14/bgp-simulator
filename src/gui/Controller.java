package gui;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.io.File;

public class Controller {

    public GridPane pane;
    public TextField networkTextField;

    private FileChooser fileChooser = new FileChooser();

    public void handleClickedBrowseButton(ActionEvent actionEvent) {
        File file = fileChooser.showOpenDialog(pane.getScene().getWindow());

        if (file != null && file.exists()) {
            networkTextField.setText(file.getAbsolutePath());
        }
    }
}
