package application.scheduler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import application.main.FXMLDocumentController;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import congcrete.Day;
import congcrete.Room;
import congcrete.Subject;
import congcrete.Teacher;
import congcrete.TimeSlot;
import database.Connect;

public class Scheduler {
	
	private int dept_id ;
	private ResultSet result ;
	private ArrayList<Teacher> teachersList ;
	private ArrayList<Room> roomsList ;
	private ArrayList<Subject> subjectsList ; 
	private String startTime = "07:00:00" ;
	private String stopTime = "07:30:00" ;
	
	public Scheduler(int dept_id){
		this.dept_id = dept_id ;
		
		//setting Teachers
		teachersList = Teacher.getTeacherList(dept_id) ;
	
		for(int x = 0 ; x < teachersList.size() ; x++){
			ArrayList<Subject> subjectList = Subject.getSubjectInTeacher(teachersList.get(x).getTeacher_id()) ;
			teachersList.get(x).setSubjects(subjectList);
		}
		
		//setting up the rooms
		roomsList = Room.getRoomList(dept_id) ;
	
	}
	
	public void start(){
		for(int roomIteration = 0 ; roomIteration < roomsList.size() ; roomIteration++){
			for(int roomDayIteration = 0 ; roomDayIteration < roomsList.get(roomIteration).getDays().size() ; roomDayIteration++){
				//Look for teachers
				
			}
		}
	}
	
	private boolean isComplete(){
		for(int x = 0 ; x < teachersList.size() ; x++){
			for(int y = 0 ; y < teachersList.get(x).getSubjects().size() ; y++){
				if(!(teachersList.get(x).getSubjects().get(y).getRemaining_unit() <= 0)){
					return false ;
				}
			}
		}
		return true ;
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
	
	public void test(){
		TableView table = new TableView() ;
		//adding columns
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY) ;
		
		for(int x = 0 ; x < teachersList.get(0).getDays().size() ; x++){
			TableColumn column = new TableColumn(teachersList.get(0).getDays().get(x).getDay()) ;
			table.getColumns().add(column) ;
		}
		
		
		long maximum = 0 ;
		int loc = 0 ;
		
		for(int x = 0 ; x < teachersList.get(0).getDays().size() ; x++){
			for(int y = 0 ; y < teachersList.get(0).getDays().get(x).getTimeSlots().size() ; y++){
				if(x == 0){
					maximum = teachersList.get(0).getDays().get(x).getTimeSlots().get(y).getTo().toLocalTime().toSecondOfDay() - Day.start_minutes ;
					loc = 0 ;
				}else{
					long temp = teachersList.get(0).getDays().get(x).getTimeSlots().get(y).getTo().toLocalTime().toSecondOfDay() - Day.start_minutes ;
					if(maximum < temp){
						maximum = temp ;
						loc = y ;
					}
				}
			}
		}
		
		table.getItems().addAll("4") ;
		
		for(int x = 0 ; x < maximum / 30 ; x++){
			table.getItems().add("aw") ;
		}
		
		FXMLDocumentController.getInstance().getWorkplacePane().setCenter(table);

	}
	
	public Teacher getPrioritizeTeacher(Time start){
				
		for(int x = 0 ; x < teachersList.size() ; x++){
			
		}
		
	}
}
