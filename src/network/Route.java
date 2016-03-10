package network;

public class Route implements Comparable<Route> {

    private Node destination;
    private Attribute attribute;
    private PathAttribute path;

    public Route(Node destination, Attribute attribute, PathAttribute path) {
        this.destination = destination;
        this.attribute = attribute;
        this.path = path;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public void setPath(PathAttribute path) {
        this.path = path;
    }

    public Node getDestination() {
        return destination;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public PathAttribute getPath() {
        return path;
    }

    @Override
    public int compareTo(Route route) {
        // TODO - implement Route.compareTo
        throw new UnsupportedOperationException();
    }

}
