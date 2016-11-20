package core;

import static core.InvalidPath.invalidPath;

/**
 * Represents an invalid route. An invalid route has invalid attribute and path. This is implemented as as
 * singleton.
 */
public class InvalidRoute extends Route {

    // It exists only one unique Invalid Route instance that can be accessed through the
    // static method invalidRoute().
    private static final InvalidRoute invalid = new InvalidRoute();

    private InvalidRoute() {    // not be instantiated directly
        super(InvalidAttribute.invalidAttr(), invalidPath());
    }

    /**
     * Returns always the same instance of an invalid route. Its the only way to get an invalid route
     * instance.
     *
     * @return invalid route instance (not a new instance).
     */
    public static InvalidRoute invalidRoute() {
        return invalid;
    }

    /**
     * The invalid route is equal to other invalid routes and greater than any other route.
     *
     * @param route route to be compared.
     * @return 0 if route is invalid or greater than 0 if is not an invalid route.
     */
    @Override
    public int compareTo(Route route) {
        return route == invalidRoute() ? 0 : 1;
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object other) {
        return other == invalidRoute();
    }

    // HASHCODE - Since there is only one object the default hashCode() implementation is sufficient

    @Override
    public String toString() {
        return "•";
    }

}
