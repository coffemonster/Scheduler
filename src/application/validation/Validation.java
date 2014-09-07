package application.validation;
import java.util.ArrayList;
import java.util.regex.Pattern;

import NodeUtils.ShakeAnimation;
import application.main.FXMLDocumentController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;


public class Validation {
	
	private ArrayList<String> errorList ;
	private ArrayList<Node> nodeList ;
	
	public Validation(){
		errorList = new ArrayList<String>() ;
		nodeList = new ArrayList<Node>() ;
	}
	
	public void validateTextOnly(String container , String text , Node holder){
		text = text.trim() ;
		if(!text.matches("^[a-zA-Z]*$")){
			String error = container + " field contains illegal characters or numbers" ;
			errorList.add(error) ;
			nodeList.add(holder) ;
		}
	}
	
	public void validateTextWithNumbers(String container , String text , Node holder){
		text = text.trim() ;
		if(!text.matches("^[a-zA-Z0-9]*$")){
			String error = container + " field contains illegal characters" ;
			errorList.add(error) ;
			nodeList.add(holder) ;
		}
	}
	
	public void validateEmpty(String container , String text, Node holder){
		if(text.trim().length() == 0){
			String error = container + " field is required" ;
			errorList.add(error) ;
			nodeList.add(holder) ;
		}
	}
	
	public void validateChoiceBox(String container , ChoiceBox holder){
		if(holder.getSelectionModel().getSelectedItem() == null){
			String error = container + " field is required" ;
			errorList.add(error) ;
			nodeList.add(holder) ;
		}
	}
	
	public boolean hasError(){
		if(errorList.size() == 0){
			return false; 
		}else{
			return true ;
		}
	}
		
	public void showError(){
		ListView errorView = new ListView() ;
		errorView.maxHeight(100) ;
		errorView.setPrefHeight(100);
		errorView.autosize();
		errorView.getCellFactory() ;
		for(int x = 0 ; x < errorList.size() ; x++){
			errorView.getItems().add(errorList.get(x)) ;
		}
		FXMLDocumentController.getWorkplacePane().setBottom(errorView);
		errorView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Object>(){

			@Override
			public void changed(ObservableValue<? extends Object> observable,
					Object oldValue, Object newValue) {
				Node active = nodeList.get(errorView.getSelectionModel().getSelectedIndex());
				ShakeAnimation shake = new ShakeAnimation(active) ;
				shake.animate();
			}
			
		});
		
	}
}
