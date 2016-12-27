package main;


import core.policies.gaorexford.GaoRexfordPolicy;
import core.policies.peerplus.PeerPlusPolicy;
import core.policies.shortestpath.ShortestPathPolicy;
import core.policies.siblings.SiblingsPolicy;
import io.topologyreaders.PolicyTagger;
import main.cli.CLIApplication;

public class Main {

    private static final String VERSION = "2.1.1";

    public static void main(String[] args) {

        if (args.length == 1 && args[0].equals("-v")) {
            System.out.println("SS-BGP Simulator: v" + VERSION);
            System.exit(1);
        }

        PolicyTagger.register(new ShortestPathPolicy(), "ShortestPath");
        PolicyTagger.register(new PeerPlusPolicy(), "RoC");
        PolicyTagger.register(new PeerPlusPolicy(), "Peer+");
        PolicyTagger.register(new GaoRexfordPolicy(), "GaoRexford");
        PolicyTagger.register(new SiblingsPolicy(), "Siblings");

        CLIApplication.launch(args);
    }

}
