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
		
		TreeItem<String> item = (TreeItemData)FXMLDocumentController.getInstance().getTree().getSelectionModel().getSelectedItem();
		Section sectionData = TreeItemData.getItemData(item) ;
		
		section.setText(sectionData.getSection());
		year.setText(sectionData.getYear().getYear() + "");
		
		if(FXMLDocumentController.getInstance().getRightAccordion().getExpandedPane() == null){
			FXMLDocumentController.getInstance().getRightAccordion().setExpandedPane(FXMLDocumentController.getInstance().getDetailsTitledPane());
		}
	}

}
