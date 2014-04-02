package clock.commands;

import clock.UtcClockSingleton;

/**
 * @author Alex
 */
public class HelpCommand extends Command {
    private static final String helpMessage = "Welcome to UTC-Clock!\n" +
            "The initial Value of the clock displays the UTC-Time\n" +
            "- 'SHOW' creates a new display\n" +
            "\t-- type 1: 24hours\n" +
            "\t-- type 2: AM/PM\n" +
            "- 'SET' Time of all Clocks displayed\n" +
            "- 'Inc/dec' increase or decrease hours, minutes and/or seconds per one\n" +
            "\nGood luck and have fun!";
    public HelpCommand(){ super.setName("Help"); }
    @Override
    public void doCommand() {
        UtcClockSingleton.getInstance().setHelpMessage(helpMessage);
    }

    @Override
    public void undoCommand() {
        UtcClockSingleton.getInstance().setHelpMessage("Nothing to see here!");
    }
}
