package io.reporters;

import java.io.File;

public interface Reporter {

    File REPORT_DIRECTORY = new File(System.getProperty("user.dir"));

}
