package clock;

import clock.commands.Command;
import clock.commands.DecrementTimeCommand;
import clock.commands.IncrementTimeCommand;
import clock.commands.SetTimeCommand;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    CheckBox hoursBool;
    @FXML
    CheckBox minutesBool;
    @FXML
    CheckBox secondsBool;
    @FXML
    TextField hours;
    @FXML
    TextField minutes;
    @FXML
    TextField seconds;
    @FXML
    Label label;

    @FXML
    public void setTime(){
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
            executeCommand(new SetTimeCommand(hours, minutes, seconds));
        }
        catch(NumberFormatException e){
            printError("Invalid time!");
        }
    }

    @FXML
    public void undoCommand(){
        label.setText("");
        if(!UtcClockSingleton.getInstance().doneStack.empty()){
            Command lastCommand = UtcClockSingleton.getInstance().doneStack.peek();
            lastCommand.undoCommand();
            printMessage("Command '" + lastCommand.getName() +"' undone.");
            UtcClockSingleton.getInstance().doneStack.pop();
            UtcClockSingleton.getInstance().undoneStack.push(lastCommand);
        }
        else{
            printError("Nothing to undo!");
        }
    }

    @FXML
    public void redoCommand(){
        label.setText("");
        if(!UtcClockSingleton.getInstance().undoneStack.empty()){
            Command prevCommand = UtcClockSingleton.getInstance().undoneStack.peek();
            prevCommand.doCommand();
            printMessage("Command '" + prevCommand.getName() +"' redone.");
            UtcClockSingleton.getInstance().undoneStack.pop();
            UtcClockSingleton.getInstance().doneStack.push(prevCommand);
        }
        else{
            printError("Nothing to redo!");
        }

    }
    @FXML
    public void incrementTime(){
        executeCommand(new IncrementTimeCommand(hoursBool.isSelected(), minutesBool.isSelected(),
                secondsBool.isSelected()));
    }

    @FXML
    public void decrementTime(){
        executeCommand(new DecrementTimeCommand(hoursBool.isSelected(), minutesBool.isSelected(),
                secondsBool.isSelected()));
    }

    public void executeCommand(Command cmd){
        cmd.doCommand();
        printMessage("Command '" + cmd.getName() + "' executed.");
        UtcClockSingleton.getInstance().doneStack.push(cmd);
        UtcClockSingleton.getInstance().undoneStack.clear();
        UtcClockSingleton.getInstance().notifyAllObservers();
    }

    public void printMessage(String msg){
        label.setStyle("-fx-text-fill:black;");
        label.setText(msg);
    }

    public void printError(String err){
        label.setStyle("-fx-text-fill:red;");
        label.setText(err);
    }
}
