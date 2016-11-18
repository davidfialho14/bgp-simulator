package v2.simulators;

import v2.io.reporters.Reporter;

import java.io.IOException;

public class Simulator {

    private Setup setup;

    public Simulator(Setup setup) {
        this.setup = setup;
    }

    public Setup getSetup() {
        return setup;
    }

    public void setSetup(Setup setup) {
        this.setup = setup;
    }

    public void simulate() {
        setup.setup();
        setup.start();
        setup.cleanup();
    }

    public void report(Reporter reporter) throws IOException {
        setup.getDataCollector().report(reporter);
    }

}
