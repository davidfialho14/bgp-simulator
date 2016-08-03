package policies;

import io.InvalidTagException;
import policies.gaorexford.GaoRexfordPolicy;
import policies.roc.RoCPolicy;
import policies.shortestpath.ShortestPathPolicy;

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
        policy("RoC", new RoCPolicy());
        policy("GaoRexford", new GaoRexfordPolicy());
    }

    // --- Stop editing from now on ---------------------------------------------------------------------------------

    // --- PUBLIC INTERFACE -----------------------------------------------------------------------------------------

    private Policies() {
    }   // can not be instantiated

    // --- PRIVATE METHODS ------------------------------------------------------------------------------------------

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

    private static void policy(String tag, Policy policy) {
        policies.put(tag, policy);
    }
}
