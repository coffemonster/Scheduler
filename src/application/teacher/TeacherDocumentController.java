package application.teacher;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import tree.TreeItemData;
import tree.UpdateTree;
import congcrete.Department;
import congcrete.Teacher;
import NodeUtils.ImageGetter;
import application.main.FXMLDocumentController;
import application.main.Main;
import database.Connect;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class TeacherDocumentController implements Initializable{
	
	@FXML private TextField firstName ;
	@FXML private TextField lastName ;
	@FXML private TextField middleInitial ;
	@FXML private ChoiceBox department ;
	@FXML private Button browsePicture ;
	@FXML private ImageView teacherPicture ;
	private String path ;
	private ArrayList<Department> index ;
	private FileInputStream fileStream ;
	private InputStream is ;
	private File f ;
	
	//open a filechooser when click browse
	@FXML public void browsePicture(ActionEvent e){
		FileChooser fc = new FileChooser();
		//adding the extension
		FileChooser.ExtensionFilter jpgExtension = new FileChooser.ExtensionFilter("JPEG", "*.jpg") ;
		FileChooser.ExtensionFilter pngExtension = new FileChooser.ExtensionFilter("PNG", "*.png") ;
		
		fc.getExtensionFilters().add(jpgExtension) ;
		fc.getExtensionFilters().add(pngExtension) ;
 		
		f = fc.showOpenDialog(Main.getMainStage()) ;
		
		if(f == null){
			System.out.print("Empty Picture");
			return ;
		}
		
		try {
			fileStream = new FileInputStream(f) ;
			is = fileStream ;
			teacherPicture.setImage(new Image(is));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
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
		
		String SQL  = "INSERT INTO `teachers` VALUES(?,?,?,?,?,?)" ;
		PreparedStatement pst ;
		try {
			pst = Connect.getConnection().prepareStatement(SQL) ;
			pst.setInt(1, nextPrimary);
			pst.setInt(2, dept_id);
			pst.setString(3, firstName.getText());
			pst.setString(4, lastName.getText());
			pst.setString(5, middleInitial.getText()) ;
			
			BufferedImage bImage = SwingFXUtils.fromFXImage(teacherPicture.getImage(), null);
			ByteArrayOutputStream s = new ByteArrayOutputStream();
			ImageIO.write(bImage, "png", s);
			byte[] res  = s.toByteArray() ;
			
			pst.setBytes(6, res);
			pst.executeUpdate();
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		FXMLDocumentController.updateTree();
		
		UpdateTree.expandTree();
		//0 = const node teacher
		UpdateTree.selectItem(Teacher.getItem(dept_id,nextPrimary));
		
		/*
		TreeItem<String> root = FXMLDocumentController.getTree().getRoot() ;
		
		for(int x = 0 ; x < root.getChildren().size() ; x++){
			//0 for teacher node constant
			TreeItem<String> teacherConst = root.getChildren().get(x).getChildren().get(0) ;
			for(int y = 0 ; y < teacherConst.getChildren().size() ; y++){
				TreeItemData teacher = (TreeItemData) teacherConst.getChildren().get(y) ;
				Teacher teacherData = TreeItemData.getItemData(teacher) ;
				if(teacherData.getTeacher_id() == nextPrimary){
					UpdateTree.selectItem(teacher);
				}
			}
		}
		*/
	}
	
	public void initialize(URL url , ResourceBundle rsc){
		index = Department.getDepartmentList() ;
		for(int x = 0 ; x < index.size() ; x++){
			department.getItems().add(index.get(x).getDept_name()) ;
		}
	}
}
