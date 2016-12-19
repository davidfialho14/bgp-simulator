package core.schedulers;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RandomDelayGeneratorTest {

    private RandomDelayGenerator delayGenerator;

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    public void forcingToUseSeed10_InitialSeedIs10() throws Exception {
        // forcing to always use seed 10
        delayGenerator = new RandomDelayGenerator(1, 10, 10L);

        assertThat(delayGenerator.getSeed(), is(10L));
    }

    @Test
    public void forcingToUseSeed10_SecondSeedIs10() throws Exception {
        // forcing to always use seed 10
        delayGenerator = new RandomDelayGenerator(1, 10, 10L);
        delayGenerator.reset(); // force the delay generator to choose a new seed

        assertThat(delayGenerator.getSeed(), is(10L));
    }

    @Test
    public void forcingToUseSeeds1And2_FirstSeedIs1AndSecondSeedIs2() throws Exception {
        // forcing to always use seeds 1 and 2
        Long[] seeds = {1L, 2L};
        delayGenerator = new RandomDelayGenerator(1, 10, Arrays.asList(seeds));

        collector.checkThat(delayGenerator.getSeed(), is(1L));
        delayGenerator.reset(); // force the delay generator to choose a new seed
        collector.checkThat(delayGenerator.getSeed(), is(2L));
    }

    @Test
    public void forcingToUseSeeds1And2_ThirdSeedIs1() throws Exception {
        // forcing to always use seeds 1 and 2
        Long[] seeds = {1L, 2L};
        delayGenerator = new RandomDelayGenerator(1, 10, Arrays.asList(seeds));
        // force the delay generator to choose the third seed
        delayGenerator.reset();
        delayGenerator.reset();

        assertThat(delayGenerator.getSeed(), is(1L));
    }

}