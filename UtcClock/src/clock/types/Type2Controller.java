package clock.types;

import clock.UtcClockSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author Philipp
 */
public class Type2Controller extends ClockObserver{
    @FXML Label time;
    @FXML Label timezone;
    @FXML Label type;

    @Override
    public void updateObserver() {
        String timeString;
        int hours = UtcClockSingleton.getInstance().modulo(
                UtcClockSingleton.getInstance().getHours() + super.offset, 24);

        timeString = UtcClockSingleton.getInstance().convertToAMPM(hours);
        hours = Integer.parseInt(timeString.split(" ")[0]);
        type.setText(timeString.split(" ")[1]);

        time.setText(String.format("%02d:%02d:%02d\n", hours,
                UtcClockSingleton.getInstance().getMinutes(), UtcClockSingleton.getInstance().getSeconds()));

    }

    @Override
    public void setTimezoneLabel(String timezone){
        this.timezone.setText(timezone);
    }
}
