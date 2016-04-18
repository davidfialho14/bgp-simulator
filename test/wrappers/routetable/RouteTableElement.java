package wrappers.routetable;

/**
 * Represents any element that can belong to a route table: node, link, and route.
 */
public interface RouteTableElement {

    /**
     * Inserts the element in the given routing table according to its type.
     * @param tableWrapper table to insert element in.
     */
    void insert(RouteTableWrapper tableWrapper);
}
