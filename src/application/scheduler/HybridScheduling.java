package application.scheduler;

import java.sql.Time;
import java.util.ArrayList;

import congcrete.Day;
import congcrete.Room;
import congcrete.Section;
import congcrete.Subject;
import congcrete.Teacher;
import congcrete.TimeSlot;

public class HybridScheduling {
	
	private int dept_id ;
	public static ArrayList<Teacher> teachersList ;
	public static ArrayList<Room> roomsList ;
	public static ArrayList<Section> sectionList ;
	
	public HybridScheduling(int dept_id){
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
				availableTeacher = SimpleScheduler.getAvailableTeacher(teachersList , targetDay) ;
								
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
		Teacher choosenTeacher = null ;
		Section choosenSection = null ;
		Subject choosenSubject = null ;
		int teacherDelay = 0 ;
		int sectionDelay = 0 ;
		boolean flag = true ;
		
		for(int iTeacher = 0 ; iTeacher < teachers.size() ; iTeacher++){
			Teacher targetTeacher = teachers.get(iTeacher) ;
			Day teacherDay = SimpleScheduler.getDayFrom(targetTeacher.getDays(), targetDay.getDay()) ;
			for(int iSubject = 0 ; iSubject < targetTeacher.getSubjects().size() ; iSubject++){
				Subject currentSubject = targetTeacher.getSubjects().get(iSubject) ;
				Section currentSection = currentSubject.getSection() ;
				Day sectionDay = SimpleScheduler.getDayFrom(currentSection.getDays(), targetDay.getDay()) ;
				
				//Check if it doesnt match any Days
				if(sectionDay == null){
					continue ;
				}
				//Check if there is no remaining units
				if(currentSubject.getRemaining_unit() == 0){
					continue ;
				}
				//Check if there is the same Subject
				boolean isDuplicate = false ;
				for(int iTime = 0 ; iTime < sectionDay.getTimeSlots().size() ; iTime++){
					TimeSlot currentTime = sectionDay.getTimeSlots().get(iTime) ;
					if(currentTime.getSubject().getSubject_id() == currentSubject.getSubject_id()){
						isDuplicate = true ;
					}
				}
				if(isDuplicate){
					continue ;
				}
				
				//MAIN
				if(flag){
					teacherDelay = TimeSlot.getTotalMinutes(teacherDay.getLastTime(), targetDay.getLastTime()) ;
					sectionDelay = TimeSlot.getTotalMinutes(sectionDay.getLastTime(), targetDay.getLastTime()) ;
					choosenTeacher = targetTeacher ;
					choosenSection = currentSection ;
					choosenSubject = currentSubject ;
					flag = false ;
				}else{
					int tempTeacherDelay = TimeSlot.getTotalMinutes(teacherDay.getLastTime(), targetDay.getLastTime()) ;
					int tempSectionDelay = TimeSlot.getTotalMinutes(sectionDay.getLastTime(), targetDay.getLastTime()) ;
					if(teacherDelay + sectionDelay > tempTeacherDelay + tempSectionDelay){
						teacherDelay = tempTeacherDelay ;
						sectionDelay = tempSectionDelay ;
						choosenTeacher = targetTeacher ;
						choosenSection = currentSection ;
						choosenSubject = currentSubject ;
					}
				}
			}
		}
		
		if(choosenSection.getYear().getYear() == 2 && choosenSubject.getSubject_name().equals("BIO213")){
			System.out.println("aw") ;
		}
				
		float sliceUnit = Scheduler.getSliceableTime(choosenSubject.getRemaining_unit()) ;
		
		TimeSlot time = new TimeSlot(targetDay.getLastTime() , Time.valueOf(targetDay.getLastTime().toLocalTime().plusHours(Math.round(sliceUnit)))) ;
		
		time.setSubject(choosenSubject);
		time.setSection(choosenSection);
		time.setTeacher(choosenTeacher);
		
		for(int x = 0 ; x < roomsList.size() ; x++){
			if(roomsList.get(x).getDays().contains(targetDay)){
				time.setRoom(roomsList.get(x));
				break ;
			}
		}
		//Setting the day
		SimpleScheduler.getDayFrom(choosenTeacher.getDays() , targetDay.getDay()).getTimeSlots().add(time) ;
		SimpleScheduler.getDayFrom(choosenSection.getDays() , targetDay.getDay()).getTimeSlots().add(time) ;
		targetDay.getTimeSlots().add(time) ;
		
		choosenSubject.setRemaining_unit(choosenSubject.getRemaining_unit() - sliceUnit);
		
		
	}
}
