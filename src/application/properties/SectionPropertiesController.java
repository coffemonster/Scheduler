package application.properties;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import congcrete.Section;
import database.Connect;
import tree.TreeItemData;
import application.main.FXMLDocumentController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;

public class SectionPropertiesController implements Initializable{

	@FXML private TextField section ;
	@FXML private TextField year ;
	@FXML private TextField txtNumberOfSubject ;
	@FXML private TextField txtTotalUnits ;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		TreeItem<String> item = (TreeItemData)FXMLDocumentController.getInstance().getTree().getSelectionModel().getSelectedItem();
		Section sectionData = TreeItemData.getItemData(item) ;
		
		section.setText(sectionData.getSection());
		year.setText(sectionData.getYear().getYear() + "");
		
		if(FXMLDocumentController.getInstance().getRightAccordion().getExpandedPane() == null){
			FXMLDocumentController.getInstance().getRightAccordion().setExpandedPane(FXMLDocumentController.getInstance().getDetailsTitledPane());
		}
		
		ResultSet result = Connect.QUERY("SELECT COUNT(Sub.subject_id) AS num FROM sections S" +
										" JOIN classes C ON C.section_id = S.section_id" +
										" JOIN subjects Sub ON Sub.subject_id = C.subject_id" +
										" WHERE S.section_id = " + sectionData.getSection_id()) ;
		try {
			result.next() ;
			txtNumberOfSubject.setText(result.getInt("num") + "");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		result = Connect.QUERY("SELECT SUM(Sub.subject_unit) AS num FROM sections S" +
				" JOIN classes C ON C.section_id = S.section_id" +
				" JOIN subjects Sub ON Sub.subject_id = C.subject_id" +
				" WHERE S.section_id = " + sectionData.getSection_id()) ;
		try {
			result.next() ;
			txtTotalUnits.setText(result.getInt("num") + "");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
	}

}
