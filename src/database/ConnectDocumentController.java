package database;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.controlsfx.dialog.Dialogs;

import application.main.Main;
import application.splash.SplashDocumentController;
import congcrete.Connection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConnectDocumentController implements Initializable {
	
	@FXML private TextField txtHost ;
	@FXML private TextField txtUsername ;
	@FXML private TextField txtPassword ;
	@FXML private TextField txtDatabase  ;
	@FXML private TextField txtPort ;
	public static boolean isOpen = false ;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		isOpen = true ;
	}

	@FXML public void handleConnect(ActionEvent e){
		
		Object obj = Connect.connect(txtHost.getText(), txtPort.getText(), txtDatabase.getText(), txtUsername.getText(), txtPassword.getText()) ;
		//Connect.connect("localhost", "3306", "scheduler", "root", "1234") ;

			if(obj == null){
				Dialogs.create()
				.title("Error")
				.message("Cant connect to the server") ;
				return ;
			}
	
		
		Connection connection = new Connection() ;
		connection.setHostName(txtHost.getText());
		connection.setUsername(txtUsername.getText());
		connection.setPassword(txtPassword.getText());
		connection.setDatabase(txtDatabase.getText());
		connection.setPort(txtPort.getText());
		
		try
	      {
	         FileOutputStream fileOut = new FileOutputStream("Connection.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(connection);
	         out.close();
	         fileOut.close();
	      }catch(IOException i)
	      {
	    	  Dialogs.create()
	    	  .title("Error")
	    	  .showExceptionInNewWindow(i) ;
	    	  return ;
	      }
		
		if(SplashDocumentController.connectionChecker != null){
			SplashDocumentController.connectionChecker.start();
			Main.getInstance().showSplash();
		}
		Main.getCopyConnection().close(); 
	}
	
}
