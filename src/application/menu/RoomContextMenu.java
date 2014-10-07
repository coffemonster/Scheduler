package application.menu;

import tree.TreeItemData;
import application.User;
import application.main.FXMLDocumentController;
import application.properties.RoomPropertiesController;
import application.properties.TeacherPropertiesController;
import application.scheduler.Scheduler;
import congcrete.Department;
import congcrete.Room;
import congcrete.Teacher;
import database.Connect;
import NodeUtils.ImageGetter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;

public class RoomContextMenu extends ContextMenu{
	
	private MenuItem viewSchedule ;
	private MenuItem delete ;
	private MenuItem update ;
	
	public RoomContextMenu(){
		super() ;
		
		ImageView viewScheduleImage = new ImageView(new ImageGetter("calendar53.png").getImage()) ;
		ImageView image = new ImageView(new NodeUtils.ImageGetter("delete23.png").getImage()) ;
		
		viewSchedule = new MenuItem("View Schedule" , viewScheduleImage);
		delete = new MenuItem("Delete" , image) ;
		
		viewSchedule.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				TreeItem<String> item = (TreeItemData) FXMLDocumentController.getInstance().getTree().getSelectionModel().getSelectedItem() ;
				Room room = TreeItemData.getItemData(item) ;
				Scheduler.viewRoomSchedule(room);
			}
			
		});
		
		delete.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				TreeItem<String> treeItem = (TreeItem<String>) FXMLDocumentController.getInstance().getTree().getSelectionModel().getSelectedItem() ;
				Room roomData = TreeItemData.getItemData(treeItem) ;
				Connect.emptyQUERY("DELETE FROM rooms WHERE room_id = " + roomData.getRoom_id());
				FXMLDocumentController.updateTree();
				TreeItem<String> dept = Department.getItem(roomData.getD().getDept_id()) ;
				FXMLDocumentController.getInstance().getTree().getSelectionModel().select(dept.getChildren().get(1)) ;
			}
		});
		
		ImageView updateImage = new ImageView(new NodeUtils.ImageGetter("synchronize1.png").getImage()) ;
		update = new MenuItem("Update" , updateImage) ;
		
		update.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				RoomPropertiesController.getInstance().getBtnSave().setVisible(true);
				RoomPropertiesController.getInstance().getRoomCode().setEditable(true);
				RoomPropertiesController.getInstance().getRoomName().setEditable(true);
			}
		});
		
		if(User.isAdmin() || User.getPrivilege().contains(new Integer(2))){
			getItems().add(update) ;
		}
		
		if(User.isAdmin() || User.getPrivilege().contains(new Integer(3))){
			getItems().add(delete) ;
		}
		
		if(User.isAdmin() || User.getPrivilege().contains(new Integer(0))){
			getItems().add(viewSchedule) ;
		}
	}
}
