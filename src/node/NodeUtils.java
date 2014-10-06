package node;


import org.controlsfx.control.NotificationPane;

import application.main.FXMLDocumentController;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class NodeUtils {
	public static void addToCenter(Node child , String text){
		try{
			TabPane parentPane = FXMLDocumentController.getInstance().getTabpane() ;
			parentPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
			Tab tab = new Tab(text) ;
			BorderPane container = new BorderPane(child) ;
			//not.set
			NotificationPane not = new NotificationPane(container) ;
			container.setOnMouseClicked(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent arg0) {
					not.hide();
				}
			});
			tab.setContent(not);
			tab.setClosable(true);
			parentPane.getTabs().add(tab) ;
			parentPane.getSelectionModel().select(tab);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}

}
