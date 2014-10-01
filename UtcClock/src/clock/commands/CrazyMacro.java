package clock.commands;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Alex
 */
public class CrazyMacro extends Command {
    private ArrayList<Command> commandArrayList = new ArrayList<Command>();

    public CrazyMacro(){
        super.setName("Crazy Macro");
        int x = 340, y = 250;
        commandArrayList.add(new SetTimeCommand(1,2,3));

        commandArrayList.add(new ShowCommand("Type 1", "Los Angeles", 1, 1));
        commandArrayList.add(new ShowCommand("Type 2", "Los Angeles", x, 1));

        commandArrayList.add(new ShowCommand("Type 1", "UTC", 1, y));
        commandArrayList.add(new ShowCommand("Type 2", "UTC", x*3, y));

        commandArrayList.add(new ShowCommand("Type 1", "Moscow", x*2, y*2));
        commandArrayList.add(new ShowCommand("Type 2", "Moscow", x*3, y*2));

        commandArrayList.add(new ShowCommand("Type 1", "Sydney", x*2, 1));
        commandArrayList.add(new ShowCommand("Type 2", "Sydney", x*3, 1));

        commandArrayList.add(new ShowCommand("Type 1", "Vienna", 1 , y*2));
        commandArrayList.add(new ShowCommand("Type 2", "Vienna", x , y*2));

        commandArrayList.add(new IncrementTimeCommand(true, false, false));
        commandArrayList.add(new DecrementTimeCommand(false, true, false));
    }
    @Override
    public void doCommand() {
        for(Command tmp : commandArrayList) tmp.doCommand();
    }

    @Override
    public void undoCommand() {
        Collections.reverse(commandArrayList);
        for(Command tmp : commandArrayList) tmp.undoCommand();
        Collections.reverse(commandArrayList);
    }
}
