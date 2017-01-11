package simulators;

import core.Destination;

import java.io.IOException;

public class SequentialExperiment implements Experiment {

    private final DestinationShuffler shuffler;
    private final int repetitionCount;
    private final int permutationCount;
    private final SequentialSimulation simulation;

    private int currentRepetition = -1;
    private int currentPermutation = -1;

    private SequentialExperiment(Destination[] destinations, int repetitionCount,
                                 int permutationCount, SequentialSimulation simulation) {
        this.shuffler = new DestinationShuffler(destinations);
        this.repetitionCount = repetitionCount;
        this.permutationCount = permutationCount;
        this.simulation = simulation;
    }

    @Override
    public void run(SimulatorNew simulator) throws IOException {

        for (currentPermutation = 0; currentPermutation < permutationCount; currentPermutation++) {
            shuffler.shuffle(); // get random permutation

            for (currentRepetition = 0; currentRepetition < repetitionCount; currentRepetition++) {
                simulation.setup(this, simulator);
                simulation.run(this, simulator);
                simulation.report(this, simulator);
                simulation.cleanup(this, simulator);
            }
        }

    }

    public int getRepetitionCount() {
        return repetitionCount;
    }

    public int getPermutationCount() {
        return permutationCount;
    }

    public int getCurrentRepetition() {
        return currentRepetition;
    }

    public int getCurrentPermutation() {
        return currentPermutation;
    }

    public Destination[] getCurrentSequence() {
        return shuffler.getDestinations();
    }

}
