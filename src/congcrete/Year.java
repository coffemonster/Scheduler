package congcrete;

public class Year {
	private int year ;
	private Course course ;
	public static final String YEAR = "year" ;
	public static final String COURSE_ID = "course_id" ;
	
	
	public int getYear() {
		return year;
	}
	
	
	
	public Year(int year, Course course) {
		super();
		this.year = year;
		this.course = course;
	}

	public Year() {
		// TODO Auto-generated constructor stub
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
	
	public static String getYearText(int year){
		if(year == 1){
			return "First Year" ;
		}else if(year == 2){
			return "Second Year" ;
		}else if(year == 3){
			return "Third Year" ;
		}else if(year == 4){
			return "Fourth Year" ;
		}else if(year == 5){
			return "Fifth Year" ;
		}else if(year == 6){
			return "Sixth Year" ;
		}else if(year == 7){
			return "Seventh Year" ;
		}else if(year == 8){
			return "Eight Year" ;
		}
		return "Max" ;
	}
	
}
