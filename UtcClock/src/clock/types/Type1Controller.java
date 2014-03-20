package clock.types;

import clock.UtcClockSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * @author Alex
 */
public class Type1Controller extends ClockObserver{
    @FXML Label time;
    @FXML Label timezone;
    @FXML Label type;

    @Override
    public void updateObserver() {
        time.setText(String.format("%02d:%02d:%02d\n", UtcClockSingleton.getInstance().modulo(
                UtcClockSingleton.getInstance().getHours() + super.offset, 24),
                UtcClockSingleton.getInstance().getMinutes(), UtcClockSingleton.getInstance().getSeconds()));
    }

    @Override
    public void setTimezoneLabel(String timezone){
        this.timezone.setText(timezone);
    }
}
