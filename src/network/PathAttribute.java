package network;

public class PathAttribute implements Attribute {

    private static final PathAttribute INVALID = new PathAttribute();

    // TODO replace the default constructor with the static method createInvalid()
    // TODO make the default constructor private

    /**
     * Constructs and invalid path.
     */
    public PathAttribute() {
    }

    public PathAttribute(Node node) {
        // TODO - implement PathAttribute.PathAttribute
        // throw new UnsupportedOperationException();
    }

    /**
     * Returns an invalid path instance.
     * @return invalid path instance.
     */
    public static PathAttribute createInvalid() {
        return INVALID;
    }

    public void add(Node destination) {
        // TODO - implement PathAttribute.add
        throw new UnsupportedOperationException();
    }

    public boolean contains(Node node) {
        // TODO - implement PathAttribute.contains
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isInvalid() {
        // TODO - implement PathAttribute.isInvalid
        throw new UnsupportedOperationException();
    }

    @Override
    public int compareTo(Attribute attribute) {
        // TODO - implement PathAttribute.compareTo
        throw new UnsupportedOperationException();
    }

}
