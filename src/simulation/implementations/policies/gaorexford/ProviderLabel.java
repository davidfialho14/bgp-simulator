package simulation.implementations.policies.gaorexford;

public class ProviderLabel extends GaoRexfordLabel {

    @Override
    protected int getRowCode() {
        return 2;
    }

    @Override
    public String toString() {
        return "P";
    }
}
