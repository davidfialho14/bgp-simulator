package addons.statscollectors;

import simulation.Engine;
import simulation.State;

/**
 * A stats collector is responsible for collecting the necessary stats during the simulation. It stores the collected
 * stats and provides an interface to access them. In order to be able to collect stats during simulation it must
 * register him self with an engine. To do that it uses the register() method.
 */
public interface StatsCollector {

    /**
     * Registers the stats collector with the given engine and state
     *
     * @param engine engine used to simulate.
     * @param state state being simulated.
     */
    void register(Engine engine, State state);

}
