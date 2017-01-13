package main.cli;


import core.Destination;
import core.Topology;
import io.AnycastReader;
import io.DestinationNotFoundException;
import io.IntegerLineReader;
import io.ParseException;
import simulators.Experiment;
import simulators.SequentialExperiment;
import simulators.SequentialSimulation;
import simulators.Simulator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static main.Application.application;

class SequentialExecution extends Execution {

    public SequentialExecution(Parameters parameters) {
        super(parameters);
    }

    @Override
    protected Experiment setupExperiment(Simulator simulator) {

        File destinationsFile = parameters.getDestinationsFile();

        // load destinations file
        List<Integer> destinationIds = null;
        try (IntegerLineReader reader = new IntegerLineReader(destinationsFile)) {
            destinationIds = reader.readValues();

        } catch (IOException e) {
            application().errorHandler.onDestinationsIOException(e);
            application().exitWithError();

        } catch (ParseException e) {
            application().errorHandler.onDestinationsParseException(e);
            application().exitWithError();
        }

        Topology topology = simulator.getTopology();
        File anycastFile = parameters.getAnycastFile();

        List<Destination> destinations = new ArrayList<>(destinationIds.size());
        List<Integer> missingIds = new ArrayList<>(destinationIds.size());

        // get destinations from IDs
        for (Integer destinationId : destinationIds) {
            Destination destination = topology.getRouter(destinationId);

            if (destination == null) {
                missingIds.add(destinationId);
            } else {
                destinations.add(destination);
            }
        }

        if (!missingIds.isEmpty()) {
            // destinations not found in the topology might be anycast destinations

            if (anycastFile != null) { // check if we have an anycast file

                try (AnycastReader reader = new AnycastReader(anycastFile, topology)) {
                    Destination[] missingDestinations = reader.readThis(missingIds);
                    Collections.addAll(destinations, missingDestinations);

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
        }


        // create sequential experiment

        if (parameters.hasPermutationSeed()) {
            return new SequentialExperiment(
                    destinations.toArray(new Destination[destinations.size()]),
                    parameters.getRepetitionCount(),
                    parameters.getPermutationCount(),
                    parameters.getPermutationSeed(),
                    new SequentialSimulation(simulator)
            );

        } else {
            return new SequentialExperiment(
                    destinations.toArray(new Destination[destinations.size()]),
                    parameters.getRepetitionCount(),
                    parameters.getPermutationCount(),
                    new SequentialSimulation(simulator)
            );
        }

    }

}
