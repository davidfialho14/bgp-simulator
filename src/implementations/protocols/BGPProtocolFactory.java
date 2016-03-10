package implementations.protocols;

import network.Protocol;
import network.ProtocolFactory;

public class BGPProtocolFactory implements ProtocolFactory {

    @Override
    public Protocol createProtocol(int id) {
        return new BGPProtocol();
    }

}
