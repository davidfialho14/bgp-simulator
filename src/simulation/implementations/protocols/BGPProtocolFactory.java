package simulation.implementations.protocols;

import simulation.Protocol;
import simulation.ProtocolFactory;

public class BGPProtocolFactory implements ProtocolFactory {

    @Override
    public Protocol createProtocol(int id) {
        return new BGPProtocol();
    }

}
