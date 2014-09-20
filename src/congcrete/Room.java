package congcrete;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Connect;
import javafx.scene.control.TreeItem;
import tree.TreeItemData;

public class Room {
	private int room_id ;
	private Department d ;
	private String room_name ;
	private String room_code ;
	private ArrayList<Day> days = new ArrayList<Day>() ;
	public static final String ROOM_ID = "room_id" ;
	public static final String DEPT_ID = "dept_id" ;
	public static final String ROOM_NAME = "room_name" ;
	public static final String ROOM_CODE = "room_code" ;
	
	public Room(){
		days.add(new Day(Day.MONDAY)) ;
		days.add(new Day(Day.TUESDAY)) ;
		days.add(new Day(Day.WEDNESDAY)) ;
		days.add(new Day(Day.THURSDAY)) ;
		days.add(new Day(Day.FRIDAY)) ;
	}
	
	public Room(int room_id, Department d, String room_name, String room_code) {
		super();
		this.room_id = room_id;
		this.d = d;
		this.room_name = room_name;
		this.room_code = room_code;
		
		days.add(new Day(Day.MONDAY)) ;
		days.add(new Day(Day.TUESDAY)) ;
		days.add(new Day(Day.WEDNESDAY)) ;
		days.add(new Day(Day.THURSDAY)) ;
		days.add(new Day(Day.FRIDAY)) ;
	}

	public int getRoom_id() {
		return room_id;
	}

	public void setRoom_id(int room_id) {
		this.room_id = room_id;
	}

	public Department getD() {
		return d;
	}

	public void setD(Department d) {
		this.d = d;
	}

	public String getRoom_name() {
		return room_name;
	}

	public void setRoom_name(String room_name) {
		this.room_name = room_name;
	}

	public String getRoom_code() {
		return room_code;
	}

	public void setRoom_code(String room_code) {
		this.room_code = room_code;
	}
	
	public static TreeItem<String> getItem(int dept_id , int room_id){
		//1 for const room
		TreeItem<String> dept = Department.getItem(dept_id) ;
		for(int x = 0 ; x < dept.getChildren().get(1).getChildren().size() ; x++){
			TreeItem<String> item = dept.getChildren().get(1).getChildren().get(x) ;
			Room data = TreeItemData.getItemData(item) ;
			if(data.getRoom_id() == room_id){
				return item ;
			}
		}
		return null ;
	}
	
	public static ArrayList<Room> getRoomList(int dept_id){
		ArrayList<Room> roomList = new ArrayList<Room>() ;
		ResultSet result = Connect.QUERY("SELECT * FROM rooms WHERE dept_id = " + dept_id) ;
		try {
			while(result.next()){
				Room room = new Room() ;
				room.setRoom_id(result.getInt(Room.ROOM_ID));
				room.setRoom_code(result.getString(Room.ROOM_CODE)) ;
				room.setRoom_name(result.getString(Room.ROOM_NAME));
				
				Department dept = Department.getDepartment(result.getInt(Room.DEPT_ID)) ;
				
				room.setD(dept);
				
				roomList.add(room);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return roomList ;
	}
	
	public void setDay(ArrayList<Day> days){
		this.days = days ;
	}
	
	public ArrayList<Day> getDays(){
		return days ;
	}
}
