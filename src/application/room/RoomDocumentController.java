package application.room;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.controlsfx.control.NotificationPane;

import tree.TreeItemData;
import tree.UpdateTree;
import congcrete.Department;
import congcrete.Room;
import database.Connect;
import application.main.FXMLDocumentController;
import application.validation.Validation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class RoomDocumentController implements Initializable{
	
	@FXML private TextField roomName ;
	@FXML private TextField roomCode ;
	@FXML private ChoiceBox department ;
	private ArrayList<Department> index ;
	
	@FXML public void handleAddRoom(ActionEvent e){
		//VALIDATION
		Validation validator = new Validation() ;
		validator.validateEmpty("Room name", roomName.getText(), roomName);
		validator.validateTextWithNumbers("Room name", roomName.getText(), roomName);
		validator.validateEmpty("Room code", roomCode.getText(), roomCode);
		validator.validateTextWithNumbers("Room code", roomCode.getText(), roomCode);
		validator.validateChoiceBox("Department", department);
		
		if(validator.hasError()){
			validator.showError();
			ImageView image = new ImageView(new NodeUtils.ImageGetter("error.png").getImage()) ;
			NotificationPane noti = (NotificationPane)FXMLDocumentController.getInstance().getTabpane().getSelectionModel().getSelectedItem().getContent() ;
			noti.show("An error occured during validation" , image);
			return ;
		}else{
			validator.hideError();
		}
		
		//get the primary
		int nextPrimary = Connect.getNextIntegerPrimary("rooms", "room_id") ;
		//get the departmetn
		int dept = index.get(department.getSelectionModel().getSelectedIndex()).getDept_id() ;
		Connect.emptyQUERY("INSERT INTO `rooms` VALUES(" + nextPrimary + " , " + dept + " , '" + roomName.getText().trim() + "' , '" + 
							roomCode.getText().trim() + "')");
		
		ImageView image = new ImageView(new NodeUtils.ImageGetter("check.png").getImage()) ;
		NotificationPane noti = (NotificationPane)FXMLDocumentController.getInstance().getTabpane().getSelectionModel().getSelectedItem().getContent() ;
		noti.show("Success" , image);
		
		FXMLDocumentController.updateTree();
		
		UpdateTree.expandTree(); 
		
		UpdateTree.selectItem((Room.getItem(dept, nextPrimary))) ;
		
		roomName.setText("");
		roomCode.setText("");
		department.getSelectionModel().clearSelection();
		
		initialize(null , null) ;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		index = Department.getDepartmentList() ;
		department.getItems().clear();
		for(int x = 0 ; x < index.size() ; x++){
			department.getItems().add(index.get(x).getDept_name()) ;
		}
	}
	
}
