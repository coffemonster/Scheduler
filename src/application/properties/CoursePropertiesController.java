package application.properties;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable ;
import javafx.scene.control.TextField;

public class CoursePropertiesController implements Initializable{

	@FXML TextField course ;
	@FXML TextField courseCode ;
	@FXML TextField deptName ;
	@FXML TextField deptCode ;
	@FXML TextField year ;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
}
