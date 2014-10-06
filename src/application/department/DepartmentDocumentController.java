package application.department;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ResourceBundle;

import org.controlsfx.control.NotificationPane;

import congcrete.Department;
import congcrete.TimeSlot;
import NodeUtils.BounceInTransition;
import NodeUtils.NodeAnimation;
import NodeUtils.ScaleAnimation;
import NodeUtils.ShakeAnimation;
import application.main.FXMLDocumentController;
import application.scheduler.Scheduler;
import application.scheduler.SimpleScheduler;
import application.validation.Validation;
import tree.TreeItemData;
import tree.UpdateTree;
import database.Connect;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class DepartmentDocumentController implements Initializable{
	
	@FXML private TextField inputDeptName ;
	@FXML private TextField inputDeptCode ;
	@FXML private AnchorPane rootPane ;
	@FXML private Button finish ;
	
	Scheduler sc ;
	
	//Handle , Adding in the DB
	@FXML public void handleAddDepartment(ActionEvent e) throws SQLException{
		//VALIDATION
		Validation validator = new Validation() ;
		validator.validateTextOnly("Department Name", inputDeptName.getText() , inputDeptName);
		validator.validateEmpty("Department Name", inputDeptName.getText() , inputDeptName);
		validator.validateTextWithNumbers("Department Code", inputDeptCode.getText() , inputDeptCode);
		validator.validateEmpty("Department Code", inputDeptCode.getText() , inputDeptCode);
		
		if(validator.hasError()){
			validator.showError();
			ImageView image = new ImageView(new NodeUtils.ImageGetter("error.png").getImage()) ;
			NotificationPane noti = (NotificationPane)FXMLDocumentController.getInstance().getTabpane().getSelectionModel().getSelectedItem().getContent() ;
			noti.show("An error occured during validation" , image);
			return ;
		}else{
			validator.hideError() ;
		}
		
		int nextPrimary = Connect.getNextIntegerPrimary("departments", "dept_id") ;
		Connect.emptyQUERY("INSERT INTO `departments` VALUES(" + nextPrimary + 
						   ",'" + inputDeptName.getText() + "' , '" + inputDeptCode.getText() + "')");
		inputDeptName.setText("");
		inputDeptCode.setText("");
		
		ImageView image = new ImageView(new NodeUtils.ImageGetter("check.png").getImage()) ;
		NotificationPane noti = (NotificationPane)FXMLDocumentController.getInstance().getTabpane().getSelectionModel().getSelectedItem().getContent() ;
		noti.show("Success" , image);
		
		FXMLDocumentController.updateTree();
		
		UpdateTree.expandTree();
		
		//Select the added
		UpdateTree.selectItem(Department.getItem(nextPrimary));
	}
	
	//Closing the Department
	@FXML public void removeDepartment(MouseEvent e){
		sc.start();
		
		//FXMLDocumentController.getInstance().getWorkplacePane().setCenter(null);
	
		//Validation.hideError();
	}
	
	public void initialize(URL url , ResourceBundle res){
		//sc = new Scheduler(1) ;
		new SimpleScheduler(1).start();
		System.out.println(TimeSlot.getTotalMinutes(Time.valueOf("7:00:00"), Time.valueOf("7:00:00"))) ;
	}
}
