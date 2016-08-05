package core;

import network.Link;
import policies.Attribute;

/**
 * Represents an abstraction of the labels associated with links.
 */
public interface Label {
    // TODO equals and hashcode must be implemented

    Attribute extend(Link link, Attribute attribute);

}
