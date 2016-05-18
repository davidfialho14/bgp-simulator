package io.stats;

public class CutOffLinksCountStat implements Stat {
    
    /**
     * Returns the title for the stat.
     *
     * @return the title of the stat.
     */
    @Override
    public String getTitle() {
        return "Cut-Off Links Count";
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof CutOffLinksCountStat;
    }

    @Override
    public int hashCode() {
        return 34;  // mut be different for each stat implementation
    }
}
