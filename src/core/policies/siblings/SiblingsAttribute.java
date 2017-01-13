package core.policies.siblings;

import core.Attribute;
import core.InvalidAttribute;
import core.policies.gaorexford.GRAttribute;

public class SiblingsAttribute implements Attribute {

    final Attribute attribute;
    final int hopCount;   // number of sibling hops

    private SiblingsAttribute(Attribute attribute, int hopCount) {
        this.attribute = attribute;
        this.hopCount = hopCount;
    }

    public static Attribute customer(int hops) {
        return new SiblingsAttribute(GRAttribute.customer(), hops);
    }

    public static Attribute peer(int hops) {
        return new SiblingsAttribute(GRAttribute.peer(), hops);
    }

    public static Attribute provider(int hops) {
        return new SiblingsAttribute(GRAttribute.provider(), hops);
    }

    @Override
    public int compareTo(Attribute attribute) {
        if (attribute == InvalidAttribute.invalidAttr()) return -1;

        SiblingsAttribute other = (SiblingsAttribute) attribute;

        int comparison = this.attribute.compareTo(other.attribute);
        if (comparison == 0) {
            comparison = this.hopCount - other.hopCount;
        }

        return comparison;
    }

    public Attribute getBaseAttribute() {
        return attribute;
    }

    public int getHopCount() {
        return hopCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SiblingsAttribute)) return false;

        SiblingsAttribute that = (SiblingsAttribute) o;

        if (hopCount != that.hopCount) return false;
        return attribute != null ? attribute.equals(that.attribute) : that.attribute == null;
    }

    @Override
    public int hashCode() {
        int result = attribute != null ? attribute.hashCode() : 0;
        result = 31 * result + hopCount;
        return result;
    }

    @Override
    public String toString() {
        return "(" + attribute + ", " + hopCount + ')';
    }
}
