package statemodifiers;

import core.Engine;
import core.topology.Link;
import core.topology.Topology;

/**
 * A link breaker is able to break any link while a topology is being processed. It is always associated
 * with a state
 */
public class LinkBreaker {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private Fields
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private Topology topology;  // topology where the links are broken
    private Engine engine;      // engine processing the topology that mus tbe notified of a broken link

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Creates a link breaker associated with a topology and engine. The given topology will be the only
     * topology where links are broken. The given engine will be notified every time a new link is broken
     * so that it can adjust to the change.
     *
     * @param topology topology to break links on
     * @param engine engine to be notified every time a new link is broken
     */
    public LinkBreaker(Topology topology, Engine engine) {
        this.topology = topology;
        this.engine = engine;
    }

    /**
     * Breaks a link from the topology associated with the link breaker. It notifies the engine once the
     * link is removed from the topology.
     *
     * @param link link to be broken
     */
    public void breakLink(Link link) {
        topology.getNetwork().removeLink(link);
        engine.onBrokenLink(link);
    }

}
