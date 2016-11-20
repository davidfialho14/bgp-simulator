package main;


import core.policies.gaorexford.GaoRexfordPolicy;
import core.policies.peerplus.PeerPlusPolicy;
import core.policies.shortestpath.ShortestPathPolicy;
import core.policies.siblings.SiblingsPolicy;
import io.topologyreaders.PolicyTagger;
import main.cli.CLIApplication;

public class Main {

    public static void main(String[] args) {

        PolicyTagger.register(new ShortestPathPolicy(), "ShortestPath");
        PolicyTagger.register(new PeerPlusPolicy(), "RoC");
        PolicyTagger.register(new PeerPlusPolicy(), "Peer+");
        PolicyTagger.register(new GaoRexfordPolicy(), "GaoRexford");
        PolicyTagger.register(new SiblingsPolicy(), "Siblings");

        CLIApplication.launch(args);

    }

}
