package congcrete;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;

public class TimeSlot {
	
	private Time from ;
	private Time to ;
	
	public TimeSlot(Time from , Time to){
		this.from  = from ;
		this.to = to ;
	}

	public Time getFrom() {
		return from;
	}

	public void setFrom(Time from) {
		this.from = from;
	}

	public Time getTo() {
		return to;
	}

	public void setTo(Time to) {
		this.to = to;
	}
	
	public static int getTotalMinutes(Time from , Time to){
		LocalTime temp = to.toLocalTime().minusHours(from.toLocalTime().getHour()) ;
		temp = temp.minusMinutes(from.toLocalTime().getMinute()) ;
		
		return (temp.getHour() * 60) + temp.getMinute() ;
	}
	
	public static ArrayList<TimeSlot> getTeacherPriorities(int teacher_id){
		//TODO
		return new ArrayList<TimeSlot>() ;
	}
}
