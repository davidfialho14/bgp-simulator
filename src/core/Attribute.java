package core;

/**
 * An attribute class models a policy attribute that can be assigned to a route. All attribute implementations must
 * implement this interface in order to work with the engine. Attributes define the policy preference of a route.
 * They can be compared and this comparison depends on the implementation. All attribute implementations must be
 * support comparison with the invalidAttr attribute.
 */
public interface Attribute extends Comparable<Attribute> {
}
