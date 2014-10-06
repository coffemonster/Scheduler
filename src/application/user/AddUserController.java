package application.user;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.controlsfx.control.NotificationPane;
import org.controlsfx.dialog.Dialogs;

import database.Connect;
import application.main.FXMLDocumentController;
import application.main.Main;
import application.validation.Validation;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class AddUserController implements Initializable{

	@FXML private CheckBox chkAdmin ;
	@FXML private CheckBox chkViewResources ;
	@FXML private CheckBox chkAddResources ;
	@FXML private CheckBox chkEditResources ;
	@FXML private CheckBox chkDeleteResources ;
	@FXML private CheckBox chkPrintSchedule ;
	@FXML private CheckBox chkPrintLogs ;
	@FXML private CheckBox chkViewLogs ;
	@FXML private CheckBox chkDeleteLogs ;
	@FXML private CheckBox chkAddAccounts ;
	@FXML private CheckBox chkDisableAccounts ;
	@FXML private CheckBox chkChangeLoginInformation ;
	@FXML private CheckBox chkChangePriviledge ;
	@FXML private TextField txtUsername ;
	@FXML private TextField txtPassword ;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		chkAdmin.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				if(chkAdmin.isSelected()){
					chkViewResources.setDisable(true);
					chkAddResources.setDisable(true);
					chkEditResources.setDisable(true);
					chkDeleteResources.setDisable(true);
					chkPrintSchedule.setDisable(true);
					chkPrintLogs.setDisable(true);
					chkViewLogs.setDisable(true);
					chkDeleteLogs.setDisable(true);
					chkAddAccounts.setDisable(true);
					chkDisableAccounts.setDisable(true);
					chkChangeLoginInformation.setDisable(true);
					chkChangePriviledge.setDisable(true);
				}else{
					chkViewResources.setDisable(false);
					chkAddResources.setDisable(false);
					chkEditResources.setDisable(false);
					chkDeleteResources.setDisable(false);
					chkPrintSchedule.setDisable(false);
					chkPrintLogs.setDisable(false);
					chkViewLogs.setDisable(false);
					chkDeleteLogs.setDisable(false);
					chkAddAccounts.setDisable(false);
					chkDisableAccounts.setDisable(false);
					chkChangeLoginInformation.setDisable(false);
					chkChangePriviledge.setDisable(false);
				}
			}
		});
		
		//check if there is already an admin
		ResultSet result = Connect.QUERY("SELECT * FROM accounts WHERE isAdmin = true") ;
		try {
			if(result.next()){
				chkAdmin.setVisible(false);
			}else{
				chkAdmin.setVisible(true);
				chkAdmin.setSelected(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@FXML public void handleCheckAll(ActionEvent e){
		if(chkAdmin.isSelected()){
			return ;
		}
		
		if(chkViewResources.isSelected() && chkAddResources.isSelected() && chkEditResources.isSelected() && chkDeleteResources.isSelected() && chkPrintSchedule.isSelected() &&
		   chkPrintLogs.isSelected() && chkViewLogs.isSelected() && chkDeleteLogs.isSelected() && chkAddAccounts.isSelected() && chkDisableAccounts.isSelected() &&
		   chkChangeLoginInformation.isSelected() && chkChangePriviledge.isSelected()){
			chkViewResources.setSelected(false);
			chkAddResources.setSelected(false);
			chkEditResources.setSelected(false);
			chkDeleteResources.setSelected(false);
			chkPrintSchedule.setSelected(false);
			chkPrintLogs.setSelected(false);
			chkViewLogs.setSelected(false);
			chkDeleteLogs.setSelected(false);
			chkAddAccounts.setSelected(false);
			chkDisableAccounts.setSelected(false);
			chkChangeLoginInformation.setSelected(false);
			chkChangePriviledge.setSelected(false);
		}else{
			chkViewResources.setSelected(true);
			chkAddResources.setSelected(true);
			chkEditResources.setSelected(true);
			chkDeleteResources.setSelected(true);
			chkPrintSchedule.setSelected(true);
			chkPrintLogs.setSelected(true);
			chkViewLogs.setSelected(true);
			chkDeleteLogs.setSelected(true);
			chkAddAccounts.setSelected(true);
			chkDisableAccounts.setSelected(true);
			chkChangeLoginInformation.setSelected(true);
			chkChangePriviledge.setSelected(true);
		}
	}
	
	@FXML public void handleCheckAllResources(ActionEvent e){
		if(chkAdmin.isSelected()){
			return ;
		}
		
		if(chkViewResources.isSelected() && chkAddResources.isSelected() && chkEditResources.isSelected() && chkDeleteResources.isSelected()){
			chkViewResources.setSelected(false);
			chkAddResources.setSelected(false);
			chkEditResources.setSelected(false);
			chkDeleteResources.setSelected(false);
		}else{
			chkViewResources.setSelected(true);
			chkAddResources.setSelected(true);
			chkEditResources.setSelected(true);
			chkDeleteResources.setSelected(true);
		}
	}
	
	@FXML public void handleCheckAllLogs(ActionEvent e){
		if(chkAdmin.isSelected()){
			return ;
		}
		
		if(chkViewLogs.isSelected() && chkDeleteLogs.isSelected()){
			chkViewLogs.setSelected(false);
			chkDeleteLogs.setSelected(false);
		}else{
			chkViewLogs.setSelected(true);
			chkDeleteLogs.setSelected(true);
		}
	}
	
	@FXML public void handleCheckAllPriviliedges(ActionEvent e){
		if(chkAdmin.isSelected()){
			return ;
		}
		
		if(chkPrintSchedule.isSelected() && chkPrintLogs.isSelected()){
			chkPrintSchedule.setSelected(false);
			chkPrintLogs.setSelected(false);	
		}else{
			chkPrintSchedule.setSelected(true);
			chkPrintLogs.setSelected(true);
		}
	}
	
	@FXML public void handleCheckAllAccounts(ActionEvent e){
		if(chkAdmin.isSelected()){
			return ;
		}
		
		if(chkAddAccounts.isSelected() && chkDisableAccounts.isSelected() && chkChangeLoginInformation.isSelected() && chkChangePriviledge.isSelected()){
			chkAddAccounts.setSelected(false);
			chkDisableAccounts.setSelected(false);
			chkChangeLoginInformation.setSelected(false);
			chkChangePriviledge.setSelected(false);	
		}else{
			chkAddAccounts.setSelected(true);
			chkDisableAccounts.setSelected(true);
			chkChangeLoginInformation.setSelected(true);
			chkChangePriviledge.setSelected(true);
		}
	}
	
	@FXML public void handleAddUser(ActionEvent e){
		Validation validator = new Validation() ;
		validator.validateEmpty("Username", txtUsername.getText(), txtUsername) ;
		validator.validateTextWithNumbers("Usernamer", txtUsername.getText(), txtUsername) ;
		validator.validateEmpty("Password", txtPassword.getText(), txtPassword) ;
		validator.validateTextWithNumbers("Password", txtPassword.getText(), txtPassword) ;
		
		if(!chkViewResources.isSelected() && !chkAddResources.isSelected() && !chkEditResources.isSelected() && !chkDeleteResources.isSelected() && !chkPrintSchedule.isSelected() &&
				   !chkPrintLogs.isSelected() && !chkViewLogs.isSelected() && !chkDeleteLogs.isSelected() && !chkAddAccounts.isSelected() && !chkDisableAccounts.isSelected() &&
				   !chkChangeLoginInformation.isSelected() && !chkChangePriviledge.isSelected() && !chkAdmin.isSelected()){
			validator.getErrorList().add("Error : Please select atleast 1 privilege") ;
		}

		
		if(validator.hasError()){
			validator.showError();
			ImageView image = new ImageView(new NodeUtils.ImageGetter("error.png").getImage()) ;
			NotificationPane noti = (NotificationPane)FXMLDocumentController.getInstance().getTabpane().getSelectionModel().getSelectedItem().getContent() ;
			noti.show("An error occured during validation" , image);
			return ;
		}else{
			validator.hideError();
		}
		
		ResultSet result = Connect.QUERY("SELECT * FROM accounts WHERE username = '"+ txtUsername.getText().trim() +"'");
		try {
			if(result.next()){
				Dialogs.create()
				.title("Error creating account")
				.message("Username already taken")
				.showError() ;
				return ;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		int nextPrimary = Connect.getNextIntegerPrimary("accounts", "account_id") ;
		
		Connect.emptyQUERY("INSERT INTO accounts(account_id,username,password) VALUES("+ nextPrimary +",'"+ txtUsername.getText() +"','"+ txtPassword.getText() + "')") ;
		
		if(!chkAdmin.isSelected()){
			if(chkViewResources.isSelected()){
				int privilegePrimary = Connect.getNextIntegerPrimary("privileges", "privilege_id") ;
				Connect.emptyQUERY("INSERT INTO privileges VALUES("+ privilegePrimary +","+ nextPrimary +","+ 0 +",'Viewing Resources')");
			}
			if(chkAddResources.isSelected()){
				int privilegePrimary = Connect.getNextIntegerPrimary("privileges", "privilege_id") ;
				Connect.emptyQUERY("INSERT INTO privileges VALUES("+ privilegePrimary +","+ nextPrimary +","+ 1 +",'Adding Resources')");
			}
			if(chkEditResources.isSelected()){
				int privilegePrimary = Connect.getNextIntegerPrimary("privileges", "privilege_id") ;
				Connect.emptyQUERY("INSERT INTO privileges VALUES("+ privilegePrimary +","+ nextPrimary +","+ 2 +",'Editing Resources')");
			}
			if(chkDeleteResources.isSelected()){
				int privilegePrimary = Connect.getNextIntegerPrimary("privileges", "privilege_id") ;
				Connect.emptyQUERY("INSERT INTO privileges VALUES("+ privilegePrimary +","+ nextPrimary +","+ 3 +",'Deleting Resources')");
			}
			if(chkViewLogs.isSelected()){
				int privilegePrimary = Connect.getNextIntegerPrimary("privileges", "privilege_id") ;
				Connect.emptyQUERY("INSERT INTO privileges VALUES("+ privilegePrimary +","+ nextPrimary +","+ 4 +",'Viewing Logs')");
			}
			if(chkDeleteLogs.isSelected()){
				int privilegePrimary = Connect.getNextIntegerPrimary("privileges", "privilege_id") ;
				Connect.emptyQUERY("INSERT INTO privileges VALUES("+ privilegePrimary +","+ nextPrimary +","+ 5 +",'Deleting Logs')");
			}
			if(chkPrintSchedule.isSelected()){
				int privilegePrimary = Connect.getNextIntegerPrimary("privileges", "privilege_id") ;
				Connect.emptyQUERY("INSERT INTO privileges VALUES("+ privilegePrimary +","+ nextPrimary +","+ 6 +",'Printing Schedules')");
			}
			if(chkPrintLogs.isSelected()){
				int privilegePrimary = Connect.getNextIntegerPrimary("privileges", "privilege_id") ;
				Connect.emptyQUERY("INSERT INTO privileges VALUES("+ privilegePrimary +","+ nextPrimary +","+ 7 +",'Printing Logs')");
			}
			if(chkAddAccounts.isSelected()){
				int privilegePrimary = Connect.getNextIntegerPrimary("privileges", "privilege_id") ;
				Connect.emptyQUERY("INSERT INTO privileges VALUES("+ privilegePrimary +","+ nextPrimary +","+ 8 +",'Adding Accounts')");
			}
			if(chkDisableAccounts.isSelected()){
				int privilegePrimary = Connect.getNextIntegerPrimary("privileges", "privilege_id") ;
				Connect.emptyQUERY("INSERT INTO privileges VALUES("+ privilegePrimary +","+ nextPrimary +","+ 9 +",'Disabling Accounts')");
			}
			if(chkChangeLoginInformation.isSelected()){
				int privilegePrimary = Connect.getNextIntegerPrimary("privileges", "privilege_id") ;
				Connect.emptyQUERY("INSERT INTO privileges VALUES("+ privilegePrimary +","+ nextPrimary +","+ 10 +",'Changing Login Information')");
			}
			if(chkChangePriviledge.isSelected()){
				int privilegePrimary = Connect.getNextIntegerPrimary("privileges", "privilege_id") ;
				Connect.emptyQUERY("INSERT INTO privileges VALUES("+ privilegePrimary +","+ nextPrimary +","+ 11 +",'Changing Privilege')");
			}
		}
		
		ImageView image = new ImageView(new NodeUtils.ImageGetter("check.png").getImage()) ;
		NotificationPane noti = (NotificationPane)FXMLDocumentController.getInstance().getTabpane().getSelectionModel().getSelectedItem().getContent() ;
		noti.show("Success" , image);

		
		chkAddResources.setSelected(false);
		chkEditResources.setSelected(false);
		chkDeleteResources.setSelected(false);
		chkPrintSchedule.setSelected(false);
		chkPrintLogs.setSelected(false);
		chkViewLogs.setSelected(false);
		chkDeleteLogs.setSelected(false);
		chkAddAccounts.setSelected(false);
		chkDisableAccounts.setSelected(false);
		chkChangeLoginInformation.setSelected(false);
		chkChangePriviledge.setSelected(false);
		
		txtUsername.clear();
		txtPassword.clear();
		chkAdmin.setSelected(false);
		
		FXMLDocumentController.getInstance().updateAccounts();
	}
}
