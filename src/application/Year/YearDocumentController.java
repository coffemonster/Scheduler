package application.Year;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import tree.UpdateTree;
import congcrete.Course;
import congcrete.Department;
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

public class YearDocumentController implements Initializable{
	
	@FXML private TextField yearLevel ;
	@FXML private ChoiceBox course  ;
	@FXML private ChoiceBox<String> department ;
	private ArrayList<Department> indexDepartment ;
	private ArrayList<Course> indexCourse ;
	private int year ;
	
	@FXML public void removeYear(MouseEvent e){
		FXMLDocumentController.getInstance().getWorkplacePane().setCenter(null);
	}
	
	@FXML public void handleAddYear(ActionEvent e){
		//VALIDATION
		Validation validator = new Validation() ;
		validator.validateChoiceBox("Course", course);
		validator.validateChoiceBox("Department", department);
		
		if(validator.hasError()){
			validator.showError();
			return ;
		}else{
			Validation.hideError();
		}
		
		int course_id = indexCourse.get(course.getSelectionModel().getSelectedIndex()).getCourse_id() ;
		int primary = Connect.getNextIntegerPrimary("years", "year_id") ;
		Connect.emptyQUERY("INSERT INTO `years` VALUES(" + primary + "," + year + "," + course_id + ")");
		
		refreshYear(); 
		
		FXMLDocumentController.updateTree();
		
		UpdateTree.expandTree();
		
		UpdateTree.selectItem(Year.getItem(primary));
		
		course.getSelectionModel().clearSelection();
		department.getSelectionModel().clearSelection();
		yearLevel.setText("");
		
		refreshList() ;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		refreshList() ;
		
		//list all the course available
		department.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>(){

			@Override
			public void changed(ObservableValue<? extends Object> arg0,
					Object arg1, Object arg2) {
				//get the list of course associated in the dept_id
				//if there is no selected
				if(department.getSelectionModel().getSelectedIndex() < 0){
					return ;
				}
				yearLevel.setText("");
				indexCourse = Course.getList(indexDepartment.get(department.getSelectionModel().getSelectedIndex()).getDept_id()) ;
				course.getItems().clear();
				for(int x = 0 ; x < indexCourse.size() ; x++){
					course.getItems().add(indexCourse.get(x).getCourse_name()) ;
				}
				course.getSelectionModel().clearSelection();
			}
			
		});
		
		//Set the appropriate Year
		course.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>(){

			@Override
			public void changed(ObservableValue<? extends Object> observable,
					Object oldValue, Object newValue) {
					if(!(course.getSelectionModel().getSelectedItem() == null)){
						refreshYear() ;
					}
			}
			
		}) ;
	}
	
	private void refreshYear(){
		ResultSet result = Connect.QUERY("SELECT MAX(year) FROM `years` WHERE `course_id` = " + indexCourse.get(course.getSelectionModel().getSelectedIndex()).getCourse_id());
		//if the result has no column it will set to 1
		try {
			if(result.next()){
				//get the max year
				yearLevel.setText(Year.getYearText((result.getInt(1) + 1)));
				year = result.getInt(1) + 1 ;
			}else{
				yearLevel.setText(Year.getYearText(1));
				year = 1 ;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void refreshList(){
		indexDepartment = Department.getDepartmentList() ;
		department.getItems().clear();
		for(int x = 0 ; x < indexDepartment.size() ; x++){
			department.getItems().add(indexDepartment.get(x).getDept_name()) ;
		}
	}
	
}
