package gui;

import javafx.scene.control.RadioButton;
import protocols.Protocol;

/**
 * A protocol radio button associates a protocol with a radio button.
 */
public abstract class ProtocolRadioButton extends RadioButton {

    /**
     * Returns the protocol associated with the radio button.
     *
     * @return the protocol associated with the radio button.
     */
    public abstract Protocol getProtocol();
}
