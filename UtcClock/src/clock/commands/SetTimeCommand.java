package clock.commands;

import clock.UtcClockSingleton;

/**
 * @author Alex
 */
public class SetTimeCommand implements Command{

    private int prevHours, prevMinutes, prevSeconds, hours, minutes, seconds;

    public SetTimeCommand(int hours, int minutes, int seconds){
        prevHours = UtcClockSingleton.getInstance().getHours();
        prevMinutes = UtcClockSingleton.getInstance().getMinutes();
        prevSeconds = UtcClockSingleton.getInstance().getSeconds();
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    @Override
    public void doCommand() {
        if(this.hours != -1) UtcClockSingleton.getInstance().setHours(hours);
        if(this.minutes != -1 ) UtcClockSingleton.getInstance().setMinutes(minutes);
        if(this.seconds != -1) UtcClockSingleton.getInstance().setSeconds(seconds);
    }

    @Override
    public void undoCommand() {
        UtcClockSingleton.getInstance().setHours(prevHours);
        UtcClockSingleton.getInstance().setMinutes(prevMinutes);
        UtcClockSingleton.getInstance().setSeconds(prevSeconds);
    }
}
