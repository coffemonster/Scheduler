package application.main;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.* ;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
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
import java.sql.Time;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.ResourceBundle ;

/*
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.DialogStyle;
import org.controlsfx.dialog.Dialogs;
import org.controlsfx.dialog.Dialogs.UserInfo;
*/















import java.util.Vector;

import org.controlsfx.control.Notifications;
import org.controlsfx.dialog.Dialogs;

import congcrete.Account;
import congcrete.Course;
import congcrete.Department;
import congcrete.Room;
import congcrete.Section;
import congcrete.Subject;
import congcrete.Teacher;
import congcrete.TimeSlot;
import congcrete.Year;
import database.Connect;
import application.User;
import application.list.SubjectList;
import application.menu.CourseContextMenu;
import application.menu.RoomContextMenu;
import application.menu.SectionContextMenu;
import application.menu.SubjectContextMenu;
import application.menu.TeacherContextMenu;
import application.menu.YearContextMenu;
import application.priority.PriorityTime;
import application.scheduler.HybridScheduling;
import application.scheduler.Scheduler;
import application.scheduler.SimpleScheduler;
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
	@FXML private ImageView subjectImageView ;
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
	@FXML private ListView accountsList ;
	@FXML private TabPane tabPaneWorlplace ;
	@FXML private TabPane topTabPane ;
	@FXML private Label connectionStatus ;
	@FXML private Label lblUpdate ;
	@FXML private TitledPane tpSubjects;
	@FXML private TitledPane recentLogs;
	@FXML private ImageView rtsScheduling ;
	@FXML private ImageView srtsScheduling ;
	@FXML private ImageView logsImageView ;
	@FXML private ImageView scheduleImageView ;
	private ScaleAnimationProperty scaleProperty ;
	private NodeAnimation animation ;
	private static FXMLDocumentController staticInstance ;
	private TeacherContextMenu teacherMenu ;
	private RoomContextMenu roomMenu ;
	private SectionContextMenu sectionMenu ;
	private application.menu.DepartmentContextMenu departmentMenu ;
	private CourseContextMenu courseMenu ;
	private YearContextMenu yearMenu ;
	private SubjectContextMenu subjectMenu ;
	
	/*
	 * MENU CONTROLS HANDLER
	 */
	//Show Add in center pane
	@FXML public void handleAddToCenter(MouseEvent e) throws Exception{
		
		//new Scheduler(1).start();
		
		SplitPane p = new SplitPane();
		
		String resourceURL = null ;
		ImageView ev = (ImageView)e.getSource() ;
		
		String text = null ;
		
		if(ev.getId().equals("departmentImageView")){
			text = "Add Department" ;
			resourceURL = "/application/department/addDepartmentDocument.fxml" ;
		}else if(ev.getId().equals("teacherImageView")){
			text = "Add Teacher" ;
			resourceURL = "/application/teacher/addTeacherDocument.fxml" ;
		}else if(ev.getId().equals("roomImageView")){
			resourceURL = "/application/room/addRoomDocument.fxml" ;
			text = "Add Room" ;
		}else if(ev.getId().equals("courseImageView")){
			resourceURL = "/application/course/addCourseDocument.fxml" ;
			text = "Add Course" ;
		}else if(ev.getId().equals("yearImageView")){
			resourceURL = "/application/Year/addYearDocument.fxml" ;
			text = "Add Year" ;
		}else if(ev.getId().equals("sectionImageView")){
			text = "Add Section";
			resourceURL = "/application/section/addSectionDocument.fxml" ;
		}else if(ev.getId().equals("subjectImageView")){
			resourceURL = "/application/Subject/addSubjectDocument.fxml" ;
			text = "Add Subject" ;
			
		}
		
		Pane child = FXMLLoader.load(getClass().getResource(resourceURL)) ;
		
		node.NodeUtils.addToCenter(child, text);
		BounceInTransition bounce = new BounceInTransition(child) ;
		/*
		animation = new ScaleAnimation(scaleProperty);
		animation.animate(child);
		*/
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
	
	@FXML public void handleRTS(MouseEvent e){
		SimpleScheduler sc = new SimpleScheduler(1) ;
		sc.start();
		Scheduler.setRoomsList(sc.roomsList);
		Scheduler.setTeachersList(sc.teachersList);
		Scheduler.setSectionList(sc.sectionList);
	}
	
	@FXML public void handleSRTS(MouseEvent e){
		HybridScheduling sc = new HybridScheduling(1) ;
		sc.start();
		Scheduler.setRoomsList(sc.getRoomsList());
		Scheduler.setTeachersList(sc.getTeachersList());
		Scheduler.setSectionList(sc.getSectionList());
	}

	@Override public void initialize(URL url , ResourceBundle rs){
		
		//PRIVILEDGE
		if(!User.isAdmin() && !User.getPrivilege().contains(new Integer(1))){
			departmentImageView.setDisable(true);
			teacherImageView.setDisable(true);
			courseImageView.setDisable(true);
			roomImageView.setDisable(true);
			yearImageView.setDisable(true);
			sectionImageView.setDisable(true);
			subjectImageView.setDisable(true);
			System.out.print(!User.getPrivilege().contains(new Integer(1)));
		}
		if(!User.isAdmin() && !User.getPrivilege().contains(new Integer(0))){
			leftAccordion.getPanes().remove(hierarchyPane) ;
		}
		if(!User.isAdmin() && !User.getPrivilege().contains(new Integer(4))){
			leftAccordion.getPanes().remove(recentLogs) ;
		}
		if(!User.isAdmin() && !User.isAdmin()){
			rtsScheduling.setDisable(true);
			srtsScheduling.setDisable(true);
		}
		if(!User.isAdmin() && !User.getPrivilege().contains(new Integer(6))){
			scheduleImageView.setDisable(true);
		}
		if(!User.isAdmin() && !User.getPrivilege().contains(new Integer(7))){
			logsImageView.setDisable(true);
		}
		
		//Initilize connection status
		ImageView statusImage = new ImageView(new ImageGetter("check.png").getImage()) ;
		statusImage.setFitHeight(16);
		statusImage.setFitWidth(16);
		connectionStatus.setGraphic(statusImage);
		connectionStatus.setText("Connected");
		
		if(User.isAdmin()){
			//Set the property listener for account list
			accountsList.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Object>(){

				@Override
				public void changed(ObservableValue<? extends Object> arg0,
						Object arg1, Object arg2) {
					if(FXMLDocumentController.getInstance().getAccountsList().getSelectionModel().getSelectedItem() == null){
						return ;
					}
					
					FXMLLoader loader = new FXMLLoader() ;
					loader.setLocation(getClass().getResource("/application/properties/userProperties.fxml"));
					loader.setResources(new ResourceBundle(){

						@Override
						public Enumeration<String> getKeys() {
							Vector<String> key = new Vector<String>() ;
							key.add("account") ;
							return (Enumeration<String>) key;
						}

						@Override
						protected Object handleGetObject(String arg0) {
							Account account = Account.getAccount(FXMLDocumentController.getInstance().getAccountsList().getSelectionModel().getSelectedItem().toString()) ;
							return account ;
						}
						
					});
					
					try {
						AnchorPane details = loader.load() ;
						ScrollPane sp = (ScrollPane) FXMLDocumentController.getInstance().getDetailsTitledPane().getContent() ;
						details.setPrefWidth(sp.getWidth() - 5);
						sp.setContent(details);
						
						
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					FXMLDocumentController.getInstance().getRightAccordion().setExpandedPane(FXMLDocumentController.getInstance().getDetailsTitledPane()) ;
				}
				
			});
		}
		
		//show the accounts list
		updateAccounts() ;
		
		//teacher Context menu
		teacherMenu = new TeacherContextMenu() ;
		roomMenu = new RoomContextMenu() ;
		sectionMenu = new SectionContextMenu() ;
		departmentMenu = new application.menu.DepartmentContextMenu() ;
		courseMenu = new CourseContextMenu(); 
		yearMenu = new YearContextMenu() ;
		subjectMenu = new SubjectContextMenu() ;
		sectionMenu.setAutoHide(true);
		roomMenu.setAutoHide(true);
		teacherMenu.setAutoHide(true);
		departmentMenu.setAutoHide(true);
		courseMenu.setAutoHide(true);
		yearMenu.setAutoHide(true);
		subjectMenu.setAutoHide(true);
		
		
		treeView.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent e) {
				teacherMenu.hide();
				roomMenu.hide();
				sectionMenu.hide();
				departmentMenu.hide();
				courseMenu.hide();
				yearMenu.hide();
				subjectMenu.hide(); 
				
				//teacherItemData is for the whole
				if(e.getButton() == MouseButton.SECONDARY){
					TreeItem<String> teacherItem = treeView.getSelectionModel().getSelectedItem() ;
					if(teacherItem instanceof TreeItemData){
						TreeItemData teacherItemData = (TreeItemData)teacherItem ;
						if(teacherItemData.getData() instanceof Teacher){
							teacherMenu.show(treeView , e.getScreenX() , e.getScreenY());
						}else if(teacherItemData.getData() instanceof Room){
							roomMenu.show(treeView , e.getScreenX() , e.getScreenY());
						}else if(teacherItemData.getData() instanceof Section){
							sectionMenu.show(treeView , e.getScreenX() , e.getScreenY());
						}else if(teacherItemData.getData() instanceof Department){
							departmentMenu.show(treeView , e.getScreenX() , e.getScreenY());
						}else if(teacherItemData.getData() instanceof Course){
							courseMenu.show(treeView , e.getScreenX() , e.getScreenY());
						}else if(teacherItemData.getData() instanceof Year){
							yearMenu.show(treeView , e.getScreenX() , e.getScreenY());
						}else if(teacherItemData.getData() instanceof Subject){
							if(teacherItem.getParent().getValue().equalsIgnoreCase("Subjects")){
								if(!(teacherItem.getParent() instanceof TreeItemData)){
									subjectMenu.show(treeView , e.getScreenX() , e.getScreenY());
								}
							}
						}
					}else{
						e.consume();
					}
				}
			}			
		});
		
		treeView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Object>(){
			@Override
			public void changed(ObservableValue<? extends Object> arg0,
					Object arg1, Object arg2) {
				teacherMenu.hide();
				roomMenu.hide();
				sectionMenu.hide();
				departmentMenu.hide();
			}
			
		});
		
		
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
		
		//set Property for adding at worplacePane
		scaleProperty = new ScaleAnimationProperty(.1 , 1 , .1 , 1 , Duration.millis(200) , 1) ;
		//Setting School as TreeView root
		Node img = new ImageView(new ImageGetter("school47.png").getImage()) ;
		treeView.setRoot(new TreeItem<String>("School" , img));
				
		updateTree();
		//set the school expanded
		treeView.getRoot().setExpanded(true);
		//set the Hierarchy as Open
		leftAccordion.setExpandedPane(hierarchyPane); 
					
		//set the details if any Node are selected
		treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem>(){
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
						e.printStackTrace();
					}
				}else if(itemObject instanceof Department){
					try {
						AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/properties/departmentProperties.fxml")) ;
						pane.setPrefWidth(detailsScrollPane.getWidth() - 5) ;
						detailsScrollPane.setContent(pane);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else if(itemObject instanceof Course){
					try {
						AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/properties/courseProperties.fxml")) ;
						pane.setPrefWidth(detailsScrollPane.getWidth() - 5);
						detailsScrollPane.setContent(pane);
					} catch (IOException e) {
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
						detailsScrollPane.setContent(pane) ;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else if(itemObject instanceof Section){
					try {
						AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/properties/sectionProperties.fxml")) ;
						pane.setPrefWidth(detailsScrollPane.getWidth() - 5);
						detailsScrollPane.setContent(pane);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else if(itemObject instanceof Subject){
					try {
						TreeItem<String> temp = treeView.getSelectionModel().getSelectedItem() ;
						if(temp.getParent().getValue().equalsIgnoreCase("Subjects")){
							if(!(temp.getParent() instanceof TreeItemData)){
								AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/properties/subjectMainProperties.fxml")) ;
								pane.setPrefWidth(detailsScrollPane.getWidth() - 5);
								detailsScrollPane.setContent(pane);
								return ;
							}
						}
						AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/properties/subjectProperties.fxml")) ;
						pane.setPrefWidth(detailsScrollPane.getWidth() - 5);
						detailsScrollPane.setContent(pane);
					} catch (IOException e) {
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
				FXMLDocumentController.getInstance().getTree().getRoot().getChildren().clear();
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
						TreeItemData deptTreeItem = new TreeItemData(department.getDept_code() , deptImg , department);
						
						//TreeCell<String> cell = new TreeCell<String>() ;
						//FXMLDocumentController.getTree().getRoot().getChildren().add(cell);
						
						
						FXMLDocumentController.getInstance().getTree().getRoot().getChildren().add(deptTreeItem);
					}
				}catch(SQLException e){
					e.printStackTrace();
				}
				
				//Adding the teacher , room , course to Department
				TreeView<String> tree = FXMLDocumentController.getInstance().getTree() ;
				for(int x = 0 ; x < tree.getRoot().getChildren().size() ; x++){
					Node teacherImg = new ImageView(new ImageGetter("instructor1.png").getImage()) ;
					TreeItem<String> teacher = new TreeItem<String>("Teachers" , teacherImg) ;
					Node roomImg = new ImageView(new ImageGetter("house112.png").getImage()) ;
					TreeItem<String> room = new TreeItem<String>("Rooms" , roomImg) ;
					Node courseImg = new ImageView(new ImageGetter("diploma.png").getImage()) ;
					TreeItem<String> course = new TreeItem<String>("Courses" , courseImg) ;
					teacher.setValue("Teachers");
					room.setValue("Rooms");
					course.setValue("Courses");
					tree.getRoot().getChildren().get(x).getChildren().add(teacher) ;
					tree.getRoot().getChildren().get(x).getChildren().add(room) ;
					tree.getRoot().getChildren().get(x).getChildren().add(course) ;
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
						
						TreeItem<String> root = tree.getRoot() ;
						
						for(int x = 0 ; x < root.getChildren().size() ; x++){
							TreeItemData treeItem = (TreeItemData) root.getChildren().get(x) ;
							Department dept = (Department) treeItem.getData() ;
							if(dept.getDept_id() == data.getD().getDept_id()){
								root.getChildren().get(x).getChildren().get(teacher_node).getChildren().add(teacher);
							}
						}
					}
				} catch (SQLException e) {
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
						TreeItemData room = new TreeItemData(roomData.getRoom_code() , roomImg , roomData) ;
						
						TreeItem<String> root = tree.getRoot() ;
												
						for(int x = 0 ; x < root.getChildren().size() ; x++){
							TreeItemData treeItem = (TreeItemData) root.getChildren().get(x) ;
							Department dept = (Department) treeItem.getData() ;
							if(dept.getDept_id() == roomData.getD().getDept_id()){
								root.getChildren().get(x).getChildren().get(room_node).getChildren().add(room);
							}
						}
					}
				} catch (SQLException e) {
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
						TreeItemData courseItem = new TreeItemData(course.getCourse_code(), courseImg , course) ;
						
						TreeItem<String> root = tree.getRoot() ;
												
						for(int x = 0 ; x < root.getChildren().size() ; x++){
							TreeItemData treeItem = (TreeItemData) root.getChildren().get(x) ;
							Department dept = (Department) treeItem.getData() ;
							if(dept.getDept_id() == course.getD().getDept_id()){
								root.getChildren().get(x).getChildren().get(course_node).getChildren().add(courseItem);
							}
						}
					}
				} catch (SQLException e) {
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
						
						ImageView subjectImage = new ImageView(new ImageGetter("text87.png").getImage()) ;
						TreeItem<String> Subjects = new TreeItem<String>("Subjects" , subjectImage) ;
						
						yearItem.getChildren().add(Subjects) ;
						
						TreeItem<String> root = tree.getRoot() ;
												
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
						
						TreeItem<String> root = tree.getRoot() ;
						
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
					e.printStackTrace();
				}
				
				
				tree.setCellFactory(new Callback<TreeView<String> , TreeCell<String>>(){

					@Override
					public TreeCell<String> call(TreeView<String> arg0) {
						return new TreeCellFactory();
					}
					
				});
				
		}
			
			
			
	}
	
	public void updateAccounts(){
		ArrayList<Account> accounts = User.getAccountsList() ;
		accountsList.getItems().clear();
		for(int x = 0 ; x < accounts.size() ; x++){
			accountsList.getItems().add(accounts.get(x).getUsername()) ;
		}
		accountsList.setCellFactory(cell -> 
		 new ListCell<String>(){
				@Override
		        public void updateItem(String item, boolean empty) {
		            super.updateItem(item, empty);
		            if (item != null) {
		                ImageView image ;
		                image = new ImageView(new NodeUtils.ImageGetter("male157.png").getImage()) ;
		                setGraphic(image);
		                
		                if(!accounts.get(getIndex()).isEnabled()){
		                	setStyle("-fx-background-color:red") ;
		                }
		                
		                setText(item);
		            }
		        }
			});
	}
	
	@FXML public void handlePrintLogs(MouseEvent e){
		PrinterJob job = PrinterJob.createPrinterJob();
		 if (job != null) {
			 job.showPrintDialog(Main.getMainStage()) ;
			
		    boolean success = job.printPage(FXMLDocumentController.getInstance().getAccountsList());
		    if (success) {
		        job.endJob();
		    }
		 }
	}
	
	@FXML public void handlePrintSchedule(MouseEvent e){
		if(FXMLDocumentController.getInstance().getTree().getSelectionModel().getSelectedIndex() == -1 || !(FXMLDocumentController.getInstance().getTree().getSelectionModel().getSelectedItem() instanceof TreeItemData)){
			Dialogs.create()
			.title("Error")
			.message("Please select a Teacher/Room/Section")
			.showError() ;
			return ;
		}
		
		TreeItem<String> item = (TreeItem<String>) FXMLDocumentController.getInstance().getTree().getSelectionModel().getSelectedItem() ;
		Object obj = TreeItemData.getItemData(item) ;
		
		if(!(obj instanceof Teacher) && !(obj instanceof Section) && !(obj instanceof Room)){
			Dialogs.create()
			.title("Error")
			.message("Please select a Teacher/Room/Section")
			.showError() ;
			return ;
		}
		
		TableView table = Scheduler.viewTeacherSchedule((Teacher)obj) ;
		
		//Printer printer = Printer.getDefaultPrinter();
		//PageLayout pageLayout = printer.createPageLayout(Paper.NA_LETTER, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);

		PrinterJob job = PrinterJob.createPrinterJob();
		 if (job != null) {
			 job.showPrintDialog(Main.getMainStage()) ;
			
		    boolean success = job.printPage(table);
		    if (success) {
		        job.endJob();
		    }
		 }
		
	}
	
	public ListView getAccountsList() {
		return accountsList;
	}

	public TabPane getTabpane(){
		return tabPaneWorlplace ;
	}
	
	public TabPane getTopTabPane() {
		return topTabPane;
	}

	public TreeView getTree(){
		return treeView ;
	}
	
	public Accordion getRightAccordion(){
		return rightAccordion ;
	}
	public TitledPane getDetailsTitledPane(){
		return detailsTitledPane ;
	}
	public Accordion getLeftAccordion(){
		return leftAccordion ;
	}
	public TitledPane getHierarchyPane(){
		return hierarchyPane ;
	}
	public VBox getSubjectBox(){
		return subjectBox ;
	}
	
	public static FXMLDocumentController getInstance(){
		return staticInstance ;
	}
}
