package v2.core;


/**
 * Label is an abstraction of a relationship between two routers/ASes or the cost of the link connecting
 * them.
 */
public interface Label {
    // IMPORTANT equals and hashcode must be implemented

    Attribute extend(Link link, Attribute attribute);

}
