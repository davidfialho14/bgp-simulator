package addons.linkinserters;

import core.network.Link;
import core.network.Network;

/**
 * Inserts one link at a fixed instant of time.
 */
public class FixedTimeLinkInserter extends AbstractLinkInserter {

    private final Link linkToInsert;
    private final long timeToInsert;
    private boolean alreadyInserted = false;

    public FixedTimeLinkInserter(Link linkToInsert, long timeToInsert) {
        this.linkToInsert = linkToInsert;
        this.timeToInsert = timeToInsert;
    }

    /**
     * Inserts the configured link at the configured fixed instant of time.
     *
     * @param network     core.network to insert link on.
     * @param currentTime current time of simulation.
     * @return link inserted or null is no link was inserted.
     */
    @Override
    public Link insertAnyLink(Network network, long currentTime) {
        Link insertedLink = null;

        if (!alreadyInserted && currentTime >= timeToInsert) {
            insert(linkToInsert, network);
            alreadyInserted = true;
            insertedLink = linkToInsert;
        }

        return insertedLink;
    }
}
