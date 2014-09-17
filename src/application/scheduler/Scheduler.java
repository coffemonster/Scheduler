package application.scheduler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import congcrete.Room;
import congcrete.Subject;
import congcrete.Teacher;
import database.Connect;

public class Scheduler {
	
	private ResultSet result ;
	private ArrayList<Teacher> teachers ;
	private int dept_id ;
	private ArrayList<Room> roomList ;
	private String startTime = "07:00:00" ;
	private String stopTime = "07:30:00" ;
	
	public Scheduler(int dept_id){
		this.dept_id = dept_id ;
		
		//setting Teachers Subjects
		teachers = Teacher.getTeacherList(dept_id) ;
	
		for(int x = 0 ; x < teachers.size() ; x++){
			ArrayList<Subject> subjectList = Subject.getSubjectInTeacher(teachers.get(x).getTeacher_id()) ;
			teachers.get(x).setSubjects(subjectList);
		}
		
		//setting up the rooms
		roomList = Room.getRoomList(dept_id) ;
	}
	
	public void start(){
		for(int x = 0 ; x < roomList.size() ; x++){
			
		}
	}
	
	private boolean isComplete(){
		for(int x = 0 ; x < teachers.size() ; x++){
			for(int y = 0 ; y < teachers.get(x).getSubjects().size() ; y++){
				if(!(teachers.get(x).getSubjects().get(y).getRemaining_unit() <= 0)){
					return false ;
				}
			}
		}
		return true ;
	}
	
	public Room getPriorityRoom(){
		result = Connect.QUERY("SELECT * FROM rooms R JOIN schedules S ON S.room_id = R.room_id");
		//Iteration if there is no 
		try {
			result.next() ;
			if(result.getRow() == 0){
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new Room() ;
	}
	
	public boolean isDayClear(int room_id , String day , String stop){
		String query = "SELECT MAX(`S`.`end_time`) AS last FROM rooms R" +
						"JOIN schedules S ON S.room_id = R.room_id" +
						"WHERE R.room_id = "+ room_id +" AND day = '"+ day +"'" +
						"GROUP BY R.room_id";
		result = Connect.QUERY(query) ;
		try {
			result.next() ;
			if(result.getRow() == 0){
				return false ;
			}else{
				String end = result.getString("last") ;
				String[] stop_split = stop.split(":") ;
				String stop_new_minutes = ((Integer.parseInt(stop_split[1]) == 30)?"aw":"aw") ;
				//TODO if(stop.split(":")[0] >)
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true ;
	}
}
