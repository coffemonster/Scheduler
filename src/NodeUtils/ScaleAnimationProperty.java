package NodeUtils;

import javafx.util.Duration;

public class ScaleAnimationProperty {
	private double fromX ;
	private double toX ;
	private double fromY ;
	private double toY ;
	private Duration d ;
	private int cycle ;
	
	public ScaleAnimationProperty(double fromX, double toX, double fromY,
			double toY, Duration d, int cycle) {
		super();
		this.fromX = fromX;
		this.toX = toX;
		this.fromY = fromY;
		this.toY = toY;
		this.d = d;
		this.cycle = cycle;
	}

	public double getFromX() {
		return fromX;
	}

	public void setFromX(double fromX) {
		this.fromX = fromX;
	}

	public double getToX() {
		return toX;
	}

	public void setToX(double toX) {
		this.toX = toX;
	}

	public double getFromY() {
		return fromY;
	}

	public void setFromY(double fromY) {
		this.fromY = fromY;
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
