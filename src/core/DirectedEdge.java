package core;

/**
 * A directed edge models the connection between two routers. It does no have any state information as the 
 * directed edge does. It is the most basic structure to model a connections between two routers.
 */
public class DirectedEdge {
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private Fields
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    protected final Router source;
    protected final Router target;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Constructors
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Creates a directed edge connecting the given source and target routers
     * 
     * @param source source router of the edge.
     * @param target target router of the edge.
     */
    public DirectedEdge(Router source, Router target) {
        this.source = source;
        this.target = target;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Returns the source router of the directed edge.
     *
     * @return the source router of the directed edge
     */
    public Router getSource() {
        return this.source;
    }

    /**
     * Returns the target router of the directed edge.
     *
     * @return the target router of the directed edge
     */
    public Router getTarget() {
        return this.target;
    }

    /**
     * Two directed edges are considered equal if they share the same source and target routers.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DirectedEdge that = (DirectedEdge) o;

        if (source != null ? !source.equals(that.source) : that.source != null) return false;
        return target != null ? target.equals(that.target) : that.target == null;

    }

    @Override
    public int hashCode() {
        int result = source != null ? source.hashCode() : 0;
        result = 31 * result + (target != null ? target.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return target + "<-" + source;
    }

}
