package application.scheduler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;






import com.sun.prism.paint.Color;

import application.main.FXMLDocumentController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import congcrete.Day;
import congcrete.Room;
import congcrete.Section;
import congcrete.Subject;
import congcrete.TableCellList;
import congcrete.Teacher;
import congcrete.TimeSlot;
import database.Connect;

public class Scheduler {
	
	private int dept_id ;
	private ResultSet result ;
	//TODO change to non-static if finish
	private static ArrayList<Teacher> teachersList ;
	private static ArrayList<Room> roomsList ;
	private static ArrayList<Section> sectionList ;
	private static ArrayList<Subject> subjectsList ; 
	private String startTime = "07:00:00" ;
	private String stopTime = "07:30:00" ;
	private static String myColor = null ;

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
		
		//setting the Sections
		//0 = select *
		sectionList = Section.getList(0) ;
		
		for(int x = 0 ; x < sectionList.size() ; x++){
			//System.out.print(sectionList.get(x).getSection_id());
		}
		
		//set section for reference
		for(int x = 0 ; x < teachersList.size() ; x++){
			for(int y = 0 ; y < teachersList.get(x).getSubjects().size() ; y++){
				for(int z = 0 ; z < sectionList.size() ; z++){
					if(sectionList.get(z).getSection_id() == teachersList.get(x).getSubjects().get(y).getSection().getSection_id()){
						teachersList.get(x).getSubjects().get(y).setSection(sectionList.get(z));
					}
				}
			}
		}
		
		Day targetDay = Day.getNextRoomDay(roomsList) ;
		ArrayList<Teacher> availableTeachers = getAvailabeTeachers(targetDay) ;
		ArrayList<Section> availableSections = getAvailableSections(availableTeachers, targetDay) ; 
	
		Teacher teacher = getClosestTeacher(availableTeachers, targetDay) ;

		
	}
	
	public void superTest(){
		Day targetDay = Day.getNextRoomDay(roomsList) ;
		Time time = targetDay.getLastTime() ;
		//System.out.println(targetDay + " = " + roomsList.get(0).getDays().get(0) + " " + roomsList.get(0).getDays().get(1) + " " + roomsList.get(0).getDays().get(2) + " " + roomsList.get(0).getDays().get(3) + " " + roomsList.get(0).getDays().get(4));
		/*
		if(time.toLocalTime().getMinute() == 30){
			//time = Time.valueOf((time.toLocalTime().getHour() + 1) + ":00:00") ;
			
		}else{
			time = Time.valueOf(time.toLocalTime().getHour() + ":30:00") ;
		}
		*/
		
		targetDay.getTimeSlots().add(new TimeSlot(targetDay.getLastTime() , Time.valueOf(targetDay.getLastTime().toLocalTime().plusMinutes(30)))) ;
		System.out.println("--Room Last Time--") ;
		for(int x = 0 ; x < roomsList.size() ; x++){
			System.out.println(roomsList.get(x).getRoom_name() + " " + roomsList.get(x).getDays().get(0).getLastTime()+ " " + roomsList.get(x).getDays().get(1).getLastTime()+ " " + roomsList.get(x).getDays().get(2).getLastTime()+ " " + roomsList.get(x).getDays().get(3).getLastTime()+ " " + roomsList.get(x).getDays().get(4).getLastTime());
		}
	}
	
	public void start(){
		//get the least day
		while(!isComplete()){
			
		if(getRem() == 0){
			return ;
		}
		
			System.out.println("Start");
			Day targetDay = Day.getNextRoomDay(roomsList) ;
			System.out.println("--ROOM DETAILS--");
			Room currentRoom = null ;
			Day copyDay = null ;
			
			for(int iRoom = 0 ; iRoom < roomsList.size() ; iRoom++){
				for(int iDay = 0 ; iDay < roomsList.get(iRoom).getDays().size() ; iDay++){
					Day currentDay = roomsList.get(iRoom).getDays().get(iDay) ;
					if(currentDay.equals(targetDay)){
						currentRoom = roomsList.get(iRoom) ;
						copyDay = currentDay ;
					}
				}
			}
			
			System.out.print("Room Name : ");
			System.out.println(currentRoom.getRoom_name());
			System.out.println(copyDay + " = " + targetDay + " : " + copyDay.getDay() + " = " + targetDay.getDay());
			System.out.println("Last time : " + targetDay.getLastTime() + " = " + copyDay.getLastTime()) ;
			
			ArrayList<Teacher> availableTeachers ;
			ArrayList<Section> availableSections = null ;
			
			main:while(true){
				availableTeachers = getAvailabeTeachers(targetDay) ;
				
				System.out.println("--Teacher Availble Teachers--");
				for(int x = 0 ; x < availableTeachers.size() ; x++){
					System.out.println(availableTeachers.get(x).getFirst_name());
				}
				
				if(availableTeachers.size() == 0 && !isComplete()){
					targetDay.getTimeSlots().add(new TimeSlot(targetDay.getLastTime() , Time.valueOf(targetDay.getLastTime().toLocalTime().plusMinutes(30)))) ;
					System.out.println("--Cant Find available Teachers--");
				}else{
					break main ;
					/*
					availableSections = getAvailableSections(availableTeachers, targetDay) ; 
					if(availableSections.size() == 0){
						targetDay.getLastTime().toLocalTime().plusMinutes(30) ;
					}else{
						break main ;
					}
					*/
				}
				
			}
			
			//System.out.print(availableTeachers.size() + " " + availableSections.size());
			
			simpleAlgorithm(targetDay , availableTeachers , availableSections) ;
	
		}
			
		if(isComplete()){
			for(int x = 0 ; x < teachersList.get(3).getDays().get(1).getTimeSlots().size() ; x++){
				System.out.println("From : " + teachersList.get(3).getDays().get(1).getTimeSlots().get(x).getFrom());
				System.out.println("From : " + teachersList.get(3).getDays().get(1).getTimeSlots().get(x).getTo());
			}
		}
	}
	
	public void simpleAlgorithm(Day targetDay , ArrayList<Teacher> availableTeacher , ArrayList<Section> section){
		 //prioritize teacher

		Teacher teacher = getClosestTeacher(availableTeacher , targetDay) ;
		Subject subject = getClosestSubject(teacher, targetDay) ;
		
		System.out.println("--Selected Teacher--");
		System.out.println("Selected Teacher : " + teacher.getFirst_name());
		
		System.out.println("--Section Details--");
		
		System.out.println("Section = " + (subject.getSection().getYear().getYear() + subject.getSection().getSection()));
		System.out.println("Start Time : " + targetDay.getLastTime());
		System.out.println("Subject : " + subject.getSubject_name());
		
		//adding TimeSLot
		TimeSlot time = new TimeSlot(targetDay.getLastTime() , Time.valueOf(targetDay.getLastTime().toLocalTime().plusHours(subject.getSubject_unit()))) ;
		
		time.setSubject(subject);
		time.setSection(subject.getSection());
		time.setTeacher(teacher);
		
		for(int x = 0 ; x < roomsList.size() ; x++){
			if(roomsList.get(x).getDays().contains(targetDay)){
				time.setRoom(roomsList.get(x));
				break ;
			}
		}
		
		for(int iDay = 0 ; iDay < teacher.getDays().size() ; iDay++){
			if(teacher.getDays().get(iDay).getDay().equalsIgnoreCase(targetDay.getDay())){
				teacher.getDays().get(iDay).getTimeSlots().add(time) ;
			}
		}
		
		//System.out.println(time.getFrom() + " " +  time.getTo());
		
		for(int iDay = 0 ; iDay < subject.getDays().size() ; iDay++){
			if(subject.getDays().get(iDay).getDay().equalsIgnoreCase(targetDay.getDay())){
				subject.getDays().get(iDay).getTimeSlots().add(time) ;
			}
		}
		
		targetDay.getTimeSlots().add(time);
		
		for(int iDay = 0 ; iDay < subject.getSection().getDays().size() ; iDay++){
			if(subject.getSection().getDays().get(iDay).getDay().equalsIgnoreCase(targetDay.getDay())){
				subject.getSection().getDays().get(iDay).getTimeSlots().add(time) ;
				System.out.println("Updated Time : " + subject.getSection().getDays().get(iDay).getLastTime());
			}
		}
		
		//System.out.println(subject.getSubject_name() + " " + subject.getSection().getYear().getYear() + subject.getSection().getSection());
		subject.setRemaining_unit(0);
		System.out.println("Remaining Unit : " + getRem());
		
		System.out.println("-- MORE DETAILS --") ;
		System.out.println("--Teacher Last Time--") ;
		for(int x = 0 ; x < teachersList.size() ; x++){
			System.out.println(teachersList.get(x).getFirst_name() + " " + teachersList.get(x).getDays().get(0).getLastTime()+ " " + teachersList.get(x).getDays().get(1).getLastTime()+ " " + teachersList.get(x).getDays().get(2).getLastTime()+ " " + teachersList.get(x).getDays().get(3).getLastTime()+ " " + teachersList.get(x).getDays().get(4).getLastTime());
		}
		
		System.out.println("--Room Last Time--") ;
		for(int x = 0 ; x < roomsList.size() ; x++){
			System.out.println(roomsList.get(x).getRoom_name() + " " + roomsList.get(x).getDays().get(0).getLastTime()+ " " + roomsList.get(x).getDays().get(1).getLastTime()+ " " + roomsList.get(x).getDays().get(2).getLastTime()+ " " + roomsList.get(x).getDays().get(3).getLastTime()+ " " + roomsList.get(x).getDays().get(4).getLastTime());
		}
		
		System.out.println("--MAIN--");
		System.out.print(time.getRoom());
	}
	
	public Teacher getClosestTeacher(ArrayList<Teacher> teachers , Day targetDay){
		int low = 0 ;
		boolean flag = true ;
		int teacherLoc = 0 ;
		int dayLoc = 0 ;
			
		for(int iTeacher = 0 ; iTeacher < teachers.size() ; iTeacher++){
			for(int iDay = 0 ; iDay < teachers.get(iTeacher).getDays().size() ; iDay++){
				
				if(teachers.get(iTeacher).getDays().get(iDay).getDay().equalsIgnoreCase(targetDay.getDay())){
					Day currentDay = teachers.get(iTeacher).getDays().get(iDay) ;
					if(flag){
						low = TimeSlot.getTotalMinutes(currentDay.getLastTime(), targetDay.getLastTime()) ;
						teacherLoc = iTeacher ;
						dayLoc = iDay ;
						flag = false ;
					}else{
						//TODO handle if equal here
						int temp = TimeSlot.getTotalMinutes(currentDay.getLastTime(), targetDay.getLastTime()) ;
						if(low > temp){
							teacherLoc = iTeacher ;
							dayLoc = iDay ;
						}
					}
				}
			}
		}
	
		//System.out.print(teachers.size());
		return teachers.get(teacherLoc) ;
	}
	
	public Subject getClosestSubject(Teacher teacher , Day targetDay){
		ArrayList<Subject> subjects = teacher.getSubjects() ;
		boolean flag = true ;
		int low = 0 ;
		int subjectLoc = 0 ;
		//TODO handle if equal
		for(int iSubject = 0 ; iSubject < subjects.size() ; iSubject++){
			if(subjects.get(iSubject).getRemaining_unit() == 0){
				continue ;
			}
			//System.out.println(subjects.get(iSubject).getSubject_name() + " " + subjects.get(iSubject).getSection().getYear().getYear() + subjects.get(iSubject).getSection().getSection() + " rem = " + subjects.get(iSubject).getRemaining_unit());
			Section section = subjects.get(iSubject).getSection() ;
			for(int iDay = 0 ; iDay < section.getDays().size() ; iDay++){
				if(section.getDays().get(iDay).getDay().equalsIgnoreCase(targetDay.getDay())){
					if(flag){
						low = TimeSlot.getTotalMinutes(section.getDays().get(iDay).getLastTime(), targetDay.getLastTime()) ;
						subjectLoc = iSubject ;
						flag = false ;
					}else{
						if(low >= TimeSlot.getTotalMinutes(section.getDays().get(iDay).getLastTime(), targetDay.getLastTime())){
							low = TimeSlot.getTotalMinutes(section.getDays().get(iDay).getLastTime(), targetDay.getLastTime()) ;
							subjectLoc = iSubject ;
						}
					}
				}
			}
		}
		
		//System.out.print(subjects.get(subjectLoc).getRemaining_unit());
		
		//System.out.print(subjects.get(subjectLoc));
		return subjects.get(subjectLoc) ;
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
	
	private int getRem(){
		int total = 0 ;
		for(int x = 0 ; x < teachersList.size() ; x++){
			for(int y = 0 ; y < teachersList.get(x).getSubjects().size() ; y++){
				total += teachersList.get(x).getSubjects().get(y).getRemaining_unit() ;
			}
		}
		return total ;
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
		boolean flag = true ;
		
		
		
		table.getItems().addAll("4") ;
		
		for(int x = 0 ; x < maximum / 30 ; x++){
			table.getItems().add("aw") ;
		}
		
		FXMLDocumentController.getInstance().getWorkplacePane().setCenter(table);

	}
	
	private ArrayList<Section> getAvailableSections(ArrayList<Teacher> availableTeachers , Day day){
		ArrayList<Section> availableSection = new ArrayList<Section>() ;
		for(int iTeacher = 0 ; iTeacher < availableTeachers.size() ; iTeacher++){
			for(int iSubject = 0 ; iSubject < availableTeachers.get(iTeacher).getSubjects().size() ; iSubject++){
				if(availableTeachers.get(iTeacher).getSubjects().get(iSubject).getRemaining_unit() == 0){
					continue ;
				}
				for(int iDay = 0 ; iDay < availableTeachers.get(iTeacher).getSubjects().get(iSubject).getSection().getDays().size() ; iDay++){
					if(availableTeachers.get(iTeacher).getSubjects().get(iSubject).getSection().getDays().get(iDay).getDay().equalsIgnoreCase(day.getDay())){
						if(availableTeachers.get(iTeacher).getSubjects().get(iSubject).getSection().getDays().get(iDay).getLastTime().toLocalTime().isBefore(day.getLastTime().toLocalTime()) ||
						   availableTeachers.get(iTeacher).getSubjects().get(iSubject).getSection().getDays().get(iDay).getLastTime().toLocalTime().equals(day.getLastTime().toLocalTime())){
							if(!isContainSection(availableSection, availableTeachers.get(iTeacher).getSubjects().get(iSubject).getSection().getSection_id())){
								availableSection.add(availableTeachers.get(iTeacher).getSubjects().get(iSubject).getSection()) ;
							}
							//availableSection.add(availableTeachers.get(iTeacher).getSubjects().get(iSubject).getSection()) ;
						}
					}
				}
			}
		}
		return availableSection ;
	}
	
	
	private ArrayList<Teacher> getAvailabeTeachers(Day day){
		ArrayList<Teacher> availableTeachers = new ArrayList<Teacher>() ;
		Time lastTime = day.getLastTime() ;
		
		for(int iTeacher = 0 ; iTeacher < teachersList.size() ; iTeacher++){
			if(!isAvailableTeacher(teachersList.get(iTeacher))){
				continue ;
			}
			for(int iDay = 0 ; iDay < teachersList.get(iTeacher).getDays().size() ; iDay++){
				Day currentDay = teachersList.get(iTeacher).getDays().get(iDay) ;
				if(currentDay.getDay().equalsIgnoreCase(day.getDay())){
					//System.out.println(currentDay.getLastTime().toLocalTime() + " = " + lastTime.toLocalTime() + " : " + currentDay.getLastTime().toLocalTime().equals(lastTime.toLocalTime())) ;
					if(currentDay.getLastTime().toLocalTime().equals(lastTime.toLocalTime()) || currentDay.getLastTime().toLocalTime().isBefore(lastTime.toLocalTime())){
						availableTeachers.add(teachersList.get(iTeacher)) ;
					}
				}
			}
		}
		
		return availableTeachers ;
	}
	
	private boolean isContainSection(ArrayList<Section> sections ,int section_id){
		for(int x = 0 ; x < sections.size() ; x++){
			if(sections.get(x).getSection_id() == section_id){
				return true ;
			}
		}
		return false ;
	}
	
	public boolean isAvailableTeacher(Teacher teacher){
		ArrayList<Subject> subject = teacher.getSubjects() ;
		
		for(int x = 0 ; x < subject.size() ; x++){
			if(subject.get(x).getRemaining_unit() != 0){
				return true ;
			}
		}
		
		return false ;
	}
	
	public static boolean viewTeacherSchedule(Teacher teacher){
		//TODO add validation if not yet Schedule
		
		ArrayList<Day> schedule = new ArrayList<Day>() ;
		
		/*
		for(int iRoom = 0 ; iRoom < roomsList.size() ; iRoom++){
			for(int iDay = 0 ; iDay < roomsList.get(iRoom).getDays().size() ; iDay++){
				Day currentDay = roomsList.get(iRoom).getDays().get(iDay) ;
				for(int iTime = 0 ; iTime < currentDay.getTimeSlots().size() ; iTime++){
					TimeSlot currentTime = currentDay.getTimeSlots().get(iTime) ;
					if(currentTime.getTeacher() != null){
						if(currentTime.getTeacher().equals(teacher)){
							schedule.get(iDay).getTimeSlots().add(currentTime) ;
						}
					}
				}
			}
		}
		
		setSchedule(schedule) ;
		*/
		
		for(int x = 0 ; x < teachersList.size() ; x++){
			if(teachersList.get(x).getTeacher_id() == teacher.getTeacher_id()){
				setSchedule(teachersList.get(x).getDays()) ;
			}
		}
		
		return true ;
	}
	
	public static TableView setSchedule(ArrayList<Day> days){
		TableView table = new TableView() ;
		
		//table.setDisable(true);
		
		table.setId("my-table");
		
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		ArrayList<TableCellList> cells = new ArrayList<TableCellList>() ;
		
		ObservableList<TableCellList> data = FXCollections.observableArrayList() ;
		
		TableColumn time = new TableColumn("Time / Date") ;

		time.setCellValueFactory(new PropertyValueFactory<TableCellList , String>("time"));
		//TODO code coloring here
		
		/*
		time.setCellFactory(column -> {
			return new TableCell<TableCellList , String>(){
				@Override
				protected void updateItem(String item , boolean empty){
					super.updateItem(item, empty);
					
					if(item == null || empty || item == ""){
						setText("") ;
						setStyle("") ;
					}else{
						setText(getItem());
						//setTextFill(Color.BLACK);
						setStyle("-fx-text-fill:white") ;
					}
				}
			};
		});
		*/
		
		time.setMinWidth(60);
		
		TableColumn first_day = new TableColumn(days.get(0).getDay()) ;
		first_day.setCellValueFactory(new PropertyValueFactory<TableCellList , String>("day_1"));
		
		
		first_day.setCellFactory(column -> {
			return new TableCell<TableCellList , String>(){
				@Override
				protected void updateItem(String item , boolean empty){
					super.updateItem(item, empty);
					
					if(item == null || empty || item == ""){
						setText("") ;
						setStyle("") ;
					}else{
						setText(getItem());
						
						//System.out.println(getStyle());
						
							if(!getItem().equals("-")){
								for(int x = 0 ; x < days.get(0).getTimeSlots().size() ; x++){
									if(getItem().equals(days.get(0).getTimeSlots().get(x).getSubject().getSubject_name())){
										myColor =  days.get(0).getTimeSlots().get(x).getColor() ; 
										System.out.println(myColor);
									}
								}
							}
						
							setStyle("-fx-background-color:" + myColor) ;	
					}
				}
			};
		});
		
	
		TableColumn second_day = new TableColumn(days.get(1).getDay()) ;
		second_day.setCellValueFactory(new PropertyValueFactory<TableCellList , String>("day_2"));
		
		TableColumn third_day = new TableColumn(days.get(2).getDay()) ;
		third_day.setCellValueFactory(new PropertyValueFactory<TableCellList , String>("day_3"));
		
		TableColumn fourth_day = new TableColumn(days.get(3).getDay()) ;
		fourth_day.setCellValueFactory(new PropertyValueFactory<TableCellList , String>("day_4"));
		
		TableColumn fifth_day = new TableColumn(days.get(4).getDay()) ;
		fifth_day.setCellValueFactory(new PropertyValueFactory<TableCellList , String>("day_5"));
		
		boolean flag = true ;
		Time high = null ;
		
		/*
		//get the last time in all day
		for(int iDay = 0 ; iDay < days.size() ; iDay++){
			if(flag){
				high = days.get(iDay).getLastTime() ;
				flag = false ;
			}else{
				if(high.toLocalTime().isBefore(days.get(iDay).getLastTime().toLocalTime())){
					high = days.get(iDay).getLastTime() ;
				}
			}
		}
		*/
		
		int NumberOfRows = TimeSlot.getTotalMinutes(Day.start, Day.stop) / 30 ;
		
		for(int x = 0 ; x < NumberOfRows + 5; x++){
			if(x == 0){
				Time start = Day.start ;
				String startString = TimeSlot.getCompleteTime(start) ;
				TableCellList row = new TableCellList(startString , "" , "" , "" , "" , "") ;
				data.add(row) ;
			}else{
				//System.out.print(data.get(data.size() - 1).getTime().substring(0, data.get(data.size() - 1).getTime().length() - 2));
				Time lastTime = Time.valueOf(data.get(data.size() - 1).getTime().substring(0, data.get(data.size() - 1).getTime().length() - 2) + ":00") ;
				Time temp = Time.valueOf(lastTime.toLocalTime().plusMinutes(30)) ;
				String startString = TimeSlot.getCompleteTime(temp) ;
				TableCellList row = new TableCellList(startString , "" , "" , "" , "" , "") ;
				data.add(row) ;
			}
		}
		//COMPLETING the time
		for(int x = 0 ; x < data.size() ; x++){
			Time currentTime = Time.valueOf(data.get(x).getTime().substring(0, data.get(data.size() - 1).getTime().length() - 2) + ":00") ;
			data.get(x).setTime(data.get(x).getTime() + "-" + TimeSlot.getCompleteTime(Time.valueOf(currentTime.toLocalTime().plusMinutes(30))));
		}
		
			//FIRST DAY
			for(int x = 0 ; x < days.get(0).getTimeSlots().size() ; x++){
				TimeSlot timeSlot = days.get(0).getTimeSlots().get(x) ;
				int start  = TimeSlot.getTotalMinutes(Day.start, timeSlot.getFrom()) / 30 ;
				int stop = ((TimeSlot.getTotalMinutes(Day.start, timeSlot.getFrom()) + TimeSlot.getTotalMinutes(timeSlot.getFrom(), timeSlot.getTo())) / 30) - 1 ;
				
				data.get(start).setDay_1(timeSlot.getSubject().getSubject_name());
				data.get(start + 1).setDay_1(timeSlot.getSection().getYear().getYear() + timeSlot.getSection().getSection());
				data.get(start + 2).setDay_1(timeSlot.getRoom().getRoom_name()) ;
						
				for(int z = start ; z <= stop ; z++){
					if(stop - start + 1 >= 3){
						if(data.get(z).getDay_1().equals("") || data.get(z).getDay_1().equals(null)){
							data.get(z).setDay_1("-");
						}
					}
				}
				/*
				for(int z = start ; z <= stop ; z++){
					if()
					data.get(z).setDay_1("-");
				}
				*/
				//System.out.println("FROM : " + timeSlot.getFrom() + " TO : " + timeSlot.getTo());
				//System.out.println("LOC : " + TimeSlot.getTotalMinutes(Day.start, timeSlot.getFrom()) / 30 );
				//System.out.println("TO : " + (((TimeSlot.getTotalMinutes(Day.start, timeSlot.getFrom()) + TimeSlot.getTotalMinutes(timeSlot.getFrom(), timeSlot.getTo())) / 30) - 1));
				
			}
			
			//SECOND DAY
			for(int x = 0 ; x < days.get(1).getTimeSlots().size() ; x++){
				TimeSlot timeSlot = days.get(1).getTimeSlots().get(x) ;
				int start  = TimeSlot.getTotalMinutes(Day.start, timeSlot.getFrom()) / 30 ;
				int stop = ((TimeSlot.getTotalMinutes(Day.start, timeSlot.getFrom()) + TimeSlot.getTotalMinutes(timeSlot.getFrom(), timeSlot.getTo())) / 30) - 1 ;
				
				data.get(start).setDay_2(timeSlot.getSubject().getSubject_name());
				data.get(start + 1).setDay_2(timeSlot.getSection().getYear().getYear() + timeSlot.getSection().getSection());
				data.get(start + 2).setDay_2(timeSlot.getRoom().getRoom_name()) ;
				
				for(int z = start ; z <= stop ; z++){
					if(stop - start + 1 >= 3){
						if(data.get(z).getDay_2().equals("") || data.get(z).getDay_2().equals(null)){
							data.get(z).setDay_2("-");
						}
					}
				}
				
			}
			
			
			//THIRD DAY
			for(int x = 0 ; x < days.get(2).getTimeSlots().size() ; x++){
				TimeSlot timeSlot = days.get(2).getTimeSlots().get(x) ;
				int start  = TimeSlot.getTotalMinutes(Day.start, timeSlot.getFrom()) / 30 ;
				int stop = ((TimeSlot.getTotalMinutes(Day.start, timeSlot.getFrom()) + TimeSlot.getTotalMinutes(timeSlot.getFrom(), timeSlot.getTo())) / 30) - 1 ;
				
				data.get(start).setDay_3(timeSlot.getSubject().getSubject_name());
				data.get(start + 1).setDay_3(timeSlot.getSection().getYear().getYear() + timeSlot.getSection().getSection());
				data.get(start + 2).setDay_3(timeSlot.getRoom().getRoom_name()) ;
				
				for(int z = start ; z <= stop ; z++){
					if(stop - start + 1 >= 3){
						if(data.get(z).getDay_3().equals("") || data.get(z).getDay_3().equals(null)){
							data.get(z).setDay_3("-");
						}
					}
				}
				
			}
			
			//FOURTH DAY
			for(int x = 0 ; x < days.get(3).getTimeSlots().size() ; x++){
				TimeSlot timeSlot = days.get(3).getTimeSlots().get(x) ;
				int start  = TimeSlot.getTotalMinutes(Day.start, timeSlot.getFrom()) / 30 ;
				int stop = ((TimeSlot.getTotalMinutes(Day.start, timeSlot.getFrom()) + TimeSlot.getTotalMinutes(timeSlot.getFrom(), timeSlot.getTo())) / 30) - 1 ;
				
				data.get(start).setDay_4(timeSlot.getSubject().getSubject_name());
				data.get(start + 1).setDay_4(timeSlot.getSection().getYear().getYear() + timeSlot.getSection().getSection());
				data.get(start + 2).setDay_4(timeSlot.getRoom().getRoom_name()) ;
				
				for(int z = start ; z <= stop ; z++){
					if(stop - start + 1 >= 3){
						if(data.get(z).getDay_4().equals("") || data.get(z).getDay_4().equals(null)){
							data.get(z).setDay_4("-");
						}
					}
				}
				
			}
			
			//FIFTH DAY
			for(int x = 0 ; x < days.get(4).getTimeSlots().size() ; x++){
				TimeSlot timeSlot = days.get(4).getTimeSlots().get(x) ;
				int start  = TimeSlot.getTotalMinutes(Day.start, timeSlot.getFrom()) / 30 ;
				int stop = ((TimeSlot.getTotalMinutes(Day.start, timeSlot.getFrom()) + TimeSlot.getTotalMinutes(timeSlot.getFrom(), timeSlot.getTo())) / 30) - 1 ;
				
				data.get(start).setDay_5(timeSlot.getSubject().getSubject_name());
				data.get(start + 1).setDay_5(timeSlot.getSection().getYear().getYear() + timeSlot.getSection().getSection());
				data.get(start + 2).setDay_5(timeSlot.getRoom().getRoom_name()) ;
				
				for(int z = start ; z <= stop ; z++){
					if(stop - start + 1 >= 3){
						if(data.get(z).getDay_5().equals("") || data.get(z).getDay_5().equals(null)){
							data.get(z).setDay_5("-");
						}
					}
				}
				
			}
		
		
		table.setItems(data);
		
		table.getColumns().add(time) ;
		table.getColumns().add(first_day) ;
		table.getColumns().add(second_day) ;
		table.getColumns().add(third_day) ;
		table.getColumns().add(fourth_day) ;
		table.getColumns().add(fifth_day) ;
		
		FXMLDocumentController.getInstance().getWorkplacePane().setCenter(table);
		
		//FXMLDocumentController.getInstance().getWorkplacePane().getCenter().setDisable(true);		
		
		return null ;
	}
	
}
