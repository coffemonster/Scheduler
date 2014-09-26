package application.scheduler;

import java.sql.Time;
import java.util.ArrayList;

import congcrete.Day;
import congcrete.Room;
import congcrete.Section;
import congcrete.Subject;
import congcrete.Teacher;
import congcrete.TimeSlot;

public class SimpleScheduler {
	
	private int dept_id ;
	public static ArrayList<Teacher> teachersList ;
	public static ArrayList<Room> roomsList ;
	public static ArrayList<Section> sectionList ;
	
	public SimpleScheduler(int dept_id){
		this.dept_id = dept_id ;
		
		teachersList = Teacher.getTeacherList(dept_id) ;
		roomsList = Room.getRoomList(dept_id) ;
		sectionList = Section.getList(0) ;
		
		//Assigning Subjects
		for(int x = 0 ; x < teachersList.size() ; x++){
			ArrayList<Subject> subjectList = Subject.getSubjectInTeacher(teachersList.get(x).getTeacher_id() , sectionList) ;
			teachersList.get(x).setSubjects(subjectList);
		}	
	}
	
	public void start(){
		while(!Scheduler.isComplete(teachersList)){
			Day targetDay = Day.getNextRoomDay(roomsList) ;
			
			ArrayList<Teacher> availableTeacher = null ;
			
			main:while(true){
				availableTeacher = getAvailableTeacher(teachersList , targetDay) ;
				
				System.out.println("Available Teacher : " + availableTeacher.size());
				
				if(availableTeacher.size() == 0 && !Scheduler.isComplete(teachersList)){
					targetDay.getTimeSlots().add(new TimeSlot(targetDay.getLastTime() , Time.valueOf(targetDay.getLastTime().toLocalTime().plusMinutes(30)))) ;
					break main ;
				}else{
					teacherRoomPriority(availableTeacher , targetDay) ;
					//break algo
					break main ;
				}
			}
		}
	}
	
	public static void teacherRoomPriority(ArrayList<Teacher> teachers , Day targetDay){
		Teacher targetTeacher = getTeacher(teachers, targetDay) ;
		if(targetTeacher.getTeacher_id() == 2){
			System.out.print("DEBUH");
		}
		ArrayList<Subject> targetSubject = getAvailableSubject(targetTeacher , targetDay) ;
		Subject currentSubject = getSubject(targetSubject , targetDay) ;
		
		float sliceUnit = Scheduler.getSliceableTime(currentSubject.getRemaining_unit()) ;
		
		TimeSlot time = new TimeSlot(targetDay.getLastTime() , Time.valueOf(targetDay.getLastTime().toLocalTime().plusHours(Math.round(sliceUnit)))) ;
		
		time.setSubject(currentSubject);
		time.setSection(currentSubject.getSection());
		time.setTeacher(targetTeacher);
		
		for(int x = 0 ; x < roomsList.size() ; x++){
			if(roomsList.get(x).getDays().contains(targetDay)){
				time.setRoom(roomsList.get(x));
				break ;
			}
		}
		//Setting the day
		getDayFrom(targetTeacher.getDays() , targetDay.getDay()).getTimeSlots().add(time) ;
		getDayFrom(currentSubject.getSection().getDays() , targetDay.getDay()).getTimeSlots().add(time) ;
		targetDay.getTimeSlots().add(time) ;
		
		currentSubject.setRemaining_unit(currentSubject.getRemaining_unit() - sliceUnit);
		
		System.out.println("Selected Subject : " + currentSubject.getSubject_code()) ;
		System.out.println("--Teachers Time--");
		for(int x = 0 ; x < teachersList.size() ; x++){
			Teacher teacher = teachersList.get(x) ;
			System.out.print(teacher.getFirst_name() + " : ");
			for(int y = 0 ; y < teacher.getDays().size() ; y++){
				System.out.print(teacher.getDays().get(y).getLastTime() + " ");
			}
			System.out.println();
		}
		
		System.out.println("--Rooms Time--");
		for(int x = 0 ; x < roomsList.size() ; x++){
			Room room = roomsList.get(x) ;
			System.out.print(room.getRoom_name() + " : ");
			for(int y = 0 ; y < room.getDays().size() ; y++){
				System.out.print(room.getDays().get(y).getLastTime() + " ");
			}
			System.out.println();
		}
		
		System.out.println("--Section Time--");
		for(int x = 0 ; x < sectionList.size() ; x++){
			Section section = sectionList.get(x) ;
			System.out.print(section.getYear().getYear() + section.getSection() + " : ");
			for(int y = 0 ; y < section.getDays().size() ; y++){
				System.out.print(section.getDays().get(y).getLastTime() + " ");
			}
			System.out.println();
		}
		
	}
	
	public static Subject getSubject(ArrayList<Subject> subjects, Day targetDay){
		int low = 0 ;
		int subjectLoc = 0 ;
		boolean flag = true ;
		
		for(int iSubject = 0 ; iSubject < subjects.size() ; iSubject++){
			Subject currentSubject = subjects.get(iSubject) ;
			Section currentSection = currentSubject.getSection() ;
			if(flag){
				low = currentSection.getYear().getYear() ;
				subjectLoc = 0;
				flag = true ;
			}else{
				int temp = currentSection.getYear().getYear() ;
				if(low > temp){
					low = temp ;
					subjectLoc = iSubject ;
				}
			}
		}
		return subjects.get(subjectLoc) ;
	}
	
	public static ArrayList<Subject> getAvailableSubject(Teacher targetTeacher, Day targetDay){
		ArrayList<Subject> availableSubjects = new ArrayList<Subject>() ;
			
		for(int iSubject = 0 ; iSubject < targetTeacher.getSubjects().size() ; iSubject++){		
			Section currentSection = targetTeacher.getSubjects().get(iSubject).getSection() ;
			Day currentDay = getDayFrom(currentSection.getDays() , targetDay.getDay()) ;
			//Check if there is a mathcing day or if there is no remaining units
			if(currentDay == null || targetTeacher.getSubjects().get(iSubject).getRemaining_unit() == 0){
				System.out.print("aw");
				continue ;
			}
			
			//Check if there is the same Subject
			boolean isDuplicate = false ;
			for(int iTime = 0 ; iTime < currentDay.getTimeSlots().size() ; iTime++){
				TimeSlot currentTime = currentDay.getTimeSlots().get(iTime) ;
				if(currentTime.getSubject().getSubject_id() == targetTeacher.getSubjects().get(iSubject).getSubject_id()){
					isDuplicate = true ;
				}
			}
			if(isDuplicate){
				continue ;
			}
			
			if(currentDay.getLastTime().toLocalTime().isBefore(targetDay.getLastTime().toLocalTime()) || currentDay.getLastTime().toLocalTime().equals(targetDay.getLastTime().toLocalTime())){
				availableSubjects.add(targetTeacher.getSubjects().get(iSubject)) ;
			}
		}
		
		return availableSubjects;
	}
	
	
	public static ArrayList<Teacher> getAvailableTeacher(ArrayList<Teacher> teachersList , Day targetDay){
		ArrayList<Teacher> availableTeacher = new ArrayList<Teacher>() ;
		
		for(int iTeacher = 0 ; iTeacher < teachersList.size() ; iTeacher++){
			Teacher currentTeacher = teachersList.get(iTeacher) ;
			//CHECK if there is no Unit left
			if(isClear(currentTeacher.getSubjects())){
				continue ;
			}
			Day teacherDay = getDayFrom(currentTeacher.getDays() , targetDay.getDay()) ;
			//Check if there is no present day
			if(teacherDay == null){
				continue ;
			}
			//Validate if subject ok in that day
			ArrayList<Subject> avail = getAvailableSubject(currentTeacher, targetDay) ;
			if(avail.size() == 0){
				continue ;
			}
			
			//Check if the last time of teacher on that day is before or equal to the last time of the targetday
			if(teacherDay.getLastTime().toLocalTime().isBefore(targetDay.getLastTime().toLocalTime()) || teacherDay.getLastTime().toLocalTime().equals(targetDay.getLastTime().toLocalTime())){
				availableTeacher.add(currentTeacher) ;
			}
		}
		
		return availableTeacher ;
	}
	
	public static boolean isClear(ArrayList<Subject> subjects){
		//check if the teachers subject is clear
		for(int iSubject = 0 ; iSubject < subjects.size() ; iSubject++){
			if(subjects.get(iSubject).getRemaining_unit() > 0){
				return false ;
			}
		}
		
		return true ;
	}
	
	public static Day getDayFrom(ArrayList<Day> days , String day){
		//get the day of specified day
		for(int iDay = 0 ; iDay < days.size() ; iDay++){
			if(days.get(iDay).getDay().equalsIgnoreCase(day)){
				return days.get(iDay) ;
			}
		}
		return null ;
	}
	
	public static Teacher getTeacher(ArrayList<Teacher> teachersList , Day targetDay){
		Teacher targetTeacher = null ;
		int totalMinutes = 0 ;
		int teacherLoc = 0 ;
		boolean flag = true ;
		
		for(int iTeacher = 0 ; iTeacher < teachersList.size() ; iTeacher++){
			targetTeacher = teachersList.get(iTeacher) ;
			//check if all the subject section is available
			if(!isAvailableSubject(targetTeacher.getSubjects() , targetDay)){
				continue ;
			}

			Day teacherDay = getDayFrom(targetTeacher.getDays() , targetDay.getDay()) ;
			
			if(flag){
				totalMinutes = TimeSlot.getTotalMinutes(teacherDay.getLastTime(), targetDay.getLastTime()) ;
				teacherLoc = iTeacher ;
				flag = false ;
			}else{
				int temp = TimeSlot.getTotalMinutes(teacherDay.getLastTime(), targetDay.getLastTime()) ;
				if(totalMinutes > temp){
					totalMinutes = temp ;
					teacherLoc = iTeacher ;
				}
			}
			
		}
		
		System.out.println("--Selected Teacher--");
		for(int x = 0 ; x < teachersList.size() ; x++){
			System.out.println("Teacher : " + teachersList.get(x).getFirst_name());
			System.out.println("--Subjects--");
			for(int y = 0 ; y < teachersList.get(x).getSubjects().size() ; y++){
				System.out.println(teachersList.get(x).getSubjects().get(y).getSubject_name() + " : " + teachersList.get(x).getSubjects().get(y).getRemaining_unit());
			}
		}
		
		return teachersList.get(teacherLoc) ;
	}
	
	public static boolean isAvailableSubject(ArrayList<Subject> subjects, Day targetDay){
		for(int iSubject = 0 ; iSubject < subjects.size() ; iSubject++){
			Subject currentSubject = subjects.get(iSubject) ;
			Day sectionDay = getDayFrom(currentSubject.getDays() , targetDay.getDay()) ;
			//check if this section support this day
			if(sectionDay == null){
				continue ;
			}
			//Check if section is available
			if(sectionDay.getLastTime().toLocalTime().isBefore(targetDay.getLastTime().toLocalTime()) || sectionDay.getLastTime().toLocalTime().equals(targetDay.getLastTime().toLocalTime())){
				return true ;
			}
		}
		
		return false ;
	}
}
