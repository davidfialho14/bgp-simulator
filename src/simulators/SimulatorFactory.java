package simulators;

import network.Network;
import protocols.Protocol;
import simulation.Engine;
import simulators.data.BasicDataCollector;
import simulators.data.FullDeploymentDataCollector;
import simulators.data.SPPolicyBasicDataCollector;
import simulators.data.SPPolicyFullDeploymentDataCollector;

import static policies.shortestpath.ShortestPathPolicy.isShortestPath;

public abstract class SimulatorFactory {

    /**
     * Creates an initial deployment simulator
     */
    public static Simulator newSimulator(Engine engine, Network network, int destinationId, Protocol protocol) {

        if (isShortestPath(network.getPolicy())) {
            return new InitialDeploymentSimulator(engine, network, destinationId, protocol,
                    new SPPolicyBasicDataCollector());
        } else {
            return new InitialDeploymentSimulator(engine, network, destinationId, protocol,
                    new BasicDataCollector());
        }

    }

    /**
     * Creates a full deployment simulator instance.
     */
    public static Simulator newSimulator(Engine engine, Network network, int destinationId, Protocol protocol,
                                         int deployTime) {
        if (isShortestPath(network.getPolicy())) {
            return new FullDeploymentSimulator(engine, network, destinationId, protocol,
                    new SPPolicyFullDeploymentDataCollector(), deployTime);
        } else {
            return new FullDeploymentSimulator(engine, network, destinationId, protocol,
                    new FullDeploymentDataCollector(), deployTime);
        }
    }
}
