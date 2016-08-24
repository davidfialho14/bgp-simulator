package utils;

import core.Engine;
import core.TimeListener;

/**
 * A timer listens for the time property of a simulation engine and when this timer's stop time is reached it
 * executes a runnable implementation.
 */
public class Timer implements TimeListener {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private Fields
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private final Engine engine;
    private final long stopTime;
    private final Runnable runnable;

    private boolean stopped = false;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private Constructor - Timer should be created with the TimerScheduler class
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private Timer(Engine engine, long stopTime, Runnable runnable) {
        this.engine = engine;
        this.stopTime = stopTime;
        this.runnable = runnable;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Starts the timer by starting to listen for the time property.
     */
    public void start() {
        this.engine.timeProperty().addListener(this);
    }

    /**
     * Stops the timer by removing it from the timer listeners of the engine. Stopping a stopped timer has no effect.
     */
    public void stop() {
        engine.timeProperty().removeListener(this);
    }

    /**
     * Invoked by the engine. When the new time is >= than the stop time it executes the runnable and stops the
     * timer by removing it from the engine's time listeners.
     */
    @Override
    public void onTimeChange(long newTime) {
        if (!stopped && newTime >= stopTime) {
            runnable.run();
            stopped = true;
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
    public static TimerScheduler timer(Engine engine) {
        return new TimerScheduler(engine);
    }

    /**
     * Timer scheduler provides configuration method to start a timer in a more readable way.
     */
    public static class TimerScheduler {

        private final Engine engine;
        private long stopTime = 0;
        private Runnable runnable = () -> {}; // by default runnable does nothing

        private TimerScheduler(Engine engine) {
            this.engine = engine;
        }

        public TimerScheduler atTime(long time) {
            this.stopTime = time;
            return this;
        }

        public TimerScheduler doOperation(Runnable runnable) {
            this.runnable = runnable;
            return this;
        }

        public Timer start() {
            Timer timer = new Timer(engine, stopTime, runnable);
            timer.start();
            return timer;
        }

    }

}
