package core;

import core.schedulers.RouteReference;

/**
 * Implementation of a MRAI timer.
 */
public class MRAITimer {

    // Minimum Route Advertisement Interval
    private final int MRAI;
    private int expirationTime = 0;
    private RouteReference exportedRouteReference = null;

    /**
     * Creates a MRAI timer with the default MRAI set to 20.
     */
    public MRAITimer() {
        this.MRAI = 20;
    }

    /**
     * Creates a MRAI timer with the given MRAI value.
     */
    public MRAITimer(int MRAI) {
        this.MRAI = MRAI;
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
     * Checks if the MRA Timer has expired.
     *
     * @param currentTime   current time to compare with the expiration time of the current timer.
     * @return true if the timer is active, and false otherwise.
     */
    public boolean hasExpired(long currentTime) {
        // the timer only expires if the expiration time is lower then the current time
        // the timer can not be expired if the expiration time is equal to the current time since that can
        // lead to scheduling errors!!
        return exportedRouteReference == null || expirationTime < currentTime;
    }

    /**
     * Returns the reference for the export route.
     *
     * @return the reference for the export route.
     */
    public RouteReference getExportRouteReference() {
        return exportedRouteReference;
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
    public void updateExportRoute(Route route) {
        exportedRouteReference.setRoute(route);
    }

    /**
     * Resets the timer to expire MRAI units of time after the current time.
     */
    public void reset(int currentTime, Route exportedRoute) {
        expirationTime = currentTime + MRAI;
        this.exportedRouteReference = new RouteReference(exportedRoute);
    }

    /**
     * Resets the timer to 0.
     */
    public void reset() {
        expirationTime = 0;
        exportedRouteReference = null;
    }

}
