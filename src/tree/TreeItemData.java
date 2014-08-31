package tree;

import congcrete.Department;
import congcrete.Teacher;
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
	
	public static <T> T getItemData(TreeItem<String> item){
		TreeItemData dept = (TreeItemData)item ;
		@SuppressWarnings("unchecked")
		T data = (T)dept.getData() ;
		return data ;
	}
}
