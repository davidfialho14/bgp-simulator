package utils;

import core.Engine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static utils.Timer.timer;

@RunWith(MockitoJUnitRunner.class)
public class TimerTest {

    private Engine engine = new Engine(null);

    @Mock
    private Runnable mockRunnable;

    private void evolveTime(int... times) {
        for (int time : times) {
            engine.timeProperty().setTime(time);
        }
    }

    @Test
    public void startTimer_DoOperationAtTime3WhenTimeNeverReaches3_OperationIsNeverCalled() throws
            Exception {

        timer(engine)
                .atTime(3)
                .doOperation(mockRunnable)
                .start();

        evolveTime(1, 2);

        verify(mockRunnable, times(0)).run();
    }

    @Test
    public void startTimer_DoOperationAtTime3AndTimeActuallyReaches3_CallsOperationOnce() throws Exception {

        timer(engine)
                .atTime(3)
                .doOperation(mockRunnable)
                .start();

        evolveTime(1, 2, 3, 4);

        verify(mockRunnable, times(1)).run();
    }

    @Test
    public void startTimer_DoOperationAtTime3WhenTimeJumpsFrom2To4_CallsOperationOnce() throws
            Exception {

        timer(engine)
                .atTime(3)
                .doOperation(mockRunnable)
                .start();

        evolveTime(1, 2, 4, 5);

        verify(mockRunnable, times(1)).run();
    }

}