package clock.commands;

/**
 * @author Alex
 */
public interface Command {
    void doCommand();
    void undoCommand();
}
