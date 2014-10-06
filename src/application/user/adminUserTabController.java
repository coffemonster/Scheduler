package application.user;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Vector;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialogs;

import congcrete.Account;
import database.Connect;
import application.User;
import application.main.FXMLDocumentController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class adminUserTabController implements Initializable{

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	}
	
	@FXML public void handleAddUser(MouseEvent e){
		try {
			AnchorPane user = FXMLLoader.load(getClass().getResource("/application/user/addUserAccounts.fxml")) ;
			user.setStyle("-fx-background-color:white");
			node.NodeUtils.addToCenter(user, "Add User");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
	
	@FXML public void handleDisableUser(MouseEvent e){
		if(FXMLDocumentController.getInstance().getAccountsList().getSelectionModel().getSelectedItem() == null){
			Dialogs.create()
            .title("Error Disabling Account")
            .message("Please select an account") 
            .showInformation() ;
			return ;
		}else{
			Optional<String> o = Dialogs.create()
			.title("Verification")
			.message("Input Password")
			.showTextInput() ;
			
			if(!o.get().equalsIgnoreCase(User.getPassword())){
				Dialogs.create()
				.title("Error")
				.message("Wrong Password")
				.showError() ;
				return ;
			}
			
			Action action = Dialogs.create()
			.title("Confirmation")
			.message("Are you sure you want to Disable " + FXMLDocumentController.getInstance().getAccountsList().getSelectionModel().getSelectedItem().toString())
			.showConfirm() ;
			
			if(action.toString().equalsIgnoreCase("YES")){
				ArrayList<Account> accounts = User.getAccountsList() ;
				Connect.emptyQUERY("UPDATE accounts SET isEnabled = false WHERE account_id = " + accounts.get(FXMLDocumentController.getInstance().getAccountsList().getSelectionModel().getSelectedIndex()).getAccount_id());
				
				Dialogs.create()
				.title("Success")
				.message(FXMLDocumentController.getInstance().getAccountsList().getSelectionModel().getSelectedItem().toString() + " has been disabled")
				.showInformation();
				
				FXMLDocumentController.getInstance().updateAccounts();
			}
		}
	}
	
	@FXML public  void handleEditUser(MouseEvent e){
		if(FXMLDocumentController.getInstance().getAccountsList().getSelectionModel().getSelectedItem() == null){
			Dialogs.create()
            .title("Error")
            .message("Please select an account") 
            .showInformation() ;
			return ;
		}
		
		try {
			FXMLLoader loader = new FXMLLoader() ;
			loader.setLocation(getClass().getResource("/application/user/changePassword.fxml"));
			
			loader.setResources(new ResourceBundle(){

				@Override
				public Enumeration<String> getKeys() {
					Vector<String> key = new Vector<String>() ;
					key.add("account") ;
					return (Enumeration<String>) key;
				}

				@Override
				protected Object handleGetObject(String key) {
					Account account = Account.getAccount(FXMLDocumentController.getInstance().getAccountsList().getSelectionModel().getSelectedItem().toString()) ;
					return account;
				}
				
			});
			
			AnchorPane changePassword = loader.load() ;
			changePassword.setStyle("-fx-background-color:white");
			node.NodeUtils.addToCenter(changePassword, "Change " + FXMLDocumentController.getInstance().getAccountsList().getSelectionModel().getSelectedItem() + "'s password");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
	
	@FXML public void handleChangePrivilege(MouseEvent e){
		if(FXMLDocumentController.getInstance().getAccountsList().getSelectionModel().getSelectedItem() == null){
			Dialogs.create()
            .title("Error")
            .message("Please select an account") 
            .showInformation() ;
			return ;
		}
		
		try {
			FXMLLoader loader = new FXMLLoader() ;
			loader.setLocation(getClass().getResource("/application/user/changePriviledge.fxml"));
			
			loader.setResources(new ResourceBundle(){

				@Override
				public Enumeration<String> getKeys() {
					Vector<String> key = new Vector<String>() ;
					key.add("account") ;
					return (Enumeration<String>) key;
				}

				@Override
				protected Object handleGetObject(String key) {
					Account account = Account.getAccount(FXMLDocumentController.getInstance().getAccountsList().getSelectionModel().getSelectedItem().toString()) ;
					return account;
				}
				
			});
			
			AnchorPane changePrivilege = loader.load() ;
			changePrivilege.setStyle("-fx-background-color:white");
			node.NodeUtils.addToCenter(changePrivilege, "Change " + FXMLDocumentController.getInstance().getAccountsList().getSelectionModel().getSelectedItem() + "'s privilege");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
