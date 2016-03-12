package protocols;

import network.Protocol;
import network.ProtocolFactory;

public class DummyProtocolFactory implements ProtocolFactory {

    @Override
    public Protocol createProtocol(int id) {
        return new DummyProtocol();
    }

}
