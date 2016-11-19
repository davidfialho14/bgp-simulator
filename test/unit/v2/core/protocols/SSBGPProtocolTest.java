package v2.core.protocols;

import org.junit.Test;
import v2.core.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static v2.core.InvalidAttribute.invalidAttr;
import static v2.core.InvalidRoute.invalidRoute;
import static v2.core.protocols.SSBGPProtocol.ssBGPProtocol;
import static v2.stubs.Stubs.*;
import static v2.wrappers.PathWrapper.path;
import static v2.wrappers.TopologyWrapper.link;
import static v2.wrappers.TopologyWrapper.router;

public class SSBGPProtocolTest {

    private static Link turnedOffLink(int sourceID, int targetID) {
        Link link = stubLink(router(0), router(1));
        link.setTurnedOff(true);

        return link;
    }

    @Test
    public void
    importRoute_AnyValidRouteFromTurnedOffLink_InvalidRoute() throws Exception {
        Route validRoute = stubRoute(0, path());

        Route importedRoute = ssBGPProtocol().importRoute(validRoute, turnedOffLink(0, 1));

        assertThat(importedRoute, is(invalidRoute()));
    }

    @Test
    public void
    importRoute_FromLinkWithLabelExtendingToInvalidAttr_InvalidRoute() throws Exception {
        Route route = stubRoute(0, path());
        Label invalidExtendingLabel = (link, attribute) -> invalidAttr();
        Link link = link(0, 1, invalidExtendingLabel);

        Route importedRoute = ssBGPProtocol().importRoute(route, link);

        assertThat(importedRoute, is(invalidRoute()));
    }

    @Test
    public void
    importRoute_WithAttr1AndEmptyPathFromLinkFrom0To1WithLabelExtendingToAttr2_RouteWithAttr2AndPath1() throws Exception {
        Route route = stubRoute(1, path());
        Label label = (link, attribute) -> stubAttr(2);
        Link link = link(0, 1, label);

        Route importedRoute = ssBGPProtocol().importRoute(route, link);

        assertThat(importedRoute, is(stubRoute(2, path(1))));
    }

    @Test
    public void
    importRoute_WithPath0FromLinkWithLabelExtendingToAttr2_PathWithReferenceDifferentFromPath0() throws Exception {
        Path initialPath = path();
        Route route = stubRoute(1, initialPath);
        Label label = (link, attribute) -> stubAttr(2);
        Link link = link(0, 1, label);

        Route importedRoute = ssBGPProtocol().importRoute(route, link);

        assertThat(initialPath, not(sameInstance(importedRoute.getPath())));
    }

    @Test
    public void
    learn_RouteWithAttr1AndPath1FromLinkFrom0To1_RouteWithAttr1AndPath1() throws Exception {
        Router router1 = router(1);
        Route route = stubRoute(1, path(router1));
        Link link = stubLink(router(0), router1);

        Route learnedRoute = ssBGPProtocol().learn(link, route, 0);

        assertThat(learnedRoute, is(stubRoute(1, path(router1))));
    }

    @Test
    public void
    learn_RouteWithAttr1AndPath1FromLinkFrom1To0_InvalidRoute() throws Exception {
        Route route = stubRoute(1, path(router(1)));
        Link link = stubLink(router(1), router(0));

        Route learnedRoute = ssBGPProtocol().learn(link, route, 0);

        assertThat(learnedRoute, is(invalidRoute()));
    }

    @Test
    public void
    learn_LoopRouteFromEnabledLinkAndPolicyConflictIsDetected_LinkIsTurnedOff() throws Exception {
        Route route = stubRoute(1, path(router(1), router(0)));
        Detection alwaysDetect = (link, learnedRoute, alternativeRoute) -> true;
        Router learningRouter = new Router(0, -1, alwaysDetect);    // MRAI value does not matter
        Link link = stubLink(learningRouter, router(1));
        link.setTurnedOff(false);   // ensure link is enabled

        ssBGPProtocol().learn(link, route, 0);

        assertThat(link.isTurnedOff(), is(true));
    }

    @Test
    public void
    learn_LoopRouteFromEnabledLinkAndPolicyConflictIsDetected_InvalidRoute() throws Exception {
        Route route = stubRoute(1, path(router(1), router(0)));
        Detection alwaysDetect = (link, learnedRoute, alternativeRoute) -> true;
        Router learningRouter = new Router(0, -1, alwaysDetect);    // MRAI value does not matter
        Link link = stubLink(learningRouter, router(1));
        link.setTurnedOff(false);   // ensure link is enabled

        Route learnedRoute = ssBGPProtocol().learn(link, route, 0);

        assertThat(learnedRoute, is(invalidRoute()));
    }

}