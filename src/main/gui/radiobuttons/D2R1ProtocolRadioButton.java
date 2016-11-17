package main.gui.radiobuttons;

import protocols.D2R1Protocol;
import core.Protocol;

/**
 * A D2R1 protocol radio button associates the D2R1 protocol with a radio button.
 */
public class D2R1ProtocolRadioButton extends ProtocolRadioButton {

    /**
     * Returns the D2R1 protocol.
     * 
     * @return the D2R1 protocol.
     */
    public Protocol getProtocol() {
        return new D2R1Protocol();
    }
}
