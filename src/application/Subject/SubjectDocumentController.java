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
	@FXML private ChoiceBox<String>  section ;
	@FXML private ChoiceBox<String>  teacher ;
	private ArrayList<Department> departmentIndex ;
	private ArrayList<Course> courseIndex ;
	private ArrayList<Year> yearIndex ;
	private ArrayList<Section> sectionIndex ;
	private ArrayList<Teacher> teacherIndex ;
	
	@FXML public void handleAddSubject(MouseEvent e){
		int nextPrimary = Connect.getNextIntegerPrimary("subjects", "subject_id") ;
		int teacher_id = teacherIndex.get(teacher.getSelectionModel().getSelectedIndex()).getTeacher_id();
		int section_id = sectionIndex.get(section.getSelectionModel().getSelectedIndex()).getSection_id() ;
		Connect.emptyQUERY("INSERT INTO subjects VALUES("+ nextPrimary +","+ teacher_id +","
							+section_id+",'"+subject.getText()+"','"+subjectCode.getText()+"',"+
							units.getText() +")");
		
		FXMLDocumentController.updateTree();		
	}
	
	@FXML public void removeSubject(MouseEvent e){
		FXMLDocumentController.getWorkplacePane().setCenter(null);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		departmentIndex = Department.getDepartmentList() ;
		for(int x = 0 ; x < departmentIndex.size() ; x++){
			department.getItems().add(departmentIndex.get(x).getDept_name()) ;
		}
		
		department.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Object>(){

			@Override
			public void changed(ObservableValue<? extends Object> arg0,
					Object arg1, Object arg2) {
				//get the new List of Course
				courseIndex = Course.getList(departmentIndex.get(department.getSelectionModel().getSelectedIndex()).getDept_id()) ;
				course.getItems().clear();
				//deselect all
				course.getSelectionModel().clearSelection();
				year.getSelectionModel().clearSelection();
				section.getSelectionModel().clearSelection();
				teacher.getSelectionModel().clearSelection();
				
				for(int x = 0 ; x < courseIndex.size() ; x++){
					course.getItems().add(courseIndex.get(x).getCourse_name()) ;
 				}
				
				teacherIndex = Teacher.getTeacherList(departmentIndex.get(department.getSelectionModel().getSelectedIndex()).getDept_id()) ;
				teacher.getItems().clear();
				for(int x = 0 ; x < teacherIndex.size() ; x++){
					teacher.getItems().add(teacherIndex.get(x).getLast_name() + ", " + teacherIndex.get(x).getLast_name() + " " + teacherIndex.get(x).getMiddle_initial() + ".") ;
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
					section.getSelectionModel().clearSelection();
					
					section.getItems().clear();
				}
			}
			
		});
		
		year.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Object>(){

			@Override
			public void changed(ObservableValue<? extends Object> arg0,
					Object arg1, Object arg2) {
				
				if(!(year.getSelectionModel().getSelectedIndex() == -1)){
					sectionIndex = Section.getList(yearIndex.get(year.getSelectionModel().getSelectedIndex()).getYear_id()) ;
					section.getItems().clear();
					for(int x = 0 ; x < sectionIndex.size() ; x++){
						section.getItems().add(sectionIndex.get(x).getSection()) ;
					}
					
					section.getSelectionModel().clearSelection();
				}
			}
			
		});
	}
	
}
