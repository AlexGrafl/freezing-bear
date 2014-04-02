package clock.commands;

import clock.UtcClockSingleton;
import clock.types.ClockObserver;
import clock.types.Type1Controller;
import clock.types.Type2Controller;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * @author Alex
 */
public class ShowCommand extends Command {
    private ClockObserver controller;
    private Stage stage;
    private int xLocation,yLocation;

    public ShowCommand(String type, String timezone, int xLocation, int yLocation) {
        super.setName("Show");
        int offset;
        this.xLocation = xLocation;
        this.yLocation = yLocation;

        if(timezone.equals("Vienna")) {
            offset = 1;
        } else if (timezone.equals("Moscow")) {
            offset = 4;
        } else if (timezone.equals("Sydney")) {
            offset = 10;
        } else if (timezone.equals("Los Angeles")) {
            offset = -8;
        } else {
            offset = 0;
            timezone = "UTC";
        }

        FXMLLoader f1 = new FXMLLoader();
        if (type.equals("Type 1")) {
            f1.setLocation(getClass().getResource("../types/type1.fxml"));
        }
        if (type.equals("Type 2")) {
            f1.setLocation(getClass().getResource("../types/type2.fxml"));
        }
        try {
            f1.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = f1.getRoot();
        Stage newStage = new Stage(StageStyle.DECORATED);
        newStage.initModality(Modality.NONE);
        controller = f1.getController();
        Scene scene = new Scene(root);
        newStage.setScene(scene);
        newStage.setTitle(type);

        newStage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                UtcClockSingleton.getInstance().unregisterObserver(controller);
            }
        });

        controller.setTimezoneLabel(timezone);
        controller.setOffset(offset);


        this.stage = newStage;
    }

    @Override
    public void doCommand() {
        if(xLocation != -1 && yLocation != -1) {
            stage.setX(xLocation);
            stage.setY(yLocation);
        }
        stage.show();
        UtcClockSingleton.getInstance().registerObserver(controller);
    }

    @Override
    public void undoCommand() {
        stage.close();
    }
}
