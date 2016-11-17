package v2.wrappers;


import v2.core.Router;

/**
 * Implements wrapper methods to create topology components in a more readable way.
 */
public interface TopologyWrapper {


    /**
     * More readable way to create a router instance providing only a router ID.
     *
     * @param routerID id to assigning to the new router.
     * @return new instance of a router with the given ID.
     */
    static Router router(int routerID) {
        return new Router(routerID);
    }

}
