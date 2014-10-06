package congcrete;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Connect;

public class Account {
	
	private int account_id ;
	private String username ;
	private String password ;
	private boolean isAdmin ;
	private boolean isActive ;
	private boolean isEnabled ;
	private ArrayList<Integer> privileges ;
	
	public Account(int account_id, String username, String password,
			boolean isAdmin, boolean isActive, boolean isEnabled) {
		super();
		this.account_id = account_id;
		this.username = username;
		this.password = password;
		this.isAdmin = isAdmin;
		this.isActive = isActive;
		this.isEnabled = isEnabled;
		
		privileges = new ArrayList<Integer>() ;
	}

	public Account() {
		privileges = new ArrayList<Integer>() ;
	}

	public int getAccount_id() {
		return account_id;
	}

	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	
	public ArrayList<Integer> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(ArrayList<Integer> privileges) {
		this.privileges = privileges;
	}

	public static Account getAccount(String username){
		Account account = new Account() ;
		ResultSet result = Connect.QUERY("SELECT * FROM accounts WHERE username = '"+ username +"'" ) ;
		try {
			result.next() ;
			account.setAccount_id(result.getInt("account_id"));
			account.setActive(result.getBoolean("isActive"));
			account.setAdmin(result.getBoolean("isAdmin"));
			account.setEnabled(result.getBoolean("isEnabled"));
			account.setPassword(result.getString("password"));
			account.setUsername(result.getString("username"));
			result.close();
			
			ArrayList<Integer> priv = new ArrayList<Integer>() ;
			result = Connect.QUERY("SELECT * FROM privileges WHERE account_id = " + account.getAccount_id()) ;
			while(result.next()){
				int currentPrivilege = result.getInt("privilege") ;
				priv.add(new Integer(currentPrivilege)) ;
			}
			
			account.setPrivileges(priv) ;
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return account;
	}

}
