package core;

/**
 * Implementation of a MRAI timer.
 */
public class MRAITimer {

    private final Router owner; // owner associated with the timer

    // Minimum Route Advertisement Interval
    private int MRAI;
    private int expirationTime = 0;
    private boolean enabled = false;
    private Route exportRoute = null;

    /**
     * Creates a MRAI timer with the default MRAI set to 20.
     */
    public MRAITimer(Router owner) {
        this.owner = owner;
        this.MRAI = 20;
    }

    /**
     * Creates a MRAI timer with the given MRAI value.
     */
    public MRAITimer(Router owner, int MRAI) {
        this.owner = owner;
        this.MRAI = MRAI;
    }

    /**
     * Returns the owner of the MRAI timer.
     *
     * @return the owner of the MRAI timer.
     */
    public Router getOwner() {
        return owner;
    }

    /**
     * Gets the MRAI of the timer.
     *
     * @return the MRAI of the timer.
     */
    public int getMRAI() {
        return MRAI;
    }

    /**
     * Sets a new value for the MRAI.
     *
     * @param MRAI vale to set as MRAI.
     */
    public void setMRAI(int MRAI) {
        this.MRAI = MRAI;
    }

    /**
     * Checks if the MRA Timer has expired.
     *
     * @param currentTime   current time to compare with the expiration time of the current timer.
     * @return true if the timer is active, and false otherwise.
     */
    public boolean hasExpired(long currentTime) {
        // the timer only expires if the expiration time is lower then the current time
        // the timer can not be expired if the expiration time is equal to the current time since
        // that can lead to scheduling errors!!
        return isEnabled() && (exportRoute == null || expirationTime <= currentTime);
    }

    /**
     * Returns the route to be exported when the timer expires.
     *
     * @return the route to be exported when the timer expires.
     */
    public Route getExportRoute() {
        return exportRoute;
    }

    /**
     * Checks if the timer has a route to export. If it returns false it means that the owner of
     * the route did not select/export any route while the timer was active.
     *
     * @return true if there is a route to export and false if not.
     */
    public boolean hasExportableRoute() {
        return exportRoute != null;
    }

    /**
     * Returns the expiration time of the timer.
     *
     * @return the expiration time of the timer.
     */
    public int getExpirationTime() {
        return expirationTime;
    }

    /**
     * Updates the export route. This changes the exported route for all messages already included
     * in the scheduler with this route reference. Should be called every time a new route is exported and
     * the timer is still active.
     *
     * @param route route to update to.
     */
    public void setExportRoute(Route route) {
        this.exportRoute = route;
    }

    /**
     * Resets the timer to expire MRAI units of time after the current time. By default the timer
     * is initialized with no route to export.
     */
    public void reset(int currentTime) {
        expirationTime = currentTime + MRAI;
        exportRoute = null;
        setEnabled(true);
    }

    /**
     * Clears the timer. Disables the timer and sets the expiration time to 0.
     */
    public void clear() {
        expirationTime = 0;
        exportRoute = null;
        setEnabled(false);
    }

    /**
     * Checks if the timer is enabled.
     *
     * @return true if the timer is enabled and false if otherwise.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Enables or disables the timer.
     *
     * @param enabled true to enable the timer and false to disable.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
