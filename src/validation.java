import java.util.regex.Pattern;

import javafx.scene.Node;


public class validation {
	
	public validation(){
		
	}
	
	public boolean validateTextOnly(String text){
		Pattern p = Pattern.compile("[a-zA-Z]") ;
		if(p.matcher(text).find()){
			return true;
		}else{
			return false ;
		}
	}
	
}
