package congcrete;

public class ColorInator{
	public int start ;
	public int stop ;
	public String color ;
	public String day ;
	
	public ColorInator(int start , int stop , String color , String day){
		this.start = start ;
		this.stop = stop ;
		this.color = color ;
		this.day = day ;
	}
	
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getStop() {
		return stop;
	}

	public void setStop(int stop) {
		this.stop = stop;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	
}
