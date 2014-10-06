package application.priority;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.controlsfx.control.NotificationPane;

import tree.TreeItemData;
import congcrete.Teacher;
import database.Connect;
import application.main.FXMLDocumentController;
import application.validation.Validation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class PriorityDocumentController implements Initializable{

	@FXML private ChoiceBox day ;
	@FXML private TextField fromHours ;
	@FXML private TextField fromMinutes ;
	@FXML private ChoiceBox fromType ;
	@FXML private Label name ;
	private Teacher teacher ;
	private ArrayList<String> days = new ArrayList<String>() ;
	
	@FXML public void handleAddPriority(ActionEvent e){
		String toTimeMilitary = null ;
		String fromTimeMilitary = null ;
		
		//Trimming
		fromHours.setText(fromHours.getText().trim());
		fromMinutes.setText(fromMinutes.getText().trim());
		
		//Validation 
		Validation validator = new Validation() ;
		validator.validateChoiceBox("Day", day) ;
		boolean empty = validator.validateEmpty("Hour(s) in From", fromHours.getText(), fromHours) ;
		boolean number = validator.validateNumberOnly("Hours(s) in From", fromHours.getText(), fromHours) ;
		
		if(empty && number){
			validator.validateHour("Hour(s) in From", fromHours.getText(), fromHours) ;
		}
		
		validator.validateChoiceBox("AM/PM in From", fromType) ;

		if(validator.hasError()){
			validator.showError();
			ImageView image = new ImageView(new NodeUtils.ImageGetter("error.png").getImage()) ;
			NotificationPane noti = (NotificationPane)FXMLDocumentController.getInstance().getTabpane().getSelectionModel().getSelectedItem().getContent() ;
			noti.show("An error occured during validation" , image);
			return ;
		}else{
			validator.hideError();
		}
		
		//TODO validate if time is sufficient
		
		int nextPrimary = Connect.getNextIntegerPrimary("priorities", "priority_id") ;
		String dayString = day.getSelectionModel().getSelectedItem().toString() ;
		
		boolean duplicate = validator.validateDuplicateRecordsPriorities("day", dayString , teacher.getTeacher_id()) ;
		
		PriorityTime fromPriority = new PriorityTime(fromHours.getText() + ":" + fromMinutes.getText() + ":00") ;
		
		if(!duplicate){
			ResultSet result = Connect.QUERY("SELECT priority_id FROM priorities WHERE teacher_id = "+ teacher.getTeacher_id() +" AND day = '"+ dayString +"'");
			int priority_id = 0 ;
			try {
				result.next();
				priority_id = result.getInt(1) ;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			Connect.emptyQUERY("UPDATE priorities SET `from` = '"+fromPriority.getTime()+"' WHERE `priority_id` =" + priority_id);
		}else{
			Connect.emptyQUERY("INSERT INTO priorities(`priority_id`,`teacher_id`,`day`,`from`) VALUES("+ nextPrimary +","+ teacher.getTeacher_id() +",'"+ dayString +"','"+fromPriority.getTime() +"')");
		}
		
		validator.hideError(); 
		
		ImageView image = new ImageView(new NodeUtils.ImageGetter("check.png").getImage()) ;
		NotificationPane noti = (NotificationPane)FXMLDocumentController.getInstance().getTabpane().getSelectionModel().getSelectedItem().getContent() ;
		noti.show("Success" , image);
		
		//empty fields
		fromHours.clear();
		fromMinutes.clear();
		fromType.getSelectionModel().clearSelection();
		
	}
		
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		TreeItemData item =  (TreeItemData) arg1.getObject("item") ;
		teacher = TreeItemData.getItemData(item) ;
		
		name.setText(teacher.getLast_name() + " , " + teacher.getFirst_name() + " " + teacher.getMiddle_initial() + ".") ; 
		
		days.clear();
		days.add("Monday") ;
		days.add("Tuesday") ;
		days.add("Wednesday") ;
		days.add("Thursday") ;
		days.add("Friday") ;
		days.add("Saturday") ;
		days.add("Sunday") ;
		
		day.getItems().clear();
		for(int x = 0 ; x < days.size() ; x++){
			day.getItems().add(days.get(x)) ;
		}
		
		fromType.getItems().add("AM") ;
		fromType.getItems().add("PM") ;
		
	}
}
