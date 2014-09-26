package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Connect;

public class User {
	private static String username = null ;
	private static String password = null ;
	private static ArrayList<String> priviledges = null;
	private static boolean isAdmin = false ;
	
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
	
	public static String login(String username , String password){
		String error = null ;
		
		ResultSet result = Connect.QUERY("SELECT * FROM accounts WHERE username = '" + username + "' AND password = '" + password +"'") ;
		try {
			if(result.next()){
				User.username = result.getString("username") ;
				User.password = result.getString("password") ;
				User.isAdmin = result.getBoolean("isAdmin") ;
				Connect.emptyQUERY("UPDATE accounts SET isActive = 1 WHERE username = '" + username + "' && password ='" + password + "'");
			}else{
				error = "Invalid username or password" ;
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return error ;
	}
	
	public static void clearUserDetails(){
		username = null ;
		password = null ;
		priviledges = null ;
	}
	
	public static boolean isActive(){
		if(username == null && password == null && priviledges == null){
			return false ;
		}else{
			return true ;
		}
	}
	
	public static void logout(){
		Connect.emptyQUERY("UPDATE accounts SET isActive = 0 WHERE username = '" + username + "' && password ='" + password + "'");
	}
}
