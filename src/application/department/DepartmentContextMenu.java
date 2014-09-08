package application.department;

import NodeUtils.ImageGetter;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;

public class DepartmentContextMenu extends ContextMenu{
	private MenuItem addCourse ;
	private MenuItem addTeacher ;
	private MenuItem addRoom ;
	
	public DepartmentContextMenu(){
		super() ;
		
		//Setting up menuitem
		Node img = new ImageView(new ImageGetter("course.png").getImage()) ;
		addCourse = new MenuItem("Add Course" , img) ;
		
		
		
		getItems().add(addCourse) ;
	}
}
