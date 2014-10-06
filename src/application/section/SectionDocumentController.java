package application.section;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.controlsfx.control.NotificationPane;

import tree.UpdateTree;
import congcrete.Course;
import congcrete.Department;
import congcrete.Section;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class SectionDocumentController implements Initializable{

	@FXML private ChoiceBox course ;
	@FXML private ChoiceBox year ;
	@FXML private ChoiceBox department ;
	@FXML private TextField section ;
	private ArrayList<Department> departmentIndex ;
	private ArrayList<Course> courseIndex ;
	private ArrayList<Year> yearIndex ;
	
	@FXML public void handleAddSection(ActionEvent e){
		//VALIDATION
		Validation validator = new Validation() ;
		validator.validateChoiceBox("Course", course);
		validator.validateChoiceBox("Year", year);
		validator.validateChoiceBox("Department", department);
		
		if(validator.hasError()){
			validator.showError();
			ImageView image = new ImageView(new NodeUtils.ImageGetter("error.png").getImage()) ;
			NotificationPane noti = (NotificationPane)FXMLDocumentController.getInstance().getTabpane().getSelectionModel().getSelectedItem().getContent() ;
			noti.show("An error occured during validation" , image);
			return ;
		}else{
			validator.hideError();
		}
		
		int nextPrimary = Connect.getNextIntegerPrimary("sections", "section_id") ;
		int year_id = yearIndex.get(year.getSelectionModel().getSelectedIndex()).getYear_id() ;
		Connect.emptyQUERY("INSERT INTO sections VALUES(" + nextPrimary + ",'" + section.getText() + "'," + year_id + ")");
		
		ImageView image = new ImageView(new NodeUtils.ImageGetter("check.png").getImage()) ;
		NotificationPane noti = (NotificationPane)FXMLDocumentController.getInstance().getTabpane().getSelectionModel().getSelectedItem().getContent() ;
		noti.show("Success" , image);
		
		refreshSection() ;
		FXMLDocumentController.updateTree();
		
		UpdateTree.expandTree();
		
		UpdateTree.selectItem(Section.getItem(nextPrimary));
		
		/*
		course.getSelectionModel().clearSelection();
		department.getSelectionModel().clearSelection();
		year.getSelectionModel().clearSelection();
		section.setText("");
		*/
		refreshSection() ;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		refreshList() ;
		
		department.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Object>(){

			@Override
			public void changed(ObservableValue<? extends Object> arg0,
					Object arg1, Object arg2) {
				//unselect the course and year
				if(department.getSelectionModel().getSelectedIndex() == -1){
					return ;
				}
				course.getSelectionModel().clearSelection();
				year.getSelectionModel().clearSelection();
				section.setText("");
				//to prevent multiple
				course.getItems().clear();
				courseIndex = Course.getList(departmentIndex.get(department.getSelectionModel().getSelectedIndex()).getDept_id()) ;
				for(int x = 0 ; x < courseIndex.size() ; x++){
					course.getItems().add(courseIndex.get(x).getCourse_name()) ;
				}
			}
			
		});
		
		course.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>(){

			@Override
			public void changed(ObservableValue<? extends Object> arg0,
					Object arg1, Object arg2) {
				if(!(course.getSelectionModel().getSelectedItem() == null)){
					year.getSelectionModel().clearSelection();
					year.getItems().clear();
					section.setText("");
					yearIndex = Year.getList(courseIndex.get(course.getSelectionModel().getSelectedIndex()).getCourse_id()) ;
					for(int x = 0 ; x < yearIndex.size(); x++){
						year.getItems().add(yearIndex.get(x).getYear()) ;
					}
					year.getSelectionModel().select(0);
				}
			}
			
		});
		
		year.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>(){

			@Override
			public void changed(ObservableValue<? extends Object> arg0,
					Object arg1, Object arg2) {
				if(!(course.getSelectionModel().getSelectedItem() == null) && !(year.getSelectionModel().getSelectedItem() == null)){
					refreshSection() ;
					
				}
			}
			
		});
		
	}
	
	private void refreshSection(){
		//get the last added section
		ResultSet result = Connect.QUERY("SELECT section FROM sections WHERE `year_id` = " + yearIndex.get(year.getSelectionModel().getSelectedIndex()).getYear_id() + " ORDER BY section DESC LIMIT 1") ;
		try {
			if(result.next()){
				char sectionChar = result.getString(1).charAt(0) ;
				int sectionInt = Character.getNumericValue(sectionChar) + 56;
				char newSection = (char)sectionInt ;
				section.setText((char)sectionInt + "");
			}else{
				section.setText("A");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void refreshList(){
		departmentIndex = Department.getDepartmentList() ;
		department.getItems().clear();
		for(int x = 0 ; x < departmentIndex.size() ; x++){
			department.getItems().add(departmentIndex.get(x).getDept_name()) ;
		}
	}
}
