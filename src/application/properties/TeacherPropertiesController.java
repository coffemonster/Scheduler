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
import java.util.ArrayList;
import java.util.ResourceBundle;

import congcrete.Course;
import congcrete.Department;
import congcrete.Room;
import congcrete.Teacher;
import database.Connect;
import tree.TreeItemData;
import NodeUtils.ImageGetter;
import application.main.FXMLDocumentController;
import application.main.Main;
import application.validation.Validation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.image.WritableImage;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialogs;
public class TeacherPropertiesController implements Initializable{

	@FXML private ImageView teacherPicture ;
	@FXML private TextField firstName ;
	@FXML private TextField lastName ;
	@FXML private TextField middleInitial ;
	@FXML private TextField deptName ;
	@FXML private TextField deptCode ;
	@FXML private TextField txtNumberOfSubject ;
	@FXML private TextField txtTotalUnits ;
	@FXML ChoiceBox cboDepartment ;
	@FXML private Button btnSave ;
	private static TeacherPropertiesController instance ;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = this ;
		
		TreeItemData data = (TreeItemData)FXMLDocumentController.getInstance().getTree().getSelectionModel().getSelectedItem() ;
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
			e.printStackTrace();
		}
		
		//setting the data
		firstName.setText(teacher.getFirst_name());
		lastName.setText(teacher.getLast_name());
		middleInitial.setText(teacher.getMiddle_initial());
		deptName.setText(teacher.getD().getDept_name());
		deptCode.setText(teacher.getD().getDept_code());
		
		if(FXMLDocumentController.getInstance().getRightAccordion().getExpandedPane() == null){
			FXMLDocumentController.getInstance().getRightAccordion().setExpandedPane(FXMLDocumentController.getInstance().getDetailsTitledPane());
		}
		
		//set the department
		ArrayList<Department> departments = Department.getDepartmentList() ;
		cboDepartment.getItems().clear();
		for(int x = 0 ; x < departments.size() ; x++){
			cboDepartment.getItems().add(departments.get(x).getDept_code()) ;
		}
		//select the department
		for(int x = 0 ; x < departments.size() ; x++){
			if(teacher.getD().getDept_id() == departments.get(x).getDept_id()){
				cboDepartment.getSelectionModel().select(x);
				break ;
			}
		}
		
		cboDepartment.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Object>(){
			@Override
			public void changed(ObservableValue<? extends Object> arg0,
					Object arg1, Object arg2) {	
				Connect.emptyQUERY("UPDATE teachers SET dept_id = " + departments.get(cboDepartment.getSelectionModel().getSelectedIndex()).getDept_id() + " WHERE teacher_id = " + teacher.getTeacher_id());
				deptName.setText(departments.get(cboDepartment.getSelectionModel().getSelectedIndex()).getDept_name());
				deptCode.setText(departments.get(cboDepartment.getSelectionModel().getSelectedIndex()).getDept_code());
				FXMLDocumentController.getInstance().updateTree();
				FXMLDocumentController.getInstance().getTree().getSelectionModel().select(Teacher.getItem( departments.get(cboDepartment.getSelectionModel().getSelectedIndex()).getDept_id(), teacher.getTeacher_id()));
			}
		});
		
		result = Connect.QUERY("SELECT COUNT(Sub.subject_id) AS num FROM sections S" +
				" JOIN classes C ON C.section_id = S.section_id" +
				" JOIN subjects Sub ON Sub.subject_id = C.subject_id" +
				" JOIN teachers T ON T.teacher_id = C.teacher_id" + 
				" WHERE  T.teacher_id= " + teacher.getTeacher_id()) ;
		try {
			result.next() ;
			txtNumberOfSubject.setText(result.getInt("num") + "");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		result = Connect.QUERY("SELECT SUM(Sub.subject_unit) AS num FROM sections S" +
								" JOIN classes C ON C.section_id = S.section_id" +
								" JOIN subjects Sub ON Sub.subject_id = C.subject_id" +
								" JOIN teachers T ON T.teacher_id = C.teacher_id" + 
								" WHERE  T.teacher_id= " + teacher.getTeacher_id()) ;
		try {
			result.next() ;
			txtTotalUnits.setText(result.getInt("num") + "");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	@FXML public void handleSave(ActionEvent e){
		Validation validator = new Validation() ;
		validator.validateEmpty("Firstname", firstName.getText(), firstName);
		validator.validateTextOnly("Firstname", firstName.getText(), firstName);
		validator.validateEmpty("Lastname", lastName.getText(), lastName);
		validator.validateTextOnly("Lastname", lastName.getText(), lastName);
		validator.validateEmpty("Middle initial", middleInitial.getText(), middleInitial);
		validator.validateTextOnly("Middle Initial", middleInitial.getText(), middleInitial);
		validator.validateMiddleInitial("Middle Initial", middleInitial.getText(), middleInitial);
		
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
			Teacher teacherData = TreeItemData.getItemData(item) ;
			
			Connect.emptyQUERY("UPDATE teachers SET first_name = '"+ firstName.getText() +"' , last_name = '"+ lastName.getText() +"' , middle_initial = '"+ middleInitial.getText() +"' WHERE teacher_id = " + teacherData.getTeacher_id() ) ;
						
			FXMLDocumentController.updateTree();
			
			TreeItem<String> select = Teacher.getItem(teacherData.getD().getDept_id(), teacherData.getTeacher_id()) ;
			
			FXMLDocumentController.getInstance().getTree().getSelectionModel().select(select);
			
			btnSave.setVisible(false);
			firstName.setEditable(false);
			lastName.setEditable(false);
			middleInitial.setEditable(false);
			cboDepartment.setDisable(true);
		}else{
			return ;
		}
		
	}

	public ImageView getTeacherPicture() {
		return teacherPicture;
	}

	public void setTeacherPicture(ImageView teacherPicture) {
		this.teacherPicture = teacherPicture;
	}

	public TextField getFirstName() {
		return firstName;
	}

	public void setFirstName(TextField firstName) {
		this.firstName = firstName;
	}

	public TextField getLastName() {
		return lastName;
	}

	public void setLastName(TextField lastName) {
		this.lastName = lastName;
	}

	public TextField getMiddleInitial() {
		return middleInitial;
	}

	public void setMiddleInitial(TextField middleInitial) {
		this.middleInitial = middleInitial;
	}

	public TextField getDeptName() {
		return deptName;
	}

	public void setDeptName(TextField deptName) {
		this.deptName = deptName;
	}

	public TextField getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(TextField deptCode) {
		this.deptCode = deptCode;
	}

	public TextField getTxtNumberOfSubject() {
		return txtNumberOfSubject;
	}

	public void setTxtNumberOfSubject(TextField txtNumberOfSubject) {
		this.txtNumberOfSubject = txtNumberOfSubject;
	}

	public TextField getTxtTotalUnits() {
		return txtTotalUnits;
	}

	public void setTxtTotalUnits(TextField txtTotalUnits) {
		this.txtTotalUnits = txtTotalUnits;
	}

	public ChoiceBox getCboDepartment() {
		return cboDepartment;
	}

	public void setCboDepartment(ChoiceBox cboDepartment) {
		this.cboDepartment = cboDepartment;
	}

	public Button getBtnSave() {
		return btnSave;
	}

	public void setBtnSave(Button btnSave) {
		this.btnSave = btnSave;
	}

	public static TeacherPropertiesController getInstance() {
		return instance;
	}

	public void setInstance(TeacherPropertiesController instance) {
		this.instance = instance;
	}
	
	
}
