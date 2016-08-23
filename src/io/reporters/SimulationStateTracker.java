package io.reporters;


import core.Engine;
import core.events.DetectEvent;
import core.events.DetectListener;
import core.events.EndEvent;
import core.events.EndListener;

/**
 * Keeps track of the simulation state while the simulation execution evolves.
 */
public class SimulationStateTracker implements EndListener, DetectListener {

    private final Engine engine;    // stores the engine to which it is registered

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  State information to keep
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private int simulationCount = 0;
    private int detectionCount = 0;

    public SimulationStateTracker(Engine engine) {
        this.engine = engine;
        engine.getEventGenerator().addEndListener(this);
        engine.getEventGenerator().addDetectListener(this);
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Getters to access the stored information
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public int getSimulationCount() {
        return simulationCount;
    }

    public int getDetectionCount() {
        return detectionCount;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Methods invoked during the simulation process to update the state
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Invoked when a simulation terminates.
     *
     * @param event end event that occurred.
     */
    @Override
    public void onEnded(EndEvent event) {
        simulationCount++;
    }

    /**
     * Invoked when a detect event occurs.
     *
     * @param event detect event that occurred.
     */
    @Override
    public void onDetected(DetectEvent event) {
        detectionCount++;
    }

    /**
     * Unregisters from the engine event generator
     */
    public void unregister() {
        engine.getEventGenerator().removeEndListener(this);
        engine.getEventGenerator().removeDetectListener(this);
    }

}
