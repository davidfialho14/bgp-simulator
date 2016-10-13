package protocols;

import core.Attribute;
import core.Protocol;
import core.Route;
import core.topology.Link;

/**
 * This is a generic implementation of a protocol that can be configured to implement any combination of
 * detection-reaction implementations. Therefore, it requires the actual detection and reaction implementations to be
 * provided.
 */
public class GenericProtocol implements Protocol {

    private final Detection detection;
    private final Reaction reaction;

    /**
     * Initializes the protocol given the detection and reaction implementations.
     *
     * @param detection detection implementation to be used.
     * @param reaction  reaction implementation to be used.
     */
    public GenericProtocol(Detection detection, Reaction reaction) {
        this.detection = detection;
        this.reaction = reaction;
    }

    /**
     * Extends the attribute using the given link, while applying some modifications to the import properties
     * according to the protocol implemented.
     *
     * @param attribute   attribute to be extended.
     * @param link        link to extend the attribute.
     * @return extended attribute (new instance).
     */
    @Override
    public Attribute extend(Attribute attribute, Link link) {
        return reaction.extend(attribute, link);
    }

    /**
     * Checks if the conditions to detect a policy-based oscillation is verified. This should called every time a new
     * route containing loop is learned.
     *
     * @param link              link from which the new route was learned.
     * @param learnedRoute      new learned route.
     * @param alternativeRoute  most preferred route learned from other neighbor (not the destination node of the link)
     * @return true if the detection conditions are verified and false otherwise.
     */
    @Override
    public boolean isPolicyDispute(Link link, Route learnedRoute, Route alternativeRoute) {
        return detection.isPolicyDispute(link, learnedRoute, alternativeRoute);
    }

    /**
     * Sets the parameters used by the extend operation. This adds more flexibility to configure the extend operation.
     * Some of the following parameters might not be used.
     *
     * @param link         link from which the route was learned.
     * @param learnedRoute route learned by the node.
     * @param alternativeRoute    route preferred excluding the node from which the route was learned.
     */
    @Override
    public void detectionInfo(Link link, Route learnedRoute, Route alternativeRoute) {
        reaction.detectionInfo(link, learnedRoute, alternativeRoute);
    }

    /**
     * Resets the state of the protocol. Protocols must be reset before each simulation.
     */
    @Override
    public void reset() {
        reaction.reset();
    }

    /**
     * Returns a string label of the protocol taking into account both the detection and the reaction implementations.
     *
     * @return string corresponding to the concatenation of the detection and the reaction strings.
     */
    @Override
    public String toString() {
        return detection.toString() + reaction.toString();
    }
}
