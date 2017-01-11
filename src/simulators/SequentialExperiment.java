package simulators;

import core.Destination;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class SequentialExperiment implements Experiment {

    private final DestinationShuffler shuffler;
    private final int repetitionCount;
    private final int permutationCount;
    private final SequentialSimulation simulation;

    private int currentRepetition = -1;
    private int currentPermutation = -1;

    public SequentialExperiment(Destination[] destinations, int repetitionCount,
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
            savePermutation(simulator, getCurrentSequence());

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

    private void savePermutation(SimulatorNew simulator, Destination[] permutation) throws IOException {

        File permutationsFile = new File(simulator.getReportDestination(),
                getPermutationsFilename(simulator.getTopologyFile().getName()));

        try (
                CSVPrinter printer = new CSVPrinter(new FileWriter(permutationsFile, true),
                        CSVFormat.EXCEL.withDelimiter(';'))
        ) {

            String permutationString = Arrays.stream(permutation)
                    .map(destination -> String.valueOf(destination.getId()))
                    .collect(Collectors.joining(", "));

            printer.printRecord(currentPermutation, permutationString);
        }

    }

    private String getPermutationsFilename(String topologyFilename) {
        String topologyName = FilenameUtils.removeExtension(topologyFilename);
        return String.format("%s.perm.csv", topologyName);
    }

}
