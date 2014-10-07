package application.properties;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialogs;

import congcrete.Course;
import congcrete.Department;
import database.Connect;
import tree.TreeItemData;
import application.main.FXMLDocumentController;
import application.validation.Validation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable ;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;

public class CoursePropertiesController implements Initializable{

	@FXML private TextField course ;
	@FXML private TextField courseCode ;
	@FXML private TextField deptName ;
	@FXML private TextField deptCode ;
	@FXML private TextField year ;
	@FXML ChoiceBox<String> cboDepartment ;
	@FXML Button btnSave ;
	private static CoursePropertiesController copyInstance = null ;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		copyInstance = this ;
		
		TreeItem<String> courseItem = (TreeItemData) FXMLDocumentController.getInstance().getTree().getSelectionModel().getSelectedItem();
		Course courseData = TreeItemData.getItemData(courseItem) ;
		course.setText(courseData.getCourse_name());
		courseCode.setText(courseData.getCourse_code());
		deptName.setText(courseData.getD().getDept_name());
		deptCode.setText(courseData.getD().getDept_code());
		//get the year
		ResultSet result = Connect.QUERY("SELECT MAX(year) AS year FROM years WHERE course_id = " + courseData.getCourse_id());
		try {
			result.next();
			year.setText(result.getInt("year") + "");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
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
			if(courseData.getD().getDept_id() == departments.get(x).getDept_id()){
				cboDepartment.getSelectionModel().select(x);
				break ;
			}
		}
		
		cboDepartment.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Object>(){
			@Override
			public void changed(ObservableValue<? extends Object> arg0,
					Object arg1, Object arg2) {	
				Connect.emptyQUERY("UPDATE courses SET dept_id = " + departments.get(cboDepartment.getSelectionModel().getSelectedIndex()).getDept_id() + " WHERE course_id = " + courseData.getCourse_id());
				deptName.setText(departments.get(cboDepartment.getSelectionModel().getSelectedIndex()).getDept_name());
				deptCode.setText(departments.get(cboDepartment.getSelectionModel().getSelectedIndex()).getDept_code());
				FXMLDocumentController.getInstance().updateTree();
				FXMLDocumentController.getInstance().getTree().getSelectionModel().select(Course.getItem( departments.get(cboDepartment.getSelectionModel().getSelectedIndex()).getDept_id(), courseData.getCourse_id()));
			}
		});
	}
	
	@FXML public void handleSave(){
		Validation validator = new Validation() ;
		validator.validateEmpty("Course", course.getText(), course) ;
		validator.validateTextWithNumbers("Course", course.getText(), course) ;
		validator.validateEmpty("Course Code", courseCode.getText(), courseCode) ;
		validator.validateTextWithNumbers("Course Code", courseCode.getText(), courseCode) ;
		
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
			Course courseData = TreeItemData.getItemData(item) ;
			
			Connect.emptyQUERY("UPDATE courses SET course_name = '"+ course.getText() +"' , course_code = '"+ courseCode.getText().trim() +"' WHERE course_id = " + courseData.getCourse_id()) ;
			
			FXMLDocumentController.updateTree();
			TreeItem<String> select = Course.getItem(courseData.getD().getDept_id(), courseData.getCourse_id()) ;
			FXMLDocumentController.getInstance().getTree().getSelectionModel().select(select);
			
			btnSave.setVisible(false);
			course.setEditable(false);
			courseCode.setEditable(false);
			cboDepartment.setDisable(true);
		}else{
			return ;
		}
	}
	
	public static CoursePropertiesController getInstance(){
		return copyInstance ;
	}

	public TextField getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(TextField courseCode) {
		this.courseCode = courseCode;
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

	public TextField getYear() {
		return year;
	}

	public void setYear(TextField year) {
		this.year = year;
	}

	public ChoiceBox<String> getCboDepartment() {
		return cboDepartment;
	}

	public void setCboDepartment(ChoiceBox<String> cboDepartment) {
		this.cboDepartment = cboDepartment;
	}

	public TextField getCourse() {
		return course;
	}

	public Button getBtnSave() {
		return btnSave;
	}
	
	
}
