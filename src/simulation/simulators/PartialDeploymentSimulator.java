package simulation.simulators;

import network.Network;
import protocols.BGPProtocol;
import simulation.State;

import java.io.File;

/**
 * Implements the partial deployment simulator which starts all nodes using the BGP protocol and after
 * a given moment of time all nodes start to detect. It counts the number of messages
 */
public class PartialDeploymentSimulator extends Simulator {

    public PartialDeploymentSimulator(File networkFile, int destinationId, int repetitions) {
        super(networkFile, destinationId, repetitions);
    }

    /**
     * Initializes the simulation according to the specific simulation configuration.
     * Called before the simulationLoop() method.
     *
     * @param network parsed network.
     */
    @Override
    protected void initSimulation(Network network) {
        state = State.create(network, destinationId, new BGPProtocol());
    }

    /**
     * Invoked to execute the simulation loop according to the specific simulation configuration.
     * When this method is invoked the initSimulation() was already called.
     */
    @Override
    protected void simulationLoop() {
        // TODO to implement
        throw new UnsupportedOperationException();
    }

}
