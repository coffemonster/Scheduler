package application.splash;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import database.Connect;
import application.main.Main;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SplashDocumentController implements Initializable{
	
	@FXML private Label splashStatus ;
	@FXML private ProgressBar splashProgressBar ;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Timer time = new Timer();
		double val = 0 ;
		Connect.connect("localhost", "3306", "scheduler", "root", "1234") ;
		splashStatus.setText("Connecting to localhost ...");
		time.schedule(new TimerTask(){
			public void run(){
				splashProgressBar.setProgress(splashProgressBar.getProgress() + .01);
				if(splashProgressBar.getProgress() > 1){
					time.cancel();
					Platform.runLater(new Runnable(){
						public void run(){
							Main.getSplashStage().close();
							Main.getMainStage().show();
							
						}
					});
					
				}
			}
		}, 2000 , 20);
		
	}

	
}
