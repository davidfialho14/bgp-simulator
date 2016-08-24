package newsimulators;


import core.Engine;
import core.State;

/**
 * Simulators execute one simulation instance through the {@link #simulate()}. Simulators might have different
 * behaviours during the simulation and might collect different sets of data. Simulators have two components: the
 * data collection and simulation behaviour. Data collection is delegated to a data collectors, which means the
 * simulator only implements the behaviour of the simulation.
 *
 * The Simulator class is a template class for simulator with two template methods {@link #setup()} and
 * {@link #cleanup()} called before and after the simulation, respectively.
 */
public abstract class Simulator {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private Fields
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    protected final Engine engine;  // engine used for the simulation
    protected final State state;    // current state of the simulation

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Constructors
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Creates a new simulator given an engine and initial state.
     *
     * @param engine        engine used for the simulation.
     * @param initialState  initial state.
     */
    public Simulator(Engine engine, State initialState) {
        this.engine = engine;
        this.state = initialState;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Executes a simulation using the provided engine. It always starts the simulation with the initial state
     * provided in the constructor (it calls the state reset() method to make sure of that.
     */
    public void simulate() {
        setup();
        state.reset();
        engine.simulate(state);
        cleanup();
    }

    /**
     * Gives access to the data collected during the last simulation.
     *
     * @return a dataset with the data collected in the last simulation.
     */
    public abstract Dataset getData();

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Template Methods
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * (Template Method)
     *
     * Called before starting a simulations instance (before running {@link #simulate()}).
     * Subclasses should use this method to implement any necessary initial setup before a
     * simulation starts.
     */
    protected abstract void setup();

    /**
     * (Template Method)
     *
     * Called after finishing a simulation instance (after running {@link #simulate()}).
     * Subclasses should use this method to implement any necessary final cleanup after a
     * simulation finishes.
     */
    protected abstract void cleanup();

}
