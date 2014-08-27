package tree;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import congcrete.Department;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import database.Connect;

public class UpdateTree {
	
	private static ArrayList<String> values ;

	
	public static void addToTree(String table_name , TreeItem<String> parent , String display_column){
		ResultSet result = Connect.QUERY("SELECT * FROM " + table_name) ;
		values = new ArrayList<String>() ;
		
		try {
			while(result.next()){
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
