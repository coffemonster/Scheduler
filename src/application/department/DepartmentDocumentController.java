package application.department;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import congcrete.Department;
import NodeUtils.NodeAnimation;
import NodeUtils.ScaleAnimation;
import application.main.FXMLDocumentController;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class DepartmentDocumentController implements Initializable{
	
	@FXML private TextField inputDeptName ;
	@FXML private TextField inputDeptCode ;
	@FXML private AnchorPane rootPane ;
	@FXML private Button finish ;
	
	//Handle , Adding in the DB
	@FXML public void handleAddDepartment(ActionEvent e) throws SQLException{
		int nextPrimary = Connect.getNextIntegerPrimary("departments", "dept_id") ;
		Connect.emptyQUERY("INSERT INTO `departments` VALUES(" + nextPrimary + 
						   ",'" + inputDeptName.getText() + "' , '" + inputDeptCode.getText() + "')");
		inputDeptName.setText("");
		inputDeptCode.setText("");
		
		FXMLDocumentController.updateTree();
		
		UpdateTree.expandTree();
		
		//Select the added
		UpdateTree.selectItem(Department.getItem(nextPrimary));
		
	}
	
	//Closing the Department
	@FXML public void removeDepartment(MouseEvent e){
		Platform.runLater(new Runnable(){
			public void run(){
				NodeAnimation animation = new ScaleAnimation() ;
				animation = new ScaleAnimation(1 , .1 , 1 , .1 , Duration.millis(200) , 1) ;
				animation.animate(rootPane);
			}
		});
		FXMLDocumentController.getWorkplacePane().setCenter(null);
	}
	
	public void initialize(URL url , ResourceBundle res){
		
	}
}
