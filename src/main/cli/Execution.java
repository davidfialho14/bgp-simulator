package main.cli;

import io.topologyreaders.exceptions.TopologyParseException;
import simulators.Experiment;
import simulators.SimulatorNew;

import java.io.IOException;

import static main.Application.application;

abstract class Execution {

    protected Parameters parameters;

    public Execution(Parameters parameters) {
        this.parameters = parameters;
    }

    public void run() {

        SimulatorNew simulator = setupSimulator();
        Experiment experiment = setupExperiment(simulator);

        try {
            simulator.run(experiment);

        } catch (IOException e) {
            application().errorHandler.onReportingIOException(e);
            application().exitWithError();
        }

    }

    protected abstract Experiment setupExperiment(SimulatorNew simulator);

    protected SimulatorNew setupSimulator() {

        SimulatorNew simulator = null;

        try {
            simulator = new SimulatorNew(
                    parameters.getReaderFactory(),
                    parameters.getTopologyFile(),
                    parameters.getMinDelay(),
                    parameters.getMaxDelay(),
                    parameters.getSeed(),
                    parameters.getThreshold()
            );

        } catch (IOException e) {
            application().errorHandler.onTopologyLoadIOException(e);
            application().exitWithError();

        } catch (TopologyParseException e) {
            application().errorHandler.onTopologyLoadParseException(e);
            application().exitWithError();
        }

        if (parameters.hasForcedMRAI()) {
            simulator.setMRAI(parameters.forcedMRAI());
        }

        if (parameters.hasForcedDetection()) {
            simulator.setDetection(parameters.forcedDetection());
        }

        simulator.setReportDestination(parameters.getReportDestination());

        return simulator;
    }

}
