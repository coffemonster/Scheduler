package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.bind.Validator;

import org.controlsfx.dialog.Dialogs;

import application.validation.Validation;
import congcrete.Account;
import database.Connect;

public class User {
	private static int account_id = 0 ;
	private static String username = null ;
	private static String password = null ;
	private static boolean isAdmin = false ;
	private static ArrayList<Integer> privilege ;	
	
	public static String getUsername() {
		return username;
	}
	public static void setUsername(String username) {
		User.username = username;
	}
	public static String getPassword() {
		return password;
	}
	public static void setPassword(String password) {
		User.password = password;
	}
	
	public static int getAccount_id() {
		return account_id;
	}
	public static ArrayList<Integer> getPrivilege() {
		return privilege;
	}
	public static String login(String username , String password){
		String error = null ;
		
		Validation validator = new Validation() ;
		validator.validateEmpty("Username", username, null) ;
		validator.validateTextWithNumbers("Username", username, null) ;
		validator.validateEmpty("Password", password, null) ;
		validator.validateTextWithNumbers("Password", password, null) ;
		
		if(validator.hasError()){
			String errorList = "" ;
			
			for(int x = 0 ; x < validator.getErrorList().size() ; x++){
				errorList += validator.getErrorList().get(x).toString() + "\n" ;
			}
			return errorList;
		}
		
		//LOGIN
		ResultSet result = Connect.QUERY("SELECT * FROM accounts WHERE username = '" + username + "' AND password = '" + password +"'") ;
		try {
			if(result.next()){
				
				if(!result.getBoolean("isEnabled")){
					return "Account is currently disabled";
				}
				
				if(result.getBoolean("isActive")){
					return "Account already Logged in" ;
				}
				
				User.account_id = result.getInt("account_id") ;
				User.username = result.getString("username") ;
				User.password = result.getString("password") ;
				User.isAdmin = result.getBoolean("isAdmin") ;
				Connect.emptyQUERY("UPDATE accounts SET isActive = 1 WHERE username = '" + username + "' && password ='" + password + "'");
			
				//PRIVILEGE
				privilege = new ArrayList<Integer>() ;
				ResultSet priv = Connect.QUERY("SELECT * FROM privileges WHERE account_id = " + User.account_id) ;
				while(priv.next()){
					Integer privNum = new Integer(result.getInt("privilege")) ;
					User.privilege.add(privNum) ;
				}
			}else{
				error = "Invalid username or password" ;
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return error; 
	
	}
	
	
	public static boolean isAdmin() {
		return isAdmin;
	}
	public static void setAdmin(boolean isAdmin) {
		User.isAdmin = isAdmin;
	}
	public static void clearUserDetails(){
		account_id = 0 ;
		username = null ;
		password = null ;
		privilege = null ;
	}
	
	public static boolean isActive(){
		if(account_id == 0 && username == null && password == null && privilege == null){
			return false ;
		}else{
			return true ;
		}
	}
	
	public static void logout(){
		Connect.emptyQUERY("UPDATE accounts SET isActive = 0 WHERE username = '" + username + "' && password ='" + password + "'");
	}
	
	public static ArrayList<Account> getAccountsList(){
		ArrayList<Account> accountsList = new ArrayList<Account>() ;
		ResultSet result = Connect.QUERY("SELECT * FROM accounts ORDER BY isAdmin DESC") ;
		try {
			while(result.next()){
				Account account = new Account() ;
				account.setAccount_id(result.getInt("account_id"));
				account.setUsername(result.getString("username"));
				account.setPassword(result.getString("password"));
				account.setActive(result.getBoolean("isActive"));
				account.setAdmin(result.getBoolean("isAdmin"));
				account.setEnabled(result.getBoolean("isEnabled"));
				accountsList.add(account) ;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accountsList; 
	}
}
