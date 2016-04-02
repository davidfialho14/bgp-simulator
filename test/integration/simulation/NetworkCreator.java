package simulation;

import network.Network;
import network.exceptions.NodeExistsException;
import network.exceptions.NodeNotFoundException;
import simulation.implementations.policies.shortestpath.ShortestPathLabel;

public class NetworkCreator {

    private NetworkCreator() {} // should not be instantiated

    static Network createNetwork0() throws NodeExistsException, NodeNotFoundException {
        Network network = new Network();
        network.addNode(0);
        network.addNode(1);
        network.link(0, 1, new ShortestPathLabel(1));
        return network;
    }

    static Network createNetwork1() throws NodeExistsException, NodeNotFoundException {
        Network network = new Network();
        network.addNode(0);
        network.addNode(1);
        network.addNode(2);
        network.link(0, 1, new ShortestPathLabel(1));
        network.link(1, 2, new ShortestPathLabel(1));
        network.link(0, 2, new ShortestPathLabel(0));
        return network;
    }

    static Network createNetwork2() throws NodeExistsException, NodeNotFoundException {
        Network network = new Network();
        network.addNode(0);
        network.addNode(1);
        network.addNode(2);
        network.addNode(3);
        network.addNode(4);
        network.addNode(5);
        network.link(0, 1, new ShortestPathLabel(1));
        network.link(0, 2, new ShortestPathLabel(1));
        network.link(1, 2, new ShortestPathLabel(1));
        network.link(2, 3, new ShortestPathLabel(0));
        network.link(2, 4, new ShortestPathLabel(-1));
        network.link(3, 5, new ShortestPathLabel(0));
        network.link(4, 5, new ShortestPathLabel(3));
        return network;
    }

    static Network createNetwork3() throws NodeExistsException, NodeNotFoundException {
        Network network = new Network();
        network.addNode(0);
        network.addNode(1);
        network.addNode(2);
        network.addNode(3);
        network.link(1, 0, new ShortestPathLabel(0));
        network.link(2, 0, new ShortestPathLabel(0));
        network.link(3, 0, new ShortestPathLabel(0));
        network.link(1, 2, new ShortestPathLabel(1));
        network.link(1, 2, new ShortestPathLabel(-1));
        network.link(2, 3, new ShortestPathLabel(1));
        network.link(3, 1, new ShortestPathLabel(-2));
        return network;
    }
}
