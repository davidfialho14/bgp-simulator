package network;

public interface ProtocolFactory {

    /**
     * Creates a new protocol instance for the node with the given id.
     * @param id id of the node to be assigned the protocol instance.
     * @return new instance of a protocol implementation.
     */
    Protocol createProtocol(int id);

}
