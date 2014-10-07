package application.menu;

import tree.TreeItemData;
import NodeUtils.ImageGetter;
import application.User;
import application.main.FXMLDocumentController;
import application.properties.CoursePropertiesController;
import application.properties.SubjectMainPropertiesController;
import application.properties.SubjectPropertiesController;
import congcrete.Department;
import congcrete.Subject;
import congcrete.Teacher;
import database.Connect;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;

public class SubjectContextMenu extends ContextMenu{
	
	private MenuItem delete ;
	private MenuItem update ;
	
	public SubjectContextMenu(){
		super() ;

		ImageView imageT = new ImageView(new ImageGetter("delete23.png").getImage()) ;
		delete = new MenuItem("Delete" , imageT) ;
		
		delete.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				TreeItem<String> treeItem = (TreeItem<String>) FXMLDocumentController.getInstance().getTree().getSelectionModel().getSelectedItem() ;
				Subject subjectData = TreeItemData.getItemData(treeItem) ;
				Connect.emptyQUERY("DELETE FROM subjects WHERE subject_id = " + subjectData.getSubject_id());
				TreeItem<String> temp = treeItem.getParent() ;
				FXMLDocumentController.updateTree();
				FXMLDocumentController.getInstance().getTree().getSelectionModel().select(temp) ;
			}
		});
		
		ImageView updateImage = new ImageView(new NodeUtils.ImageGetter("synchronize1.png").getImage()) ;
		update = new MenuItem("Update" , updateImage) ;
		
		update.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				SubjectMainPropertiesController.getInstance().getBtnSave().setVisible(true);
				SubjectMainPropertiesController.getInstance().getSubject().setEditable(true);
				SubjectMainPropertiesController.getInstance().getSubjectCode().setEditable(true);
				SubjectMainPropertiesController.getInstance().getSubjectUnits().setEditable(true);
			}
		});
		
		if(User.isAdmin() || User.getPrivilege().contains(new Integer(2))){
			getItems().add(update) ;
		}
		
		if(User.isAdmin() || User.getPrivilege().contains(new Integer(3))){
			getItems().add(delete) ;
		}
	}
}
