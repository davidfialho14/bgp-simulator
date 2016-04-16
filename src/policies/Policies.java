package policies;

import io.InvalidTagException;
import policies.implementations.shortestpath.ShortestPathPolicy;

import java.util.HashMap;
import java.util.Map;

public class Policies {

    // maps the tags to the policies available
    private static Map<String, Policy> policies = new HashMap<>();

    // --- Edit under this line to add more protocols ---------------------------------------------------------------

    /**
     * Sets up the set of protocols available by associating a tag with a protocol.
     */
    static {
        policy("ShortestPath", new ShortestPathPolicy());
    }

    // --- Stop editing from now on ---------------------------------------------------------------------------------

    // --- PUBLIC INTERFACE -----------------------------------------------------------------------------------------

    /**
     * Parses the tag and returns the respective policy instance.
     * @param tag tag to be parsed.
     * @return policy instance corresponding to the given tag.
     */
    public static Policy getPolicy(String tag) throws InvalidTagException {
        Policy policy = policies.get(tag);

        if (policy == null) {
            throw new InvalidTagException(tag, "the policy tag is not valid");
        }

        return policy;
    }

    // --- PRIVATE METHODS ------------------------------------------------------------------------------------------

    private static void policy(String tag, Policy policy) {
        policies.put(tag, policy);
    }

    private Policies() {}   // can not be instantiated
}
