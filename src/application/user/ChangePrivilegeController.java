package application.user;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialogs;

import com.mysql.jdbc.exceptions.MySQLDataException;

import application.main.FXMLDocumentController;
import application.validation.Validation;
import congcrete.Account;
import database.Connect;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class ChangePrivilegeController implements Initializable{
	
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
	private Account account ;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		account = (Account) arg1.getObject("account") ;
		
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
		
		txtUsername.setText(account.getUsername());

		if(account.isAdmin()){
			chkAdmin.setSelected(true);
		}
		if(account.getPrivileges().contains(new Integer(0))){
			chkViewResources.setSelected(true);
		}
		if(account.getPrivileges().contains(new Integer(1))){
			chkAddResources.setSelected(true);
		}
		if(account.getPrivileges().contains(new Integer(2))){
			chkEditResources.setSelected(true);
		}
		if(account.getPrivileges().contains(new Integer(3))){
			chkDeleteResources.setSelected(true);
		}
		if(account.getPrivileges().contains(new Integer(4))){
			chkViewLogs.setSelected(true);
		}
		if(account.getPrivileges().contains(new Integer(5))){
			chkDeleteLogs.setSelected(true);
		}
		if(account.getPrivileges().contains(new Integer(6))){
			chkPrintSchedule.setSelected(true);
		}
		if(account.getPrivileges().contains(new Integer(7))){
			chkPrintLogs.setSelected(true);
		}
		if(account.getPrivileges().contains(new Integer(8))){
			chkAddAccounts.setSelected(true);
		}
		if(account.getPrivileges().contains(new Integer(9))){
			chkDisableAccounts.setSelected(true);
		}
		if(account.getPrivileges().contains(new Integer(10))){
			chkChangeLoginInformation.setSelected(true);
		}
		if(account.getPrivileges().contains(new Integer(11))){
			chkChangePriviledge.setSelected(true);
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
	
	@FXML public void handleEditPrivilege(ActionEvent e){
		Validation validator = new Validation() ;
		
		
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
 		
 		Action action = Dialogs.create()
 		.title("Warning")
 		.message("Do you want to continue ?")
 		.showConfirm() ;
 		
 		if(!action.toString().equalsIgnoreCase("YES")){
 			return ;
 		}
 		
 		if(chkAdmin.isSelected()){
 			Connect.emptyQUERY("UPDATE accounts SET isAdmin = true WHERE account_id = '"+ account.getAccount_id() +"'");
 		}else{
 			ResultSet result ;
 			Connect.emptyQUERY("UPDATE accounts SET isAdmin = false WHERE account_id = '"+ account.getAccount_id() +"'");
 			try {
				if(chkViewResources.isSelected()){
					result = Connect.QUERY("SELECT * FROM privileges WHERE account_id = " + account.getAccount_id() + " AND privilege = " + 0) ;
					if(!result.next()){
						int primary = Connect.getNextIntegerPrimary("privileges", "privilege_id") ;
						Connect.emptyQUERY("INSERT INTO privileges(privilege_id , account_id , privilege , privilege_desc) VALUES("+ primary +","+ account.getAccount_id() +","+ 0 +",'Viewing Resources')");
					}
				}else{
					result = Connect.QUERY("SELECT * FROM privileges WHERE account_id = " + account.getAccount_id() + " AND privilege = " + 0) ;
					if(result.next()){
						Connect.emptyQUERY("DELETE FROM privileges WHERE privilege_id = " + result.getInt("privilege_id"));
					}
				}
				
				if(chkAddResources.isSelected()){
					result = Connect.QUERY("SELECT * FROM privileges WHERE account_id = " + account.getAccount_id() + " AND privilege = " + 1) ;
					if(!result.next()){
						int primary = Connect.getNextIntegerPrimary("privileges", "privilege_id") ;
						Connect.emptyQUERY("INSERT INTO privileges(privilege_id , account_id , privilege , privilege_desc) VALUES("+ primary +","+ account.getAccount_id() +","+ 1 +",'Adding Resources')");
					}
				}else{
					result = Connect.QUERY("SELECT * FROM privileges WHERE account_id = " + account.getAccount_id() + " AND privilege = " + 1) ;
					if(result.next()){
						Connect.emptyQUERY("DELETE FROM privileges WHERE privilege_id = " + result.getInt("privilege_id"));
					}
				}
				
				if(chkEditResources.isSelected()){
					result = Connect.QUERY("SELECT * FROM privileges WHERE account_id = " + account.getAccount_id() + " AND privilege = " + 2) ;
					if(!result.next()){
						int primary = Connect.getNextIntegerPrimary("privileges", "privilege_id") ;
						Connect.emptyQUERY("INSERT INTO privileges(privilege_id , account_id , privilege , privilege_desc) VALUES("+ primary +","+ account.getAccount_id() +","+ 2 +",'Editing Resources')");
					}
				}else{
					result = Connect.QUERY("SELECT * FROM privileges WHERE account_id = " + account.getAccount_id() + " AND privilege = " + 2) ;
					if(result.next()){
						Connect.emptyQUERY("DELETE FROM privileges WHERE privilege_id = " + result.getInt("privilege_id"));
					}
				}
				
				if(chkDeleteResources.isSelected()){
					result = Connect.QUERY("SELECT * FROM privileges WHERE account_id = " + account.getAccount_id() + " AND privilege = " + 3) ;
					if(!result.next()){
						int primary = Connect.getNextIntegerPrimary("privileges", "privilege_id") ;
						Connect.emptyQUERY("INSERT INTO privileges(privilege_id , account_id , privilege , privilege_desc) VALUES("+ primary +","+ account.getAccount_id() +","+ 3 +",'Deleting Resources')");
					}
				}else{
					result = Connect.QUERY("SELECT * FROM privileges WHERE account_id = " + account.getAccount_id() + " AND privilege = " + 3) ;
					if(result.next()){
						Connect.emptyQUERY("DELETE FROM privileges WHERE privilege_id = " + result.getInt("privilege_id"));
					}
				}
				
				if(chkViewLogs.isSelected()){
					result = Connect.QUERY("SELECT * FROM privileges WHERE account_id = " + account.getAccount_id() + " AND privilege = " + 4) ;
					if(!result.next()){
						int primary = Connect.getNextIntegerPrimary("privileges", "privilege_id") ;
						Connect.emptyQUERY("INSERT INTO privileges(privilege_id , account_id , privilege , privilege_desc) VALUES("+ primary +","+ account.getAccount_id() +","+ 4 +",'Viewing Logs')");
					}
				}else{
					result = Connect.QUERY("SELECT * FROM privileges WHERE account_id = " + account.getAccount_id() + " AND privilege = " + 4) ;
					if(result.next()){
						Connect.emptyQUERY("DELETE FROM privileges WHERE privilege_id = " + result.getInt("privilege_id"));
					}
				}
				
				if(chkDeleteLogs.isSelected()){
					result = Connect.QUERY("SELECT * FROM privileges WHERE account_id = " + account.getAccount_id() + " AND privilege = " + 5) ;
					if(!result.next()){
						int primary = Connect.getNextIntegerPrimary("privileges", "privilege_id") ;
						Connect.emptyQUERY("INSERT INTO privileges(privilege_id , account_id , privilege , privilege_desc) VALUES("+ primary +","+ account.getAccount_id() +","+ 5 +",'Deleting Logs')");
					}
				}else{
					result = Connect.QUERY("SELECT * FROM privileges WHERE account_id = " + account.getAccount_id() + " AND privilege = " + 5) ;
					if(result.next()){
						Connect.emptyQUERY("DELETE FROM privileges WHERE privilege_id = " + result.getInt("privilege_id"));
					}
				}
				
				if(chkPrintSchedule.isSelected()){
					result = Connect.QUERY("SELECT * FROM privileges WHERE account_id = " + account.getAccount_id() + " AND privilege = " + 6) ;
					if(!result.next()){
						int primary = Connect.getNextIntegerPrimary("privileges", "privilege_id") ;
						Connect.emptyQUERY("INSERT INTO privileges(privilege_id , account_id , privilege , privilege_desc) VALUES("+ primary +","+ account.getAccount_id() +","+ 6 +",'Printing Schedule')");
					}
				}else{
					result = Connect.QUERY("SELECT * FROM privileges WHERE account_id = " + account.getAccount_id() + " AND privilege = " + 6) ;
					if(result.next()){
						Connect.emptyQUERY("DELETE FROM privileges WHERE privilege_id = " + result.getInt("privilege_id"));
					}
				}
				
				if(chkPrintLogs.isSelected()){
					result = Connect.QUERY("SELECT * FROM privileges WHERE account_id = " + account.getAccount_id() + " AND privilege = " + 7) ;
					if(!result.next()){
						int primary = Connect.getNextIntegerPrimary("privileges", "privilege_id") ;
						Connect.emptyQUERY("INSERT INTO privileges(privilege_id , account_id , privilege , privilege_desc) VALUES("+ primary +","+ account.getAccount_id() +","+ 7 +",'Printing Logs')");
					}
				}else{
					result = Connect.QUERY("SELECT * FROM privileges WHERE account_id = " + account.getAccount_id() + " AND privilege = " + 7) ;
					if(result.next()){
						Connect.emptyQUERY("DELETE FROM privileges WHERE privilege_id = " + result.getInt("privilege_id"));
					}
				}
				
				if(chkAddAccounts.isSelected()){
					result = Connect.QUERY("SELECT * FROM privileges WHERE account_id = " + account.getAccount_id() + " AND privilege = " + 8) ;
					if(!result.next()){
						int primary = Connect.getNextIntegerPrimary("privileges", "privilege_id") ;
						Connect.emptyQUERY("INSERT INTO privileges(privilege_id , account_id , privilege , privilege_desc) VALUES("+ primary +","+ account.getAccount_id() +","+ 8 +",'Adding Accounts')");
					}
				}else{
					result = Connect.QUERY("SELECT * FROM privileges WHERE account_id = " + account.getAccount_id() + " AND privilege= " + 8) ;
					if(result.next()){
						Connect.emptyQUERY("DELETE FROM privileges WHERE privilege_id = " + result.getInt("privilege_id"));
					}
				}
				
				if(chkDisableAccounts.isSelected()){
					result = Connect.QUERY("SELECT * FROM privileges WHERE account_id = " + account.getAccount_id() + " AND privilege = " + 9) ;
					if(!result.next()){
						int primary = Connect.getNextIntegerPrimary("privileges", "privilege_id") ;
						Connect.emptyQUERY("INSERT INTO privileges(privilege_id , account_id , privilege , privilege_desc) VALUES("+ primary +","+ account.getAccount_id() +","+ 9 +",'Deleting Accounts')");
					}
				}else{
					result = Connect.QUERY("SELECT * FROM privileges WHERE account_id = " + account.getAccount_id() + " AND privilege = " + 9) ;
					if(result.next()){
						Connect.emptyQUERY("DELETE FROM privileges WHERE privilege_id = " + result.getInt("privilege_id"));
					}
				}
				
				if(chkChangeLoginInformation.isSelected()){
					result = Connect.QUERY("SELECT * FROM privileges WHERE account_id = " + account.getAccount_id() + " AND privilege = " + 10) ;
					if(!result.next()){
						int primary = Connect.getNextIntegerPrimary("privileges", "privilege_id") ;
						Connect.emptyQUERY("INSERT INTO privileges(privilege_id , account_id , privilege , privilege_desc) VALUES("+ primary +","+ account.getAccount_id() +","+ 10 +",'Change Login Information')");
					}
				}else{
					result = Connect.QUERY("SELECT * FROM privileges WHERE account_id = " + account.getAccount_id() + " AND privilege = " + 10) ;
					if(result.next()){
						Connect.emptyQUERY("DELETE FROM privileges WHERE privilege_id = " + result.getInt("privilege_id"));
					}
				}
				
				if(chkChangePriviledge.isSelected()){
					result = Connect.QUERY("SELECT * FROM privileges WHERE account_id = " + account.getAccount_id() + " AND privilege = " + 11) ;
					if(!result.next()){
						int primary = Connect.getNextIntegerPrimary("privileges", "privilege_id") ;
						Connect.emptyQUERY("INSERT INTO privileges(privilege_id , account_id , privilege , privilege_desc) VALUES("+ primary +","+ account.getAccount_id() +","+ 11 +",'Change Privileges')");
					}
				}else{
					result = Connect.QUERY("SELECT * FROM privileges WHERE account_id = " + account.getAccount_id() + " AND privilege = " + 11) ;
					if(result.next()){
						Connect.emptyQUERY("DELETE FROM privileges WHERE privilege_id = " + result.getInt("privilege_id"));
					}
				}
				
				ImageView image = new ImageView(new NodeUtils.ImageGetter("check.png").getImage()) ;
				NotificationPane noti = (NotificationPane)FXMLDocumentController.getInstance().getTabpane().getSelectionModel().getSelectedItem().getContent() ;
				noti.show("Success" , image);
				
				int index = FXMLDocumentController.getInstance().getAccountsList().getSelectionModel().getSelectedIndex() ;
				FXMLDocumentController.getInstance().getAccountsList().getSelectionModel().select(null);
				FXMLDocumentController.getInstance().getAccountsList().getSelectionModel().select(index);
				FXMLDocumentController.getInstance().getAccountsList().getSelectionModel().selectedIndexProperty() ;
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
 		}
 		
	}
}
