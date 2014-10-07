package congcrete;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
	private float remaining_unit ;
	private ArrayList<Day> days ;
	
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
		
		days = new ArrayList<Day>() ;
		days.add(new Day(Day.MONDAY)) ;
		days.add(new Day(Day.TUESDAY)) ;
		days.add(new Day(Day.WEDNESDAY)) ;
		days.add(new Day(Day.THURSDAY)) ;
		days.add(new Day(Day.FRIDAY)) ;
		
	}
	
	public Subject() {
		
		days = new ArrayList<Day>() ;
		days.add(new Day(Day.MONDAY)) ;
		days.add(new Day(Day.TUESDAY)) ;
		days.add(new Day(Day.WEDNESDAY)) ;
		days.add(new Day(Day.THURSDAY)) ;
		days.add(new Day(Day.FRIDAY)) ;
		
	}
	
	/*
	@Override
	public String toString(){
		return subject_name + "";
	}
	*/
	
	
	
	public ArrayList<Day> getDays() {
		return days;
	}

	public void setDays(ArrayList<Day> days) {
		this.days = days;
	}

	public float getRemaining_unit() {
		return remaining_unit;
	}

	public void setRemaining_unit(float remaining_unit) {
		this.remaining_unit = remaining_unit;
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
		remaining_unit = this.subject_unit ;
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
	
	public static ArrayList<Subject> getSubjectInTeacher(int teacher_id){
		ArrayList<Subject> subjects = new ArrayList<Subject>() ;
		String subjectQuery = "SELECT * FROM classes C " +
							  "JOIN subjects S ON S.subject_id = C.subject_id " +
							  "JOIN sections SEC ON SEC.section_id = C.section_id " +
							  "JOIN years Y ON Y.year_id = SEC.year_ID " + 
							  "JOIN teachers T ON T.teacher_id = C.teacher_id WHERE T.teacher_id = ";
		
		ResultSet result = Connect.QUERY(subjectQuery + teacher_id) ;
		try {
			while(result.next()){
				Subject sub = new Subject() ;
				sub.setSubject_id(result.getInt(Subject.SUBJECT_ID));
				sub.setSubject_name(result.getString(Subject.SUBJECT_NAME));
				sub.setSubject_code(result.getString(Subject.SUBJECT_CODE));
				sub.setSubject_unit(result.getInt(Subject.SUBJECT_UNIT));
				
				Year year = Year.getYear(result.getInt(Subject.YEAR_ID)) ;
				
				Section section = Section.getSection(result.getInt(Section.SECTION_ID)) ;
				
				sub.setSection(section);
				
				sub.setYear(year);
				
				subjects.add(sub) ;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return subjects ;
	}
	
	public static ArrayList<Subject> getSubjectInTeacher(int teacher_id , ArrayList<Section> sectionList){
		ArrayList<Subject> subjects = new ArrayList<Subject>() ;
		String subjectQuery = "SELECT * FROM classes C " +
							  "JOIN subjects S ON S.subject_id = C.subject_id " +
							  "JOIN sections SEC ON SEC.section_id = C.section_id " +
							  "JOIN years Y ON Y.year_id = SEC.year_ID " + 
							  "JOIN teachers T ON T.teacher_id = C.teacher_id WHERE T.teacher_id = ";
		
		ResultSet result = Connect.QUERY(subjectQuery + teacher_id) ;
		try {
			while(result.next()){
				Subject sub = new Subject() ;
				sub.setSubject_id(result.getInt(Subject.SUBJECT_ID));
				sub.setSubject_name(result.getString(Subject.SUBJECT_NAME));
				sub.setSubject_code(result.getString(Subject.SUBJECT_CODE));
				sub.setSubject_unit(result.getInt(Subject.SUBJECT_UNIT));
				
				Year year = Year.getYear(result.getInt(Subject.YEAR_ID)) ;
				
				Section section = null ;
				
				for(int iSection = 0 ; iSection < sectionList.size() ; iSection++){
					if(sectionList.get(iSection).getSection_id() == result.getInt(Section.SECTION_ID)){
						section = sectionList.get(iSection) ;
						break ;
					}
				}
				
				sub.setSection(section);
				
				sub.setYear(year);
				
				subjects.add(sub) ;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return subjects ;
	}
	
	/*
	public static ArrayList<Subject> getSubjectList(int year_id){
		ArrayList<Subject> subjects = new ArrayList<Subject>() ;
		String subjectQuery = "SELECT * FROM classes C " +
							  "JOIN subjects S ON S.subject_id = C.subject_id " +
							  "JOIN years Y ON Y.year_id = S.year_ID " + 
							  "JOIN teachers T ON T.teacher_id = C.teacher_id WHERE Y.year_id = " + year_id;
		ResultSet result = Connect.QUERY(subjectQuery) ;
		
		try {
			while(result.next()){
				Subject subject = new Subject() ;
				Section section = Section.getSection(result.getInt(Section.SECTION_ID)) ;
				subject.setSection(section);
				subject.setSubject_code(result.getString("subject_code"));
				subject.setSubject_id(result.getInt("subject_id"));
				subject.setSubject_name(result.getString("subject_name"));
				subject.setSubject_unit(result.getInt("subject_unit"));
				Teacher teacher = Teacher.getTeacher(result.getInt("teacher_id")) ;
				subject.setTeacher(teacher);
				Year year = Year.getYear(result.getInt("year_id")) ;
				subject.setYear(year);
				
				subjects.add(subject) ;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return subjects ;
	}
	*/
	
}
