package io.networkreaders;

import com.alexmerz.graphviz.Parser;
import com.alexmerz.graphviz.objects.Edge;
import com.alexmerz.graphviz.objects.Graph;
import com.alexmerz.graphviz.objects.Node;
import com.alexmerz.graphviz.objects.PortNode;
import core.topology.Label;
import core.topology.Network;
import core.topology.Policy;
import core.topology.Topology;
import core.topology.exceptions.NodeNotFoundException;
import io.networkreaders.exceptions.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Topology reader implementation for the Graphviz file format.
 */
public class GraphvizReader implements TopologyReader {

    private final FileReader fileReader;    // file reader used to parse the file
    private final Parser parser;            // Graphviz file parser

    /**
     * Creates a new GraphvizReader, given the graphviz File to read from.
     *
     * @param graphvizFile the graphviz file to read from
     * @throws FileNotFoundException if the file can not be open for reading
     */
    public GraphvizReader(File graphvizFile) throws FileNotFoundException {
        fileReader = new FileReader(graphvizFile);
        parser = new Parser();
    }

    /**
     * Reads a single topology from a Graphviz file format.
     *
     * @return topology associating the topology and policy read
     */
    @Override
    public Topology read() throws IOException, ParseException, NodeNotFoundException {

        try {
            parser.parse(fileReader);
        } catch (com.alexmerz.graphviz.ParseException e) {
            throw new ParseException(e.getMessage());
        }

        // get the parsed graph, note that the DOT language allows more then one graph to be parsed
        // here we just want the first graph
        Graph graph = parser.getGraphs().get(0);

        Policy policy = PolicyTagger.getPolicy(graph.getAttribute("policy"));
        Network network = new Network();

        // add all nodes to the parsedNetwork
        for (Node node : graph.getNodes(true)) {
            network.addNode(getId(node));
        }

        // add links between nodes
        for (Edge edge : graph.getEdges()) {
            int sourceId = getId(edge.getSource());
            int destId = getId(edge.getTarget());
            Label label = policy.createLabel(edge.getAttribute("label"));

            network.addLink(sourceId, destId, label);
        }

        return new Topology(network, policy);
    }

    @Override
    public void close() throws IOException {
        fileReader.close();
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private Helper Methods
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Returns the ID of the graphviz Node.
     *
     * @param node graphviz Node to get ID from
     * @return the ID of the graphviz Node.
     */
    private int getId(Node node) {
        String id = node.getId().getId();
        return Integer.parseInt(id);
    }

    /**
     * Returns the ID of the graphviz PortNode.
     *
     * @param portNode graphviz PortNode to get ID from
     * @return the ID of the graphviz PortNode.
     */
    private int getId(PortNode portNode) {
        return getId(portNode.getNode());
    }

}
