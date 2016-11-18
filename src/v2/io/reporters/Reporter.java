package v2.io.reporters;

import java.io.IOException;

/**
 * Reporter is used to report the data in a dataset. Reporting is implemented using the visitor design pattern, where
 * the reporter is the visitor class and the dataset is the visited class. This allows adding new report formats
 * without changing the datasets interfaces.
 */
public interface Reporter {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    *
    *  Visitor Methods to Write Data from different datasets
    *
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    *
    *  Methods to write summaries
    *
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Writes a summary of the simulation after it finishes. Writes basic information abouts the total results of
     * the simulation.
     */
    void writeAfterSummary() throws IOException;

}
