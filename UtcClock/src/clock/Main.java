package clock;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.TimeZone;


public class Main extends Application implements Runnable{
    private Thread thread;
    private boolean running;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("controlPanel.fxml"));
        primaryStage.setTitle("UTC Clock - Control panel");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        System.out.println("Started.");
        thread = new Thread(this);
        running = true;
        thread.start();
    }
    @Override
    public void stop() {
        System.out.println("Stopped.");
        running = false;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void run() {
        while(running){
            try{
                Thread.sleep(1000);

                //nach jeder sekunde hochzählen und dann die sekunden, minuten und stunden anpassen
                if(UtcClockSingleton.getInstance().incrementSeconds())
                    if(UtcClockSingleton.getInstance().incrementMinutes())
                        UtcClockSingleton.getInstance().incrementHours();

                //danach alle benachrichtigen
                UtcClockSingleton.getInstance().notifyAllObservers();
                System.out.format("%02d:%02d:%02d\n", UtcClockSingleton.getInstance().getHours(),
                        UtcClockSingleton.getInstance().getMinutes(), UtcClockSingleton.getInstance().getSeconds());
            } catch (InterruptedException e) {
                e.printStackTrace();
                this.thread.interrupt();
            }
        }
    }
}
