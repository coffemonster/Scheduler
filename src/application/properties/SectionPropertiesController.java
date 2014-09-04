package application.properties;

import java.net.URL;
import java.util.ResourceBundle;

import congcrete.Section;
import tree.TreeItemData;
import application.main.FXMLDocumentController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;

public class SectionPropertiesController implements Initializable{

	@FXML TextField section ;
	@FXML TextField year ;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		TreeItem<String> item = (TreeItemData)FXMLDocumentController.getTree().getSelectionModel().getSelectedItem();
		Section sectionData = TreeItemData.getItemData(item) ;
		
		section.setText(sectionData.getSection());
		year.setText(sectionData.getYear().getYear() + "");
		
		if(FXMLDocumentController.getRightAccordion().getExpandedPane() == null){
			FXMLDocumentController.getRightAccordion().setExpandedPane(FXMLDocumentController.getDetailsTitledPane());
		}
	}

}
