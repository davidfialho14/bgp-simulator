package main;

import io.networkreaders.PolicyTagger;
import main.cli.CLIApplication;
import main.gui.GUIApplication;
import policies.gaorexford.GaoRexfordPolicy;
import policies.roc.RoCPolicy;
import policies.shortestpath.ShortestPathPolicy;

public class Main {

    public static void main(String[] args) {

        PolicyTagger.register(new ShortestPathPolicy(), "ShortestPath");
        PolicyTagger.register(new RoCPolicy(), "RoC");
        PolicyTagger.register(new GaoRexfordPolicy(), "GaoRexford");

        if (args.length == 0) {
            GUIApplication.launch(args);
        } else {
            CLIApplication.launch(args);
        }

    }

}
