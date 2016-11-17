package v2.core;

/**
 * An attribute class models a policy attribute that can be assigned to a route. All attribute implementations must
 * implement this interface in order to work with the engine. Attributes must always be immutable. Attributes define
 * the policy preference of a route. They can be compared and this comparison depends on the implementation. All
 * attribute implementations must be support comparison with the invalid attribute.
 */
public interface Attribute extends Comparable<Attribute> {
}
