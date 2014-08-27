package application.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import congcrete.Teacher;
import tree.TreeItemData;
import application.main.FXMLDocumentController;
import application.main.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
		System.out.print(teacher.getFirst_name());
		File f = new File(teacher.getPicture_path()) ;
		FileInputStream fis;
		try {
			System.out.print("awaaa");
			fis = new FileInputStream(f);
			InputStream is = fis ;
			teacherPicture.setImage(new Image(is));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
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
