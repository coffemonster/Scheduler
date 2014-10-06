package application.properties;

import java.net.URL;
import java.util.ResourceBundle;

import application.User;
import congcrete.Account;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class UserPropertiesController implements Initializable{
	
	@FXML private TextField txtUsername ;
	@FXML private TextField txtPassword ;
	@FXML private CheckBox chkAdmin ;
	@FXML private CheckBox chkEnabled ;
	@FXML private CheckBox chkActive ;
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
	@FXML private CheckBox chkChangeInformation ;
	@FXML private CheckBox chkChangePrivileges ;
	private Account account ;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		account = (Account) arg1.getObject("account") ;
		
		txtUsername.setText(account.getUsername());
		txtPassword.setText(account.getPassword());
		
		if(account.isActive()){
			chkActive.setSelected(true);
		}
		if(account.isAdmin()){
			chkAdmin.setSelected(true);
		}
		if(account.isEnabled()){
			chkEnabled.setSelected(true);
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
			chkChangeInformation.setSelected(true);
		}
		if(account.getPrivileges().contains(new Integer(11))){
			chkChangePrivileges.setSelected(true);
		}
	}
}
