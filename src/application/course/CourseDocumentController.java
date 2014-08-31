package application.course;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import tree.TreeItemData;
import tree.UpdateTree;
import application.main.FXMLDocumentController;
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
				//get the primary
				int nextPrimary = Connect.getNextIntegerPrimary("courses", "course_id") ;
				//get the departmetn
				int dept = index.get(department.getSelectionModel().getSelectedIndex()).getDept_id() ;
				Connect.emptyQUERY("INSERT INTO `courses` VALUES(" + nextPrimary + "," + dept + ",'" + courseName.getText() + "','" + 
									courseCode.getText() + "')") ;
				
				FXMLDocumentController.updateTree();
				
				UpdateTree.expandTree();
				
				UpdateTree.selectItem(Course.getItem(dept, nextPrimary));
	}
	
	@FXML public void removeCourse(MouseEvent e){
		FXMLDocumentController.getWorkplacePane().setCenter(null);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		index = Department.getDepartmentList() ;
		for(int x = 0 ; x < index.size() ;x++){
			department.getItems().add(index.get(x).getDept_name()) ;
		}
	}
	
}
