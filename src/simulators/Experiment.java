package simulators;

import java.io.IOException;

public interface Experiment {

    default void setup(SimulatorNew simulator) {}

    void run(SimulatorNew simulator) throws IOException;

    default void cleanup(SimulatorNew simulator) {}

}
