package simulation.implementations.policies.gaorexford;

public class PeerLabel extends GaoRexfordLabel {

    @Override
    protected int getRowCode() {
        return 1;
    }

    @Override
    public String toString() {
        return "R";
    }
}
