package congcrete;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;

public class TimeSlot {
	
	
	private Time from ;
	private Time to ;
	private Subject subject ;
	private Section section ;
	private Room room ;
	private Teacher teacher ;
	private String color ;
	private static final String[] colorChoice = {"#d35400" , "#c0392b" , "#bdc3c7" , "#7CB9E8" , "#C9FFE5" , "#EFDECD" , "#F19CBB" , "#FAEBD7" , "#FBCEB1" , "#7FFFD4" , "#B2BEB5" , "#F0FFFF" , "#DBE9F4" , "#F4C2C2" , "#FF91AF" , "#FAE7B5" , "#BCD4E6" , "#F5F5DC" , "#FFE4C4" , "#FE6F5E" , "#FFEBCD" , "#ACE5EE" , "#FAF0BE" , "#E3DAC9" , "#BF94E4" , "#D891EF" , "#D19FE8" , "#F4BBFF" , "#FFC1CC" , "#F0DC82"} ;
	
	public TimeSlot(Time from , Time to){
		int colorChoiceLoc = (int) (Math.random() * colorChoice.length - 1) ;
		color = colorChoice[colorChoiceLoc] ;
		this.from  = from ;
		this.to = to ;
	}
	
	

	public Teacher getTeacher() {
		return teacher;
	}

	

	public String getColor() {
		return color ;
	}



	public void setColor(String color) {
		this.color = color;
	}



	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
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
	
	
	
	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
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
	
	public static String getCompleteTime(Time time){
		if(time.toLocalTime().getHour() == 12){
			return time.toLocalTime() + "PM" ;
		}else if(time.toLocalTime().getHour() == 24){
			return time.toLocalTime().withHour(1) + "AM" ;
		}else if(time.toLocalTime().getHour() > 12){
			return time.toLocalTime().withHour(time.toLocalTime().getHour() - 12) + "PM" ;
		}else{
			return time.toLocalTime() + "AM" ;
		}
	}
}
