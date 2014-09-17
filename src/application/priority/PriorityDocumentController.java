package application.priority;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
import javafx.scene.input.MouseEvent;

public class PriorityDocumentController implements Initializable{

	@FXML private ChoiceBox day ;
	@FXML private TextField fromHours ;
	@FXML private TextField fromMinutes ;
	@FXML private TextField toHours ;
	@FXML private TextField toMinutes ;
	@FXML private ChoiceBox fromType ;
	@FXML private ChoiceBox toType ;
	@FXML private Label name ;
	private Teacher teacher ;
	private ArrayList<String> days = new ArrayList<String>() ;
	private ArrayList<String> types = new ArrayList<String>() ;
	private PriorityTime fromPriority ;
	private PriorityTime toPriority ;
	
	
	@FXML public void handleAddPriority(ActionEvent e){
		String toTimeMilitary = null ;
		String fromTimeMilitary = null ;
		
		//Trimming
		fromHours.setText(fromHours.getText().trim());
		toHours.setText(toHours.getText().trim());
		fromMinutes.setText(fromMinutes.getText().trim());
		toMinutes.setText(toMinutes.getText().trim());
		
		//Validation 
		Validation validator = new Validation() ;
		validator.validateChoiceBox("Day", day) ;
		boolean empty = validator.validateEmpty("Hour(s) in From", fromHours.getText(), fromHours) ;
		boolean number = validator.validateNumberOnly("Hours(s) in From", fromHours.getText(), fromHours) ;
		
		if(empty && number){
			validator.validateHour("Hour(s) in From", fromHours.getText(), fromHours) ;
		}
		
		validator.validateChoiceBox("AM/PM in From", fromType) ;
		
		empty = validator.validateEmpty("Minute(s) in To", fromMinutes.getText(), fromMinutes) ;
		number = validator.validateNumberOnly("Minute(s) in From", fromMinutes.getText(), fromMinutes) ;
		
		if(empty && number){
			validator.validateMinute("Minute(s) in To", fromMinutes.getText(), fromMinutes) ;
		}
		
		validator.validateChoiceBox("AM/PM in From", fromType) ;
		validator.validateChoiceBox("AM/PM in To", toType) ;
		
		if(!validator.hasError()){
			int from = Integer.parseInt(fromHours.getText()) ;
			if((fromType.getSelectionModel().getSelectedIndex() == 1 && from != 12) || (fromType.getSelectionModel().getSelectedIndex() == 0 && from == 12)){
				from += 12 ;
			}
			fromPriority = new PriorityTime(from + ":" + fromMinutes.getText() + ":00") ;
			
			int to = Integer.parseInt(toHours.getText()) ;
			if((toType.getSelectionModel().getSelectedIndex() == 1 && to != 12) || (toType.getSelectionModel().getSelectedIndex() == 0 && to == 12)){
				from += 12 ;
			}
			toPriority = new PriorityTime(to + ":" + toMinutes.getText() + ":00" ) ; 
			
			validator.validateTimeSpan("From and To", fromPriority, toPriority) ;
		}
		
		if(validator.hasError()){
			validator.showError();
			return ;
		}
		
		//TODO validate if time is sufficient
		//TODO specify if its a restriction or priority
		
		int nextPrimary = Connect.getNextIntegerPrimary("priorities", "priority_id") ;
		String dayString = day.getSelectionModel().getSelectedItem().toString() ;
		
		boolean duplicate = validator.validateDuplicateRecordsPriorities("day", dayString , teacher.getTeacher_id()) ;
		
		if(!duplicate){
			ResultSet result = Connect.QUERY("SELECT priority_id FROM priorities WHERE teacher_id = "+ teacher.getTeacher_id() +" AND day = '"+ dayString +"'");
			int priority_id = 0 ;
			try {
				result.next();
				priority_id = result.getInt(1) ;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			Connect.emptyQUERY("UPDATE priorities SET `from` = '"+fromPriority.getTime()+"',`to`='"+toPriority.getTime()+"' WHERE `priority_id` =" + priority_id);
		}else{
			Connect.emptyQUERY("INSERT INTO priorities(`priority_id`,`teacher_id`,`day`,`from`,`to`) VALUES("+ nextPrimary +","+ teacher.getTeacher_id() +",'"+ dayString +"','"+fromPriority.getTime() +"','"+toPriority.getTime()+"')");
		}
		
		Validation.hideError(); 
		
		//empty fields
		fromHours.clear();
		toHours.clear();
		fromMinutes.clear();
		toMinutes.clear();
		fromType.getSelectionModel().clearSelection();
		toType.getSelectionModel().clearSelection();
		
	}
	
	@FXML public void removePriority(MouseEvent e){
		FXMLDocumentController.getInstance().getWorkplacePane().setCenter(null);
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
		
		types.clear(); 
		types.add("AM") ;
		types.add("PM") ;
		
		toType.getItems().clear();
		fromType.getItems().clear();
		for(int x = 0 ; x < types.size() ; x++){
			fromType.getItems().add(types.get(x)) ;
			toType.getItems().add(types.get(x)) ;
		}
	}
}
