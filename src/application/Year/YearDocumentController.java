package application.Year;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import congcrete.Course;
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

public class YearDocumentController implements Initializable{
	
	@FXML private TextField yearLevel ;
	@FXML private ChoiceBox course  ;
	private ArrayList<Course> index ;
	
	@FXML public void removeYear(MouseEvent e){
		FXMLDocumentController.getWorkplacePane().setCenter(null);
	}
	
	@FXML public void handleAddYear(ActionEvent e){
		int course_id = index.get(course.getSelectionModel().getSelectedIndex()).getCourse_id() ;
		int primary = Connect.getNextIntegerPrimary("years", "year_id") ;
		Connect.emptyQUERY("INSERT INTO `years` VALUES(" + primary + "," + yearLevel.getText() + "," + course_id + ")");
		
		FXMLDocumentController.updateTree();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		index = Course.getList() ;
		for(int x = 0 ; x < index.size() ; x++){
			course.getItems().add(index.get(x).getCourse_name()) ;
		}
		
		course.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>(){

			@Override
			public void changed(ObservableValue<? extends Object> observable,
					Object oldValue, Object newValue) {
					ResultSet result = Connect.QUERY("SELECT * FROM `years` WHERE `course_id` = " + index.get(course.getSelectionModel().getSelectedIndex()).getCourse_id());
					//if the result has no column it will set to 1
					try {
						if(result.next()){
							yearLevel.setText(Year.getYearText((result.getInt(Year.YEAR) + 1)));
						}else{
							yearLevel.setText(Year.getYearText(1));
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			
		}) ;
	}
	
}
