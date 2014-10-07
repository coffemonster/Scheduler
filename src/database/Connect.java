package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.controlsfx.dialog.Dialogs;

public class Connect {
	
	private static Connection connection = null ;
	private static Statement stm = null ;
	
	public static Connection connect(String server , String port , String dbName , String user , String pass){
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance() ;
			connection = DriverManager.getConnection("jdbc:mysql://".concat(server).concat(":").
																concat(port).concat("/").concat(dbName) , user , pass) ;
			return connection ;
		}catch(Exception e){
			Dialogs.create()
			.title("Error")
			.message("Error Connecting")
			.showError() ;
			
			e.printStackTrace();
			connection = null ;
			return null ;
		}
	}
	
	public static Connection getConnection(){
		return connection ;
	}
	
	public static void emptyQUERY(String Command){
		try{
			stm = connection.createStatement() ;
			stm.execute(Command) ;
		}catch(SQLException e){
			e.printStackTrace();
		}
		
	}
	
	public static ResultSet QUERY(String Command){
		try{
			stm = connection.createStatement() ;
			ResultSet result = stm.executeQuery(Command) ;
			return result ;
		}catch(SQLException e){
			e.printStackTrace();
			return null ;
		}
	}
	
	public static int getNextIntegerPrimary(String tableName , String fieldName){
		int num = -1 ;
		ResultSet result = Connect.QUERY("SELECT MAX(" +  fieldName + ") AS Prime FROM " + tableName + " ORDER BY Prime DESC LIMIT 1") ;
		try{
			while(result.next()){
				num = result.getInt(1) ;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		if(num == -1){
			return 1;
		}else{
			return num + 1 ;
		}
	}
}

