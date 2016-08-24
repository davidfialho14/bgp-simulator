package newsimulators;

import io.reporters.Reporter;

/**
 * Tag interface for a simulation dataset.
 * A dataset stores a group of data collected by a data collector and provides and interface to access that data.
 * This interface is implementation dependent and not valid for all datasets. The data collector can be visited by a
 * reporter to report its data.
 *
 * Datasets exist separate the data storing from the data collection. By having the dataset being visited by a
 * reporter instead of the data collector we are able to hide the collector interface from the reporter and just
 * provide the interface to access the data that the reporter needs.
 */
public interface Dataset {

    /**
     * Reports the current collected data using the given reporter implementation.
     *
     * @param reporter reporter implementation to be used.
     */
    void report(Reporter reporter);

}
