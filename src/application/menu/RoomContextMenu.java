package application.menu;

import tree.TreeItemData;
import application.main.FXMLDocumentController;
import application.scheduler.Scheduler;
import congcrete.Room;
import congcrete.Teacher;
import NodeUtils.ImageGetter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;

public class RoomContextMenu extends ContextMenu{
	
	private MenuItem viewSchedule ;
	
	public RoomContextMenu(){
		super() ;
		
		ImageView viewScheduleImage = new ImageView(new ImageGetter("calendar53.png").getImage()) ;
		
		viewSchedule = new MenuItem("View Schedule" , viewScheduleImage);
		
		viewSchedule.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				TreeItem<String> item = (TreeItemData) FXMLDocumentController.getInstance().getTree().getSelectionModel().getSelectedItem() ;
				Room room = TreeItemData.getItemData(item) ;
				Scheduler.viewRoomSchedule(room);
			}
			
		});
		
		getItems().add(viewSchedule) ;
	}
}