package gui.basics;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public interface NumberSpinner {

    static void setupNumberSpinner(Spinner<Integer> spinner, int min, int max, int initialValue) {
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, initialValue));

        // commit values when the spinner is unfocused
        spinner.focusedProperty().addListener((observable, oldState, newState) -> {
            if (!newState) {
                // commit value
                String text = spinner.getEditor().getText();
                Integer value = spinner.getValueFactory().getConverter().fromString(text);
                spinner.getValueFactory().setValue(value);
            }
        });

        spinner.setEditable(true);
    }

}
