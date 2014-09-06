package congcrete;

import java.sql.ResultSet;
import java.sql.SQLException;

import tree.TreeItemData;
import javafx.scene.control.TreeItem;
import database.Connect;

public class Subject {
	private int subject_id ;
	private Teacher teacher ;
	private Year year ;
	private Section section ;
	private String subject_name ;
	private String subject_code ;
	private int subject_unit ;
	public static String SUBJECT_ID = "subject_id";
	public static String YEAR_ID = "year_id" ; 
	public static String SUBJECT_NAME = "subject_name";
	public static String SUBJECT_CODE = "subject_code";
	public static String SUBJECT_UNIT = "subject_unit";
	
	public Subject(int subject_id, Teacher teacher, Year year, Section section,
			String subject_name, String subject_code, int subject_unit) {
		super();
		this.subject_id = subject_id;
		this.teacher = teacher;
		this.year = year;
		this.section = section;
		this.subject_name = subject_name;
		this.subject_code = subject_code;
		this.subject_unit = subject_unit;
	}

	@Override
	public String toString(){
		return subject_name + "";
	}
	
	public Subject() {
		// TODO Auto-generated constructor stub
	}

	public int getSubject_id() {
		return subject_id;
	}

	public void setSubject_id(int subject_id) {
		this.subject_id = subject_id;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Year getYear() {
		return year;
	}

	public void setYear(Year year) {
		this.year = year;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public String getSubject_name() {
		return subject_name;
	}

	public void setSubject_name(String subject_name) {
		this.subject_name = subject_name;
	}

	public String getSubject_code() {
		return subject_code;
	}

	public void setSubject_code(String subject_code) {
		this.subject_code = subject_code;
	}

	public int getSubject_unit() {
		return subject_unit;
	}

	public void setSubject_unit(int subject_unit) {
		this.subject_unit = subject_unit;
	}
	
	public static Subject getSubject(int subject_id){
		ResultSet result = Connect.QUERY("SELECT * FROM subjects WHERE subject_id = " + subject_id) ;
		Subject subject = new Subject() ;
		try {
			while(result.next()){
				subject.setSubject_id(result.getInt(Subject.SUBJECT_ID));
				subject.setSubject_code(result.getString(Subject.SUBJECT_CODE));
				subject.setSubject_name(result.getString(Subject.SUBJECT_NAME));
				subject.setSubject_unit(result.getInt(Subject.SUBJECT_UNIT));
				Year year = Year.getYear(result.getInt(Subject.YEAR_ID)) ;
				subject.setYear(year);
		
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return subject ;
	}
	
	public static TreeItem<String> getItem(int subject_id){
		Subject subject = Subject.getSubject(subject_id) ;
		
		TreeItem<String> section = Section.getItem(subject.getSection().getSection_id());
		for(int x = 0 ; x < section.getChildren().size() ; x++){
			TreeItem<String> sectionItem = section.getChildren().get(x) ;
			Subject subjectData = TreeItemData.getItemData(sectionItem) ;
			if(subjectData.getSubject_id() == subject_id){
				return sectionItem ;
			}
		}
		return null ;
	}
	
}
