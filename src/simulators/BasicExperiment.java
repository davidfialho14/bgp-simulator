package simulators;

import core.Destination;

import java.io.IOException;

public class BasicExperiment implements Experiment {

    protected Destination destination;
    protected final int repetitionCount;
    protected final BasicSimulation simulation;

    protected int currentRepetition = -1;

    public  BasicExperiment(Destination destination, int repetitionCount,
                            BasicSimulation simulation) {
        this.destination = destination;
        this.repetitionCount = repetitionCount;
        this.simulation = simulation;
    }

    @Override
    public void run(Simulator simulator) throws IOException {

        for (currentRepetition = 0; currentRepetition < repetitionCount; currentRepetition++) {
            simulation.setup(this, simulator);
            simulation.run(this, simulator);
            simulation.report(this, simulator);
            simulation.cleanup(this, simulator);
        }

    }

    public int getRepetitionCount() {
        return repetitionCount;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public int getCurrentRepetition() {
        return currentRepetition;
    }

}
