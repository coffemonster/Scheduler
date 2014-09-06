package application.properties;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import congcrete.Section;
import congcrete.Subject;
import congcrete.Teacher;
import database.Connect;
import tree.TreeItemData;
import application.main.FXMLDocumentController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;

public class SubjectPropertiesController implements Initializable{
	
	@FXML private TextField subject ;
	@FXML private TextField subjectCode ;
	@FXML private TextField subjectUnits ;
	@FXML private TextField year ;
	@FXML private TextField section ;
	@FXML private ChoiceBox teacher ;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		TreeItem<String> item = (TreeItemData) FXMLDocumentController.getTree().getSelectionModel().getSelectedItem() ;
		Subject subjectData = TreeItemData.getItemData(item) ;
		
		subject.setText(subjectData.getSubject_name());
		subjectCode.setText(subjectData.getSubject_code());
		subjectUnits.setText(subjectData.getSubject_unit() + "");
		year.setText(subjectData.getYear().getYear() + "");
		
		TreeItem<String> sec = (TreeItemData)item.getParent() ;
		Section sectionData = TreeItemData.getItemData(sec) ;
		
		section.setText(sectionData.getSection());
		
		//set teachers
		ArrayList<Teacher> teacherList = Teacher.getTeacherList(subjectData.getYear().getCourse().getD().getDept_id()) ;
		for(int x = 0 ; x < teacherList.size() ; x++){
			teacher.getItems().add(teacherList.get(x).getLast_name() + ", " + teacherList.get(x).getFirst_name()) ;
		}
	}

}
