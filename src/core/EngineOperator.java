package core;

/**
 * Engine operator is just a tag interface to classify classes that implement some of the operations executed by the
 * simulation engine. These operations might be the learning, processing and exporting of routes. Engine operators
 * must always be associated with an engine!
 */
public interface EngineOperator {

    /**
     * Associates an engine with the operator.
     *
     * @param engine engine to associate.
     */
    void setEngine(Engine engine);

}
