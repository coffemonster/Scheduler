package application.menu;

import java.io.IOException;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.Vector;

import node.NodeUtils;
import congcrete.Department;
import congcrete.Section;
import congcrete.Teacher;
import database.Connect;
import tree.TreeItemData;
import application.main.FXMLDocumentController;
import application.scheduler.Scheduler;
import NodeUtils.BounceInTransition;
import NodeUtils.ImageGetter;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class TeacherContextMenu extends ContextMenu{
	
	private MenuItem setPriorities ;
	private MenuItem viewSchedule ;
	private MenuItem delete ;
	
	public TeacherContextMenu(){
		super() ;
		
		ImageView image = new ImageView(new ImageGetter("medical50.png").getImage()) ;
		ImageView viewScheduleImage = new ImageView(new ImageGetter("calendar53.png").getImage()) ;
		
		setPriorities = new MenuItem("Set Priorities" , image) ;
		viewSchedule = new MenuItem("View Schedule" , viewScheduleImage) ;
		
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
					
					TreeItem<String> obj = (TreeItem<String>) FXMLDocumentController.getInstance().getTree().getSelectionModel().getSelectedItem() ; 
					Teacher teacher = TreeItemData.getItemData(obj) ;
					
					NodeUtils.addToCenter(priorityPane , teacher.getFirst_name() + " " + teacher.getLast_name() + " Priorities");
					
					
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
		
		viewSchedule.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				
				TreeItem<String> item = (TreeItemData) FXMLDocumentController.getInstance().getTree().getSelectionModel().getSelectedItem() ;
				Teacher teacher = TreeItemData.getItemData(item) ;
				Scheduler.viewTeacherSchedule(teacher);
		
			}
			
		});
		
		ImageView imageT = new ImageView(new ImageGetter("delete23.png").getImage()) ;
		delete = new MenuItem("Delete" , imageT) ;
		
		delete.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				TreeItem<String> treeItem = (TreeItem<String>) FXMLDocumentController.getInstance().getTree().getSelectionModel().getSelectedItem() ;
				Teacher teacherData = TreeItemData.getItemData(treeItem) ;
				Connect.emptyQUERY("DELETE FROM teachers WHERE teacher_id = " + teacherData.getTeacher_id());
				FXMLDocumentController.updateTree();
				TreeItem<String> dept = Department.getItem(teacherData.getD().getDept_id()) ;
				FXMLDocumentController.getInstance().getTree().getSelectionModel().select(dept.getChildren().get(0)) ;
			}
		});
		
		getItems().add(delete) ;
		getItems().add(setPriorities);
		getItems().add(viewSchedule) ;
	}
}
