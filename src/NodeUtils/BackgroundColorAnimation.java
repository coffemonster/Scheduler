package NodeUtils;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.Node;

public class BackgroundColorAnimation {
	
	private Node node ;
	
	public BackgroundColorAnimation(Node node){
		this.node = node ;
	}
	
	public void animate(String color , Long duration){
		Timeline bg = new Timeline(
                new KeyFrame(Duration.millis(0),
                        new KeyValue(node.styleProperty(), "-fx-background-color : white", Interpolator.EASE_OUT),
                        new KeyValue(node.opacityProperty(), 0, Interpolator.EASE_OUT)
                
                ),
                new KeyFrame(Duration.millis(duration),
                		new KeyValue(node.styleProperty() , "-fx-background-color : " + color , Interpolator.EASE_OUT) ,
                		new KeyValue(node.opacityProperty(), 1, Interpolator.EASE_OUT)
                ));
		bg.play();
	}
}
