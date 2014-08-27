package NodeUtils;

import javafx.scene.image.Image;

public class ImageGetter {
	String loc ;
	public ImageGetter(String loc){
		this.loc = loc ;
	}
	
	public Image getImage(){
		return new Image(getClass().getResourceAsStream("/images/" + loc)) ;
	}
}
