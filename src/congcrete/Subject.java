package congcrete;

public class Subject {
	private int subject_id ;
	private Teacher teacher ;
	private Year year ;
	private Section section ;
	private String subject_name ;
	private String subject_code ;
	private int subject_unit ;
	public static String SUBJECT_ID = "subject_id";
	public static String TEACHER_ID = "teacher_id";
	public static String SECTION_ID = "section_id" ; 
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
	
	
	
	

}
