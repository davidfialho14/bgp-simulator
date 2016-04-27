package simulation;

import network.Link;
import network.Network;

/**
 * Inserts one link at a fixed instant of time.
 */
public class FixedTimeLinkInserter extends AbstractLinkInserter {

    private final Link linkToInsert;
    private final long timeToInsert;

    public FixedTimeLinkInserter(Link linkToInsert, long timeToInsert) {
        this.linkToInsert = linkToInsert;
        this.timeToInsert = timeToInsert;
    }

    /**
     * Inserts the configured link at the configured fixed instant of time.
     *
     * @param network     network to insert link on.
     * @param currentTime current time of simulation.
     * @return link inserted or null is no link was inserted.
     */
    @Override
    public Link insertAnyLink(Network network, long currentTime) {
        Link insertedLink = null;

        if (currentTime >= timeToInsert) {
            insert(linkToInsert, network);
            insertedLink = linkToInsert;
        }

        return insertedLink;
    }
}
