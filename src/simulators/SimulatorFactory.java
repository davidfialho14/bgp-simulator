package simulators;

import network.Network;
import protocols.D1R1Protocol;
import simulation.Engine;
import simulators.data.BasicDataCollector;
import simulators.data.SPPolicyBasicDataCollector;

import static policies.shortestpath.ShortestPathPolicy.isShortestPath;

public abstract class SimulatorFactory {

    /**
     * Creates an initial deployment simulator
     */
    public static Simulator newSimulator(Engine engine, Network network, int destinationId, D1R1Protocol protocol) {

        if (isShortestPath(network.getPolicy())) {
            return new InitialDeploymentSimulator(engine, network, destinationId, protocol,
                    new SPPolicyBasicDataCollector());
        } else {
            return new InitialDeploymentSimulator(engine, network, destinationId, protocol,
                    new BasicDataCollector());
        }

    }
}
