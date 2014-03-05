package clock.types;

/**
 * @author Alex
 *
 * Abstrakte Klasse für Typ-Fenster (Digital, AM-PM, Analog...)
 * Soll generelle Fenstereigenschaften enthalten
 *
 */
public abstract class ClockObserver {

    public ClockObserver(){ }

    public abstract void updateObserver();

}
