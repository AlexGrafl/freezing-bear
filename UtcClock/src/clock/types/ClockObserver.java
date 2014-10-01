package clock.types;

import javafx.stage.Stage;

/**
 * @author Alex
 *
 * Abstrakte Klasse für Typ-Fenster (Digital, AM-PM, Analog...)
 * Soll generelle Fenstereigenschaften enthalten
 *
 */
public abstract class ClockObserver {
    protected int offset;
    public ClockObserver(){ }

    public abstract void updateObserver();

    public void setOffset(int offset){
        this.offset = offset;
    }

    public abstract void setTimezoneLabel(String timezone);


}
