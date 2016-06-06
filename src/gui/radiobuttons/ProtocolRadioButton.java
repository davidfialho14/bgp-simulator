package gui.radiobuttons;

import javafx.scene.control.RadioButton;
import protocols.Protocol;

/**
 * A protocol radio button associates a protocol with a radio button. This can be used to select the
 * protocol to be used. Allows obtaining the selected protocol without having to check for the selected radio button
 * in the toggle group using polymorphism.
 */
public abstract class ProtocolRadioButton extends RadioButton {

    /**
     * Returns the protocol instance being represented by the radio button. This should be called for the selected
     * protocol radio button to obtain the selected protocol.
     *
     * @return protocol instance depending on the selected protocol.
     */
    public abstract Protocol getProtocol();
}
