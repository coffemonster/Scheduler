package application.menu;

import java.io.IOException;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.Vector;

import application.main.FXMLDocumentController;
import NodeUtils.BounceInTransition;
import NodeUtils.ImageGetter;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class TeacherContextMenu extends ContextMenu{
	
	private MenuItem setPriorities ;
	
	public TeacherContextMenu(){
		super() ;
		
		ImageView image = new ImageView(new ImageGetter("medical50.png").getImage()) ;
		
		setPriorities = new MenuItem("Set Priorities" , image) ;
		setPriorities.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				FXMLLoader loader = new FXMLLoader() ;
				loader.setResources(new ResourceBundle(){

					@Override
					public Enumeration<String> getKeys() {
						Vector list = new Vector() ;
						list.add("item") ;
						list.add("type") ;
						return (Enumeration<String>) list ;
					}

					@Override
					protected Object handleGetObject(String key) {
						if(key.equals("item")){
							return FXMLDocumentController.getInstance().getTree().getSelectionModel().getSelectedItem() ;
						}else{
							return "aw" ;
						}
					}
					
				});
				
				loader.setLocation(getClass().getResource("/application/priority/priorityDocument.fxml"));
				try {
					AnchorPane priorityPane = loader.load() ;
					priorityPane.setStyle("-fx-background-color : white ");
					node.NodeUtils.addToCenter(FXMLDocumentController.getInstance().getWorkplacePane(), priorityPane);
					
					Platform.runLater(new Runnable(){

						@Override
						public void run() {
							new BounceInTransition(priorityPane) ;
							
						}
						
					});
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		});
		
		getItems().add(setPriorities);
	}
}
