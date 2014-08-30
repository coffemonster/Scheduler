package application.properties;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import congcrete.Teacher;
import database.Connect;
import tree.TreeItemData;
import NodeUtils.ImageGetter;
import application.main.FXMLDocumentController;
import application.main.Main;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;
public class TeacherPropertiesController implements Initializable{

	@FXML private ImageView teacherPicture ;
	@FXML private TextField firstName ;
	@FXML private TextField lastName ;
	@FXML private TextField middleInitial ;
	@FXML private TextField deptName ;
	@FXML private TextField deptCode ;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		TreeItemData data = (TreeItemData)FXMLDocumentController.getTree().getSelectionModel().getSelectedItem() ;
		Teacher teacher = (Teacher)data.getData() ;
		//setting the picture		
		ResultSet result = Connect.QUERY("SELECT `picture_path` FROM `teachers` WHERE `teacher_id` = " + teacher.getTeacher_id()) ;
		
		try {
			while(result.next()){
				System.out.print("Naano");
				byte[] res  = result.getBytes("picture_path") ;
				ByteArrayInputStream bais = new ByteArrayInputStream(res);
		        BufferedImage image = ImageIO.read(bais);
		        Image i = SwingFXUtils.toFXImage(image , null) ;
		        teacherPicture.setImage(i);
			}
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		//setting the data
		firstName.setText(teacher.getFirst_name());
		lastName.setText(teacher.getLast_name());
		middleInitial.setText(teacher.getMiddle_initial());
		deptName.setText(teacher.getD().getDept_name());
		deptCode.setText(teacher.getD().getDept_code());
		
		FXMLDocumentController.getRightAccordion().setExpandedPane(FXMLDocumentController.getDetailsTitledPane());
	}
	
}
