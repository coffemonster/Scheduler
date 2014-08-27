package NodeUtils;

import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class ScaleAnimation implements NodeAnimation{
	
	private double fromX ;
	private double fromY ;
	private double toX ;
	private double toY ;
	private Duration d ;
	private int cycle ;
	private ScaleTransition scale ;
	
	public ScaleAnimation(ScaleAnimationProperty property) {
		this.fromX = property.getFromX() ;
		this.fromY = property.getFromY() ;
		this.toX = property.getToX() ;
		this.toY = property.getToX() ; 
		this.d = property.getD() ;
		this.cycle = property.getCycle() ;
		
		scale = new ScaleTransition();
	}
	
	public ScaleAnimation() {
		
	}

	public ScaleAnimation(double fromX, double toX, double fromY , double toY,
			Duration d, int cycle) {
		//Initialization
		this.fromX = fromX;
		this.fromY = fromY;
		this.toX = toX;
		this.toY = toY;
		this.d = d;
		this.cycle = cycle;
		//others
		scale = new ScaleTransition() ;
	}
	
	public void animate(Node e){
		scale.setAutoReverse(true);
		scale.setDuration(d);
		scale.setCycleCount(cycle);
		scale.setNode(e);
		scale.setFromX(fromX);
		scale.setFromY(fromY);
		scale.setToY(toY);
		scale.setToX(toX);
		scale.play();
	}

	public double getFromX() {
		return fromX;
	}

	public void setFromX(double fromX) {
		this.fromX = fromX;
	}

	public double getFromY() {
		return fromY;
	}

	public void setFromY(double fromY) {
		this.fromY = fromY;
	}

	public double getToX() {
		return toX;
	}

	public void setToX(double toX) {
		this.toX = toX;
	}

	public double getToY() {
		return toY;
	}

	public void setToY(double toY) {
		this.toY = toY;
	}

	public Duration getD() {
		return d;
	}

	public void setD(Duration d) {
		this.d = d;
	}

	public int getCycle() {
		return cycle;
	}

	public void setCycle(int cycle) {
		this.cycle = cycle;
	}
	
}
