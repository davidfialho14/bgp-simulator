package utils;

import core.Engine;
import core.TimeListener;

/**
 * Based on a timer a periodic timer executes an operation every period of time. Listens for the time property
 * It must be stopped explicitly otherwise it will run indefinitely.
 */
public class PeriodicTimer implements TimeListener {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private Fields
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private final Engine engine;
    private final Runnable runnable;
    private final int period;

    private boolean stopped = true;
    private long executeTime;  // updated at every period

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private Constructor - PeriodicTimer should be created with the PeriodicTimerScheduler class
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private PeriodicTimer(Engine engine, Runnable runnable, int period) {
        this.engine = engine;
        this.runnable = runnable;
        this.period = period;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Starts the timer by starting to listen for the time property. If the timer was not stopped it raises an
     * IllegalStateException.
     */
    public void start() {

        if (!stopped) {
            throw new IllegalStateException("can not start a periodic timer that is already running");
        }

        executeTime = period;
        engine.timeProperty().addListener(this);
        stopped = false;
    }

    /**
     * Stops the timer by removing it from the timer listeners of the engine. Stopping a stopped timer has no effect.
     */
    public void stop() {

        if (!stopped) {
            engine.timeProperty().removeListener(this);
            stopped = true;
        }
    }

    /**
     * Invoked by the engine. When the new time is >= than the current stop time it executes the runnable and
     * updates the stop time by adding one more period of time.
     */
    @Override
    public void onTimeChange(long newTime) {
        if (newTime >= executeTime) {
            runnable.run();
            executeTime += period;
        }
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Builder class - The timer scheduler allows a timer to be created in a more readable way
     *  than using a constructor.
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Creates a timer scheduler in a readable way.
     *
     * @param engine    engine to schedule timer for.
     * @return new timer scheduler instance
     */
    public static PeriodicTimerScheduler periodicTimer(Engine engine) {
        return new PeriodicTimerScheduler(engine);
    }

    /**
     * Periodic timer scheduler provides configuration method to start a periodic scheduler in a more readable way.
     */
    public static class PeriodicTimerScheduler {

        private final Engine engine;
        private int period = 1;
        private Runnable runnable = () -> {}; // by default runnable does nothing

        private PeriodicTimerScheduler(Engine engine) {
            this.engine = engine;
        }

        public PeriodicTimerScheduler withPeriod(int period) {
            this.period = period;
            return this;
        }

        public PeriodicTimerScheduler doOperation(Runnable runnable) {
            this.runnable = runnable;
            return this;
        }

        public PeriodicTimer start() {
            PeriodicTimer periodicTimer = new PeriodicTimer(engine, runnable, period);
            periodicTimer.start();
            return periodicTimer;
        }

    }

}
