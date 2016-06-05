package gui.radiobuttons;

import protocols.D2R1Protocol;
import protocols.Protocol;

/**
 * Used to select the D2R1 protocol.
 */
public class D2R1ProtocolRadioButton extends ProtocolRadioButton {

    /**
     * Returns an instance of the D2R1 protocol.
     *
     * @return instance of the D2R1 protocol
     */
    @Override
    public Protocol getProtocol() {
        return new D2R1Protocol();
    }

}

