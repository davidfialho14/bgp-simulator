package simulators;

import core.Engine;
import core.Protocol;
import core.topology.Topology;
import simulators.data.BasicDataCollector;
import simulators.data.FullDeploymentDataCollector;
import simulators.data.SPPolicyBasicDataCollector;
import simulators.data.SPPolicyFullDeploymentDataCollector;

import static policies.shortestpath.ShortestPathPolicy.isShortestPath;

public abstract class SimulatorFactory {

    /**
     * Creates an initial deployment simulator
     */
    public static Simulator newSimulator(Engine engine, Topology topology, int destinationId, Protocol protocol) {

        if (isShortestPath(topology.getPolicy())) {
            return new InitialDeploymentSimulator(engine, topology, destinationId, protocol,
                    new SPPolicyBasicDataCollector());
        } else {
            return new InitialDeploymentSimulator(engine, topology, destinationId, protocol,
                    new BasicDataCollector());
        }

    }

    /**
     * Creates a full deployment simulator instance.
     */
    public static Simulator newSimulator(Engine engine, Topology topology, int destinationId, Protocol protocol,
                                         int deployTime) {
        if (isShortestPath(topology.getPolicy())) {
            return new FullDeploymentSimulator(engine, topology, destinationId, protocol,
                    new SPPolicyFullDeploymentDataCollector(), deployTime);
        } else {
            return new FullDeploymentSimulator(engine, topology, destinationId, protocol,
                    new FullDeploymentDataCollector(), deployTime);
        }
    }

    /**
     * Creates a gradual deployment simulator instance.
     */
    public static Simulator newSimulator(Engine engine, Topology topology, int destinationId, Protocol protocol,
                                         int deployPeriod, double nodePercentage) {
        // TODO add shortest path special case
        return new GradualDeploymentSimulator(engine, topology, destinationId, protocol, deployPeriod, nodePercentage);
    }
}
