package gui.radiobuttons;

import protocols.D1R1Protocol;
import protocols.Protocol;

/**
 * Used to select the D1R1 protocol.
 */
public class D1R1ProtocolRadioButton extends ProtocolRadioButton {

    /**
     * Returns an instance of the D1R1 protocol.
     *
     * @return instance of the D1R1 protocol
     */
    @Override
    public Protocol getProtocol() {
        return new D1R1Protocol();
    }

}
