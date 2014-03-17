package clock.commands;

/**
 * @author Alex
 */
public abstract class Command {



    private String name;
    public abstract void doCommand();
    public abstract void undoCommand();

    public String getName(){
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
