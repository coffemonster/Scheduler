package application.menu;

import tree.TreeItemData;
import application.main.FXMLDocumentController;
import congcrete.Course;
import congcrete.Department;
import congcrete.Section;
import database.Connect;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;

public class CourseContextMenu extends ContextMenu{
	
	private MenuItem delete ;
	
	public CourseContextMenu(){
		super();
		
		ImageView image = new ImageView(new NodeUtils.ImageGetter("delete23.png").getImage()) ;
		delete = new MenuItem("Delete" , image) ;
		
		delete.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				TreeItem<String> treeItem = (TreeItem<String>) FXMLDocumentController.getInstance().getTree().getSelectionModel().getSelectedItem() ;
				Course courseData = TreeItemData.getItemData(treeItem) ;
				Connect.emptyQUERY("DELETE FROM courses WHERE course_id = " + courseData.getCourse_id());
				FXMLDocumentController.updateTree();
				TreeItem<String> dept = Department.getItem(courseData.getD().getDept_id()) ;
				FXMLDocumentController.getInstance().getTree().getSelectionModel().select(dept.getChildren().get(2)) ;
			}
		});
		
		getItems().add(delete) ;
		
	}
}
