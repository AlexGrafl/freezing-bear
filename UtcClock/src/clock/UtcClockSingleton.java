package clock;

import clock.commands.Command;
import clock.types.ClockObserver;
import com.sun.jmx.remote.internal.ClientCommunicatorAdmin;
import javafx.beans.Observable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Stack;
import java.util.TimeZone;

/**
 * @author Alex
 *
 * Uhrobjekt
 */
public class UtcClockSingleton {
    private static UtcClockSingleton ourInstance = new UtcClockSingleton();

    private int hours;
    private int minutes;
    private int seconds;
    public ArrayList<ClockObserver> clockObservers = new ArrayList<ClockObserver>();

    public Stack<Command> doneStack = new Stack<Command>();
    public Stack<Command> undoneStack = new Stack<Command>();

    public static UtcClockSingleton getInstance() {
        return ourInstance;
    }

    private UtcClockSingleton() {
        this.initializeClock();
    }

    private void initializeClock(){
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        hours = cal.get(Calendar.HOUR_OF_DAY);
        minutes = cal.get(Calendar.MINUTE);
        seconds = cal.get(Calendar.SECOND);
    }


    public int getHours(){
        return hours;
    }

    public int getMinutes(){
        return minutes;
    }

    public int getSeconds(){
        return seconds;
    }

    public boolean incrementHours(){
        this.hours = (this.hours + 1) % 24;
        return this.hours == 0;
    }

    public void decrementHours(){
        this.hours = (this.hours - 1) % 24;
    }

    public boolean incrementMinutes(){
        this.minutes = (this.minutes + 1) % 60;
        return this.minutes == 0;
    }

    public void decrementMinutes(){
        this.minutes = (this.minutes - 1) % 60;
    }

    public boolean incrementSeconds(){
        this.seconds = (this.seconds + 1) % 60;
        return this.seconds == 0;
    }

    public void decrementSeconds(){
        this.seconds = (this.seconds - 1) % 60;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public void registerObserver(ClockObserver observer){
        this.clockObservers.add(observer);
    }

    public boolean unregisterObserver(ClockObserver observer){
        if(this.clockObservers.contains(observer)){
            this.clockObservers.remove(observer);
            return true;
        }
        return false;
    }

    public void notifyAllObservers(){
        for(ClockObserver observer : clockObservers){
            observer.updateObserver();
        }
    }

}
