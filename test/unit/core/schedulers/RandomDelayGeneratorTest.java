package core.schedulers;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RandomDelayGeneratorTest {

    private RandomDelayGenerator delayGenerator;

    @Test
    public void forcingToUseSeed10_InitialSeedIs10() throws Exception {
        // forcing to always use seed 10
        delayGenerator = new RandomDelayGenerator(1, 10, 10L);

        assertThat(delayGenerator.getSeed(), is(10L));
    }

}