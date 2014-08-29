package congcrete;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Connect;

public class Year {
	private int year_id ;
	private int year ;
	private Course course ;
	public static final String YEAR_ID = "year_id" ;
	public static final String YEAR = "year" ;
	public static final String COURSE_ID = "course_id" ;
	
	
	public int getYear() {
		return year;
	}
	
	
	
	public Year(int year_id , int year, Course course) {
		this.year = year;
		this.course = course;
	}

	public Year() {
		// TODO Auto-generated constructor stub
	}

	

	public int getYear_id() {
		return year_id;
	}



	public void setYear_id(int year_id) {
		this.year_id = year_id;
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
	
	public static ArrayList<Year> getList(int course_id){
		ResultSet result = Connect.QUERY("SELECT * FROM `years` WHERE course_id = " + course_id) ;
		ArrayList<Year> list = new ArrayList<Year>() ;
		
		try {
			while(result.next()){
				Year year = new Year() ;
				year.setYear_id(result.getInt(Year.YEAR_ID));
				year.setYear(result.getInt(Year.YEAR));
				Course course = Course.getCourse(result.getInt(Year.COURSE_ID)) ;
				year.setCourse(course);
				list.add(year) ;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list ;
	}
	
	public static Year getYear(int year_id){
		ResultSet result = Connect.QUERY("SELECT * FROM years WHERE year_id = " + year_id) ;
		try {
			while(result.next()){
				Year year = new Year() ;
				year.setYear_id(result.getInt(Year.YEAR_ID));
				year.setYear(result.getInt(Year.YEAR));
				Course course = new Course() ;
				course = Course.getCourse(result.getInt(Year.COURSE_ID)) ;
				year.setCourse(course);
				return year ;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null ;
	}
	
}
