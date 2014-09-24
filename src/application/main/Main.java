package application.main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.SQLException ;

/*
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import org.controlsfx.dialog.Dialogs.UserInfo;
*/

import application.splash.SplashDocumentController;
import database.Connect;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;


public class Main extends Application {
	
	private static Stage copy = null ;
	private static Stage copySplash = null ;
	
	@Override
	public void start(Stage primaryStage) {
		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		try {
		
			//copy for splash purpose
			copy = primaryStage ;
			//Load Splash
			Stage stage = new Stage();  
			//copy for splash
			copySplash = stage ;
			Parent splashRoot = FXMLLoader.load(getClass().getResource("/application/splash/splashDocument.fxml")) ;
			Scene splashScene = new Scene(splashRoot) ;
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(splashScene);
			stage.show();

			//Load Primary
			Parent root = FXMLLoader.load(getClass().getResource("/application/main/Document - Copy - Copy - Copy - Copy.fxml"));
			Scene scene = new Scene(root,screenDimension.getWidth(),screenDimension.getHeight() - 100);
			scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setFullScreen(true);
			//primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		launch() ;
	}
	
	@Override
	public void stop() throws SQLException{
		
	}
	
	public static Stage getMainStage(){
		return copy ;
	}
	
	public static Stage getSplashStage(){
		return copySplash ;
	}
}
