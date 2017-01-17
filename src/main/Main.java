package main;


import io.topologyreaders.PolicyTagger;
import main.cli.CLIApplication;

import static core.policies.gaorexford.GRPolicy.gaoRexfordPolicy;
import static core.policies.peerplus.PeerPlusPolicy.peerplusPolicy;
import static core.policies.shortestpath.ShortestPathPolicy.shortestPathPolicy;
import static core.policies.siblings.SiblingsPolicy.siblingsPolicy;

public class Main {

    private static final String VERSION = "2.2.3";

    public static void main(String[] args) {

        if (args.length == 1 && args[0].equals("-v")) {
            System.out.println("SS-BGP Simulator: v" + VERSION);
            System.exit(1);
        }

        PolicyTagger.register(shortestPathPolicy(), "ShortestPath");
        PolicyTagger.register(peerplusPolicy(), "RoC");
        PolicyTagger.register(peerplusPolicy(), "Peer+");
        PolicyTagger.register(gaoRexfordPolicy(), "GaoRexford");
        PolicyTagger.register(siblingsPolicy(), "Siblings");

        CLIApplication.launch(args);
    }

}
