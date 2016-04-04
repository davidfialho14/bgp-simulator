package io;

import protocols.Protocol;
import protocols.implementations.BGPProtocol;

import java.util.HashMap;
import java.util.Map;

public class Protocols {

    // maps the tags to the protocols available
    private static Map<String, Protocol> protocols = new HashMap<>();

    // --- Edit under this line to add more protocols ---------------------------------------------------------------

    /**
     * Sets up the set of protocols available by associating a tag with a protocol.
     */
    static {
        protocol("BGP", new BGPProtocol());
    }

    // --- Stop editing from now on ---------------------------------------------------------------------------------

    // --- PUBLIC INTERFACE -----------------------------------------------------------------------------------------

    /**
     * Parses the tag and returns the respective protocol instance.
     * @param tag tag to be parsed.
     * @return protocol instance corresponding to the given tag.
     */
    public static Protocol getProtocol(String tag) throws InvalidTagException {
        Protocol protocol = protocols.get(tag);

        if (protocol == null) {
            throw new InvalidTagException(tag, "the protocol tag is not valid");
        }

        return protocol;
    }

    // --- PRIVATE METHODS ------------------------------------------------------------------------------------------

    public static void protocol(String tag, Protocol protocol) {
        protocols.put(tag, protocol);
    }

    private Protocols() {}   // can not be instantiated
}