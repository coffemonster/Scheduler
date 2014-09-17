package application.course;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import tree.TreeItemData;
import tree.UpdateTree;
import application.main.FXMLDocumentController;
import application.validation.Validation;
import congcrete.Course;
import congcrete.Department;
import database.Connect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;

public class CourseDocumentController implements Initializable{

	@FXML private TextField courseName ;
	@FXML private TextField courseCode ;
	@FXML private ChoiceBox department ;
	private ArrayList<Department> index ;
	
	@FXML public void handleAddCourse(ActionEvent e){
		//VALIDATION
		Validation validator = new Validation() ;
		validator.validateEmpty("Course ", courseName.getText(), courseName);
		validator.validateTextWithNumbers("Course", courseName.getText(), courseName);
		validator.validateEmpty("Course code", courseCode.getText(), courseCode);
		validator.validateTextWithNumbers("Course code", courseCode.getText(), courseCode);
		validator.validateChoiceBox("Department", department);
		
		if(validator.hasError()){
			validator.showError();
			return ;
		}else{
			FXMLDocumentController.getInstance().getWorkplacePane().setBottom(null) ;
		}
		
		//get the primary
		int nextPrimary = Connect.getNextIntegerPrimary("courses", "course_id") ;
		//get the departmetn
		int dept = index.get(department.getSelectionModel().getSelectedIndex()).getDept_id() ;
		Connect.emptyQUERY("INSERT INTO `courses` VALUES(" + nextPrimary + "," + dept + ",'" + courseName.getText().trim() + "','" + 
							courseCode.getText().trim() + "')") ;
		
		FXMLDocumentController.updateTree();
				
		UpdateTree.expandTree();
				
		UpdateTree.selectItem(Course.getItem(dept, nextPrimary));
		
		courseName.setText("");
		courseCode.setText("");
		department.getSelectionModel().clearSelection();
		
		this.initialize(null, null);

	}
	
	@FXML public void removeCourse(MouseEvent e){
		FXMLDocumentController.getInstance().getWorkplacePane().setCenter(null);
		Validation.hideError();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		index = Department.getDepartmentList() ;
		department.getItems().clear();
		for(int x = 0 ; x < index.size() ;x++){
			department.getItems().add(index.get(x).getDept_name()) ;
		}
	}
	
}
