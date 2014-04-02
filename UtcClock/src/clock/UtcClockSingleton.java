package clock;

import clock.commands.Command;
import clock.types.ClockObserver;
import com.sun.jmx.remote.internal.ClientCommunicatorAdmin;
import javafx.beans.Observable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private String helpMessage;
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
        this.hours = modulo(this.hours + 1, 24);
        return this.hours == 0;
    }

    public void decrementHours(){
        this.hours = modulo(this.hours - 1, 24);
    }

    public boolean incrementMinutes(){
        this.minutes = modulo(this.minutes + 1, 60);
        return this.minutes == 0;
    }

    public void decrementMinutes(){
        this.minutes = modulo(this.minutes - 1, 60);
    }

    public boolean incrementSeconds(){
        this.seconds = modulo(this.seconds + 1, 60);
        return this.seconds == 0;
    }

    public void decrementSeconds(){
        this.seconds = modulo(this.seconds - 1, 60);
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

    public synchronized void notifyAllObservers(){
        for(ClockObserver observer : clockObservers){
            observer.updateObserver();
        }
    }

    public int modulo(int value, int modulo){
         return (((value% modulo) + modulo)% modulo);
     }

    public void setHelpMessage(String message){ helpMessage = message; }
    public String getHelpMessage() { return helpMessage; }

    public String convertToAMPM(int h){
        SimpleDateFormat displayFormat = new SimpleDateFormat("hh a");
        SimpleDateFormat parseFormat = new SimpleDateFormat("HH");
        Date date = null;
        try {
            date = parseFormat.parse(Integer.toString(h));
        } catch (ParseException e) {
            System.out.println("Couldn't convert time: " + Integer.toString(h));
        }
        return displayFormat.format(date);
    }
}
