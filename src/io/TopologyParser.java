package io;

import com.alexmerz.graphviz.ParseException;
import com.alexmerz.graphviz.Parser;
import com.alexmerz.graphviz.objects.Edge;
import com.alexmerz.graphviz.objects.Graph;
import com.alexmerz.graphviz.objects.Node;
import com.alexmerz.graphviz.objects.PortNode;
import network.Network;
import network.exceptions.NodeExistsException;
import network.exceptions.NodeNotFoundException;
import policies.Label;
import policies.Policy;
import policies.implementations.shortestpath.ShortestPathPolicy;
import protocols.Protocol;
import protocols.implementations.BGPProtocol;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
    TODO add my own parse exception
    TODO check if there is more then one graph a notify that the extra ones will be ignored
    TODO if one of the ids is not an integer then this must be a parse error
    TODO labels for the links are mandatory! it's an error when one is missing
 */

public class TopologyParser {

    // maps the tags to the protocols available
    private static Map<String, Protocol> protocols = new HashMap<>();
    // maps the tags to the policies available
    private static Map<String, Policy> policies = new HashMap<>();

    /**
     * Sets up the set of protocols available by associating a tag with a protocol.
     */
    static {
        protocol("BGP", new BGPProtocol());
    }

    /**
     * Sets up the set of policies available by associating a tag with a policy.
     */
    static {
        policy("ShortestPath", new ShortestPathPolicy());
    }

    private Parser parser;              // DOT file parser
    private Network parsedNetwork;      // parsed parsedNetwork
    private Protocol parsedProtocol;    // parsed parsedProtocol
    private Policy parsedPolicy;        // parsed attribute factory

    // -------- PUBLIC INTERFACE -------------------------------------------------------------------------------------

    public TopologyParser() {
        this.parser = new Parser();
    }

    /**
     * Parses the given file and builds a parsedNetwork with it. The parsedNetwork, parsedProtocol, and attribute
     * factory being used can be obtained by calling the respective get methods after parsing the file.
     * @param inputFilePath path to the file to parse.
     * @throws IOException
     * @throws ParseException
     * @throws NodeExistsException
     */
    public void parse(String inputFilePath) throws IOException, ParseException, NodeExistsException,
                                                           NodeNotFoundException, InvalidTagException {
        try (FileReader in = new FileReader(inputFilePath)) {
            parser.parse(in);
        }

        // get the parsed graph, note that the DOT language allows more then one graph to be parsed
        // here we just want the first graph
        Graph graph = parser.getGraphs().get(0);

        parsedProtocol = parseProtocol(graph.getAttribute("parsedProtocol"));
        parsedPolicy = parsePolicy(graph.getAttribute("policy"));
        parsedNetwork = new Network();

        // add all nodes to the parsedNetwork
        for (Node node : graph.getNodes(true)) {
            parsedNetwork.addNode(getId(node));
        }

        // add links between nodes
        for (Edge edge : graph.getEdges()) {
            int sourceId = getId(edge.getSource());
            int destId = getId(edge.getTarget());
            Label label = parsedPolicy.createLabel(edge.getAttribute("label"));

            parsedNetwork.link(sourceId, destId, label);
        }
    }

    /**
     * Returns the parsedNetwork parsed after the last call to the parse() method. If the parse() method has never been
     * called then null is returned.
     * @return the last parsedNetwork parse or null if the parse() method has never been called.
     */
    public Network getParsedNetwork() {
        return parsedNetwork;
    }

    /**
     * Returns the parsedProtocol parsed after the last call to the parse() method. If the parse() method has never been
     * called then null is returned.
     * @return the last parsedProtocol parse or null if the parse() method has never been called.
     */
    public Protocol getProtocol() {
        return parsedProtocol;
    }

    /**
     * Returns the policy parsed after the last call to the parse() method. If the parse() method has never
     * been called then null is returned.
     * @return the last policy parse or null if the parse() method has never been called.
     */
    public Policy getPolicy() {
        return parsedPolicy;
    }

    // --------- PARSE METHODS -----------------------------------------------------------------------------------------

    /**
     * Parses the protocol tag and returns the respective protocol instance.
     * @param tag tag to be parsed.
     * @return protocol instance corresponding to the given tag.
     */
    private Protocol parseProtocol(String tag) {
        return protocols.get(tag);
    }

    /**
     * Parses the policy tag and returns the respective Policy instance.
     * @param tag tag to be parsed.
     * @return protocol instance corresponding to the given tag.
     */
    private Policy parsePolicy(String tag) {
        return policies.get(tag);
    }

    // --------- HELPER METHODS ---------------------------------------------------------------------------------------

    private int getId(Node node) {
        String id = node.getId().getId();
        return Integer.parseInt(id);
    }

    private int getId(PortNode portNode) {
        return getId(portNode.getNode());
    }

    // --------- TAGGERS -----------------------------------------------------------------------------------------------

    private static void protocol(String tag, Protocol protocol) {
        protocols.put(tag, protocol);
    }

    private static void policy(String tag, Policy policy) {
        policies.put(tag, policy);
    }
}
