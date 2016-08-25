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
    private long stopTime;
    private final Runnable runnable;

    private boolean paused = true;
    private boolean stopped = true;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private Constructor - Timer should be created with the PeriodicTimerScheduler class
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
     * Stops the timer by removing it from the timer listeners of the engine. Stopping a paused timer has no effect.
     */
    public void stop() {
        engine.timeProperty().removeListener(this);
        stopped = true;
    }

    /**
     * Restarts a stopped timer with the same time. If the timer is not stopped it throws an IllegalStateException.
     *
     * @throws IllegalStateException if the timer is not stopped.
     */
    public void restart() {

        if (!stopped) {
            throw new IllegalStateException("can not restarted running timer");
        }

        start();
    }

    /**
     * Restarts a stopped timer with a new time. If the timer is not stopped it throws an IllegalStateException.
     *
     * @param newStopTime new stop time.
     * @throws IllegalStateException if the timer is not stopped.
     */
    public void restart(long newStopTime) {
        stopTime = newStopTime;
        restart();
    }

    /**
     * Invoked by the engine. When the new time is >= than the stop time it executes the runnable and stops the
     * timer by removing it from the engine's time listeners.
     */
    @Override
    public void onTimeChange(long newTime) {
        if (!paused && newTime >= stopTime) {
            runnable.run();
            paused = true;
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


    /**
     * Starts the timer by starting to listen for the time property. Puts the timer in running state.
     */
    private void start() {
        stopped = false;
        paused = false;
        this.engine.timeProperty().addListener(this);
    }

}
