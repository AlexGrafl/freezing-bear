package clock.commands;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Alex
 */
public class MacroDecr10Command extends Command {
    private ArrayList<Command> commandArrayList = new ArrayList<Command>();

    public MacroDecr10Command(boolean setHours, boolean setMin, boolean setSec){
        super.setName("Decrement Macro");
        for(int i = 0; i<10;i++) commandArrayList.add(new DecrementTimeCommand(setHours, setMin, setSec));
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
