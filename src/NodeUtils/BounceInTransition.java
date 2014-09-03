package NodeUtils;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.util.Duration;

public class BounceInTransition {
	public BounceInTransition(Node node){
		Timeline bounce = new Timeline(
                new KeyFrame(Duration.millis(0),
                        new KeyValue(node.opacityProperty(), 0, Interpolator.EASE_OUT),
                        new KeyValue(node.scaleXProperty(), 0.3, Interpolator.EASE_OUT),
                        new KeyValue(node.scaleYProperty(), 0.3, Interpolator.EASE_OUT),
                        new KeyValue(node.rotateProperty() , 300 , Interpolator.EASE_OUT)
                ),
                new KeyFrame(Duration.millis(400),
                        new KeyValue(node.opacityProperty(), 1, Interpolator.EASE_OUT),
                        new KeyValue(node.scaleXProperty(), 1.05, Interpolator.EASE_OUT),
                        new KeyValue(node.scaleYProperty(), 1.05, Interpolator.EASE_OUT),
                        new KeyValue(node.rotateProperty() , 360 , Interpolator.EASE_OUT)
                ),
                new KeyFrame(Duration.millis(600),
                        new KeyValue(node.scaleXProperty(), .9, Interpolator.EASE_OUT),
                        new KeyValue(node.scaleYProperty(), .9, Interpolator.EASE_OUT)
                ),
                new KeyFrame(Duration.millis(800),
                        new KeyValue(node.scaleXProperty(), 1.02, Interpolator.EASE_OUT),
                        new KeyValue(node.scaleYProperty(), 1.02, Interpolator.EASE_OUT)
                ),
                new KeyFrame(Duration.millis(900),
                        new KeyValue(node.scaleXProperty(), .98, Interpolator.EASE_OUT),
                        new KeyValue(node.scaleYProperty(), .98, Interpolator.EASE_OUT)
                ),
                new KeyFrame(Duration.millis(1100),
                        new KeyValue(node.scaleXProperty(), 1, Interpolator.EASE_OUT),
                        new KeyValue(node.scaleYProperty(), 1, Interpolator.EASE_OUT)
                ),
                new KeyFrame(Duration.millis(1060),
                        new KeyValue(node.scaleXProperty(), 1.02, Interpolator.EASE_OUT),
                        new KeyValue(node.scaleYProperty(), 1.02, Interpolator.EASE_OUT)
                ),
                new KeyFrame(Duration.millis(1120),
                        new KeyValue(node.scaleXProperty(), 1, Interpolator.EASE_OUT),
                        new KeyValue(node.scaleYProperty(), 1, Interpolator.EASE_OUT)
                ),
                new KeyFrame(Duration.millis(1150),
                        new KeyValue(node.scaleXProperty(), 1.01, Interpolator.EASE_OUT),
                        new KeyValue(node.scaleYProperty(), 1.01, Interpolator.EASE_OUT)
                ),
                new KeyFrame(Duration.millis(1190),
                        new KeyValue(node.scaleXProperty(), 1, Interpolator.EASE_OUT),
                        new KeyValue(node.scaleYProperty(), 1, Interpolator.EASE_OUT)
                ));
		bounce.play();
	}
}
