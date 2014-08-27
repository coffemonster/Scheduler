package congcrete;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Connect;

public class Department {
	private int dept_id ;
	private String dept_name ;
	private String dept_code ;
	public static final String DEPT_ID = "dept_id" ;
	public static final String DEPT_NAME = "dept_name" ;
	public static final String DEPT_CODE = "dept_code" ;
	
	public Department(int dept_id, String dept_name, String dept_code) {
		this.dept_id = dept_id;
		this.dept_name = dept_name;
		this.dept_code = dept_code;
	}
	
	public Department() {
		// TODO Auto-generated constructor stub
	}

	public int getDept_id() {
		return dept_id;
	}
	public void setDept_id(int dept_id) {
		this.dept_id = dept_id;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getDept_code() {
		return dept_code;
	}
	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}
	public static Department getDepartment(int dept_code){
		ResultSet result = Connect.QUERY("SELECT * FROM `departments` WHERE `dept_id` = " + dept_code) ;
		Department departmentObj = new Department() ;
		try {
			while(result.next()){
				departmentObj.setDept_id(result.getInt(Department.DEPT_ID));
				departmentObj.setDept_name(result.getString(Department.DEPT_NAME));
				departmentObj.setDept_code(result.getString(Department.DEPT_CODE));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return departmentObj ;
	}
	
	public static ArrayList<Department> getDepartmentList(){
		ResultSet result = Connect.QUERY("SELECT * FROM `departments`") ;
		ArrayList<Department> deptList = new ArrayList<Department>() ;
		try {
			while(result.next()){
				Department d = new Department() ;
				d.setDept_id(result.getInt(Department.DEPT_ID));
				d.setDept_name(result.getString(Department.DEPT_NAME));
				d.setDept_code(result.getString(Department.DEPT_CODE));
				deptList.add(d) ;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deptList ;
	}
}
