package application.login;

import java.net.URL;
import java.util.ResourceBundle;

import NodeUtils.FadeAnimation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class LoginDocumentController implements Initializable{

	@FXML TextField username ;
	@FXML TextField password ;
	
	@FXML
	public void handleLogin(ActionEvent e){
		
		//FadeAnimation fade = new FadeAnimation() ;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
