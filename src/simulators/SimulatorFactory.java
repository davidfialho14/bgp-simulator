package simulators;

import network.Network;
import protocols.D1R1Protocol;
import simulation.Engine;

public abstract class SimulatorFactory {

    /**
     * Creates an initial deployment simulator
     */
    public static Simulator newSimulator(Engine engine, Network network, int destinationId, D1R1Protocol protocol) {
        return new InitialDeploymentSimulator(engine, network, destinationId, protocol);
    }
}
