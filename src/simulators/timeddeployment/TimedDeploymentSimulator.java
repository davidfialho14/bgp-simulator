package simulators.timeddeployment;

import core.Engine;
import core.Protocol;
import core.State;
import simulators.Dataset;
import simulators.Simulator;
import statemodifiers.StateModifiers;
import utils.Timer;

import static utils.Timer.timer;

/**
 * Starts the simulation with an initial state and at a fixed time instant it deploys a new protocol for all
 * nodes. The new protocol and time of deployment can be configured.
 */
public class TimedDeploymentSimulator extends Simulator {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private Fields
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private final TimedDeploymentDataCollector dataCollector = new TimedDeploymentDataCollector();
    private Timer deployTimer;

    private final long deployTime;
    private final Protocol deployProtocol;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Constructors
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Creates a new simulator given an engine, initial state, the deploy time and the deploy protocol.
     *
     * @param engine            engine used for the simulation.
     * @param initialState      initial state.
     * @param deployTime        time instant to deploy new protocol.
     * @param deployProtocol    protocol to deploy at instant deployTime.
     */
    public TimedDeploymentSimulator(Engine engine, State initialState, long deployTime, Protocol deployProtocol) {
        super(engine, initialState);
        this.deployTime = deployTime;
        this.deployProtocol = deployProtocol;
    }

    /**
     * Gives access to the data collected during the last simulation.
     *
     * @return a dataset with the data collected in the last simulation.
     */
    @Override
    public Dataset getData() {
        return dataCollector.getDataset();
    }

    /**
     * (Template Method)
     * <p>
     * Called before starting a simulations instance (before running {@link #simulate()}).
     * Subclasses should use this method to implement any necessary initial setup before a
     * simulation starts.
     */
    @Override
    protected void setup() {
        dataCollector.clear();
        dataCollector.register(engine);

        // use a timer to deploy the new protocol at the deploy time
        deployTimer = timer(engine)
                .atTime(deployTime)
                .doOperation(() -> {
                    deployProtocol.reset();
                    StateModifiers.deployProtocolToAll(state, deployProtocol);
                    dataCollector.setDeployed(true);
                })
                .start();
    }

    /**
     * (Template Method)
     * <p>
     * Called after finishing a simulation instance (after running {@link #simulate()}).
     * Subclasses should use this method to implement any necessary final cleanup after a
     * simulation finishes.
     */
    @Override
    protected void cleanup() {
        deployTimer.stop(); // ensure the deploy timer is stopped and removed from the engine
        dataCollector.unregister();
    }

    /**
     * Returns an identification of the simulator.
     *
     * @return string "Timed Deployment"
     */
    @Override
    public String toString() {
        return "Timed Deployment";
    }

}
