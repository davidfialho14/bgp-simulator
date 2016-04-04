package policies.implementations.shortestpath;

import policies.Attribute;
import policies.Label;
import network.Link;

public class ShortestPathLabel implements Label {

    int length;

    public ShortestPathLabel(int length) {
        this.length = length;
    }

    /**
     * It returns a ShortestPathAttribute with the length equal to the sum of the given attribute length and the
     * label length.
     * @param link link used to extend.
     * @param attribute attribute to be extended.
     * @return extended attribute.
     */
    @Override
    public Attribute extend(Link link, Attribute attribute) {
        if (attribute.isInvalid()) {
            return new ShortestPathAttribute();
        } else {
            ShortestPathAttribute shortestPathAttribute = (ShortestPathAttribute) attribute;
            return new ShortestPathAttribute(length + shortestPathAttribute.length);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShortestPathLabel that = (ShortestPathLabel) o;

        return length == that.length;

    }

    @Override
    public int hashCode() {
        return length;
    }

    @Override
    public String toString() {
        return "SPLabel(" + length + ')';
    }
}
