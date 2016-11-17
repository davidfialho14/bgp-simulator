package utils;

import core.Engine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static utils.PeriodicTimer.periodicTimer;

@RunWith(MockitoJUnitRunner.class)
public class PeriodicTimerTest {

    private Engine engine = new Engine(null);

    @Mock
    private Runnable mockRunnable;

    private void evolveTime(int... times) {
        for (int time : times) {
            engine.timeProperty().setTime(time);
        }
    }

    @Test
    public void periodicTimerWithPeriod2_TimeNeverReaches2_OperationIsNeverCalled() throws
            Exception {
        periodicTimer(engine)
                .withPeriod(2)
                .doOperation(mockRunnable)
                .start();

        evolveTime(0, 1);

        verify(mockRunnable, times(0)).run();
    }

    @Test
    public void periodicTimerWithPeriod2_TimeGoesFrom1To2_OperationCalledOnce() throws Exception {

        periodicTimer(engine)
                .withPeriod(2)
                .doOperation(mockRunnable)
                .start();

        evolveTime(0, 1, 2, 3);

        verify(mockRunnable, times(1)).run();
    }

    @Test
    public void periodicTimerWithPeriod2_TimeGoesFrom1To3_OperationCalledOnce() throws Exception {

        periodicTimer(engine)
                .withPeriod(2)
                .doOperation(mockRunnable)
                .start();

        evolveTime(0, 1, 3);

        verify(mockRunnable, times(1)).run();
    }

    @Test
    public void periodicTimerWithPeriod2_TimeGoesFrom1To2AndFrom3To4_OperationCalledTwice() throws
            Exception {

        periodicTimer(engine)
                .withPeriod(2)
                .doOperation(mockRunnable)
                .start();

        evolveTime(1, 2, 3, 4);

        verify(mockRunnable, times(2)).run();
    }

}