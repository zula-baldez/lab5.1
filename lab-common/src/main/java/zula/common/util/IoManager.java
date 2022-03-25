package zula.common.util;



/**
 * Class that works with console
 */
public class IoManager {

    private final InputManager inputManager;
    private final OutputManager outputManager;
    private boolean processStillWorks = true;
    public IoManager(InputManager inputManager, OutputManager outputManager) {
        this.inputManager = inputManager;
        this.outputManager = outputManager;

    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public OutputManager getOutputManager() {
        return outputManager;
    }


    public void exitProcess() {
        processStillWorks = false;
    }

    public boolean isProcessStillWorks() {
        return processStillWorks;
    }

}
