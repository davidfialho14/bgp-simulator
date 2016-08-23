package main;

/**
 * A full deployment configurable is able to configure all the parameters for a full-deployment simulation. The
 * interface reflects that  by providing a configuration method for each parameter.
 */
public interface FullDeploymentConfigurable {

    FullDeploymentConfigurable deployTime(int deployTime);

    GradualDeploymentConfigurable enableGradualDeployment(boolean enable);

    void commit();

}
