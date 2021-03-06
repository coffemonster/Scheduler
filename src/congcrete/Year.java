package congcrete;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import tree.TreeItemData;
import tree.UpdateTree;
import javafx.scene.control.TreeItem;
import database.Connect;

public class Year {
	private int year_id ;
	private int year ;
	private Course course ;
	private ArrayList<Section> sections ;
	
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
		sections = new ArrayList<Section>() ;
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
			e.printStackTrace();
		}
		return null ;
	}
	
	
	public static TreeItem<String> getItem(int year_id){
		Year year = Year.getYear(year_id) ;
		TreeItem<String> course = Course.getItem(year.getCourse().getD().getDept_id(), year.getCourse().getCourse_id()) ;
		for(int x = 0 ; x < course.getChildren().size() ; x++){
			TreeItem<String> yearItem = course.getChildren().get(x) ;
			Year yearData = TreeItemData.getItemData(yearItem) ;
			if(yearData.getYear_id() == year_id){
				return yearItem ;
			}
		}
		return null ;
	}
	
	public static ArrayList<Year> getYearList(int course_id){
		ResultSet result = Connect.QUERY("SELECT * FROM years WHERE course_id = " + course_id) ;
		ArrayList<Year> yearList = new ArrayList<Year>() ;
		try {
			while(result.next()){
				Year year = new Year() ;
				year.setYear_id(result.getInt(Year.YEAR_ID));
				year.setYear(result.getInt(Year.YEAR));
				Course course = Course.getCourse(result.getInt(Year.COURSE_ID)) ;
				year.setCourse(course);
				ArrayList<Section> sections = Section.getList(result.getInt(Year.YEAR_ID)) ;
				year.setSections(sections);
				
				yearList.add(year) ;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return yearList ;
	}



	public ArrayList<Section> getSections() {
		return sections;
	}



	public void setSections(ArrayList<Section> sections) {
		this.sections = sections;
	}
}
