package application.properties;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import congcrete.Department;
import database.Connect;
import tree.TreeItemData;
import application.main.FXMLDocumentController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;

public class DepartmentPropertiesController implements Initializable{

	@FXML TextField deptName ;
	@FXML TextField deptCode ;
	@FXML TextField numTeachers ;
	@FXML TextField numCourses ;
	@FXML TextField numRooms ;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		TreeItem<String> dept = (TreeItemData) FXMLDocumentController.getInstance().getTree().getSelectionModel().getSelectedItem() ;
		Department deptData = TreeItemData.getItemData(dept) ;
		deptName.setText(deptData.getDept_name());
		deptCode.setText(deptData.getDept_code());
		//Get the number of resources
		try {
			//Count the Teachers
			ResultSet result = Connect.QUERY("SELECT COUNT(*) AS NUM FROM teachers WHERE dept_id = " + deptData.getDept_id());
			result.next() ;
			numTeachers.setText(result.getInt("NUM") + "");
			//Count the Courses
			result = Connect.QUERY("SELECT COUNT(*) AS NUM FROM courses WHERE dept_id = " + deptData.getDept_id());
			result.next() ;
			numCourses.setText(result.getInt("NUM") + "");
			//Count the Rooms
			result = Connect.QUERY("SELECT COUNT(*) AS NUM FROM rooms WHERE dept_id = " + deptData.getDept_id());
			result.next() ;
			numRooms.setText(result.getInt("NUM") + "");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Opening the department
		if(FXMLDocumentController.getInstance().getRightAccordion().getExpandedPane() == null){
			FXMLDocumentController.getInstance().getRightAccordion().setExpandedPane(FXMLDocumentController.getInstance().getDetailsTitledPane());
		}
		
	}

}
