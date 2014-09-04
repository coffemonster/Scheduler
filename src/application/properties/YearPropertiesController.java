package application.properties;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import congcrete.Year;
import database.Connect;
import tree.TreeItemData;
import application.main.FXMLDocumentController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;

public class YearPropertiesController implements Initializable{

	@FXML private TextField year ;
	@FXML private TextField numSections ;
	@FXML private TextField numSubjects ;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		TreeItem<String> item = (TreeItemData)FXMLDocumentController.getTree().getSelectionModel().getSelectedItem() ;
		Year yearData = TreeItemData.getItemData(item) ;
		
		year.setText(yearData.getYear() + "");
		
		//SET the num of Sections and Subjects
		try {
			ResultSet result = Connect.QUERY("SELECT COUNT(*) AS NUM FROM sections WHERE year_id = " + yearData.getYear_id()); 
			result.next() ;
			numSections.setText(result.getInt("NUM") + "");
			result = Connect.QUERY("SELECT COUNT(*) AS NUM FROM subjects WHERE year_id = " + yearData.getYear_id()); 
			result.next() ;
			numSubjects.setText(result.getInt("NUM") + "");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(FXMLDocumentController.getRightAccordion().getExpandedPane() == null){
			FXMLDocumentController.getRightAccordion().setExpandedPane(FXMLDocumentController.getDetailsTitledPane());
		}
		
	}
	
}
