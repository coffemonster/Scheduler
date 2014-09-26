package application.splash;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import database.Connect;
import NodeUtils.ImageGetter;
import application.User;
import application.main.Main;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import org.controlsfx.control.Notifications;
import org.controlsfx.dialog.*;
import org.controlsfx.dialog.Dialogs.UserInfo;

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
							Dialogs.create().showLogin(new UserInfo("Username","Password"), accountinfo -> {
								
								String error = User.login(accountinfo.getUserName(), accountinfo.getPassword()) ;
								if(error != null){
									throw new RuntimeException(error);
								}else{
									Main.getMainStage().show();
									
									Platform.runLater(new Runnable(){

										@Override
										public void run() {
											ImageGetter img = new ImageGetter("we.png") ;
											ImageView image = new ImageView(img.getImage()) ;
											image.setFitHeight(100);
											image.setFitWidth(100);
											
											Notifications.create().title("Welcome!")
											.text("Welcome " + User.getUsername())
											.graphic(image)
											.position(javafx.geometry.Pos.BOTTOM_RIGHT)
											.hideAfter(Duration.millis(4000)).show();
										}
										
									});
									
									return null;
								}
								
							}) ;
							
						}
					});
					
				}
			}
		}, 2000 , 20);
	}

	
}
