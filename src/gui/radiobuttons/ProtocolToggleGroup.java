package gui.radiobuttons;

import javafx.scene.control.ToggleGroup;
import core.Protocol;

/**
 * Toggle group for protocol radio buttons.
 */
public class ProtocolToggleGroup extends ToggleGroup {

    /**
     * Returns the selected protocol from this group.
     *
     * @return selected protocol from this group.
     */
    public Protocol getSelectedProtocol() {
        return ((ProtocolRadioButton) getSelectedToggle()).getProtocol();
    }
}
