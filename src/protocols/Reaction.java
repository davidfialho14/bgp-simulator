package protocols;

import core.Attribute;
import core.Route;
import core.topology.Link;

/**
 * Interface for the reaction to a policy-based oscillation. Reactions might have a state, where they keep
 * information of previous detections in order to react accordingly. Therefore, they must be reset before each
 * simulation.
 */
public interface Reaction {

    /**
     * Extends an attribute using the given link. By default, it simply extends the attribute using the 'extend'
     * operation of the link. However, it might implemented to apply some modifications to the simple
     * 'extend' operation, changing the import policies of the protocol.
     *
     * @param attribute   attribute to be extended.
     * @param link        link to extend the attribute.
     * @return extended attribute (new instance).
     */
    default Attribute extend(Attribute attribute, Link link) {
        return link.extend(attribute);
    }

    /**
     * Provides the information used to detect the policy dispute. This method must be called right after a
     * detection takes place to provide information about it. This allows the reaction to identify detections
     * and to take action depending on the detection information.
     *
     * @param link              link from which the new route was learned.
     * @param learnedRoute      new learned route.
     * @param alternativeRoute  most preferred route learned from other neighbor (not the destination node of the link)
     */
    void detectionInfo(Link link, Route learnedRoute, Route alternativeRoute);

    /**
     * Resets the state of the reaction. Reactions might have a state, where they keep information of previous
     * detections in order to react accordingly. Therefore, they must be reset before each simulation.
     */
    void reset();

}
