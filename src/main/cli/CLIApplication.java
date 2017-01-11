package main.cli;

import org.apache.commons.cli.ParseException;

import static main.Application.application;

/**
 * The CLI application class is the dual of the GUI application that supports a command line interface instead
 * of a graphical interface.
 */
public class CLIApplication {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface
     *
     *  The CLI application is launched in the same way as the GUI application, by calling the
     *  static method launch() with the command line arguments.
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Entry point for a CLI application. Called to started the application.
     *
     * @param args command line arguments of the application.
     */
    public static void launch(String[] args) {

        try {
            ParametersCommandLineParser commandLineParser = new ParametersCommandLineParser();
            Parameters parameters = commandLineParser.parse(args);

            application().errorHandler = new CLIErrorHandler();
            application().progressHandler = new CLIProgressHandler();

            Execution execution;
            if (parameters.getDestinationId() != null) {
                execution = new BasicExecution(parameters);
            } else {
                execution = new SequentialExecution(parameters);
            }

            execution.run();

        } catch (ParseException e) {
            // failed to parse input arguments
            System.err.println(e.getMessage());
            application().exitWithError();
        }

    }

}
