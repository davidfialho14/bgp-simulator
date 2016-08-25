package io.newreporters;

import newsimulators.basic.BasicDataset;
import newsimulators.gradualdeployment.GradualDeploymentDataset;
import newsimulators.timeddeployment.TimedDeploymentDataset;

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

    /**
     * Writes the data in a basic dataset to the report. Called by the basic dataset report method.
     *
     * @param dataSet basic data set containing the data to write in the report.
     * @throws IOException if it fails to write to the report resource.
     */
    void writeData(BasicDataset dataSet) throws IOException;

    /**
     * Writes the data in a timed deployment dataset to the report. Called by the timed deployment dataset report
     * method.
     *
     * @param dataSet timed deployment data set containing the data to write in the report.
     * @throws IOException if it fails to write to the report resource.
     */
    void writeData(TimedDeploymentDataset dataSet) throws IOException;

    /**
     * Writes the data in a gradual deployment dataset to the report. Called by the gradual deployment dataset report
     * method.
     *
     * @param dataSet gradual deployment data set containing the data to write in the report.
     * @throws IOException if it fails to write to the report resource.
     */
    void writeData(GradualDeploymentDataset dataSet) throws IOException;

}
