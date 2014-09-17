package application.priority;

public class PriorityTime {
	
	private int hour ;
	private int minute ;
	private String time = null ;
	
	public PriorityTime(String time){
		String[] timeArray = time.split(":") ;
		
		hour = Integer.parseInt(timeArray[0]);
		minute = Integer.parseInt(timeArray[1]) ;
		
		this.time = time ;
	}
	
	public int getHour(){
		return hour ;
	}
	
	public int getMinute(){
		return minute ;
	}
	
	public int getSpanHour(PriorityTime to){
		if(minute > to.getMinute()){
			return to.getHour() - getHour() - 1 ;
		}
		return to.getHour() - getHour() ;
	}
	
	public int getSpanMinute(PriorityTime to){
		
		if(minute > to.getMinute()){
			return (to.getMinute() + 60) - minute ;
		}else{
			return to.getMinute() - minute ;
		}
		
	}
	
	public String getTime(){
		return time ;
	}
}
