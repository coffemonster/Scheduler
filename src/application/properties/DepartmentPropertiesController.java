package application.properties;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialogs;

import congcrete.Department;
import database.Connect;
import tree.TreeItemData;
import application.main.FXMLDocumentController;
import application.validation.Validation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;

public class DepartmentPropertiesController implements Initializable{

	@FXML private TextField deptName ;
	@FXML private TextField deptCode ;
	@FXML private TextField numTeachers ;
	@FXML private TextField numCourses ;
	@FXML private TextField numRooms ;
	@FXML private Button btnSave ;
	private static DepartmentPropertiesController instance ;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = this ;
		TreeItem<String> dept = (TreeItemData) FXMLDocumentController.getInstance().getTree().getSelectionModel().getSelectedItem() ;
		Department deptData = TreeItemData.getItemData(dept) ;
		deptName.setText(deptData.getDept_name());
		deptCode.setText(deptData.getDept_code());
		//Get the number of resources
		try {
			//Count the Teachers
			ResultSet result = Connect.QUERY("SELECT COUNT(*) AS NUM FROM teachers WHERE dept_id = " + deptData.getDept_id());
			result.next() ;
			numTeachers.setText(result.getInt("NUM") + "");
			//Count the Courses
			result = Connect.QUERY("SELECT COUNT(*) AS NUM FROM courses WHERE dept_id = " + deptData.getDept_id());
			result.next() ;
			numCourses.setText(result.getInt("NUM") + "");
			//Count the Rooms
			result = Connect.QUERY("SELECT COUNT(*) AS NUM FROM rooms WHERE dept_id = " + deptData.getDept_id());
			result.next() ;
			numRooms.setText(result.getInt("NUM") + "");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Opening the department
		if(FXMLDocumentController.getInstance().getRightAccordion().getExpandedPane() == null){
			FXMLDocumentController.getInstance().getRightAccordion().setExpandedPane(FXMLDocumentController.getInstance().getDetailsTitledPane());
		}
		
	}
	
	@FXML public void handleSave(ActionEvent e){
		Validation validator = new Validation() ;
		validator.validateEmpty("Department Name", deptName.getText(), deptName) ;
		validator.validateTextWithNumbers("Department Name", deptName.getText(), deptName) ;
		validator.validateEmpty("Department Code", deptCode.getText(), deptCode) ;
		validator.validateTextWithNumbers("Department Code", deptCode.getText(), deptCode) ;
		
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
			TreeItem<String> itemData = (TreeItem<String>) FXMLDocumentController.getInstance().getTree().getSelectionModel().getSelectedItem() ;
			Department deptData = TreeItemData.getItemData(itemData) ;
			
			Connect.emptyQUERY("UPDATE departments SET dept_name = '"+ deptName.getText().trim() +"' , dept_code = '"+ deptCode.getText().trim() +"' WHERE dept_id = " + deptData.getDept_id());
			
			FXMLDocumentController.updateTree();
			TreeItem<String> select = Department.getItem(deptData.getDept_id()) ;
			FXMLDocumentController.getInstance().getTree().getSelectionModel().select(select);
			
			btnSave.setVisible(false);
			deptCode.setEditable(false);
			deptName.setEditable(false);
		}
		
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

	public Button getBtnSave() {
		return btnSave;
	}

	public void setBtnSave(Button btnSave) {
		this.btnSave = btnSave;
	}

	public static DepartmentPropertiesController getInstance() {
		return instance;
	}	

}
