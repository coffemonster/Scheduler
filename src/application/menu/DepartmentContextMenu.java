package application.menu;

import congcrete.Department;
import tree.TreeItemData;
import application.User;
import application.department.DepartmentDocumentController;
import application.main.FXMLDocumentController;
import application.properties.CoursePropertiesController;
import application.properties.DepartmentPropertiesController;
import database.Connect;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;

public class DepartmentContextMenu extends ContextMenu{
	private MenuItem delete ;
	private MenuItem update ;
	public DepartmentContextMenu(){
		super() ;
		ImageView image = new ImageView(new NodeUtils.ImageGetter("delete23.png").getImage()) ;
		delete = new MenuItem("Delete" , image) ;
		
		delete.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				TreeItem<String> treeItem = (TreeItem<String>) FXMLDocumentController.getInstance().getTree().getSelectionModel().getSelectedItem() ;
				Department deptData = TreeItemData.getItemData(treeItem) ;
				Connect.emptyQUERY("DELETE FROM departments WHERE dept_id = " + deptData.getDept_id());
				FXMLDocumentController.updateTree();
				
			}
		});

		ImageView updateImage = new ImageView(new NodeUtils.ImageGetter("synchronize1.png").getImage()) ;
		update = new MenuItem("Update" , updateImage) ;
		
		update.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				DepartmentPropertiesController.getInstance().getBtnSave().setVisible(true);
				DepartmentPropertiesController.getInstance().getDeptCode().setEditable(true);
				DepartmentPropertiesController.getInstance().getDeptName().setEditable(true);
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
