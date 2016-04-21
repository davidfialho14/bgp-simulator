package simulation.implementations.schedulers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RandomDelayGeneratorTest {

    @Mock
    protected Random stubRandom;

    @InjectMocks
    protected RandomDelayGenerator randomDelayGenerator;

    @Test
    public void
    nextDelay_Min0AndMax1AndRandomReturns0_Returns0() throws Exception {
        randomDelayGenerator.setMin(0);
        randomDelayGenerator.setMax(1);
        when(stubRandom.nextInt(anyInt())).thenReturn(0);

        assertThat(randomDelayGenerator.nextDelay(), is(0));
    }

    @Test
    public void
    nextDelay_Min0AndMax1AndRandomReturns1_Returns1() throws Exception {
        randomDelayGenerator.setMin(0);
        randomDelayGenerator.setMax(1);
        when(stubRandom.nextInt(anyInt())).thenReturn(1);

        assertThat(randomDelayGenerator.nextDelay(), is(1));
    }

}
