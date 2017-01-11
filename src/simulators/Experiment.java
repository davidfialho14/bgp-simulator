package simulators;

import java.io.IOException;

public interface Experiment {

    default void setup(Simulator simulator) {}

    void run(Simulator simulator) throws IOException;

    default void cleanup(Simulator simulator) {}

}
