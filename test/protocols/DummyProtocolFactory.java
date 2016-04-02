package protocols;

import simulation.Protocol;
import simulation.ProtocolFactory;

public class DummyProtocolFactory implements ProtocolFactory {

    @Override
    public Protocol createProtocol(int id) {
        return new DummyProtocol();
    }

}
