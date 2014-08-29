package application.section;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import congcrete.Course;
import congcrete.Department;
import congcrete.Year;
import application.main.FXMLDocumentController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class SectionDocumentController implements Initializable{

	@FXML private ChoiceBox course ;
	@FXML private ChoiceBox year ;
	@FXML private ChoiceBox department ;
	@FXML private TextField section ;
	private ArrayList<Department> departmentIndex ;
	private ArrayList<Course> courseIndex ;
	private ArrayList<Year> yearIndex ;
	
	@FXML public void removeYear(MouseEvent e){
		FXMLDocumentController.getWorkplacePane().setCenter(null);
	}
	
	@FXML public void handleAddYear(ActionEvent e){
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
}
