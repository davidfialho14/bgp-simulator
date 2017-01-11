package main;

/**
 * Application is a central point where parameters relative to any application are kept.
 * For now this includes the error and the progress handlers.
 *
 * This is implemented as a singleton so that any component may be able to access this class.
 */
public enum Application {
    INSTANCE;

    public ErrorHandler errorHandler = null;
    public ProgressHandler progressHandler = null;

    /**
     * Nicer and cleaner method to access the application instance.
     *
     * @return the application it self.
     */
    public static Application application() {
        return INSTANCE;
    }

    /**
     * Exits the application immediately with an error code.
     */
    public void exitWithError() {
        System.exit(1);
    }

}
