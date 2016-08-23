package main;

import core.Protocol;
import io.networkreaders.TopologyReaderFactory;
import io.reporters.ReporterFactory;


/**
 * A basic configurable is able to configure all the basic parameters for a simulation. The interface reflects that
 * by providing a configuration method for each parameter. Like all configurables it provides a commit method to commit
 * the configurations.
 */
public interface BasicConfigurable {

    BasicConfigurable minDelay(int minDelay);

    BasicConfigurable maxDelay(int maxDelay);

    BasicConfigurable destinationId(int destinationId);

    BasicConfigurable repetitionCount(int repetitionCount);

    BasicConfigurable protocol(Protocol protocol);

    BasicConfigurable readerFactory(TopologyReaderFactory readerFactory);

    BasicConfigurable reporterFactory(ReporterFactory reporterFactory);

    /**
     * Enables/Disables full deployment based on the enable argument.
     *
     * @param enable true to enable and false to disable full deployment simulation.
     * @return an instance of FullDeploymentConfigurable to keep configuring the full deployment parameters.
     */
    FullDeploymentConfigurable enableFullDeployment(boolean enable);

    /**
     * Enables/Disables gradual deployment based on the enable argument.
     *
     * @param enable true to enable and false to disable gradual deployment simulation.
     * @return an instance of GradualDeploymentConfigurable to keep configuring the gradual deployment parameters.
     */
    GradualDeploymentConfigurable enableGradualDeployment(boolean enable);

    void commit();

}
