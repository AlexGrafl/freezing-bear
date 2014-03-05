package clock;

import clock.types.ClockObserver;

import java.util.ArrayList;
import java.util.Calendar;
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
    public ArrayList<ClockObserver> clockObservers= new ArrayList<ClockObserver>();


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

    public void incrementHours(){
        this.hours = (this.hours + 1) % 24;
    }

    public void decrementHours(){
        this.hours = (this.hours - 1) % 24;
    }

    public void incrementMinutes(){
        this.minutes = (this.minutes + 1) % 60;
    }

    public void decrementMinutes(){
        this.minutes = (this.minutes - 1) % 60;
    }

    public void incrementSeconds(){
        this.seconds = (this.seconds + 1) % 60;
    }

    public void decrementSeconds(){
        this.seconds = (this.seconds - 1) % 60;
    }

    public boolean setHours(int hours){
        if(0 <= hours && hours < 24){
            this.hours = hours;
            return true;
        }
        return false;
    }

    public boolean setMinutes(int minutes){
        if(0 <= minutes && minutes < 60){
            this.minutes= minutes;
            return true;
        }
        return false;
    }

    public boolean setSeconds(int seconds){
        if(0 <= seconds && seconds < 60){
            this.seconds = seconds;
            return true;
        }
        return false;
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
