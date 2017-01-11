package simulators;

import java.io.IOException;

public interface Experiment {

    default void setup(SimulatorTemp simulator) {}

    void run(SimulatorTemp simulator) throws IOException;

    default void cleanup(SimulatorTemp simulator) {}

}
