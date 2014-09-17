package congcrete;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.TreeItem;
import tree.TreeItemData;
import database.Connect;

public class Course {
	private int course_id ;
	private Department d ;
	private String course_name ;
	private String course_code ;
	public static final String COURSE_ID = "course_id" ;
	public static final String DEPT_ID = "dept_id" ;
	public static final String COURSE_NAME = "course_name" ;
	public static final String COURSE_CODE = "course_code" ;
	
	public Course(int course_id, Department d, String course_name,
			String course_code) {
		super();
		this.course_id = course_id;
		this.d = d;
		this.course_name = course_name;
		this.course_code = course_code;
	}

	public Course() {
	
	}

	public int getCourse_id() {
		return course_id;
	}

	public void setCourse_id(int course_id) {
		this.course_id = course_id;
	}

	public Department getD() {
		return d;
	}

	public void setD(Department d) {
		this.d = d;
	}

	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	public String getCourse_code() {
		return course_code;
	}

	public void setCourse_code(String course_code) {
		this.course_code = course_code;
	}
	
	public static ArrayList<Course> getList(int dept_id){
		ResultSet result = Connect.QUERY("SELECT * FROM `courses` WHERE `dept_id` = " + dept_id) ;
		ArrayList<Course> index = new ArrayList<Course>() ;
		try {
			while(result.next()){
				Course course = new Course() ;
				course.setCourse_id(result.getInt(Course.COURSE_ID));
				course.setCourse_name(result.getString(Course.COURSE_NAME));
				course.setCourse_code(result.getString(Course.COURSE_CODE));
				Department d = Department.getDepartment(result.getInt(Course.DEPT_ID)) ;
				course.setD(d);
				index.add(course) ;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return index ;
		
	}
	
	public static Course getCourse(int course_id){
		ResultSet result = Connect.QUERY("SELECT * FROM courses WHERE course_id = " + course_id);
		Course course = new Course() ;
		try {
			result.next() ;
			course.setCourse_id(result.getInt(Course.COURSE_ID)) ;
			Department d = Department.getDepartment(result.getInt(Course.DEPT_ID)) ;
			course.setD(d) ;
			course.setCourse_code(result.getString(Course.COURSE_CODE)) ;
			course.setCourse_name(result.getString(Course.COURSE_NAME)) ;
			return course ;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null ;
	}
	
	public static TreeItem<String> getItem(int dept_id , int course_id){
		//2 for const course
		TreeItem<String> dept = Department.getItem(dept_id) ;
		for(int x = 0 ; x < dept.getChildren().get(2).getChildren().size() ; x++){
			TreeItem<String> item = dept.getChildren().get(2).getChildren().get(x) ;
			Course data = TreeItemData.getItemData(item) ;
			if(data.course_id == course_id){
				return item ;
			}
		}
		return null ;
	}
	
}
