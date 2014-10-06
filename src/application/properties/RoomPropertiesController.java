package application.properties;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import congcrete.Course;
import congcrete.Department;
import congcrete.Room;
import database.Connect;
import tree.TreeItemData;
import application.main.FXMLDocumentController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;

public class RoomPropertiesController implements Initializable{

	@FXML TextField roomName ;
	@FXML TextField roomCode ;
	@FXML TextField deptName ;
	@FXML TextField deptCode ;
	@FXML ChoiceBox cboDepartment ;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		TreeItem<String> roomItem = (TreeItemData)FXMLDocumentController.getInstance().getTree().getSelectionModel().getSelectedItem() ;
		Room roomData = TreeItemData.getItemData(roomItem) ;
		
		roomName.setText(roomData.getRoom_name());
		roomCode.setText(roomData.getRoom_code());
		deptName.setText(roomData.getD().getDept_name());
		deptCode.setText(roomData.getD().getDept_code());
		
		if(FXMLDocumentController.getInstance().getRightAccordion().getExpandedPane() == null){
			FXMLDocumentController.getInstance().getRightAccordion().setExpandedPane(FXMLDocumentController.getInstance().getDetailsTitledPane());
		}
		
		//set the department
				ArrayList<Department> departments = Department.getDepartmentList() ;
				cboDepartment.getItems().clear();
				for(int x = 0 ; x < departments.size() ; x++){
					cboDepartment.getItems().add(departments.get(x).getDept_code()) ;
				}
				//select the department
				for(int x = 0 ; x < departments.size() ; x++){
					if(roomData.getD().getDept_id() == departments.get(x).getDept_id()){
						cboDepartment.getSelectionModel().select(x);
						break ;
					}
				}
				
				cboDepartment.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Object>(){
					@Override
					public void changed(ObservableValue<? extends Object> arg0,
							Object arg1, Object arg2) {	
						Connect.emptyQUERY("UPDATE rooms SET dept_id = " + departments.get(cboDepartment.getSelectionModel().getSelectedIndex()).getDept_id() + " WHERE room_id = " + roomData.getRoom_id());
						deptName.setText(departments.get(cboDepartment.getSelectionModel().getSelectedIndex()).getDept_name());
						deptCode.setText(departments.get(cboDepartment.getSelectionModel().getSelectedIndex()).getDept_code());
						FXMLDocumentController.getInstance().updateTree();
						FXMLDocumentController.getInstance().getTree().getSelectionModel().select(Room.getItem( departments.get(cboDepartment.getSelectionModel().getSelectedIndex()).getDept_id(), roomData.getRoom_id()));
					}
				});
	}
	
}
