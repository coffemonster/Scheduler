package tree;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.main.FXMLDocumentController;
import congcrete.Department;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import database.Connect;

public class UpdateTree {
	
	private static ArrayList<String> values ;
	
	public static void expandTree(){
		if(FXMLDocumentController.getLeftAccordion().getExpandedPane() == null){
			FXMLDocumentController.getLeftAccordion().setExpandedPane(FXMLDocumentController.getHierarchyPane());
		}
	}
	
	public static void selectItem(TreeItem<String> item){
		FXMLDocumentController.getTree().getSelectionModel().select(item);
	}
	
}
