package application.menu;

import java.sql.ResultSet;
import java.sql.SQLException;

import tree.TreeItemData;
import application.User;
import application.main.FXMLDocumentController;
import congcrete.Course;
import congcrete.Section;
import congcrete.Year;
import database.Connect;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;

public class YearContextMenu extends ContextMenu{
	
	private MenuItem delete ;
	
	public YearContextMenu(){
		super() ;
		ImageView image = new ImageView(new NodeUtils.ImageGetter("delete23.png").getImage()) ;
		delete = new MenuItem("Delete" , image) ;
		
		delete.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				TreeItem<String> treeItem = (TreeItem<String>) FXMLDocumentController.getInstance().getTree().getSelectionModel().getSelectedItem() ;
				Year yearData = TreeItemData.getItemData(treeItem) ;
				ResultSet result = Connect.QUERY("SELECT * FROM years WHERE course_id = " + yearData.getCourse().getCourse_id() + " ORDER BY year DESC") ;
				try {
					result.next() ;
					int year_id = result.getInt("year_id") ;
					Connect.emptyQUERY("DELETE FROM years WHERE year_id = " + year_id);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				FXMLDocumentController.updateTree();
				TreeItem<String> course = Course.getItem(yearData.getCourse().getD().getDept_id(), yearData.getCourse().getCourse_id()) ;
				FXMLDocumentController.getInstance().getTree().getSelectionModel().select(course);
			}
		});
		
		if(User.isAdmin() || User.getPrivilege().contains(new Integer(3))){
			getItems().add(delete) ;
		}
	}
}
