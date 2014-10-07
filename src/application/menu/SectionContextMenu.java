package application.menu;

import java.sql.ResultSet;
import java.sql.SQLException;

import tree.TreeItemData;
import application.User;
import application.main.FXMLDocumentController;
import application.scheduler.Scheduler;
import congcrete.Department;
import congcrete.Section;
import congcrete.Teacher;
import congcrete.Year;
import database.Connect;
import NodeUtils.ImageGetter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;

public class SectionContextMenu extends ContextMenu{
	
	private MenuItem viewSchedule ;
	private MenuItem delete ;
	
	public SectionContextMenu(){
		super() ;
		
		ImageView viewScheduleImage = new ImageView(new ImageGetter("calendar53.png").getImage()) ;
		
		viewSchedule = new MenuItem("View Schedule" , viewScheduleImage);
		
		viewSchedule.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				TreeItem<String> item = (TreeItemData) FXMLDocumentController.getInstance().getTree().getSelectionModel().getSelectedItem() ;
				Section section = TreeItemData.getItemData(item) ;
				Scheduler.viewSectionSchedule(section);
			}
			
		});
		
		ImageView image = new ImageView(new NodeUtils.ImageGetter("delete23.png").getImage()) ;
		delete = new MenuItem("Delete" , image) ;
		
		delete.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				TreeItem<String> treeItem = (TreeItem<String>) FXMLDocumentController.getInstance().getTree().getSelectionModel().getSelectedItem() ;
				Section sectionData = TreeItemData.getItemData(treeItem) ;
				
				ResultSet result = Connect.QUERY("SELECT * FROM sections WHERE year_id = " + sectionData.getYear().getYear_id() + " ORDER BY section DESC");
				try {
					result.next() ;
					int section_id = result.getInt("section_id") ;
					Connect.emptyQUERY("DELETE FROM sections WHERE section_id = " + section_id);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				FXMLDocumentController.updateTree();
				TreeItem<String> year = Year.getItem(sectionData.getYear().getYear_id()) ;
				FXMLDocumentController.getInstance().getTree().getSelectionModel().select(year);
			}
		});
		
		if(User.isAdmin() || User.getPrivilege().contains(new Integer(3))){
			getItems().add(delete) ;
		}
		
		if(User.isAdmin() || User.getPrivilege().contains(new Integer(0))){
			getItems().add(viewSchedule) ;
		}

	}
}
