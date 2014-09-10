package application.main;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.* ;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.Duration;
import NodeUtils.* ;
import tree.TreeCellFactory;
import tree.TreeItemData;
import tree.UpdateTree;

import java.io.IOException;
import java.net.* ;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle ;

import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.DialogStyle;
import org.controlsfx.dialog.Dialogs;
import org.controlsfx.dialog.Dialogs.UserInfo;

import congcrete.Course;
import congcrete.Department;
import congcrete.Room;
import congcrete.Section;
import congcrete.Subject;
import congcrete.Teacher;
import congcrete.Year;
import database.Connect;
import adder.node.Adder;
import application.department.DepartmentContextMenu;
import application.list.SubjectList;
import application.validation.Validation;
public class FXMLDocumentController implements Initializable{
	
	@FXML private BorderPane root;
	@FXML private Image backup ;
	@FXML private ImageView departmentImageView ;
	@FXML private ImageView teacherImageView ;
	@FXML private ImageView roomImageView ;
	@FXML private ImageView courseImageView ;
	@FXML private ImageView yearImageView ;
	@FXML private ImageView sectionImageView ;
	@FXML private BorderPane workplacePane ;
	@FXML private TreeView<String> treeView ;
	@FXML private BorderPane treeBorderPane ;
	@FXML private Accordion rightAccordion ;
	@FXML private TitledPane detailsTitledPane ;
	@FXML private ScrollPane detailsScrollPane ;
	@FXML private Accordion leftAccordion ;
	@FXML private TitledPane hierarchyPane ;
	@FXML private SplitPane splitPane ;
	@FXML private VBox	subjectBox ;
	private static VBox staticSubjectBox ;
	private static Accordion staticRightAccordion ;
	private static TreeView<String> staticTreeView ;
	private ScaleAnimationProperty scaleProperty ;
	private NodeAnimation animation ;
	private static BorderPane staticWorkplacePane ;
	private static TitledPane staticDetailsTitledPane ;
	private static Accordion staticLeftAccordion ;
	private static TitledPane staticHierarchyPane ;
	private static FXMLDocumentController staticInstance ;
	
	/*
	 * MENU CONTROLS HANDLER
	 */
	//Show Add in center pane
	@FXML public void handleAddToCenter(MouseEvent e) throws Exception{
		
		SplitPane p = new SplitPane();
		
		String resourceURL = null ;
		ImageView ev = (ImageView)e.getSource() ;
		
		if(ev.getId().equals("departmentImageView")){
			resourceURL = "/application/department/addDepartmentDocument.fxml" ;
		}else if(ev.getId().equals("teacherImageView")){
			resourceURL = "/application/teacher/addTeacherDocument.fxml" ;
		}else if(ev.getId().equals("roomImageView")){
			resourceURL = "/application/room/addRoomDocument.fxml" ;
		}else if(ev.getId().equals("courseImageView")){
			resourceURL = "/application/course/addCourseDocument.fxml" ;
		}else if(ev.getId().equals("yearImageView")){
			resourceURL = "/application/Year/addYearDocument.fxml" ;
		}else if(ev.getId().equals("sectionImageView")){
			resourceURL = "/application/section/addSectionDocument.fxml" ;
		}else if(ev.getId().equals("subjectImageView")){
			resourceURL = "/application/Subject/addSubjectDocument.fxml" ;
		}
		
		Pane child = FXMLLoader.load(getClass().getResource(resourceURL)) ;
		
		Adder.addToCenter(workplacePane , child);
		BounceInTransition bounce = new BounceInTransition(child) ;
		/*
		animation = new ScaleAnimation(scaleProperty);
		animation.animate(child);
		*/
		
		//close error box
		Validation.hideError();
	}

	/*
	 *MENU CUSTOMIZE
	 */	
	//change the image
	private void changeImage(Event e , String option){
		if(e instanceof MouseEvent){
			//get the source of event
			ImageView ev = (ImageView)e.getSource() ;
			String loc = null ;
			
			if(ev.getId().equals("departmentImageView")){
				loc = "/images/school7" + option  + ".png" ;
			}else if(ev.getId().equals("teacherImageView")){
				loc = "/images/teach"  + option  +  ".png" ;
			}else if(ev.getId().equals("roomImageView")){
				loc = "/images/classroom1"  + option  +  ".png" ;
			}else if(ev.getId().equals("courseImageView")){
				loc = "/images/university2"  + option  +  ".png" ;
			}else if(ev.getId().equals("yearImageView")){
				loc = "/images/amount"  + option  +  ".png" ;
			}else if(ev.getId().equals("sectionImageView")){
				loc = "/images/homework"  + option  +  ".png" ;
			}else if(ev.getId().equals("subjectImageView")){
				loc = "/images/open3" + option + ".png" ;
			}
		
			//set the image base on LOC
			ev.setImage(new Image(getClass().getResourceAsStream(loc)));
		}
	}

	//hover at the menu
	@FXML public void menuHover(MouseEvent e){
		changeImage(e , "-h") ;
		ImageView ev = (ImageView)e.getSource() ; 
		
		Platform.runLater(new Runnable(){
			public void run(){
				animation = new ScaleAnimation(1 , 1.4 , 1 , 1.4 , Duration.millis(50) , 1) ;
				animation.animate(ev);
			}
		});
	}
	
	//exit at the menu
	@FXML public void menuRestore(MouseEvent e){
		changeImage(e , "") ;
		ImageView ev = (ImageView)e.getSource() ; 
		Platform.runLater(new Runnable(){
			public void run(){
				animation = new ScaleAnimation(1.4 , 1 , 1.4 , 1 , Duration.millis(50) , 1) ;
				animation.animate(ev);
			}
		});
	}

	@Override public void initialize(URL url , ResourceBundle rs){
			
		/*
		root.getTop().setVisible(false);
		root.getBottom().setVisible(false);
		root.setCenter(null);
		*/
		/*
		root.setTop(null);
		root.setBottom(null);
		root.setCenter(null);
		root.setStyle("-fx-background-color : #222222");
		
		//LOAD the login
		try {
			AnchorPane loginPane = FXMLLoader.load(getClass().getResource("/application/login/loginDocument.fxml")) ;
			//set the center to the loginPane
			loginPane.setStyle("-fx-background-color : white ");
			root.setCenter(loginPane);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		*/

		
		//Assigning Statics
		staticInstance = this ;
		staticWorkplacePane = workplacePane ;
		staticRightAccordion = rightAccordion ;
		staticDetailsTitledPane = detailsTitledPane ;
		staticLeftAccordion = leftAccordion ;
		staticHierarchyPane = hierarchyPane ;
		staticSubjectBox = subjectBox ;
		//set Property for adding at worplacePane
		scaleProperty = new ScaleAnimationProperty(.1 , 1 , .1 , 1 , Duration.millis(200) , 1) ;
		//Setting School as TreeView root
		Node img = new ImageView(new ImageGetter("school47.png").getImage()) ;
		treeView.setRoot(new TreeItem<String>("School" , img));
				
		staticTreeView = treeView ;
		updateTree();
		//set the school expanded
		staticTreeView.getRoot().setExpanded(true);
		//TODO setting the right accrodion
					
		//set the details if any Node are selected
		staticTreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem>(){
			@Override
			public void changed(ObservableValue<? extends TreeItem> arg0,
					TreeItem arg1, TreeItem item) {
				//to prevent exception null
				if(item == null){
					return ;
				}else if(item.getParent() == null){
					return ;
				}else if(!(item instanceof TreeItemData)){
					return ;
				}
				
				//Details
				Object itemObject = TreeItemData.getItemData(item) ;
				
				if(itemObject instanceof Teacher){
					try {
						//Load the AnchorPane Details
						AnchorPane m = FXMLLoader.load(getClass().getResource("/application/properties/teacherProperties.fxml")) ;
						m.setPrefWidth(detailsScrollPane.getWidth() - 5);
						detailsScrollPane.setContent(m);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(itemObject instanceof Department){
					try {
						AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/properties/departmentProperties.fxml")) ;
						pane.setPrefWidth(detailsScrollPane.getWidth() - 5) ;
						detailsScrollPane.setContent(pane);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(itemObject instanceof Course){
					try {
						AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/properties/courseProperties.fxml")) ;
						pane.setPrefWidth(detailsScrollPane.getWidth() - 5);
						detailsScrollPane.setContent(pane);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(itemObject instanceof Room){
					try{
						AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/properties/roomProperties.fxml")) ;
						pane.setPrefWidth(detailsScrollPane.getWidth() - 5);
						detailsScrollPane.setContent(pane);
					}catch(IOException e){
						e.printStackTrace();
					}
				}else if(itemObject instanceof Year){
					try {
						AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/properties/yearProperties.fxml")) ;
						pane.setPrefWidth(detailsScrollPane.getWidth() - 5);
						detailsScrollPane.setContent(pane);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(itemObject instanceof Section){
					try {
						AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/properties/sectionProperties.fxml")) ;
						pane.setPrefWidth(detailsScrollPane.getWidth() - 5);
						detailsScrollPane.setContent(pane);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(itemObject instanceof Subject){
					try {
						AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/properties/subjectProperties.fxml")) ;
						pane.setPrefWidth(detailsScrollPane.getWidth() - 5);
						detailsScrollPane.setContent(pane);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
					
			}
		});
		
		rightAccordion.widthProperty().addListener(new ChangeListener(){

			@Override
			public void changed(ObservableValue observable, Object oldValue,
					Object newValue) {
					if( detailsScrollPane.getContent() != null){
						AnchorPane pane = (AnchorPane) detailsScrollPane.getContent();
						pane.setPrefWidth(detailsScrollPane.getWidth() - 5);
					}
			}
			
		});
		
	}
	
	//update the tree
	public static void updateTree(){
			int teacher_node = 0 ;
			int room_node = 1 ;
			int course_node = 2 ;
		
			//UPDATE departments
			if(Connect.getConnection() != null){
				//Adding The Departments
				ResultSet result = Connect.QUERY("SELECT * FROM `departments`") ;
				//emptying TreeView
				staticTreeView.getRoot().getChildren().clear();
				try{
					while(result.next()){
						//set the data to insert in the TreeItemData
						Department department = new Department() ;
						department.setDept_id(result.getInt(Department.DEPT_ID));
						department.setDept_name(result.getString(Department.DEPT_NAME));
						department.setDept_code(result.getString(department.DEPT_CODE));
						//getting image for the item
						Node deptImg = new ImageView(new ImageGetter("school7-s.png").getImage()) ;
						//adding to the Tree
						TreeItemData deptTreeItem = new TreeItemData(department.getDept_name() , deptImg , department);
						
						//TreeCell<String> cell = new TreeCell<String>() ;
						//FXMLDocumentController.getTree().getRoot().getChildren().add(cell);
						
						
						staticTreeView.getRoot().getChildren().add(deptTreeItem);
					}
				}catch(SQLException e){
					e.printStackTrace();
				}
				
				//Adding the teacher , room , course to Department
				for(int x = 0 ; x < staticTreeView.getRoot().getChildren().size() ; x++){
					Node teacherImg = new ImageView(new ImageGetter("instructor1.png").getImage()) ;
					TreeItem<String> teacher = new TreeItem<String>("Teachers" , teacherImg) ;
					Node roomImg = new ImageView(new ImageGetter("house112.png").getImage()) ;
					TreeItem<String> room = new TreeItem<String>("Rooms" , roomImg) ;
					Node courseImg = new ImageView(new ImageGetter("diploma.png").getImage()) ;
					TreeItem<String> course = new TreeItem<String>("Courses" , courseImg) ;
					teacher.setValue("Teachers");
					room.setValue("Rooms");
					course.setValue("Courses");
					staticTreeView.getRoot().getChildren().get(x).getChildren().add(teacher) ;
					staticTreeView.getRoot().getChildren().get(x).getChildren().add(room) ;
					staticTreeView.getRoot().getChildren().get(x).getChildren().add(course) ;
				}
				
				//update Teachers
				result = Connect.QUERY("SELECT * FROM teachers") ;
				
				try {
					while(result.next()){
						//Setting the data to Teacher OBJ
						Teacher data = new Teacher() ;
						data.setTeacher_id(result.getInt(Teacher.TEACHER_ID));
						Department d  = Department.getDepartment(result.getInt(Teacher.DEPT_ID)) ;
						data.setD(d);
						data.setFirst_name(result.getString(Teacher.FIRST_NAME));
						data.setLast_name(result.getString(Teacher.LAST_NAME));
						data.setMiddle_initial(result.getString(Teacher.MIDDLE_INITIAL));
						data.setPicture_path(result.getBytes(Teacher.PICTURE_PATH));
						
						//Creating an Image
						Node teacherImg = new ImageView(new ImageGetter("teacher4.png").getImage()) ;
						
						//Adding data to TreeItemData
						String name = data.getLast_name() + " , " + data.getFirst_name() + " " + data.getMiddle_initial() + "." ;
						TreeItemData teacher = new TreeItemData(name , teacherImg , data) ;
						
						TreeItem<String> root = staticTreeView.getRoot() ;
						
						for(int x = 0 ; x < root.getChildren().size() ; x++){
							TreeItemData treeItem = (TreeItemData) root.getChildren().get(x) ;
							Department dept = (Department) treeItem.getData() ;
							if(dept.getDept_id() == data.getD().getDept_id()){
								root.getChildren().get(x).getChildren().get(teacher_node).getChildren().add(teacher);
							}
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//update rooms
				result = Connect.QUERY("SELECT * FROM rooms") ;
				try {
					while(result.next()){
						//Setting the data to Room OBJ
						Room roomData = new Room() ;
						roomData.setRoom_id(result.getInt(Room.ROOM_ID));
						Department d  = Department.getDepartment(result.getInt(Room.DEPT_ID)) ;
						roomData.setD(d);
						roomData.setRoom_name(result.getString(Room.ROOM_NAME));
						roomData.setRoom_code(result.getString(Room.ROOM_CODE));
						
						//Creating an Image
						Node roomImg = new ImageView(new ImageGetter("home113.png").getImage()) ;
						
						//Adding data to TreeItemData
						TreeItemData room = new TreeItemData(roomData.getRoom_name() , roomImg , roomData) ;
						
						TreeItem<String> root = staticTreeView.getRoot() ;
												
						for(int x = 0 ; x < root.getChildren().size() ; x++){
							TreeItemData treeItem = (TreeItemData) root.getChildren().get(x) ;
							Department dept = (Department) treeItem.getData() ;
							if(dept.getDept_id() == roomData.getD().getDept_id()){
								root.getChildren().get(x).getChildren().get(room_node).getChildren().add(room);
							}
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//update course
				result = Connect.QUERY("SELECT * FROM courses") ;
				try {
					while(result.next()){
						//Setting the data to Room OBJ
						Course course = new Course() ;
						course.setCourse_id(result.getInt(Course.COURSE_ID));
						Department d  = Department.getDepartment(result.getInt(Course.DEPT_ID)) ;
						course.setD(d);
						course.setCourse_name(result.getString(Course.COURSE_NAME));
						course.setCourse_code(result.getString(Course.COURSE_CODE));
						
						//Creating an Image
						Node courseImg = new ImageView(new ImageGetter("certificate3.png").getImage()) ;
						
						//Adding data to TreeItemData
						TreeItemData courseItem = new TreeItemData(course.getCourse_name() , courseImg , course) ;
						
						TreeItem<String> root = staticTreeView.getRoot() ;
												
						for(int x = 0 ; x < root.getChildren().size() ; x++){
							TreeItemData treeItem = (TreeItemData) root.getChildren().get(x) ;
							Department dept = (Department) treeItem.getData() ;
							if(dept.getDept_id() == course.getD().getDept_id()){
								root.getChildren().get(x).getChildren().get(course_node).getChildren().add(courseItem);
							}
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//update Year
				result = Connect.QUERY("SELECT * FROM years") ;
				try {
					while(result.next()){
						//Setting the data to Room OBJ
						Year year = new Year() ;
						year.setYear_id(result.getInt(Year.YEAR_ID));
						year.setYear(result.getInt(Year.YEAR));
						Course course = new Course() ;
						course = Course.getCourse(result.getInt(Year.COURSE_ID)) ;
						year.setCourse(course) ;
						
						//Creating an Image
						Node yearImg = new ImageView(new ImageGetter("persons8.png").getImage()) ;
						
						//Adding data to TreeItemData
						TreeItemData yearItem = new TreeItemData(Year.getYearText(year.getYear()) , yearImg , year) ;
						
						TreeItem<String> root = staticTreeView.getRoot() ;
												
						for(int x = 0 ; x < root.getChildren().size() ; x++){
							TreeItemData treeItem = (TreeItemData) root.getChildren().get(x) ;
							Department dept = (Department) treeItem.getData() ;
							if(dept.getDept_id() == year.getCourse().getD().getDept_id()){
								for(int y = 0 ; y < root.getChildren().get(x).getChildren().get(course_node).getChildren().size() ; y++){
									TreeItemData item = (TreeItemData) root.getChildren().get(x).getChildren().get(course_node).getChildren().get(y) ;
									Course c = (Course)item.getData() ;
									if(c.getCourse_id() == year.getCourse().getCourse_id()){
										root.getChildren().get(x).getChildren().get(course_node).getChildren().get(y).getChildren().add(yearItem) ;
									}
								}
							}
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//update Sections
				result = Connect.QUERY("SELECT * FROM sections");
				try {
					while(result.next()){
						Section section = new Section() ;
						section.setSection_id(result.getInt(Section.SECTION_ID));
						section.setSection(result.getString(Section.SECTION));
						Year year = Year.getYear(result.getInt(Section.YEAR_ID)) ;
						section.setYear(year);
						
						//Creating an Image
						Node sectionImg = new ImageView(new ImageGetter("teamwork.png").getImage()) ;
						
						TreeItemData sectionItem = new TreeItemData(year.getYear() + "" + section.getSection() , sectionImg , section) ;
						//sectionItem.setValue(section.getSection_id() + "");
						
						TreeItem<String> root = staticTreeView.getRoot() ;
						
						for(int x = 0 ; x < root.getChildren().size() ; x++){
							TreeItemData treeItem = (TreeItemData) root.getChildren().get(x) ;
							Department dept = (Department) treeItem.getData() ;
							//find the department
							if(dept.getDept_id() == section.getYear().getCourse().getD().getDept_id()){
								TreeItem<String> courseNode = root.getChildren().get(x).getChildren().get(course_node) ;
								//find the course
								for(int y = 0 ; y < courseNode.getChildren().size() ; y++){
									TreeItemData item = (TreeItemData) courseNode.getChildren().get(y) ;
									Course course = (Course) item.getData() ;
									if(course.getCourse_id() == year.getCourse().getCourse_id()){
										TreeItem<String> current = courseNode.getChildren().get(y) ;
										for(int z = 0 ; z < current.getChildren().size() ; z++){
											TreeItemData yearItem = (TreeItemData) current.getChildren().get(z) ;
											Year yearData = (Year) yearItem.getData() ;
											if(yearData.getYear_id() == year.getYear_id()){
												current.getChildren().get(z).getChildren().add(sectionItem) ;
											}
										}
									}
								}
							}
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

				result = Connect.QUERY("SELECT * FROM subjects") ;
				try {
					while(result.next()){
						Subject subject = new Subject() ;
						subject.setSubject_id(result.getInt(Subject.SUBJECT_ID));
						Year year = Year.getYear(result.getInt(Subject.YEAR_ID)) ;
						subject.setYear(year);
						subject.setSubject_name(result.getString(Subject.SUBJECT_NAME));
						subject.setSubject_code(result.getString(Subject.SUBJECT_CODE));
						subject.setSubject_unit(result.getInt(Subject.SUBJECT_UNIT));
						
						
						
						TreeItem<String> root = Year.getItem(subject.getYear().getYear_id()) ;
						
						for(int x = 0 ; x < root.getChildren().size() ; x++){
							Node subjectImg = new ImageView(new ImageGetter("text87.png").getImage()) ;
							
							TreeItemData subjectItem = new TreeItemData(subject.getSubject_name() , subjectImg , subject) ;
							root.getChildren().get(x).getChildren().add(subjectItem) ;
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				staticTreeView.setCellFactory(new Callback<TreeView<String> , TreeCell<String>>(){

					@Override
					public TreeCell<String> call(TreeView<String> arg0) {
						// TODO Auto-generated method stub
						return new TreeCellFactory();
					}
					
				});
				
		}
			
			
			
	}
	
	//getters
	//get the editable workplace
	public static BorderPane getWorkplacePane(){
		return staticWorkplacePane ;
	}
	
	public static TreeView getTree(){
		return staticTreeView ;
	}
	
	public static Accordion getRightAccordion(){
		return staticRightAccordion ;
	}
	public static TitledPane getDetailsTitledPane(){
		return staticDetailsTitledPane ;
	}
	public static Accordion getLeftAccordion(){
		return staticLeftAccordion ;
	}
	public static TitledPane getHierarchyPane(){
		return staticHierarchyPane ;
	}
	public static VBox getSubjectBox(){
		return staticSubjectBox ;
	}
	
	public static FXMLDocumentController getInstance(){
		return staticInstance ;
	}
}
