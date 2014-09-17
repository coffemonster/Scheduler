package application.Subject;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

import tree.UpdateTree;
import congcrete.Course;
import congcrete.Department;
import congcrete.Section;
import congcrete.Subject;
import congcrete.Teacher;
import congcrete.Year;
import database.Connect;
import application.main.FXMLDocumentController;
import application.validation.Validation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class SubjectDocumentController implements Initializable{
	
	@FXML private TextField subject ;
	@FXML private TextField subjectCode ;
	@FXML private TextField units ;
	@FXML private ChoiceBox<String> department ;
	@FXML private ChoiceBox<String>  course ;
	@FXML private ChoiceBox<String>  year ;

	private ArrayList<Department> departmentIndex ;
	private ArrayList<Course> courseIndex ;
	private ArrayList<Year> yearIndex ;
	
	@FXML public void handleAddSubject(MouseEvent e){
		//VALIDATION
		Validation validator = new Validation() ;
		validator.validateEmpty("Subject", subject.getText(), subject);
		validator.validateTextWithNumbers("Subject", subject.getText(), subject);
		validator.validateEmpty("Subject code", subjectCode.getText(), subjectCode);
		validator.validateTextWithNumbers("Subject code", subjectCode.getText(), subjectCode);
		validator.validateEmpty("Subject units",units.getText(),units);
		validator.validateNumberOnly("Subject units",units.getText(),units);
		validator.validateChoiceBox("department", department);
		validator.validateChoiceBox("Course", course);
		validator.validateChoiceBox("Year", year);
		
		if(validator.hasError()){
			validator.showError();
			return ;
		}else{
			Validation.hideError();
		}
		
		int nextPrimary = Connect.getNextIntegerPrimary("subjects", "subject_id") ;
		int year_id = yearIndex.get(year.getSelectionModel().getSelectedIndex()).getYear_id() ;
		Connect.emptyQUERY("INSERT INTO subjects VALUES("+ nextPrimary +","+ year_id +",'"+subject.getText().trim()+"','"+subjectCode.getText().trim()+"',"+
							units.getText() +")");
		
		FXMLDocumentController.updateTree();
		
		FXMLDocumentController.getInstance().getTree().getSelectionModel().select(Year.getItem(year_id));
		
		subject.setText("");
		subjectCode.setText("");
		units.setText("");
		
	}
	
	@FXML public void removeSubject(MouseEvent e){
		FXMLDocumentController.getInstance().getWorkplacePane().setCenter(null);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		refreshList(); 
		
		department.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Object>(){

			@Override
			public void changed(ObservableValue<? extends Object> arg0,
					Object arg1, Object arg2) {
				//get the new List of Course
				if(department.getSelectionModel().getSelectedIndex() != -1){
					courseIndex = Course.getList(departmentIndex.get(department.getSelectionModel().getSelectedIndex()).getDept_id()) ;
					course.getItems().clear();
					//deselect all
					course.getSelectionModel().clearSelection();
					year.getSelectionModel().clearSelection();
					
					for(int x = 0 ; x < courseIndex.size() ; x++){
						course.getItems().add(courseIndex.get(x).getCourse_name()) ;
	 				}
				}
			}
			
		});
		
		course.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Object>(){

			@Override
			public void changed(ObservableValue<? extends Object> arg0,
					Object arg1, Object arg2) {
				
				if(!(course.getSelectionModel().getSelectedIndex() == -1)){
					yearIndex = Year.getList(courseIndex.get(course.getSelectionModel().getSelectedIndex()).getCourse_id()) ;
					year.getItems().clear();
					
					for(int x = 0 ; x < yearIndex.size() ; x++){
						year.getItems().add(yearIndex.get(x).getYear() + "") ;
					}
					
					year.getSelectionModel().clearSelection();

				}
			}
			
		});
	}
	
	private void refreshList(){
		departmentIndex = Department.getDepartmentList() ;
		department.getItems().clear();
		for(int x = 0 ; x < departmentIndex.size() ; x++){
			department.getItems().add(departmentIndex.get(x).getDept_name()) ;
		}
	}
	
}
