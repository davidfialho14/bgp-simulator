package simulation.implementations.policies.gaorexford;

public class CustomerLabel extends GaoRexfordLabel {

    @Override
    protected int getRowCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "C";
    }
}
