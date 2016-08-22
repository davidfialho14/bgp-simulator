package addons.linkinserters;

import core.network.Link;
import core.network.Network;

/**
 * Dummy implementation of a link inserter. It does not insert any link ever.
 */
public class DummyLinkInserter implements LinkInserter {

    @Override
    public Link insertAnyLink(Network network, long currentTime) {
        return null;
    }
}
