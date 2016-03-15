package implementations.schedulers;

import network.Factory;
import network.Link;
import network.ScheduledRoute;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

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
        Link newLink = Factory.createRandomLink();
        ScheduledRoute scheduledRoute = new ScheduledRoute(null, newLink, 0L);

        assertThat(scheduler.schedule(scheduledRoute), is(0L));
    }

    @Test
    public void schedule_RouteWithTime0AndDelay0ForLinkNotYetUsed_GetLastTimeForLinkReturns0() throws Exception {
        setDelay(0);
        Link newLink = Factory.createRandomLink();
        ScheduledRoute scheduledRoute = new ScheduledRoute(null, newLink, 0L);

        scheduler.schedule(scheduledRoute);

        assertThat(scheduler.getLastTime(newLink), is(0L));
    }

    @Test
    public void
    schedule_RouteWithTime0Delay1AndWithLinkNotYetUsed_Returns1() throws Exception {
        setDelay(1);
        ScheduledRoute scheduledRoute = new ScheduledRoute(null, Factory.createRandomLink(), 0L);

        assertThat(scheduler.schedule(scheduledRoute), is(1L));
    }

    @Test
    public void schedule_RouteWithTime1AndDelay1ForLinkNotYetUsed_GetLastTimeForLinkReturns2() throws Exception {
        setDelay(1);
        Link newLink = Factory.createRandomLink();
        ScheduledRoute scheduledRoute = new ScheduledRoute(null, newLink, 1L);
        scheduler.schedule(scheduledRoute);

        assertThat(scheduler.getLastTime(newLink), is(2L));
    }

    protected void setLinkLastTime(Link link, long lastTime) {
        setDelay(0);
        ScheduledRoute previousRoute = new ScheduledRoute(null, link, lastTime);
        scheduler.schedule(previousRoute);
        // FIXME revert the previous delay
    }
    @Test
    public void
    schedule_RouteWithTime1AndDelay1AndLinkLastTimeWas1_Returns2() throws Exception {
        Link link = Factory.createRandomLink();
        setLinkLastTime(link, 1L);
        setDelay(1);
        ScheduledRoute scheduledRoute = new ScheduledRoute(null, link, 1L);

        assertThat(scheduler.schedule(scheduledRoute), is(2L));
    }

    @Test
    public void
    schedule_RouteWithTime1AndDelay1AndLinkLastTimeWas2_Returns3() throws Exception {
        Link link = Factory.createRandomLink();
        setLinkLastTime(link, 2L);
        setDelay(1);
        ScheduledRoute scheduledRoute = new ScheduledRoute(null, link, 1L);

        assertThat(scheduler.schedule(scheduledRoute), is(3L));
    }

    @Test
    public void
    schedule_RouteWithTime1AndDelay1AndLinkLastTimeWas3_Returns4() throws Exception {
        Link link = Factory.createRandomLink();
        setLinkLastTime(link, 3L);
        setDelay(1);
        ScheduledRoute scheduledRoute = new ScheduledRoute(null, link, 1L);

        assertThat(scheduler.schedule(scheduledRoute), is(4L));
    }

}