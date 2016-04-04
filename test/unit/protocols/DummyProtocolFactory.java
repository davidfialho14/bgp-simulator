package protocols;

public class DummyProtocolFactory implements ProtocolFactory {

    @Override
    public Protocol createProtocol(int id) {
        return new DummyProtocol();
    }

}
