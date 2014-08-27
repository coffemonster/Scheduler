package congcrete;

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
		// TODO Auto-generated constructor stub
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
	
	
	
}
