package addons.linkinserters;

import core.topology.Link;
import core.topology.Network;

/**
 * A link inserter inserts new links into a topology.
 */
public interface LinkInserter {

    /**
     * Inserts any link into the given topology. The insertion of the link may depend on the current time depending on
     * the implementation. If a link is inserted the inserted link is returned and if a link is not inserted null
     * is returned.
     *
     * @param network topology to insert link on.
     * @param currentTime current time of simulation.
     * @return link inserted or null is no link was inserted.
     */
    Link insertAnyLink(Network network, long currentTime);

}
