package application.properties;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialogs;

import congcrete.Course;
import congcrete.Department;
import congcrete.Room;
import congcrete.Teacher;
import database.Connect;
import tree.TreeItemData;
import application.main.FXMLDocumentController;
import application.validation.Validation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
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
	@FXML javafx.scene.control.Button btnSave ;
	private static RoomPropertiesController instance ;
	
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
	
	@FXML public void handleSave(ActionEvent e){
		Validation validator = new Validation() ;
		validator.validateEmpty("Room name", roomName.getText(), roomName);
		validator.validateTextWithNumbers("Room name", roomName.getText(), roomName);
		validator.validateEmpty("Room code", roomCode.getText(), roomCode);
		validator.validateTextWithNumbers("Room code", roomCode.getText(), roomCode);
		
		if(validator.hasError()){
			String error = "" ;
			
			for(int x = 0 ; x < validator.getErrorList().size() ; x++){
				error += validator.getErrorList().get(x) + "\n" ;
			}
			
			Dialogs.create()
			.title("Error")
			.message(error)
			.showError() ;
			return ;
		}
		
		Action decision = Dialogs.create()
				.title("Warning")
				.message("Data will be saved permanently")
				.showConfirm() ;
		if(decision.toString().equalsIgnoreCase("YES")){
			TreeItem<String> item = (TreeItem<String>) FXMLDocumentController.getInstance().getTree().getSelectionModel().getSelectedItem() ;
			Room roomData = TreeItemData.getItemData(item) ;
			
			Connect.emptyQUERY("UPDATE rooms SET room_name = '"+ roomName.getText() +"' , room_code = '"+ roomCode.getText() +"' WHERE room_id = " + roomData.getRoom_id() ) ;
						
			FXMLDocumentController.updateTree();
			
			TreeItem<String> select = Room.getItem(roomData.getD().getDept_id(), roomData.getRoom_id()) ;
			
			FXMLDocumentController.getInstance().getTree().getSelectionModel().select(select);
			
			btnSave.setVisible(false);
			roomCode.setEditable(false);
			roomName.setEditable(false);
			cboDepartment.setDisable(true);
		}else{
			return ;
		}
	}

	public TextField getRoomName() {
		return roomName;
	}

	public void setRoomName(TextField roomName) {
		this.roomName = roomName;
	}

	public TextField getRoomCode() {
		return roomCode;
	}

	public void setRoomCode(TextField roomCode) {
		this.roomCode = roomCode;
	}

	public TextField getDeptName() {
		return deptName;
	}

	public void setDeptName(TextField deptName) {
		this.deptName = deptName;
	}

	public TextField getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(TextField deptCode) {
		this.deptCode = deptCode;
	}

	public ChoiceBox getCboDepartment() {
		return cboDepartment;
	}

	public void setCboDepartment(ChoiceBox cboDepartment) {
		this.cboDepartment = cboDepartment;
	}

	public javafx.scene.control.Button getBtnSave() {
		return btnSave;
	}

	public void setBtnSave(javafx.scene.control.Button btnSave) {
		this.btnSave = btnSave;
	}

	public static RoomPropertiesController getInstance() {
		return instance;
	}

	public static void setInstance(RoomPropertiesController instance) {
		RoomPropertiesController.instance = instance;
	}
	
	
	
}
