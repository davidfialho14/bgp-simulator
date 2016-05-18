package io.stats;

public class MessageCountStat implements Stat {

    /**
     * Returns the title for the stat.
     *
     * @return the title of the stat.
     */
    @Override
    public String getTitle() {
        return "Message Count";
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof MessageCountStat;
    }

    @Override
    public int hashCode() {
        return 31;  // mut be different for each stat implementation
    }
}
