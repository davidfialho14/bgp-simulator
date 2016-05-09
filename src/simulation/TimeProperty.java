package simulation;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the simulation time. This class defines the time Property. A property works as a JavaBean, its value
 * can be read, written, and observed for changes.
 */
public class TimeProperty {

    private long time;
    private List<TimeListener> listeners = new ArrayList<>();   // holds al listeners

    public TimeProperty() {
        this.time = -1;
    }

    public long getTime() {
        return time;
    }

    /**
     * Sets a new value for the time. If the new value is different from the previous calls the onTimeChange()
     * method for all the time listeners registered.
     *
     * @param time value to set for the time.
     */
    public void setTime(long time) {
        if (time != this.time) {
            this.time = time;
            listeners.forEach(listener -> listener.onTimeChange(this.time));
        }
    }

    /**
     * Registers a time listener with the time property. This listener will now be notified of all the changes
     * of the time value.
     *
     * @param listener time listener to register.
     */
    public void addListener(TimeListener listener) {
        listeners.add(listener);
    }

    /**
     * Unregisters a time listener. After this call the listener will no longer be notified of time changes.
     *
     * @param listener time listener to unregister.
     */
    public void removeListener(TimeListener listener) {
        listeners.remove(listener);
    }

    /**
     * Removes all listeners.
     */
    public void removeAll() {
        listeners.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeProperty that = (TimeProperty) o;

        return time == that.time;

    }

    @Override
    public int hashCode() {
        return (int) (time ^ (time >>> 32));
    }

    @Override
    public String toString() {
        return time + "t";
    }
}
