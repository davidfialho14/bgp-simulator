package main.cli;

import core.Destination;
import core.Topology;
import io.AnycastReader;
import io.DestinationNotFoundException;
import io.ParseException;
import simulators.BasicExperiment;
import simulators.BasicSimulation;
import simulators.Experiment;
import simulators.SimulatorNew;

import java.io.File;
import java.io.IOException;

import static main.Application.application;

class BasicExecution extends Execution {

    public BasicExecution(Parameters parameters) {
        super(parameters);
    }

    @Override
    protected Experiment setupExperiment(SimulatorNew simulator) {

        Topology topology = simulator.getTopology();
        int destinationId = parameters.getDestinationId();
        File anycastFile = parameters.getAnycastFile();

        Destination destination = topology.getRouter(destinationId);

        if (destination == null) {
            // destination does not exist in the topology
            // but might be an anycast destination

            if (anycastFile == null) { // check if we have an anycast file
                application().errorHandler.onUnknownDestination(destinationId);
                application().exitWithError();
            }

            try (AnycastReader reader = new AnycastReader(anycastFile, topology)) {
                destination = reader.read(destinationId);

            } catch (DestinationNotFoundException e) {
                application().errorHandler.onDestinationNotFoundOnAnycastFile(e, anycastFile);
                application().exitWithError();

            } catch (ParseException e) {
                application().errorHandler.onAnycastParseException(e);
                application().exitWithError();

            } catch (IOException e) {
                application().errorHandler.onAnycastLoadIOException(e);
                application().exitWithError();
            }

        }

        return new BasicExperiment(
                destination,
                parameters.getRepetitionCount(),
                new BasicSimulation(simulator)
        );
    }

}
