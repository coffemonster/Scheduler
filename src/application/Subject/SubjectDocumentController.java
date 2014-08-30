package application.Subject;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

import congcrete.Course;
import congcrete.Department;
import congcrete.Section;
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
	
	@FXML public void handleAddSubject(ActionEvent e){
		
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
				courseIndex = Course.getList(departmentIndex.get(department.getSelectionModel().getSelectedIndex()).getDept_id()) ;
				for(int x = 0 ; x < courseIndex.size() ; x++){
					course.getItems().add(courseIndex.get(x).getCourse_name()) ;
 				}
				
				course.getSelectionModel().select(0);
			}
			
		});
		
		course.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Object>(){

			@Override
			public void changed(ObservableValue<? extends Object> arg0,
					Object arg1, Object arg2) {
				yearIndex = Year.getList(courseIndex.get(course.getSelectionModel().getSelectedIndex()).getCourse_id()) ;
				
				for(int x = 0 ; x < yearIndex.size() ; x++){
					year.getItems().add(yearIndex.get(x).getYear() + "") ;
				}
				year.getSelectionModel().select(0);
			}
			
		});
		
		year.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Object>(){

			@Override
			public void changed(ObservableValue<? extends Object> arg0,
					Object arg1, Object arg2) {
				
				sectionIndex = Section.getList(yearIndex.get(year.getSelectionModel().getSelectedIndex()).getYear_id()) ;
				for(int x = 0 ; x < sectionIndex.size() ; x++){
					section.getItems().add(sectionIndex.get(x).getSection()) ;
				}
				section.getSelectionModel().select(0);
			}
			
		});
	}
	
}
