package simulation;

import network.Link;
import network.Network;

/**
 * A link inserter inserts new links into a network.
 */
public interface LinkInserter {

    /**
     * Inserts any link into the given network. The insertion of the link may depend on the current time depending on
     * the implementation. If a link is inserted the inserted link is returned and if a link is not inserted null
     * is returned.
     *
     * @param network network to insert link on.
     * @param currentTime current time of simulation.
     * @return link inserted or null is no link was inserted.
     */
    Link insertAnyLink(Network network, long currentTime);

}
