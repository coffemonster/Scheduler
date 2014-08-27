package congcrete;

public class Year {
	private int year ;
	private Course course ;
	public int getYear() {
		return year;
	}
	
	
	
	public Year(int year, Course course) {
		super();
		this.year = year;
		this.course = course;
	}

	public void setYear(int year) {
		this.year = year;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	
	
	
}
