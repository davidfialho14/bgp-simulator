package io.newreporters;

import newsimulators.BasicDataSet;

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

    void writeData(BasicDataSet dataSet) throws IOException;

}
