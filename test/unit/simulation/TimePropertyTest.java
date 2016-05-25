package simulation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class TimePropertyTest {

    @Test
    public void setTime_From0To1_GetsTime1() throws Exception {
        TimeProperty time = new TimeProperty(0L);

        time.setTime(1);

        assertThat(time.getTime(), is(1L));
    }

    @Test
    public void setTime_From0To1WithOneRegisteredListener_CallsOnTimeChangedWithTime1() throws
            Exception {
        TimeListener timeListener = mock(TimeListener.class);
        TimeProperty time = new TimeProperty(0);
        time.addListener(timeListener);

        time.setTime(1);

        verify(timeListener, times(1)).onTimeChange(1);
    }

    @Test
    public void
    setTime_From0To1WithTwoRegisteredListeners_CallsOnTimeChangedWithTime1ForBothListeners() throws Exception {
        TimeListener timeListener1 = mock(TimeListener.class);
        TimeListener timeListener2 = mock(TimeListener.class);
        TimeProperty time = new TimeProperty(0);
        time.addListener(timeListener1);
        time.addListener(timeListener2);

        time.setTime(1);

        verify(timeListener1, times(1)).onTimeChange(1);
        verify(timeListener2, times(1)).onTimeChange(1);
    }

    @Test
    public void
    setTime_From0To1AfterRemovingRegisteredListener_DoesNotCallOnTimeChangedForThatListener() throws Exception {
        TimeListener timeListener = mock(TimeListener.class);
        TimeProperty time = new TimeProperty(0);
        time.addListener(timeListener);

        time.removeListener(timeListener);
        time.setTime(1);

        verify(timeListener, never()).onTimeChange(1);
    }

}