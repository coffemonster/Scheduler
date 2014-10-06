package application.user;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;
import org.controlsfx.dialog.Dialogs;
import org.controlsfx.dialog.Dialogs.UserInfo;

import NodeUtils.ImageGetter;
import application.User;
import application.main.FXMLDocumentController;
import application.main.Main;
import application.splash.SplashDocumentController;
import application.validation.Validation;
import database.Connect;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class startupController implements Initializable{
	
	@FXML private TextField txtUsername ;
	@FXML private TextField txtPassword ;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	@FXML public void handleAddUser(ActionEvent e){
		Validation validator = new Validation() ;
		validator.validateEmpty("Username", txtUsername.getText(), txtUsername) ;
		validator.validateTextWithNumbers("Username", txtUsername.getText(), txtUsername) ;
		validator.validateEmpty("Password", txtPassword.getText(), txtPassword) ;
		validator.validateTextWithNumbers("Password", txtPassword.getText(), txtPassword) ;
		
		if(validator.hasError()){
			String error = "" ;
			for(int x = 0 ; x < validator.getErrorList().size() ; x++){
				error += validator.getErrorList().get(x) + "\n";
			}
			
			Dialogs.create()
            .title("Input Error")
            .message(error)
            .showError();
			return ;
		}
		
		int primary = Connect.getNextIntegerPrimary("accounts", "account_id") ;
		Connect.emptyQUERY("INSERT INTO accounts(account_id,username,password,isAdmin) VALUES("+ primary +",'"+ txtUsername.getText() +"','"+ txtPassword.getText() +"',true)");
	
		SplashDocumentController.copyStartupStage.close(); 
		
		//SHOW THE LOGIN
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
				
				//Insert Admin tab
				if(User.isAdmin()){
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
