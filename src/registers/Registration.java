package registers;

import core.Engine;
import core.TimeListener;
import core.events.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A registration object represents a registration of an simulation event listener. Encapsulates the registering
 * logic and offers a clean an readable interface to register an event listener that listens for one o many types of
 * events.
 */
public class Registration {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private Fields
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private Engine engine;
    private final List<? extends Unregistrable> unregistrables;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Constructor
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private Registration(Engine engine, List<? extends Registrable> registrables,
                         List<? extends Unregistrable> unregistrables) {
        this.engine = engine;
        this.unregistrables = unregistrables;

        registrables.forEach(Registrable::register);
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Tells if the registration is still registered or not.
     *
     * @return true if still registered and false otherwise.
     */
    public boolean isRegistered() {
        return engine != null;
    }

    /**
     * Cancels the registration. Cancelling an already cancelled registration  has no effect.
     */
    public void cancel() {

        if (engine != null) {
            unregistrables.forEach(Unregistrable::unregister);
            engine = null;
        }
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Creation Methods and Builder class
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Starts the building process to create a registered registration.
     *
     * @param engine engine to register for.
     * @return register instance to continue building process.
     */
    public static Register registrationFor(Engine engine, SimulationEventListener eventListener) {
        return new Register(engine, eventListener);
    }

    /**
     * Returns a no registration object. It is a registration object that is no registered.
     *
     * @return empty registration registered to nothing.
     */
    public static Registration noRegistration() {
        return new Registration(null, Collections.emptyList(), Collections.emptyList());
    }

    /**
     * Register is a builder class for the registration.
     */
    public static class Register {

        private final Engine engine;
        private final SimulationEventListener eventListener;

        private final List<Registrable> registrable = new ArrayList<>();
        private final List<Unregistrable> unregistrable = new ArrayList<>();

        public Register(Engine engine, SimulationEventListener eventListener) {
            this.engine = engine;
            this.eventListener = eventListener;
        }

        public Register timeEvents() {
            this.registrable.add(() -> engine.timeProperty().addListener((TimeListener) eventListener));
            this.unregistrable.add(() -> engine.timeProperty().removeListener((TimeListener) eventListener));
            return this;
        }

        public Register detectEvents() {
            this.registrable.add(() -> engine.getEventGenerator().addDetectListener((DetectListener) eventListener));
            this.unregistrable.add(() -> engine.getEventGenerator().removeDetectListener((DetectListener)
                    eventListener));
            return this;
        }

        public Register endEvents() {
            this.registrable.add(() -> engine.getEventGenerator().addEndListener((EndListener) eventListener));
            this.unregistrable.add(() -> engine.getEventGenerator().removeEndListener((EndListener) eventListener));
            return this;
        }

        public Register exportEvents() {
            this.registrable.add(() -> engine.getEventGenerator().addExportListener((ExportListener) eventListener));
            this.unregistrable.add(() -> engine.getEventGenerator().removeExportListener((ExportListener)
                    eventListener));
            return this;
        }

        public Register importEvents() {
            this.registrable.add(() -> engine.getEventGenerator().addImportListener((ImportListener) eventListener));
            this.unregistrable.add(() -> engine.getEventGenerator().removeImportListener((ImportListener)
                    eventListener));
            return this;
        }

        public Register learnEvents() {
            this.registrable.add(() -> engine.getEventGenerator().addLearnListener((LearnListener) eventListener));
            this.unregistrable.add(() -> engine.getEventGenerator().removeLearnListener((LearnListener) eventListener));
            return this;
        }

        public Register selectEvents() {
            this.registrable.add(() -> engine.getEventGenerator().addSelectListener((SelectListener) eventListener));
            this.unregistrable.add(() -> engine.getEventGenerator().removeSelectListener((SelectListener)
                    eventListener));
            return this;
        }

        public Register startEvents() {
            this.registrable.add(() -> engine.getEventGenerator().addStartListener((StartListener) eventListener));
            this.unregistrable.add(() -> engine.getEventGenerator().removeStartListener((StartListener) eventListener));
            return this;
        }

        public Register terminateEvents() {
            this.registrable.add(() -> engine.getEventGenerator()
                    .addTerminateListener((TerminateListener) eventListener));
            this.unregistrable.add(() -> engine.getEventGenerator()
                    .removeTerminateListener((TerminateListener) eventListener));

            return this;
        }

        public Registration register() {
            return new Registration(engine, registrable, unregistrable);
        }

    }

}
