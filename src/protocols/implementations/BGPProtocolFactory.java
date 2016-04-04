package protocols.implementations;

import protocols.Protocol;
import protocols.ProtocolFactory;

public class BGPProtocolFactory implements ProtocolFactory {

    @Override
    public Protocol createProtocol(int id) {
        return new BGPProtocol();
    }

}
