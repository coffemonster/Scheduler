package application.properties;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import congcrete.Section;
import congcrete.Subject;
import congcrete.Teacher;
import database.Connect;
import tree.TreeItemData;
import application.main.FXMLDocumentController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
		
		ResultSet result = Connect.QUERY("SELECT teacher_id FROM classes WHERE section_id = " + sectionData.getSection_id() + " AND subject_id = " + subjectData.getSubject_id()) ;

		try {
			result.next() ;
			System.out.print(result.getRow());
			if(result.getRow() != 0){
				for(int x = 0 ; x < teacherList.size() ; x++){
					if(teacherList.get(x).getTeacher_id() == result.getInt("teacher_id")){
						teacher.getSelectionModel().select(x);
					}
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		teacher.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Object>(){

			@Override
			public void changed(ObservableValue arg0, Object arg1, Object arg2) {
				// TODO Auto-generated method stub
				ResultSet result = Connect.QUERY("SELECT * FROM classes WHERE section_id = " + sectionData.getSection_id() + " AND subject_id = " + subjectData.getSubject_id()) ;
				try {
					result.next();
					if(result.getRow() == 0){
						int nextPrimary = Connect.getNextIntegerPrimary("classes", "class_id") ;
						int teacherid = teacherList.get(teacher.getSelectionModel().getSelectedIndex()).getTeacher_id() ;
						Connect.emptyQUERY("INSERT INTO classes VALUES("+nextPrimary+","+subjectData.getSubject_id()+","+sectionData.getSection_id()+","+teacherid+")");
					}else{
						int teacherid = teacherList.get(teacher.getSelectionModel().getSelectedIndex()).getTeacher_id() ;
						Connect.emptyQUERY("UPDATE classes SET teacher_id = " + teacherid + " WHERE section_id = " + sectionData.getSection_id() + " AND subject_id = " + subjectData.getSubject_id());
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});	
	}

}
