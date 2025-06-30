package guiPageClasses;

import applicationMainMethodClasses.FCMainClass;
import javafx.geometry.Pos;
//import javafx.scene.control.Alert;	//clay edits: the alert of this not being used was annoying me
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
//import javafx.scene.control.Alert.AlertType; //clay edits: the alert of this not being used was annoying me
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import databaseClasses.Database;
import entityClasses.User;

//NOAH EDITS
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;

/*******
 * <p> Title: GUIInstructorHomePage Class. </p>
 * 
 * <p> Description: The Java/FX-based Single Role Home Page.</p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Lynn Robert Carter
 * 
 * @version 1.00		2025-04-20 Initial version
 *  
 */

public class GUIInstructorHomePage {
	
	/**********************************************************************************************

	Attributes
	
	**********************************************************************************************/
	
	// These are the application values required by the user interface
	private Label label_PageTitle = new Label();
	private Label label_UserDetails = new Label();
	private Button button_UpdateThisUser = new Button("Account Update");

	
	private Line line_Separator1 = new Line(20, 95, FCMainClass.WINDOW_WIDTH-20, 95);
	
	private Line line_Separator4 = new Line(20, 525, FCMainClass.WINDOW_WIDTH-20,525);
	//Clay Edit
	private Button button_AskQuestion = new Button("Question Dashboard");
	private Button button_facultyDash = new Button("Faculty Dashboard");
	private Button button_Logout = new Button("Logout");
	private Button button_Quit = new Button("Quit");

	private Stage primaryStage;	
	private Pane theRootPane;
	private Database theDatabase;
	private User theUser;
//	private Alert alertNotImplemented = new Alert(AlertType.INFORMATION); //Clay edits: alert of it not being used was annoying me

	//NOAH EDITS
	// Table and data for reviewer requests
	private TableView<ReviewerRequestRow> reviewerRequestTable = new TableView<>();
	private ObservableList<ReviewerRequestRow> reviewerRequestsList = FXCollections.observableArrayList();

	/**********************************************************************************************

	Constructors
	
	**********************************************************************************************/

	
	/**********
	 * <p> Method: GUIInstructorHomePage(Stage ps, Pane theRoot, Database database, User user) </p>
	 * 
	 * <p> Description: This method initializes all the elements of the graphical user interface.
	 * This method determines the location, size, font, color, and change and event handlers for
	 * each GUI object. </p>
	 * 
	 * @param ps specifies the JavaFX Stage to be used for this GUI and it's methods
	 * 
	 * @param theRoot specifies the JavaFX Pane to be used for this GUI and it's methods
	 * 
	 * @param database specifies the Database to be used by this GUI and it's methods
	 * 
	 * @param user specifies the User for this GUI and it's methods
	 * 
	 */
	@SuppressWarnings("unused")
	public GUIInstructorHomePage(Stage ps, Pane theRoot, Database database, User user) {
		GUISystemStartUpPage.theInstructorHomePage = this;
		
		FCMainClass.activeHomePage = 2;

		primaryStage = ps;
		theRootPane = theRoot;
		theDatabase = database;
		theUser = user;
		
		double WINDOW_WIDTH = FCMainClass.WINDOW_WIDTH;	
		
		primaryStage.setTitle("CSE 360 Foundation Code: User Home Page");

		// Label the window with the title and other common titles and buttons
		
		label_PageTitle.setText("Instructor Home Page");
		setupLabelUI(label_PageTitle, "Arial", 28, WINDOW_WIDTH, Pos.CENTER, 0, 5);

		label_UserDetails.setText("User: " + user.getUserName());
		setupLabelUI(label_UserDetails, "Arial", 20, WINDOW_WIDTH, Pos.BASELINE_LEFT, 20, 55);
		
		setupButtonUI(button_UpdateThisUser, "Dialog", 18, 170, Pos.CENTER, 610, 45);
		button_UpdateThisUser.setOnAction((event) -> {performUpdate(); });
		//Clay Edits
		setupButtonUI(button_AskQuestion, "Dialog", 18, 190, Pos.CENTER, 20, 110);
	   	button_AskQuestion.setOnAction((event) -> {goToQuestions(); });

		setupButtonUI(button_facultyDash, "Dialog", 18, 190, Pos.CENTER, 220, 110);
	   	button_facultyDash.setOnAction((event) -> {goToFacDash(); });
		
        setupButtonUI(button_Logout, "Dialog", 18, 250, Pos.CENTER, 20, 540);
        button_Logout.setOnAction((event) -> {performLogout(); });
        
        setupButtonUI(button_Quit, "Dialog", 18, 250, Pos.CENTER, 300, 540);
        button_Quit.setOnAction((event) -> {performQuit(); });
        
        //NOAH EDITS: Set up the reviewer request table
        setupReviewerRequestTable();

        setup();
	}

	/**********
	 * <p> Method: setup() </p>
	 * 
	 * <p> Description: This method is called to reset the page and then populate it with new
	 * content.</p>
	 * 
	 */
	public void setup() {
		theRootPane.getChildren().clear();		
	    theRootPane.getChildren().addAll(
			label_PageTitle, label_UserDetails, button_UpdateThisUser, line_Separator1,
	        line_Separator4, 
	        button_AskQuestion,//Clay Edit
	        //NOAH EDITS: Add the table to the GUI
		    button_facultyDash,
	        reviewerRequestTable,
	        button_Logout,
	        button_Quit
	    );
			
	}
	
	/**********
	 * Private local method to initialize the standard fields for a label
	 */
	
	private void setupLabelUI(Label l, String ff, double f, double w, Pos p, double x, double y){
		l.setFont(Font.font(ff, f));
		l.setMinWidth(w);
		l.setAlignment(p);
		l.setLayoutX(x);
		l.setLayoutY(y);		
	}
	
	
	/**********
	 * Private local method to initialize the standard fields for a button
	 * 
	 * @param b		The Button object to be initialized
	 * @param ff	The font to be used
	 * @param f		The size of the font to be used
	 * @param w		The width of the Button
	 * @param p		The alignment (e.g. left, centered, or right)
	 * @param x		The location from the left edge (x axis)
	 * @param y		The location from the top (y axis)
	 */
	private void setupButtonUI(Button b, String ff, double f, double w, Pos p, double x, double y){
		b.setFont(Font.font(ff, f));
		b.setMinWidth(w);
		b.setAlignment(p);
		b.setLayoutX(x);
		b.setLayoutY(y);		
	}

	//NOAH EDITS: Sets up the TableView for reviewer requests
	private void setupReviewerRequestTable() {
		reviewerRequestTable.setLayoutX(20);
		reviewerRequestTable.setLayoutY(170);
		reviewerRequestTable.setMinWidth(670);
		reviewerRequestTable.setMaxWidth(670);
		reviewerRequestTable.setPrefHeight(340);

		TableColumn<ReviewerRequestRow, String> colUsername = new TableColumn<>("Student Username");
		colUsername.setCellValueFactory(new PropertyValueFactory<>("studentUsername"));
		colUsername.setMinWidth(220);

		TableColumn<ReviewerRequestRow, String> colStatus = new TableColumn<>("Status");
		colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
		colStatus.setMinWidth(120);

		TableColumn<ReviewerRequestRow, Button> colApprove = new TableColumn<>("Approve");
		colApprove.setMinWidth(100);
		colApprove.setCellFactory(new Callback<TableColumn<ReviewerRequestRow, Button>, TableCell<ReviewerRequestRow, Button>>() {
			@Override
			public TableCell<ReviewerRequestRow, Button> call(final TableColumn<ReviewerRequestRow, Button> param) {
				return new TableCell<ReviewerRequestRow, Button>() {
					final Button btn = new Button("Approve");
					@Override
					public void updateItem(Button item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							ReviewerRequestRow req = getTableView().getItems().get(getIndex());
							btn.setDisable(!req.getStatus().equals("pending"));
							btn.setOnAction(event -> {
								theDatabase.approveReviewerRequest(req.getRequestId());
								refreshReviewerRequests();
								Alert alert = new Alert(Alert.AlertType.INFORMATION);
								alert.setTitle("Request Approved");
								alert.setHeaderText(null);
								alert.setContentText("Approved reviewer role for " + req.getStudentUsername());
								alert.showAndWait();
							});
							setGraphic(btn);
						}
					}
				};
			}
		});

		TableColumn<ReviewerRequestRow, Button> colDeny = new TableColumn<>("Deny");
		colDeny.setMinWidth(100);
		colDeny.setCellFactory(new Callback<TableColumn<ReviewerRequestRow, Button>, TableCell<ReviewerRequestRow, Button>>() {
			@Override
			public TableCell<ReviewerRequestRow, Button> call(final TableColumn<ReviewerRequestRow, Button> param) {
				return new TableCell<ReviewerRequestRow, Button>() {
					final Button btn = new Button("Deny");
					@Override
					public void updateItem(Button item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							ReviewerRequestRow req = getTableView().getItems().get(getIndex());
							btn.setDisable(!req.getStatus().equals("pending"));
							btn.setOnAction(event -> {
								theDatabase.denyReviewerRequest(req.getRequestId());
								refreshReviewerRequests();
								Alert alert = new Alert(Alert.AlertType.INFORMATION);
								alert.setTitle("Request Denied");
								alert.setHeaderText(null);
								alert.setContentText("Denied reviewer request for " + req.getStudentUsername());
								alert.showAndWait();
							});
							setGraphic(btn);
						}
					}
				};
			}
		});

		reviewerRequestTable.getColumns().addAll(colUsername, colStatus, colApprove, colDeny);
		refreshReviewerRequests();
	}

	//NOAH EDITS: Refreshes the data in the reviewer requests table
	private void refreshReviewerRequests() {
		reviewerRequestsList.clear();
		reviewerRequestsList.addAll(theDatabase.getPendingReviewerRequests());
		reviewerRequestTable.setItems(reviewerRequestsList);
	}

	/**********************************************************************************************

	User Interface Actions for this page
	
	**********************************************************************************************/
	
	private void performUpdate () {
		if (GUISystemStartUpPage.theUserUpdatePage == null)
			GUISystemStartUpPage.theUserUpdatePage = 
				new GUIUserUpdatePage(primaryStage, theRootPane, theDatabase, theUser);
		else
			GUISystemStartUpPage.theUserUpdatePage.setup();	
	}	

	
	private void performLogout() {
		GUISystemStartUpPage.theSystemStartupPage.setup();
	}
	//Clay edits
	private void goToQuestions() {
		if (GUISystemStartUpPage.theStudentQuestionPage == null)
			GUISystemStartUpPage.theStudentQuestionPage = 
				new GUIStudentQuestionPage(primaryStage, theRootPane, theDatabase, theUser);
		else
			GUISystemStartUpPage.theStudentQuestionPage.setup(theUser);//Clay Edit
	}

	private void goToFacDash() {
		if (GUISystemStartUpPage.theFacultyDashPage == null)
			GUISystemStartUpPage.theFacultyDashPage = 
				new GUIFacultyDashPage(primaryStage, theRootPane, theDatabase, theUser);
		else
			GUISystemStartUpPage.theFacultyDashPage.setup(theUser);//Clay Edit
	}

	
	private void performQuit() {
		System.exit(0);
	}

	//NOAH EDITS: Simple POJO for reviewer request table rows
	public static class ReviewerRequestRow {
		private int requestId;
		private String studentUsername;
		private String status;

		public ReviewerRequestRow(int requestId, String studentUsername, String status) {
			this.requestId = requestId;
			this.studentUsername = studentUsername;
			this.status = status;
		}

		public int getRequestId() { return requestId; }
		public String getStudentUsername() { return studentUsername; }
		public String getStatus() { return status; }
	}
}
