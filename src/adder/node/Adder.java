package adder.node;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class Adder {
	
	public static void addToCenter(Node parent , Node child){
		try{
			BorderPane parentPane = (BorderPane)parent ;
			parentPane.setCenter(child);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
