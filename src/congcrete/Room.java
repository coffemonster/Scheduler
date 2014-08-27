package congcrete;

public class Room {
	private int room_id ;
	private Department d ;
	private String room_name ;
	private String room_code ;
	public static final String ROOM_ID = "room_id" ;
	public static final String DEPT_ID = "dept_id" ;
	public static final String ROOM_NAME = "room_name" ;
	public static final String ROOM_CODE = "room_code" ;
	
	public Room(){
		
	}
	
	public Room(int room_id, Department d, String room_name, String room_code) {
		super();
		this.room_id = room_id;
		this.d = d;
		this.room_name = room_name;
		this.room_code = room_code;
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
	
	
}
