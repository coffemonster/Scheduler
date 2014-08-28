package application.Year;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import congcrete.Course;
import database.Connect;
import application.main.FXMLDocumentController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class YearDocumentController implements Initializable{
	
	@FXML private TextField yearLevel ;
	@FXML private ChoiceBox course  ;
	private ArrayList<Course> index ;
	
	@FXML public void removeYear(MouseEvent e){
		FXMLDocumentController.getWorkplacePane().setCenter(null);
	}
	
	@FXML public void handleAddYear(ActionEvent e){
		int course_id = index.get(course.getSelectionModel().getSelectedIndex()).getCourse_id() ;
		Connect.emptyQUERY("INSERT INTO `years` VALUES(" + yearLevel.getText() + "," + course_id + ")");
		
		FXMLDocumentController.updateTree();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		yearLevel.setText(Connect.getNextIntegerPrimary("years", "year") + "");
		index = Course.getList() ;
		for(int x = 0 ; x < index.size() ; x++){
			course.getItems().add(index.get(x).getCourse_name()) ;
		}
	}
	
}
