package simulators;

import core.Destination;
import core.Engine;
import core.Router;
import core.Topology;
import core.exporters.BasicExporter;
import core.protocols.Detection;
import core.schedulers.RandomScheduler;
import core.schedulers.Scheduler;
import io.reporters.Reporter;
import io.topologyreaders.TopologyReader;
import io.topologyreaders.TopologyReaderFactory;
import io.topologyreaders.exceptions.TopologyParseException;

import java.io.File;
import java.io.IOException;

import static main.Application.application;

public class Simulator {

    private final Engine engine;

    private Topology topology;
    private File topologyFile;
    private File anycastFile;

    private File reportDestination = Reporter.REPORT_DIRECTORY;

    public Simulator(TopologyReaderFactory readerFactory, File topologyFile, int minDelay, int maxDelay,
                     Long forcedSeed, int threshold) throws IOException, TopologyParseException {

        reloadTopology(readerFactory, topologyFile);

        Scheduler scheduler;
        if (forcedSeed == null) {
            scheduler = new RandomScheduler(minDelay, maxDelay);
        } else {
            scheduler = new RandomScheduler(minDelay, maxDelay, forcedSeed);
        }

        this.engine = new Engine(new BasicExporter(scheduler), threshold);
    }

    // access methods

    public Topology getTopology() {
        return topology;
    }

    public File getTopologyFile() {
        return topologyFile;
    }

    public File getReportDestination() {
        return reportDestination;
    }

    // modification methods

    /**
     * Forces all routers in the current topology to use the given MRAI value.
     *
     * @param value MRAI value to force all routers to use.
     */
    public void setMRAI(int value) {

        for (Router router : topology.getRouters()) {
            router.getMRAITimer().setMRAI(value);
        }
    }

    /**
     * Forces all routers in the current topology to use the given detection method.
     *
     * @param detection detection to force all routers to use.
     */
    public void setDetection(Detection detection) {

        for (Router router : topology.getRouters()) {
            router.setDetection(detection);
        }
    }

    /**
     * Discards the current topology and loads a new one. After calling this method the previous topology is
     * immediately discarded even if an error occurs while loading the new topology. After calling this
     * method any MRAI value or detection method that was forced will be lost too.
     *
     * @param readerFactory factory to get reader to load topology.
     * @param topologyFile  file with the topology.
     * @throws IOException  if it fails to open or read the topology file.
     * @throws TopologyParseException   if the format of the topology format is incorrect.
     */
    public void reloadTopology(TopologyReaderFactory readerFactory, File topologyFile)
            throws IOException, TopologyParseException {

        // discard old topology immediately - this avoids having an unknown state if an error occurs
        topology = null;
        this.topologyFile = null;

        try (TopologyReader topologyReader = readerFactory.getTopologyReader(topologyFile)) {
            application().progressHandler.onStartLoadingTopology(topologyFile);
            topology = topologyReader.read();
            application().progressHandler.onFinishedLoadingTopology(topology);
        }

        this.topologyFile = topologyFile;
    }

    /**
     * Sets the directory where to place report files.
     *
     * @param reportDestination directory where to place report files.
     */
    public void setReportDestination(File reportDestination) {

        if (!reportDestination.isDirectory()) {
            throw new IllegalArgumentException("Report destination must be a directory: " +
                    "path '" + reportDestination.getPath() + "' is not a directory");
        }

        this.reportDestination = reportDestination;
    }

    public void setMinDelay(int value) {
        // TODO implement set min delay in scheduler
        throw new UnsupportedOperationException();
    }

    public void setMaxDelay(int value) {
        // TODO implement set max delay in scheduler
        throw new UnsupportedOperationException();
    }

    public void setSeed(long seed) {
        // TODO implement set initial/next seed in scheduler
        throw new UnsupportedOperationException();
    }

    public void setThreshold(int value) {
        // TODO implement set threshold in engine
        throw new UnsupportedOperationException();
    }

    // RUN METHOD

    /**
     * Entry point to start simulating. Once everything is setup use the run() method to execute the
     * experiment. This will call the setup, run, and cleanup methods (in this order) of the experiment.
     *
     * @param experiment experiment to be run.
     * @throws IOException if the an IO error occurs while trying to report the data of the experiment.
     */
    public void run(Experiment experiment) throws IOException {

        application().progressHandler.onStartExecution();

        experiment.setup(this);
        experiment.run(this);
        experiment.cleanup(this);

        application().progressHandler.onFinishExecution();
    }

    // PACKAGE METHODS USED BY EXPERIMENTS

    /**
     * Simulations should call this method to start a simulation instance. This will use the simulator
     * engine and the current topology to simulate.
     *
     * @param destination destination to simulate for.
     */
    void simulate(Destination destination, String description) {
        application().progressHandler.onStartSimulation(destination.getId(), description);
        engine.simulate(topology, destination);
        application().progressHandler.onFinishSimulation();
    }

}
