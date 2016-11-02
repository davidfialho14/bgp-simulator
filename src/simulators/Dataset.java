package simulators;

/**
 * Tag interface for a simulation dataset.
 * A dataset stores a group of data collected by a data collector and provides and interface to access that
 * data. This interface is implementation dependent and not valid for all datasets. The data collector can
 * be visited by a reporter to report its data. Datasets exist to separate the data storing from the data
 * collection.
 */
public interface Dataset {
}
