package application.properties;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialogs;

import congcrete.Course;
import congcrete.Subject;
import congcrete.Year;
import database.Connect;
import tree.TreeItemData;
import application.main.FXMLDocumentController;
import application.validation.Validation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;

public class SubjectMainPropertiesController implements Initializable {
	
	@FXML private TextField subject ;
	@FXML private TextField subjectCode ;
	@FXML private TextField subjectUnits ;
	@FXML private TextField year ;
	@FXML private Button btnSave ;
	private static SubjectMainPropertiesController instance ;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = this ;
		
		TreeItem<String> subjectItem = (TreeItem<String>) FXMLDocumentController.getInstance().getTree().getSelectionModel().getSelectedItem() ;
		Subject subjectData = TreeItemData.getItemData(subjectItem) ;
		
		subject.setText(subjectData.getSubject_name());
		subjectCode.setText(subjectData.getSubject_code());
		subjectUnits.setText(subjectData.getSubject_unit() + "");
		
		Year yearData = TreeItemData.getItemData(subjectItem.getParent().getParent()) ;
		year.setText(yearData.getYear() + "");
		
		FXMLDocumentController.getInstance().getRightAccordion().setExpandedPane(FXMLDocumentController.getInstance().getDetailsTitledPane());
		
	}
	
	@FXML public void handleSave(ActionEvent e){
		Validation validator = new Validation() ;
		validator.validateEmpty("Subject Name", subject.getText(),subject) ;
		validator.validateTextWithNumbers("Subject Name", subject.getText(),subject) ;
		validator.validateEmpty("Subject Code", subjectCode.getText(), subjectCode) ;
		validator.validateTextWithNumbers("Subject Code", subjectCode.getText(), subjectCode) ;
		validator.validateEmpty("Subject Unit", subjectUnits.getText(), subjectUnits) ;
		validator.validateTextWithNumbers("Subject Unit", subjectUnits.getText(), subjectUnits) ;
		
		if(validator.hasError()){
			String error = "" ;
			
			for(int x = 0 ; x < validator.getErrorList().size() ; x++){
				error += validator.getErrorList().get(x) + "\n" ;
			}
			
			Dialogs.create()
			.title("Error")
			.message(error)
			.showError() ;
			return ;
		}
		
		Action decision = Dialogs.create()
				.title("Warning")
				.message("Data will be saved permanently")
				.showConfirm() ;
		
		if(decision.toString().equalsIgnoreCase("YES")){
			TreeItem<String> item = (TreeItem<String>) FXMLDocumentController.getInstance().getTree().getSelectionModel().getSelectedItem() ;
			Subject subjectData = TreeItemData.getItemData(item) ;
			
			Connect.emptyQUERY("UPDATE subjects SET subject_name = '"+ subject.getText().trim() +"' , subject_code = '"+ subjectCode.getText().trim() +"' , subject_unit = "+ subjectUnits.getText().trim() +" WHERE subject_id = " + subjectData.getSubject_id()) ;
			
			TreeItem<String> temp = item.getParent().getParent() ;
			Year year = TreeItemData.getItemData(temp) ;
			
			FXMLDocumentController.updateTree();
			TreeItem<String> yearSelect = Year.getItem(year.getYear_id()) ;
			FXMLDocumentController.getInstance().getTree().getSelectionModel().select(yearSelect.getChildren().get(0));
			
			btnSave.setVisible(false);
			subject.setEditable(false);
			subjectCode.setEditable(false);
			subjectUnits.setEditable(false);
		}else{
			return ;
		}
 	}
	
	public static SubjectMainPropertiesController getInstance() {
		return instance;
	}

	public TextField getSubject() {
		return subject;
	}

	public void setSubject(TextField subject) {
		this.subject = subject;
	}

	public TextField getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(TextField subjectCode) {
		this.subjectCode = subjectCode;
	}

	public TextField getSubjectUnits() {
		return subjectUnits;
	}

	public void setSubjectUnits(TextField subjectUnits) {
		this.subjectUnits = subjectUnits;
	}

	public TextField getYear() {
		return year;
	}

	public void setYear(TextField year) {
		this.year = year;
	}

	public Button getBtnSave() {
		return btnSave;
	}

	public void setBtnSave(Button btnSave) {
		this.btnSave = btnSave;
	}
	
	
}
