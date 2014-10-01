package clock.commands;

import clock.UtcClockSingleton;

/**
 * @author Alex
 */
public class DecrementTimeCommand extends Command {

    private Boolean hours, minutes, seconds;

    public DecrementTimeCommand(Boolean hours, Boolean minutes, Boolean seconds){
        super.setName("Decrement");
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }
    @Override
    public void undoCommand() {
        //then call clock functions
        //Same goes for decrement
        if(this.hours) UtcClockSingleton.getInstance().incrementHours();
        if(this.minutes) UtcClockSingleton.getInstance().incrementMinutes();
        if(this.seconds) UtcClockSingleton.getInstance().incrementSeconds();
    }

    @Override
    public void doCommand() {
        if(this.hours) UtcClockSingleton.getInstance().decrementHours();
        if(this.minutes) UtcClockSingleton.getInstance().decrementMinutes();
        if(this.seconds) UtcClockSingleton.getInstance().decrementSeconds();
    }
}
