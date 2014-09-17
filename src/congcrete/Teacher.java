package congcrete;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.TreeItem;
import tree.TreeItemData;
import tree.UpdateTree;
import database.Connect;

public class Teacher {
	private int teacher_id ;
	private Department d ;
	private String first_name ;
	private String last_name ;
	private String middle_initial ;
	private byte[] picture_path ;
	private ArrayList<Subject> subject ;
	
	public static final String TEACHER_ID = "teacher_id" ; 
	public static final String DEPT_ID = "dept_id" ; 
	public static final String FIRST_NAME = "first_name" ; 
	public static final String LAST_NAME = "last_name" ;
	public static final String MIDDLE_INITIAL = "middle_initial" ;
	public static final String PICTURE_PATH = "picture_path" ;
	
	public Teacher(int teacher_id, Department d, String first_name,
			String last_name, String middle_initial, byte[] picture_path) {
		super();
		this.teacher_id = teacher_id;
		this.d = d;
		this.first_name = first_name;
		this.last_name = last_name;
		this.middle_initial = middle_initial;
		this.picture_path = picture_path;
		
		subject = new ArrayList<Subject>() ;
		
	}

	public Teacher() {
		subject = new ArrayList<Subject>() ;
	}

	public int getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(int teacher_id) {
		this.teacher_id = teacher_id;
	}

	public Department getD() {
		return d;
	}

	public void setD(Department d) {
		this.d = d;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getMiddle_initial() {
		return middle_initial;
	}

	public void setMiddle_initial(String middle_initial) {
		this.middle_initial = middle_initial;
	}

	public byte[] getPicture_path() {
		return picture_path;
	}

	public void setPicture_path(byte[] picture_path) {
		this.picture_path = picture_path;
	}
	
	public static ArrayList<Teacher> getTeacherList(int dept_id){
		ResultSet result = Connect.QUERY("SELECT * FROM teachers WHERE dept_id = " + dept_id) ;
		ArrayList<Teacher> list = new ArrayList<Teacher>() ;
		try {
			while(result.next()){
				Teacher teacher = new Teacher() ;
				teacher.setTeacher_id(result.getInt(Teacher.TEACHER_ID));
				Department d = Department.getDepartment(result.getInt(Teacher.DEPT_ID)) ;
				teacher.setD(d);
				teacher.setFirst_name(result.getString(Teacher.FIRST_NAME));
				teacher.setLast_name(result.getString(Teacher.LAST_NAME));
				teacher.setMiddle_initial(result.getString(Teacher.MIDDLE_INITIAL));
				teacher.setPicture_path(result.getBytes(Teacher.PICTURE_PATH));
				
				list.add(teacher) ;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return list ;
	}
	
	public static Teacher getTeacher(int teacher_id){
		ResultSet result = Connect.QUERY("SELECT * FROM teachers WHERE teacher_id = " + teacher_id) ;
		try {
			while(result.next()){
				Teacher teacher = new Teacher() ;
				teacher.setTeacher_id(result.getInt(Teacher.TEACHER_ID));
				Department d = Department.getDepartment(result.getInt(Teacher.DEPT_ID)) ;
				teacher.setD(d);
				teacher.setFirst_name(result.getString(Teacher.FIRST_NAME));
				teacher.setLast_name(result.getString(Teacher.LAST_NAME));
				teacher.setMiddle_initial(result.getString(Teacher.MIDDLE_INITIAL));
				teacher.setPicture_path(result.getBytes(Teacher.PICTURE_PATH));
				return teacher ;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		return null ;
		
	}
	
	public static TreeItem<String> getItem(int dept_id , int teacher_id){
		//1 for const room
		TreeItem<String> dept = Department.getItem(dept_id) ;
		for(int x = 0 ; x < dept.getChildren().get(0).getChildren().size() ; x++){
			TreeItem<String> item = dept.getChildren().get(0).getChildren().get(x) ;
			Teacher data = TreeItemData.getItemData(item) ;
			if(data.getTeacher_id() == teacher_id){
				return item ;
			}
		}
		return null ;
	}
	
	public ArrayList<Subject> getSubjects(){
		return subject ;
	}
	
	public void setSubjects(ArrayList<Subject> subjects){
		this.subject = subjects ;
	}
}
