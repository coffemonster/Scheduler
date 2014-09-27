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
	private ArrayList<Teacher> teachersList ;
	private ArrayList<Room> roomsList ;
	private ArrayList<Section> sectionList ;
	
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
	
	public HybridScheduling(int dept_id , ArrayList<Teacher> teachers , ArrayList<Room> rooms , ArrayList<Section> sections){
		
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
					giveBreak(teachersList , sectionList) ;
					break main ;
				}
			}
		}
	}
	
	private void teacherRoomPriority(ArrayList<Teacher> teachers , Day targetDay){
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
				//Check if Section is Available
				if(sectionDay.getLastTime().toLocalTime().isAfter(targetDay.getLastTime().toLocalTime())){
					continue ;
				}
				
				ArrayList<Room> roomsTemp = (ArrayList<Room>) roomsList.clone() ;
				
				//HybridScheduling temp = new HybridScheduling(dept_id , teachersList, roomsList , sectionList) ;
				//temp.getSectionList()
				
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
	
	public void giveBreak(ArrayList<Teacher> teachers , ArrayList<Section> section){
		//Giving break to teacher
		for(int iTeacher = 0 ; iTeacher < teachers.size() ; iTeacher++){
			Teacher currentTeacher = teachers.get(iTeacher) ;
			for(int iDay = 0 ; iDay < currentTeacher.getDays().size() ; iDay++){
				Day currentDay = currentTeacher.getDays().get(iDay) ;
				if(!Scheduler.validateBreak(currentDay)){
					TimeSlot time = new TimeSlot(currentDay.getLastTime() , Time.valueOf(currentDay.getLastTime().toLocalTime().plusHours(1))) ;
					
					Subject subject = new Subject() ;
					subject.setSubject_code("null");
					Teacher teacher = new Teacher() ;
					teacher.setFirst_name("Wala");
					teacher.setMiddle_initial("B");
					Room room = new Room() ;
					room.setRoom_code("WW");
					Section mysection = Section.getSection(1) ;
					
					time.setRoom(room);
					time.setTeacher(teacher);
					time.setSubject(subject);
					time.setSection(mysection);
					
					currentDay.getTimeSlots().add(time) ;
				}
			}
		}
		
		//Giving break to Section
		for(int iSection = 0 ; iSection < section.size() ; iSection++){
			Section currentSection = section.get(iSection) ;
			for(int iDay = 0 ; iDay < currentSection.getDays().size() ; iDay++){
				Day currentDay = currentSection.getDays().get(iDay) ;
				if(!Scheduler.validateBreak(currentDay)){
					
					TimeSlot time = new TimeSlot(currentDay.getLastTime() , Time.valueOf(currentDay.getLastTime().toLocalTime().plusHours(1))) ;
					
					Subject subject = new Subject() ;
					subject.setSubject_code("null");
					Teacher teacher = new Teacher() ;
					teacher.setFirst_name("Wala");
					teacher.setMiddle_initial("B");
					Room room = new Room() ;
					room.setRoom_code("WW");
					Section mysection = Section.getSection(1) ;
					
					time.setRoom(room);
					time.setTeacher(teacher);
					time.setSubject(subject);
					time.setSection(mysection);
					
					currentDay.getTimeSlots().add(time) ;
					
				}
			}
		}
		
		
		
	}

	public int getDept_id() {
		return dept_id;
	}

	public void setDept_id(int dept_id) {
		this.dept_id = dept_id;
	}

	public ArrayList<Teacher> getTeachersList() {
		return teachersList;
	}

	public void setTeachersList(ArrayList<Teacher> teachersList) {
		this.teachersList = teachersList;
	}

	public ArrayList<Room> getRoomsList() {
		return roomsList;
	}

	public void setRoomsList(ArrayList<Room> roomsList) {
		this.roomsList = roomsList;
	}

	public ArrayList<Section> getSectionList() {
		return sectionList;
	}

	public void setSectionList(ArrayList<Section> sectionList) {
		this.sectionList = sectionList;
	}
}
