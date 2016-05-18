package io.stats;

public class DetectingNodesCountStat implements Stat {
    
    /**
     * Returns the title for the stat.
     *
     * @return the title of the stat.
     */
    @Override
    public String getTitle() {
        return "Detecting Nodes Count";
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof DetectingNodesCountStat;
    }

    @Override
    public int hashCode() {
        return 33;  // mut be different for each stat implementation
    }
}
