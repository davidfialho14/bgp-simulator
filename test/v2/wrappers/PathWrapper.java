package v2.wrappers;


import v2.core.Path;
import v2.core.Router;

import static v2.wrappers.TopologyWrapper.router;

/**
 * Implements wrapper methods to create paths in a more readable way.
 */
public interface PathWrapper {

    /**
     * Builds a path of routers given a sequence of IDs.
     *
     * @param routerIds sequence of router ids to add to the path.
     * @return new initialized path instance.
     */
    static Path path(int... routerIds) {
        Router[] routers = new Router[routerIds.length];

        for (int i = 0; i < routerIds.length; i++) {
            routers[i] = router(routerIds[i]);
        }

        return new Path(routers);
    }

    /**
     * Builds a path of routers given a sequence of routers.
     *
     * @param routers sequence of routers to add to the path.
     * @return new initialized path instance.
     */
    static Path path(Router... routers) {
        return new Path(routers);
    }

    /**
     * Builds an empty path of routers.
     *
     * @return new initialized path instance.
     */
    static Path path() {
        return new Path();
    }

}
