package gui;

import protocols.Protocol;
import protocols.BGPProtocol;

/**
 * A BGP protocol radio button associates the BGP protocol with a radio button.
 */
public class BGPProtocolRadioButton extends ProtocolRadioButton {

    /**
     * Returns the BGP protocol.
     * 
     * @return the BGP protocol.
     */
    public Protocol getProtocol() {
        return new BGPProtocol();
    }
}
