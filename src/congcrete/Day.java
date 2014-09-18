package congcrete;

import java.sql.Time;
import java.util.ArrayList;

public class Day {
	
	private ArrayList<TimeSlot> timeSlots = new ArrayList<TimeSlot>() ;
	public static final String MONDAY = "Monday" ;
	public static final String TUESDAY = "Tuesday" ;
	public static final String WEDNESDAY = "Wednesday" ;
	public static final String THURSDAY = "Thursday" ;
	public static final String FRIDAY = "Friday" ;
	public static final String SATURDAY = "Saturday" ;
	private String day ;
	public static Time start = Time.valueOf("7:00:00") ;
	public static Time stop = Time.valueOf("19:30:00") ;
	public static long start_minutes = start.toLocalTime().toSecondOfDay() / 60 ;
	
	public Day(String day){
		this.day = day ;
	}
	
	public ArrayList<TimeSlot> getTimeSlots(){
		return timeSlots ;
	}
	
	public Time getLastTime(){
		Time time = null ;
		int loc = 0 ;
		
		if(timeSlots.size() == 0){
			return Day.start ;
		}else{
			for(int x = 0 ; x < timeSlots.size() ; x++){
				if(x == 0){
					time = timeSlots.get(x).getTo() ;
					loc = 0 ;
				}else{
					if(time.before(timeSlots.get(x).getTo())){
						time = timeSlots.get(x).getTo() ;
						loc = x ;
					}
				}
			}
			return timeSlots.get(loc).getTo() ;
		}
	}
	
	public boolean isEmpty(){
		if(timeSlots.size() == 0){
			return true ;
		}else{
			return false ;
		}
	}
	
	public String getDay(){
		return day ;
	}
}
