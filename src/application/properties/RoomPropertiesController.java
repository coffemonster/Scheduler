package application.properties;

import java.net.URL;
import java.util.ResourceBundle;

import congcrete.Room;
import tree.TreeItemData;
import application.main.FXMLDocumentController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;

public class RoomPropertiesController implements Initializable{

	@FXML TextField roomName ;
	@FXML TextField roomCode ;
	@FXML TextField deptName ;
	@FXML TextField deptCode ;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		TreeItem<String> roomItem = (TreeItemData)FXMLDocumentController.getTree().getSelectionModel().getSelectedItem() ;
		Room roomData = TreeItemData.getItemData(roomItem) ;
		
		roomName.setText(roomData.getRoom_name());
		roomCode.setText(roomData.getRoom_code());
		deptName.setText(roomData.getD().getDept_name());
		deptCode.setText(roomData.getD().getDept_code());
		
		if(FXMLDocumentController.getRightAccordion().getExpandedPane() == null){
			FXMLDocumentController.getRightAccordion().setExpandedPane(FXMLDocumentController.getDetailsTitledPane());
		}
	}
	
}
