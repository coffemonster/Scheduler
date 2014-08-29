package application.Subject;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class SubjectDocumentController implements Initializable{
	
	@FXML private TextField subject ;
	@FXML private TextField subjectCode ;
	@FXML private TextField units ;
	@FXML private ChoiceBox department ;
	@FXML private ChoiceBox course ;
	@FXML private ChoiceBox year ;
	@FXML private ChoiceBox section ;
	@FXML private ChoiceBox teacher ;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
