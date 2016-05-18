package io.stats;

public class DetectionCountStat implements Stat {
    
    /**
     * Returns the title for the stat.
     *
     * @return the title of the stat.
     */
    @Override
    public String getTitle() {
        return "Detection Count";
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof DetectionCountStat;
    }

    @Override
    public int hashCode() {
        return 32;  // mut be different for each stat implementation
    }
}
