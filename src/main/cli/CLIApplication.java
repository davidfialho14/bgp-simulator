package main.cli;

import main.Parameters;
import main.SimulatorLauncher;
import org.apache.commons.cli.ParseException;

/**
 * The CLI application class is the dual of the GUI application that supports a command line interface instead
 * of a graphical interface.
 */
public class CLIApplication {

    private final Parameters parameters;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Simulator launcher with the error handler implemented for the CLI interface
     *  Note that errors are now printed to the stderr instead of using alerts
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private final SimulatorLauncher simulatorLauncher = new SimulatorLauncher(
            new CLIErrorHandler(), new CLIProgressHandler());

    /**
     * Creates a new CLI application instance with the parameters for the launcher already initialized. It can only
     * be instantiated from the static {@link #launch(String[])} method.
     *
     * @param parameters parameters for the launcher.
     */
    private CLIApplication(Parameters parameters) {
        this.parameters = parameters;
    }

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
        ParametersCommandLineParser commandLineParser = new ParametersCommandLineParser();

        try {
            CLIApplication cliApplication = new CLIApplication(commandLineParser.parse(args));

            cliApplication.simulate();
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

    }

    /**
     * Starts the simulation with the current parameters.
     */
    private void simulate() {
        int exitCode = simulatorLauncher.launch(parameters);
        
        if (exitCode != 0) {
            System.exit(1);
        }
    }

}
