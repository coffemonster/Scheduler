package NodeUtils;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.util.Duration;

public class ShakeAnimation {
	
	private Node node ;
	
	public ShakeAnimation(Node node){
		this.node = node ;
	}
	
	public void animate(){
		Timeline bounce = new Timeline(
                new KeyFrame(Duration.millis(0),
                        new KeyValue(node.rotateProperty(), 0, Interpolator.EASE_OUT)
                ),
                new KeyFrame(Duration.millis(100),
                        new KeyValue(node.rotateProperty(), 2, Interpolator.EASE_OUT)
                ),
                new KeyFrame(Duration.millis(200),
                        new KeyValue(node.rotateProperty(), 0, Interpolator.EASE_OUT)
                ),
                new KeyFrame(Duration.millis(300),
                        new KeyValue(node.rotateProperty(), 2, Interpolator.EASE_OUT)
                ),
                new KeyFrame(Duration.millis(400),
                        new KeyValue(node.rotateProperty(), 0, Interpolator.EASE_OUT)
                ));
		bounce.play();
	}
	
}
