package v2.main;


import v2.core.policies.gaorexford.GaoRexfordPolicy;
import v2.core.policies.peerplus.PeerPlusPolicy;
import v2.core.policies.shortestpath.ShortestPathPolicy;
import v2.core.policies.siblings.SiblingsPolicy;
import v2.io.topologyreaders.PolicyTagger;
import v2.main.cli.CLIApplication;

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
