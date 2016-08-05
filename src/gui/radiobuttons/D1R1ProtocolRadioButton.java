package gui.radiobuttons;

import protocols.D1R1Protocol;
import core.Protocol;

/**
 * A D1R1 protocol radio button associates the D1R1 protocol with a radio button.
 */
public class D1R1ProtocolRadioButton extends ProtocolRadioButton {

    /**
     * Returns the D1R1 protocol.
     * 
     * @return the D1R1 protocol.
     */
    public Protocol getProtocol() {
        return new D1R1Protocol();
    }
}
