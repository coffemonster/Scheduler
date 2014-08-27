package application.teacher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import tree.TreeItemData;
import congcrete.Department;
import congcrete.Teacher;
import NodeUtils.ImageGetter;
import application.main.FXMLDocumentController;
import application.main.Main;
import database.Connect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

public class TeacherDocumentController implements Initializable{
	
	@FXML private TextField firstName ;
	@FXML private TextField lastName ;
	@FXML private TextField middleInitial ;
	@FXML private ChoiceBox department ;
	@FXML private Button browsePicture ;
	@FXML private ImageView teacherPicture ;
	private String path ;
	private ArrayList<Department> index ;
	
	//open a filechooser when click browse
	@FXML public void browsePicture(ActionEvent e){
		FileChooser fc = new FileChooser();
		File f = fc.showOpenDialog(Main.getMainStage()) ;
		//TODO add validation here if the user not picking picture it will have an exception
		/*
		path = f.getAbsolutePath() ;
		path = path.replace('\\', '/');
		
		FileInputStream fis;
		try {
			fis = new FileInputStream(f);
			InputStream is = fis ;
			teacherPicture.setImage(new Image(is));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		*/
	}
	
	//remove teacher
	@FXML public void removeTeacher(MouseEvent e){
		FXMLDocumentController.getWorkplacePane().setCenter(null);
	}
	
	//handle add teacher
	@FXML public void handleAddTeacher(ActionEvent e){
		//get the next Primary
		int nextPrimary = Connect.getNextIntegerPrimary("teachers", "teacher_id") ;
		//get the Department ID
		int dept_id = index.get(department.getSelectionModel().getSelectedIndex()).getDept_id() ;
		Connect.emptyQUERY("INSERT INTO `teachers` VALUES(" + nextPrimary + "," + dept_id + ",'" + firstName.getText() + "','" +
															lastName.getText() + "','" + middleInitial.getText() + "','" + path + "')");
		//set the path to null 
		path = null ;
		
		FXMLDocumentController.updateTree();
		/*
		Teacher t = new Teacher() ;
	
		Node teacherImg = new ImageView(new ImageGetter("teacher4.png").getImage()) ;
		TreeItemData data = new TreeItemData(firstName.getText() + "','" + lastName.getText() + "','" + middleInitial.getText() , teacherImg , t);
		FXMLDocumentController.getTree().getRoot().get
		*/
	}
	
	public void initialize(URL url , ResourceBundle rsc){
		index = Department.getDepartmentList() ;
		for(int x = 0 ; x < index.size() ; x++){
			department.getItems().add(index.get(x).getDept_name()) ;
		}
	}
}
