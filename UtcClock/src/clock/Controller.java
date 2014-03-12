package clock;

import clock.commands.Command;
import clock.commands.SetTimeCommand;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    TextField hours;
    @FXML
    TextField minutes;
    @FXML
    TextField seconds;
    @FXML
    Label error;

    @FXML
    public void setTime(){
        error.setText("");
        int hours = -1;
        int minutes = -1;
        int seconds = -1;
        try{
            if(!this.hours.getText().equals("")) hours = Integer.parseInt(this.hours.getText());
            if(!this.minutes.getText().equals("")) minutes = Integer.parseInt(this.minutes.getText());
            if(!this.seconds.getText().equals("")) seconds = Integer.parseInt(this.seconds.getText());
            if(hours < -1 || hours > 23 || minutes < -1 || minutes > 59 || seconds < -1 || seconds > 59){
                throw new NumberFormatException();
            }
            Command setTime = new SetTimeCommand(hours, minutes, seconds);
            setTime.doCommand();
            UtcClockSingleton.getInstance().doneStack.push(setTime);
        }
        catch(NumberFormatException e){
            error.setText("Invalid time!");
        }
    }

    @FXML
    public void undoCommand(){
        error.setText("");
        if(!UtcClockSingleton.getInstance().doneStack.empty()){
            Command lastCommand = UtcClockSingleton.getInstance().doneStack.peek();
            lastCommand.undoCommand();
            UtcClockSingleton.getInstance().doneStack.pop();
            UtcClockSingleton.getInstance().undoneStack.push(lastCommand);
        }
        else{
            error.setText("Nothing to undo!");
        }
    }

    @FXML
    public void redoCommand(){
        error.setText("");
        if(!UtcClockSingleton.getInstance().undoneStack.empty()){
            Command prevCommand = UtcClockSingleton.getInstance().undoneStack.peek();
            prevCommand.doCommand();
            UtcClockSingleton.getInstance().undoneStack.pop();
            UtcClockSingleton.getInstance().doneStack.push(prevCommand);
        }
        else{
            error.setText("Nothing to redo!");
        }

    }
}
