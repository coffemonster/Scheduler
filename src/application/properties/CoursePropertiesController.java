package application.properties;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import congcrete.Course;
import database.Connect;
import tree.TreeItemData;
import application.main.FXMLDocumentController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable ;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;

public class CoursePropertiesController implements Initializable{

	@FXML TextField course ;
	@FXML TextField courseCode ;
	@FXML TextField deptName ;
	@FXML TextField deptCode ;
	@FXML TextField year ;
	
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
	}
	
}
