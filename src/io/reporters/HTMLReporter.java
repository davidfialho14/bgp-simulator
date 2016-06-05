package io.reporters;

import network.Link;
import network.Node;
import org.apache.commons.lang.StringUtils;
import policies.Path;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class HTMLReporter extends Reporter {
    /**
     * Constructs a reporter associating the output file.
     *
     * @param outputFile file to output report to.
     */
    public HTMLReporter(File outputFile) {
        super(outputFile);
    }

    /*
        Set of helper method that allow displaying any element like Nodes, Links, Paths, etc into a more prettier
        format.
     */

    private static String pretty(Node node) {
        return String.valueOf(node.getId());
    }

    private static String pretty(Link link) {
        return link.getSource().getId() + " <span>&#8594;</span> " + link.getDestination().getId();
    }

    private static String pretty(Path path) {
        List<Integer> pathNodesIds = path.stream()
                .map(Node::getId)
                .collect(Collectors.toList());

        return StringUtils.join(pathNodesIds.iterator(), " <span>&#8594;</span> ");
    }
}
