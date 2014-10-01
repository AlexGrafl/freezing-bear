package clock.commands;

import clock.UtcClockSingleton;

/**
 * @author Alex
 */
public class IncrementTimeCommand extends Command {

    private Boolean hours, minutes, seconds;

    public IncrementTimeCommand(Boolean hours, Boolean minutes, Boolean seconds){
        super.setName("Increment");
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }
    @Override
    public void doCommand() {
        //then call clock functions
        //Same goes for decrement
        if(this.hours) UtcClockSingleton.getInstance().incrementHours();
        if(this.minutes) UtcClockSingleton.getInstance().incrementMinutes();
        if(this.seconds) UtcClockSingleton.getInstance().incrementSeconds();
    }

    @Override
    public void undoCommand() {
        if(this.hours) UtcClockSingleton.getInstance().decrementHours();
        if(this.minutes) UtcClockSingleton.getInstance().decrementMinutes();
        if(this.seconds) UtcClockSingleton.getInstance().decrementSeconds();
    }
}
