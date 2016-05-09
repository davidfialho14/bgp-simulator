package simulation.schedulers;

import network.Link;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import simulation.ScheduledRoute;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static wrappers.StubWrapper.anyStubLink;

@RunWith(MockitoJUnitRunner.class)
public class RandomSchedulerTest {

    @Mock
    protected RandomDelayGenerator stubGenerator;

    @InjectMocks
    protected RandomScheduler scheduler;

    private void setDelay(int delay) {
        when(stubGenerator.nextDelay()).thenReturn(delay);
    }

    @Test
    public void schedule_RouteWithTime0AndDelay0ForLinkNotYetUsed_Returns0() throws Exception {
        setDelay(0);
        ScheduledRoute scheduledRoute = new ScheduledRoute(null, anyStubLink(), 0L);

        assertThat(scheduler.schedule(scheduledRoute), is(0L));
    }

    @Test
    public void schedule_RouteWithTime0AndDelay0ForLinkNotYetUsed_GetLastTimeForLinkReturns0() throws Exception {
        setDelay(0);
        ScheduledRoute scheduledRoute = new ScheduledRoute(null, anyStubLink(), 0L);

        scheduler.schedule(scheduledRoute);

        assertThat(scheduler.getLastTime(anyStubLink()), is(0L));
    }

    @Test
    public void
    schedule_RouteWithTime0Delay1AndWithLinkNotYetUsed_Returns1() throws Exception {
        setDelay(1);
        ScheduledRoute scheduledRoute = new ScheduledRoute(null, anyStubLink(), 0L);

        assertThat(scheduler.schedule(scheduledRoute), is(1L));
    }

    @Test
    public void schedule_RouteWithTime1AndDelay1ForLinkNotYetUsed_GetLastTimeForLinkReturns2() throws Exception {
        setDelay(1);
        ScheduledRoute scheduledRoute = new ScheduledRoute(null, anyStubLink(), 1L);
        scheduler.schedule(scheduledRoute);

        assertThat(scheduler.getLastTime(anyStubLink()), is(2L));
    }

    /**
     * Sets the current time instant of a link in the scheduler.
     *
     * @param link link to set time instant.
     * @param time time instant to set.
     */
    protected void setLinkTimeInstant(Link link, long time) {
        setDelay(0);
        ScheduledRoute previousRoute = new ScheduledRoute(null, link, time);
        scheduler.schedule(previousRoute);
    }

    @Test
    public void
    schedule_RouteWithTime1AndDelay1AndLinkLastTimeWas1_Returns2() throws Exception {
        Link link = anyStubLink();
        setLinkTimeInstant(link, 1L);
        setDelay(1);
        ScheduledRoute scheduledRoute = new ScheduledRoute(null, link, 1L);

        assertThat(scheduler.schedule(scheduledRoute), is(2L));
    }

    @Test
    public void
    schedule_RouteWithTime1AndDelay1AndLinkLastTimeWas2_Returns3() throws Exception {
        Link link = anyStubLink();
        setLinkTimeInstant(link, 2L);
        setDelay(1);
        ScheduledRoute scheduledRoute = new ScheduledRoute(null, link, 1L);

        assertThat(scheduler.schedule(scheduledRoute), is(3L));
    }

    @Test
    public void
    schedule_RouteWithTime1AndDelay1AndLinkLastTimeWas3_Returns4() throws Exception {
        Link link = anyStubLink();
        setLinkTimeInstant(link, 3L);
        setDelay(1);
        ScheduledRoute scheduledRoute = new ScheduledRoute(null, link, 1L);

        assertThat(scheduler.schedule(scheduledRoute), is(4L));
    }

}