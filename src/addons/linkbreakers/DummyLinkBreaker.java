package addons.linkbreakers;

import network.Link;
import network.Network;

/**
 * A dummy link breaker does not break any link ever. Used when the purpose is not to break links.
 */
public class DummyLinkBreaker extends AbstractLinkBreaker {

    @Override
    public Link breakAnyLink(Network network, long currentTime) {
        return null;
    }
}
