package clock.commands;

import clock.UtcClockSingleton;

import java.util.ArrayList;

/**
 * @author Alex
 */
public class MacroIncr10Command extends Command {
    private ArrayList<Command> commandArrayList = new ArrayList<Command>();

    public MacroIncr10Command(boolean setHours, boolean setMin, boolean setSec){
        super.setName("Increment Macro");
        for(int i = 0; i<10;i++) commandArrayList.add(new IncrementTimeCommand(setHours, setMin, setSec));
    }
    @Override
    public void doCommand() {
        for(Command tmp : commandArrayList) tmp.doCommand();
    }

    @Override
    public void undoCommand() {
        for(Command tmp : commandArrayList) tmp.undoCommand();
    }
}
