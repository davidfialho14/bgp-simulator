package network;

public interface AttributeFactory {

    /**
     * Creates a self attribute corresponding to the given node.
     * @param node node to create self attribute for.
     * @return instance of a self attribute implementation.
     */
    Attribute createSelf(Node node);

}
