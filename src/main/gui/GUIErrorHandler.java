package main.gui;

import io.networkreaders.exceptions.TopologyParseException;
import javafx.scene.control.Alert;
import main.ErrorHandler;

import java.io.IOException;

/**
 * Implements the handling of errors by the GUI application.
 */
public class GUIErrorHandler implements ErrorHandler {

    @Override
    public void onTopologyLoadIOException(IOException exception) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "can't open the file");
        alert.setHeaderText("Network File Error");
        alert.showAndWait();
    }

    @Override
    public void onTopologyLoadParseException(TopologyParseException exception) {
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

}
