package io.topologyreaders;


import core.Policy;
import io.topologyreaders.exceptions.InvalidPolicyTagException;

import java.util.HashMap;
import java.util.Map;

/**
 * PolicyTagger is static class responsible for reading the policy tag when reading a topology through a
 * topology reader. Associates a policy instance with a tag. In order to have support for reading a topology with a
 * certain policy, this policy must be registered in the policy tagger.
 */
public final class PolicyTagger {

    // maps the tags to the registered policies
    private static Map<String, Policy> policies = new HashMap<>();

    private PolicyTagger() {
    }   // can not be instantiated

    /**
     * Registers a new policy with the given tag. If the tag is already being used for another policy it will not
     * register the policy.
     *
     * @param policy policy to register with the tag.
     * @param tag    tag to identify the policy.
     * @return true if it registered successfully and false otherwise.
     */
    public static boolean register(Policy policy, String tag) {
        return policies.putIfAbsent(tag, policy) == null;
    }

    /**
     * Parses the tag and returns the respective policy instance. Must be package protected since only the topology
     * readers should be able to get the policy from the tag.
     *
     * @param tag tag to be parsed.
     * @return policy instance corresponding to the given tag.
     */
    static Policy getPolicy(String tag) throws InvalidPolicyTagException {
        Policy policy = policies.get(tag);

        if (policy == null) {
            throw new InvalidPolicyTagException(tag, "the policy tag is not valid");
        }

        return policy;
    }

}
