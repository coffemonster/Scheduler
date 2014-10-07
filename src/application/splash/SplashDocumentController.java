package application.splash;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import database.Connect;
import database.ConnectDocumentController;
import NodeUtils.ImageGetter;
import application.User;
import application.main.FXMLDocumentController;
import application.main.Main;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import org.controlsfx.control.Notifications;
import org.controlsfx.dialog.*;
import org.controlsfx.dialog.Dialogs.UserInfo;

public class SplashDocumentController implements Initializable{
	
	@FXML private Label splashStatus ;
	@FXML private ProgressBar splashProgressBar ;
	public static Stage copyStartupStage = null ;
	public static Thread connectionChecker = null ;
	public static boolean isAlive = true ;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		Timer time = new Timer();
		splashStatus.setText("Connecting to localhost ...");
		double val = 0 ;
		Connect.connect("localhost", "3306", "scheduler", "root", "1234") ;
		
		if(Connect.getConnection() == null){
			return ;
		}else{
			/*
			connectionChecker = new Thread(new Runnable(){
				@Override
				public void run() {
					while(isAlive){
						System.out.println(Connect.QUERY("SELECT 1 FROM accounts"));
						if(Connect.QUERY("SELECT 1 FROM accounts") == null){
							
						}
						try {
							connectionChecker.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				
			}) ;
			connectionChecker.start();
			*/
		}
		
		
		time.schedule(new TimerTask(){
			public void run(){
				splashProgressBar.setProgress(splashProgressBar.getProgress() + .01);
				if(splashProgressBar.getProgress() > 1){
					time.cancel();
					Platform.runLater(new Runnable(){
						public void run(){
							Main.getSplashStage().close();
							
							//Check if there is an Account
							ResultSet result = Connect.QUERY("SELECT COUNT(*) AS num FROM accounts") ;
							try {
								if(result.next()){
									if(result.getInt("num") == 0){
										try {
											 Dialogs.create()
				                                .title("No Registered Admin")
				                                .message("Please register an Account")
				                                .showError();
											
											Parent parent = FXMLLoader.load(getClass().getResource("/application/user/startup.fxml"));
											Stage createStage = new Stage() ;											
											copyStartupStage = createStage ;
											Scene createAccount = new Scene(parent) ;
											createStage.setScene(createAccount);
											createStage.show();
											
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}else{
										//SHOW THE LOGIN
										Dialogs.create().showLogin(new UserInfo("Username","Password"), accountinfo -> {
											
											String error = User.login(accountinfo.getUserName(), accountinfo.getPassword()) ;
											if(error != null){
												throw new RuntimeException(error);
											}else{
												
												Main.getInstance().showMain() ;
												//primaryStage.show();
												
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
												
												//Insert Admin tab
												if(User.isAdmin() || User.getPrivilege().contains(new Integer(8)) || User.getPrivilege().contains(new Integer(9)) || User.getPrivilege().contains(new Integer(10)) || User.getPrivilege().contains(new Integer(11))){
													Tab adminTab = new Tab(" ACCOUNTS ") ;
													try {
														AnchorPane adminContent = FXMLLoader.load(getClass().getResource("/application/user/adminusertab.fxml")) ;
														adminTab.setContent(adminContent);
														adminTab.setStyle("-fx-base : #FF6600");
														FXMLDocumentController.getInstance().getTopTabPane().getTabs().add(adminTab) ;
													} catch (IOException e1) {
														e1.printStackTrace();
													}
													
												}
												
												return null;
											}
											
										}) ;
									}
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
										
						}
					});
					
				}
			}
		}, 2000 , 20);
	}

	
}
