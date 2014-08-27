package application.room;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import congcrete.Department;
import database.Connect;
import application.main.FXMLDocumentController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class RoomDocumentController implements Initializable{
	
	@FXML private TextField roomName ;
	@FXML private TextField roomCode ;
	@FXML private ChoiceBox department ;
	private ArrayList<Department> index ;
	
	@FXML public void handleAddRoom(ActionEvent e){
		//get the primary
		int nextPrimary = Connect.getNextIntegerPrimary("rooms", "room_id") ;
		//get the departmetn
		int dept = index.get(department.getSelectionModel().getSelectedIndex()).getDept_id() ;
		Connect.emptyQUERY("INSERT INTO `rooms` VALUES(" + nextPrimary + " , " + dept + " , '" + roomName.getText() + "' , '" + 
							roomCode.getText() + "')");
		
		FXMLDocumentController.updateTree();
	}
	
	@FXML public void removeRoom(MouseEvent e){
		FXMLDocumentController.getWorkplacePane().setCenter(null);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		index = Department.getDepartmentList() ;
		for(int x = 0 ; x < index.size() ; x++){
			department.getItems().add(index.get(x).getDept_name()) ;
		}
	}
	
}
