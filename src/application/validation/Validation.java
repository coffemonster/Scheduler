package application.validation;
import java.awt.Paint;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.regex.Pattern;

import database.Connect;
import NodeUtils.BackgroundColorAnimation;
import NodeUtils.ShakeAnimation;
import application.main.FXMLDocumentController;
import application.priority.PriorityTime;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;


public class Validation {
	
	private ArrayList<String> errorList ;
	private ArrayList<Node> nodeList ;
	
	public Validation(){
		errorList = new ArrayList<String>() ;
		nodeList = new ArrayList<Node>() ;
	}
	
	public boolean validateTextOnly(String container , String text , Node holder){
		text = text.trim() ;
		if(!text.matches("^[a-zA-Z\\s]*$")){
			String error = container + " field contains illegal characters or numbers" ;
			errorList.add(error) ;
			nodeList.add(holder) ;
			return false ;
		}
		return true ;
	}
	
	public boolean validateTextWithNumbers(String container , String text , Node holder){
		text = text.trim() ;
		if(!text.matches("^[a-zA-Z0-9\\s]*$")){
			String error = container + " field contains illegal characters" ;
			errorList.add(error) ;
			nodeList.add(holder) ;
			return false; 
		}
		return true; 
	}
	
	public boolean validateEmpty(String container , String text, Node holder){
		if(text.trim().length() == 0){
			String error = container + " field is required" ;
			errorList.add(error) ;
			nodeList.add(holder) ;
			return false ;
		}
		return true ;
	}
	
	public boolean validateChoiceBox(String container , ChoiceBox holder){
		if(holder.getSelectionModel().getSelectedItem() == null){
			String error = container + " field is required" ;
			errorList.add(error) ;
			nodeList.add(holder) ;
			return false ;
		}
		return true ;
	}
	
	public boolean hasError(){
		if(errorList.size() == 0){
			return false; 
		}else{
			return true ;
		}
	}
	
	public boolean validateMiddleInitial(String container , String text , Node holder){
		if(text.trim().length() != 1 && text.trim().length() != 0){
			String error = container + " contains more than one character" ;
			errorList.add(error) ;
			nodeList.add(holder) ;
			return false ;
		}
		return true ;
	}
	
	public boolean validatePicture(File picture , Node holder){
		if(picture == null){
			String error = "Please select an image" ;
			errorList.add(error) ;
			nodeList.add(holder) ;
			return false ;
		}
		return true ;
	}
	
	public boolean validateNumberOnly(String container , String text, Node holder){
		if(!text.trim().matches("^[0-9]*$") && text.trim().length() != 0){
			String error = container +  " must be a number" ;
			errorList.add(error) ;
			nodeList.add(holder) ;
			return false ;
		}
		return true ;
	}
	
	public boolean validateHour(String container , String text, Node holder){
		int hour = Integer.parseInt(text) ;
		if(hour > 12){
			String error = container +  " time error" ;
			errorList.add(error) ;
			nodeList.add(holder) ;
			return false ;
		}
		return true ;
	}
	
	public boolean validateMinute(String container , String text, Node holder){
		int hour = Integer.parseInt(text) ;
		if(hour > 60){
			String error = container +  " time error" ;
			errorList.add(error) ;
			nodeList.add(holder) ;
			return false ;
		}
		return true ;
	}
	
	public boolean validateDuplicateRecordsPriorities(String fieldName , String value , int teacher_id){
		ResultSet result = Connect.QUERY("SELECT * FROM `priorities` WHERE day = '" + value + "' AND teacher_id = " + teacher_id);
		try {
			result.next() ;
			if(result.getRow() != 0){
				return false ;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true ;
	}
	
	public void showError(){
		ListView errorView = new ListView() ;
		errorView.maxHeight(100) ;
		errorView.setPrefHeight(100);
		errorView.autosize();
		errorView.getCellFactory() ;
		for(int x = 0 ; x < errorList.size() ; x++){
			errorView.getItems().add("Error : " + errorList.get(x)) ;
		}
		FXMLDocumentController.getInstance().getWorkplacePane().setBottom(errorView);
		errorView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Object>(){

			@Override
			public void changed(ObservableValue<? extends Object> observable,
					Object oldValue, Object newValue) {
				
				Node active = nodeList.get(errorView.getSelectionModel().getSelectedIndex());	
				
				
				/*
				ShakeAnimation shake = new ShakeAnimation(active) ;
				shake.animate();
				*/
				
			}
			
		});
		
	}
	
	public static void hideError(){
		FXMLDocumentController.getInstance().getWorkplacePane().setBottom(null);
	}
}
