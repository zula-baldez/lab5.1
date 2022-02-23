package zula.util;


/**
 * Class that works with console
 */
public class ConsoleManager {

    private final InputManager inputManager;
    private final OutputManager outputManager;
    private final ListManager listManager;
    private boolean processStillWorks = true;

    public ConsoleManager(InputManager inputManager, OutputManager outputManager, ListManager listManager) {
        this.inputManager = inputManager;
        this.outputManager = outputManager;
        this.listManager = listManager;

    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public OutputManager getOutputManager() {
        return outputManager;
    }

    public ListManager getListManager() {
        return listManager;
    }

    public void exitProcess() {
        processStillWorks = false;
    }

    public boolean isProcessStillWorks() {
        return processStillWorks;
    }

}
