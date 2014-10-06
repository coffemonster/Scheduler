package application.properties;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import congcrete.Course;
import congcrete.Department;
import database.Connect;
import tree.TreeItemData;
import application.main.FXMLDocumentController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable ;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;

public class CoursePropertiesController implements Initializable{

	@FXML TextField course ;
	@FXML TextField courseCode ;
	@FXML TextField deptName ;
	@FXML TextField deptCode ;
	@FXML TextField year ;
	@FXML ChoiceBox<String> cboDepartment ;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
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
	
}
