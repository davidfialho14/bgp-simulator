package simulators;

public interface Simulation {

    default void setup() {}

    void run();

    void report();

    default void cleanup() {}

}
