package tree;

import javafx.scene.Node;
import javafx.scene.control.TreeItem;

public class TreeItemData extends TreeItem<String>{
	
	private Object data ;
	
	public TreeItemData(String name , Node n , Object data){
		super(name , n) ;
		this.data = data ;
	}
	
	public Object getData(){
		return data ;
	}
}
