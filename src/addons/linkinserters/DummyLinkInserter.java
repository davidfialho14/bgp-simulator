package addons.linkinserters;

import core.topology.Link;
import core.topology.Network;

/**
 * Dummy implementation of a link inserter. It does not insert any link ever.
 */
public class DummyLinkInserter implements LinkInserter {

    @Override
    public Link insertAnyLink(Network network, long currentTime) {
        return null;
    }
}
