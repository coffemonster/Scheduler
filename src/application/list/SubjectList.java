package application.list;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.management.Query;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import NodeUtils.ImageGetter;
import application.main.FXMLDocumentController;
import congcrete.Section;
import congcrete.Subject;
import database.Connect;

public class SubjectList {
	
	private Section section ;
	
	public SubjectList(Section section){
		this.section = section ;
	}
	
	public void setList(VBox box){
		ResultSet result = Connect.QUERY("SELECT * FROM subjects WHERE year_id = " + section.getYear().getYear_id()) ;
		try {
			while(result.next()){
				Subject subject = new Subject() ;
				subject.setSubject_name(result.getString(Subject.SUBJECT_NAME));
				subject.setSubject_id(result.getInt(Subject.SUBJECT_ID));
				subject.setSubject_code(result.getString(Subject.SUBJECT_CODE));
				subject.setSection(section);
				subject.setYear(section.getYear());
								
				FlowPane pane = new FlowPane() ;
				ImageView image = new ImageView(new ImageGetter("text87.png").getImage()) ;
				pane.getChildren().add(image) ;
				Label label = new Label(subject.getSubject_name()) ;
				pane.getChildren().add(label) ;
				
				pane.setStyle("-fx-background-color : red");
				
				FXMLDocumentController.getSubjectBox().getChildren().add(pane) ;
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
