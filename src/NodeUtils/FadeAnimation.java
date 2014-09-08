package NodeUtils;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class FadeAnimation {
	
	private Node node ;
	private FadeTransition fade ;
	
	public FadeAnimation(Node node){
		this.node = node ;
		fade = new FadeTransition() ;
	}
	
	public void animate(Duration d , boolean autoreverse){
		fade.setDuration(d);
		fade.setAutoReverse(autoreverse);
		fade.setFromValue(0);
		fade.setToValue(1);
		fade.setNode(node);
		fade.play();
	}
}
