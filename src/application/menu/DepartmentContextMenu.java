package application.menu;

import congcrete.Department;
import tree.TreeItemData;
import application.main.FXMLDocumentController;
import database.Connect;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;

public class DepartmentContextMenu extends ContextMenu{
	private MenuItem delete ;
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
		
		getItems().add(delete) ;
	
	}
}
