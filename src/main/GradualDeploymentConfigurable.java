package main;

/**
 * A gradual deployment configurable is able to configure all the parameters for a gradual-deployment simulation. The
 * interface reflects that  by providing a configuration method for each parameter.
 */
public interface GradualDeploymentConfigurable {

    GradualDeploymentConfigurable deployPeriod(int deployPeriod);

    GradualDeploymentConfigurable deployPercentage(int deployPercentage);

    FullDeploymentConfigurable enableFullDeployment(boolean enable);

    void commit();

}
