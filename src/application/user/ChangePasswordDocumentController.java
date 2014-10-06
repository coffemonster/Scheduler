package application.user;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialogs;

import congcrete.Account;
import database.Connect;
import application.User;
import application.main.FXMLDocumentController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class ChangePasswordDocumentController implements Initializable{

	@FXML private TextField txtUsername ;
	@FXML private TextField txtPassword ;
	private Account account ;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		account = (Account) arg1.getObject("account") ;
		txtUsername.setText(account.getUsername());
	}
	
	@FXML public void handleEditUser(){
		Action action = Dialogs.create()
		.title("Warning")
		.message("Are you sure you want to change " + account.getUsername())
		.showConfirm() ;
		
		if(action.toString().equalsIgnoreCase("YES")){
			Connect.emptyQUERY("UPDATE accounts SET password = '"+ txtPassword.getText() +"' WHERE username = '"+ txtUsername.getText() +"'");
			
			ImageView image = new ImageView(new NodeUtils.ImageGetter("check.png").getImage()) ;
			NotificationPane noti = (NotificationPane)FXMLDocumentController.getInstance().getTabpane().getSelectionModel().getSelectedItem().getContent() ;
			noti.show("Success" , image);
			
			txtPassword.setText("");
		}
	}

}
