package application.main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.SQLException ;

/*
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import org.controlsfx.dialog.Dialogs.UserInfo;
*/
















import org.controlsfx.dialog.Dialogs;

import congcrete.Connection;
import application.User;
import application.splash.SplashDocumentController;
import database.Connect;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;


public class Main extends Application {
	
	private static Stage copy = null ;
	private static Stage copySplash = null ;
	private static Stage copyConnection = null ;
	private static Main mainCopy = null ;
	
	@Override
	public void start(Stage primaryStage) {
		mainCopy = this ;
		
		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		boolean isConnectionExisted = false ;
		Stage connectStage = new Stage() ;
		copyConnection = connectStage ;
		//copy for splash purpose
		copy = primaryStage ;
		//Load Splash
		Stage stage = new Stage();  
		//copy for splash
		copySplash = stage ;
		
		try {
			
			//Show the connection pane ;
			Connection connection = null ;
			FileInputStream fileIn = null ;
			ObjectInputStream in = null  ;
			try
		      {
		         fileIn = new FileInputStream("Connection.ser");
		         in = new ObjectInputStream(fileIn);
		         connection = (Connection) in.readObject();
		         in.close();
		         fileIn.close();
		         isConnectionExisted = true ;
		      }catch(IOException i)
		      {
		    	  if(fileIn != null){
		    		  fileIn.close(); 
		    	  }
		    	  if(in != null){
		    		  in.close();
		    	  }
 
		    	  File current = new File("Connection.ser") ;
		    	  //System.out.print(current.exists());
		    	  if(current.exists()){
		    		  current.delete() ;
		    	  }
		    	 //show the connection pane
		    	  AnchorPane connectPane = FXMLLoader.load(getClass().getResource("/database/connectDocument.fxml")) ;
		    	  Scene connectScene = new Scene(connectPane) ;
		    	  connectStage.setScene(connectScene);
		    	  connectStage.show();
		    	  
		      }catch(ClassNotFoundException c)
		      {
		    	  if(fileIn != null){
		    		  fileIn.close(); 
		    	  }
		    	  if(in != null){
		    		  in.close();
		    	  }
 
		    	  File current = new File("Connection.ser") ;
		    	  System.out.print(current.exists());
		    	  if(current.exists()){
		    		  System.out.print("Is " + current.delete()) ;
		    	  }
		    	  
		         Dialogs.create()
		         .title("Error")
		         .message("No Connection String was found")
		         .showError() ;
		        
		         return ;
		      }catch(java.lang.ClassCastException z){
		    	  
		      }
		
			if(isConnectionExisted){
				Main.getInstance().showSplash();
				if(Connect.getConnection() == null){
					Dialogs.create()
					.title("Error")
					.message("Can't connect to the server")
					.showError() ;
					
					Main.getInstance().showConnect(); 
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		launch() ;
	}
	
	@Override
	public void stop() throws SQLException{
		if(User.isActive()){
			User.logout();
		}
		SplashDocumentController.isAlive = false ;
	}
	
	public static Stage getMainStage(){
		return copy ;
	}
	
	public void showConnect(){
		AnchorPane connectPane = null ;
		try {
			connectPane = FXMLLoader.load(getClass().getResource("/database/connectDocument.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	Scene connectScene = new Scene(connectPane) ;
		Main.getCopyConnection().setScene(connectScene);
		Main.getCopyConnection().show();
	}
	
	public void showMain(){
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/application/main/Document - Copy - Copy - Copy - Copy.fxml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
		Main.getMainStage().setScene(scene) ;
		Main.getMainStage().setFullScreen(true);
		Main.getMainStage().show();
	}
	
	public void showSplash(){
		Parent splashRoot = null ;
		try {
			splashRoot = FXMLLoader.load(getClass().getResource("/application/splash/splashDocument.fxml")) ;
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene splashScene = new Scene(splashRoot) ;
		Main.getSplashStage().initStyle(StageStyle.UNDECORATED);
		Main.getSplashStage().setScene(splashScene);
		Main.getSplashStage().show();	
	}
	
	
	
	public static Stage getCopyConnection() {
		return copyConnection;
	}

	public static Main getInstance(){
		return mainCopy ;
	}
	
	public static Stage getSplashStage(){
		return copySplash ;
	}
}
