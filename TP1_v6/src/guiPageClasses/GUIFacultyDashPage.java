package guiPageClasses;

import java.util.Optional;

import applicationMainMethodClasses.FCMainClass;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog; 
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import databaseClasses.Database;
import entityClasses.User;
import entityClasses.Question;
import entityClasses.QuestionSet;
import entityClasses.ReviewerTrust;
import entityClasses.Answer;
import entityClasses.AnswerSet;
import entityClasses.PrivateMessage;
import entityClasses.WorkRequest;
import javafx.scene.text.*;
import utilityClasses.UnreadAnswerTracker; // added for tracking unread answers
import java.util.ArrayList;

/*******
 * <p> Title: GUIStaffHomePage Class. </p>
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

public class GUIFacultyDashPage {
	
	/**********************************************************************************************

	Attributes
	
	**********************************************************************************************/
	
	// These are the application values required by the user interface
	private Label label_PageTitle = new Label();
	private Label label_UserDetails = new Label();
	
	private Button button_MessageDashboard = new Button("See Faculty Messages");
	private Button button_WorkRequestDashboard = new Button("See WorkRequests");
	private Button button_SubmitMessage = new Button("Message Faculty");
	Button button_createNewWorkRequest = new Button("Create New Work Request");
	private Button button_GoHome = new Button("Return Home");
	
	private Line line_Separator1 = new Line(20, 95, FCMainClass.WINDOW_WIDTH-20, 95);
	
	private Line line_Separator4 = new Line(20, 525, FCMainClass.WINDOW_WIDTH-20,525);
	
	private Button button_Logout = new Button("Logout");
	private Button button_Quit = new Button("Quit");

	private Stage primaryStage;	
	private Pane theRootPane;
	private Database theDatabase;
	private User theUser;
	private ScrollPane messagePaneScroll = new ScrollPane();
	private Pane messagePane;
	
	ArrayList<PrivateMessage> facultyMessages;
	Optional<String> result;

	/**********************************************************************************************

	Constructors
	
	**********************************************************************************************/

	
	/**********
	 * <p> Method: GUIStaffHomePage(Stage ps, Pane theRoot, Database database, User user) </p>
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
	public GUIFacultyDashPage(Stage ps, Pane theRoot, Database database, User user) {
		GUISystemStartUpPage.theFacultyDashPage = this;
		
		FCMainClass.activeHomePage = 5;

		primaryStage = ps;
		theRootPane = theRoot;
		theDatabase = database;
		theUser = user;
		
		double WINDOW_WIDTH = FCMainClass.WINDOW_WIDTH;	
		
		//Create a new pane to house the question objects
				messagePane = new Pane();
				messagePane.setLayoutX(WINDOW_WIDTH/2 - 100);
				messagePane.setLayoutY(100);
				
				//Create a scrollpane to enable scrolling in the question window
				messagePaneScroll.setContent(messagePane);
				messagePaneScroll.setLayoutX(WINDOW_WIDTH/2 - 100);
				messagePaneScroll.setLayoutY(100);
				
				//Lock Window Size to WINDOW_WIDTH/2+100, 360
				messagePaneScroll.setMaxSize(WINDOW_WIDTH/2+100, 360);
				messagePaneScroll.setMinSize(WINDOW_WIDTH/2+100, 360);
		
		primaryStage.setTitle("CSE 360 Foundation Code: Faculty Dashboard");

		// Label the window with the title and other common titles and buttons
		
		label_PageTitle.setText("Faculty Dashboard");
		setupLabelUI(label_PageTitle, "Arial", 28, WINDOW_WIDTH, Pos.CENTER, 0, 5);

		label_UserDetails.setText("User: " + user.getUserName());
		setupLabelUI(label_UserDetails, "Arial", 20, WINDOW_WIDTH, Pos.BASELINE_LEFT, 20, 55);
		
		if(theUser.getInstructorRole()) {
			
			setupButtonUI(button_createNewWorkRequest, "Dialog", 18, 250, Pos.CENTER, 20, 290);
			
			TextInputDialog dialogNewWork = new TextInputDialog();
			dialogNewWork.setTitle("Enter your request");
			dialogNewWork.setHeaderText("Enter your request");
			
			button_createNewWorkRequest.setOnAction((event) -> {result = dialogNewWork.showAndWait();
			if(result.isPresent()) {
				theDatabase.createWorkRequest(theUser.getUserName(), result.get());
				dialogNewWork.getEditor().clear();
			}
			});
			
		}
		
		setupButtonUI(button_MessageDashboard, "Dialog", 18, 250, Pos.CENTER, 20, 150);
		button_MessageDashboard.setOnAction((event) -> {populateMessages(); });
		
		setupButtonUI(button_WorkRequestDashboard, "Dialog", 18, 250, Pos.CENTER, 20, 220);
		button_WorkRequestDashboard.setOnAction((event) -> {populateRequests();
		showRequests(theDatabase.getAllRequests());});
		
		setupButtonUI(button_GoHome, "Dialog", 18, 250, Pos.CENTER, 20, 470);
		button_GoHome.setOnAction((event) -> {goToUserHomePage(); });
		
        setupButtonUI(button_Logout, "Dialog", 18, 250, Pos.CENTER, 20, 540);
        button_Logout.setOnAction((event) -> {performLogout(); });
        
        setupButtonUI(button_Quit, "Dialog", 18, 250, Pos.CENTER, 300, 540);
        button_Quit.setOnAction((event) -> {performQuit(); });
        
        
        setup(theUser);
	}

	
	/**********
	 * <p> Method: setup() </p>
	 * 
	 * <p> Description: This method is called to reset the page and then populate it with new
	 * content.</p>
	 * 
	 */
	public void setup(User user) {
		theUser = user;
		theRootPane.getChildren().clear();		
	    theRootPane.getChildren().addAll(
			label_PageTitle, label_UserDetails, line_Separator1,
	        line_Separator4, 
	        messagePaneScroll,
	        button_GoHome,
	        button_MessageDashboard,
	        button_WorkRequestDashboard,
	        button_Logout,
	        button_Quit
	    );
	    if(theUser.getInstructorRole()) {
	    	theRootPane.getChildren().add(button_createNewWorkRequest);
	    }
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

	
	/**********************************************************************************************

	User Interface Actions for this page
	
	**********************************************************************************************/
	private void goToUserHomePage() {
		if(theUser.getInstructorRole()) {
			if (GUISystemStartUpPage.theInstructorHomePage == null) {
	            GUISystemStartUpPage.theInstructorHomePage =
	                new GUIInstructorHomePage(primaryStage, theRootPane, theDatabase, theUser);
	        } else {
	            GUISystemStartUpPage.theInstructorHomePage.setup();
	        }
		}
		else if (theUser.getStaffRole()){		
		    if (GUISystemStartUpPage.theStaffHomePage == null) {
	            GUISystemStartUpPage.theStaffHomePage =
	                new GUIStaffHomePage(primaryStage, theRootPane, theDatabase, theUser);
	        } else {
	            GUISystemStartUpPage.theStaffHomePage.setup();
	        }
		}
		else {
			if (GUISystemStartUpPage.theAdminHomePage == null) {
	            GUISystemStartUpPage.theAdminHomePage =
	                new GUIAdminHomePage(primaryStage, theRootPane, theDatabase, theUser);
	        } else {
	            GUISystemStartUpPage.theAdminHomePage.setup();
	        }
		}
	}
	
	private void sendFacultyMessage(String message) {
		theDatabase.logFacultyMessage(theUser.getUserName(), message);
		populateMessages();
	}
	
	private void populateMessages() {
		messagePane.getChildren().clear();
		
		TextInputDialog dialogSendMessage = new TextInputDialog();
		dialogSendMessage.setTitle("Enter your message");
		dialogSendMessage.setHeaderText("Enter your message");
		
		setupButtonUI(button_SubmitMessage, "Dialog", 18, 170, Pos.CENTER, 0, 0);
		button_SubmitMessage.setOnAction((event) -> {result = dialogSendMessage.showAndWait();
		if(result.isPresent()) {
			sendFacultyMessage(result.get());
			dialogSendMessage.getEditor().clear();}
		});
		messagePane.getChildren().add(button_SubmitMessage);
		facultyMessages = theDatabase.getAllFacultyMessages();
		Text text;
		for(int i = 0; i < facultyMessages.size(); i++) {
			text = new Text(String.format("%s:\n%s", facultyMessages.get(i).getSender().getUserName(),
					facultyMessages.get(i).getContent()));
			text.setLayoutX(10);
    		text.setLayoutY(60 + (i * 45));
    		messagePane.getChildren().add(text);
		}
	}
	
	private void populateRequests() {
		messagePane.getChildren().clear();
		
		ArrayList<WorkRequest> requests; 
		
		TextInputDialog dialogRequestUpdate = new TextInputDialog();
		dialogRequestUpdate.setTitle("Enter your update to the request");
		dialogRequestUpdate.setHeaderText("Enter your update to the request");
		
		Button button_SeeAllRequests = new Button("See All Requests");
		Button button_SeeOpenRequests = new Button("See Open Requests");
		Button button_SeeClosedRequests = new Button("See Closed Requests");

		setupButtonUI(button_SeeAllRequests, "Dialog", 15, 120, Pos.CENTER, 0, 0);
		button_SeeAllRequests.setOnAction((event) -> {showRequests(theDatabase.getAllRequests());});
		
		setupButtonUI(button_SeeOpenRequests, "Dialog", 15, 120, Pos.CENTER, 150, 0);
		button_SeeOpenRequests.setOnAction((event) -> {showRequests(theDatabase.getAllOpenRequests());});
		
		setupButtonUI(button_SeeClosedRequests, "Dialog", 15, 120, Pos.CENTER, 320, 0);
		button_SeeClosedRequests.setOnAction((event) -> {showRequests(theDatabase.getAllClosedRequests());});
		
		messagePane.getChildren().addAll(button_SeeAllRequests,button_SeeOpenRequests,button_SeeClosedRequests);
	}
	
	private void showRequests(ArrayList<WorkRequest> requests) {
		messagePane.getChildren().clear();
		populateRequests();
		
		Button seeComments;
		Button closeRequest;
		Button reopenRequest;
		WorkRequest request;
		Text text;
		for(int i = 0; i < requests.size()*2; i+=2) {
			request = requests.get(i/2);
			text = new Text(String.format("%s : %s",request.getOriginator().getUserName(), request.getDescription()));
			text.setLayoutY(60 + i*30);
			
			seeComments = new Button("See Comments");
			setupButtonUI(seeComments, "Dialog", 12, 170, Pos.CENTER, 0, 45 + (i+1)*30);
			seeComments.setOnAction((event) -> { Button but = (Button) event.getSource();
				int index = (((int)but.getLayoutY() - 45) / 60);
				viewComments(requests.get(index));
				});
			
			if(request.getClosedStatus() && theUser.getInstructorRole()) {
				reopenRequest = new Button("Reopen Request");
				
				setupButtonUI(reopenRequest, "Dialog", 12, 170, Pos.CENTER, 175, 45 + (i+1)*30);
				reopenRequest.setOnAction((event) -> { Button but = (Button) event.getSource();
					int index = ((int)but.getLayoutY() - 45) / 60;
					theDatabase.reopenWorkRequest(requests.get(index).getDescription());
					but.setDisable(true);});
				messagePane.getChildren().add(reopenRequest);
			}
			else if(!request.getClosedStatus() && theUser.getAdminRole()){
				closeRequest = new Button("Close Request");
				
				setupButtonUI(closeRequest, "Dialog", 12, 170, Pos.CENTER, 175, 45 + (i+1)*30);
				closeRequest.setOnAction((event) -> {Button but = (Button) event.getSource();
				int index = ((int)but.getLayoutY() - 45) / 60;
				theDatabase.closeWorkRequest(requests.get(index).getDescription());
				but.setDisable(true);});
				
				messagePane.getChildren().add(closeRequest);
			}
			messagePane.getChildren().addAll(text, seeComments);
			
		}
	}
	
	private void viewComments(WorkRequest request) {
		messagePane.getChildren().clear();
		ArrayList<PrivateMessage> comments = theDatabase.getComments(request.getDescription());
		Button submitUpdate;
		PrivateMessage comment;
		Text text;
		
		TextInputDialog dialogSendMessage = new TextInputDialog();
		dialogSendMessage.setTitle("Enter your message");
		dialogSendMessage.setHeaderText("Enter your message");
		
		submitUpdate = new Button("Submit an Update");
		setupButtonUI(submitUpdate, "Dialog", 12, 170, Pos.CENTER, 0, 0);
		submitUpdate.setOnAction((event) -> {result = dialogSendMessage.showAndWait();
		if(result.isPresent()) {
				theDatabase.addRequestComment(theUser, request.getDescription(), result.get());
				dialogSendMessage.getEditor().clear();
				viewComments(request);
			}
		});
		messagePane.getChildren().add(submitUpdate);
		
		for(int i = 0; i < comments.size(); i++) {
			comment = comments.get(i);
			text = new Text(String.format("Update from %s: %s", comment.getSender().getUserName(), comment.getContent()));
			text.setLayoutY((i *45)+45);
			messagePane.getChildren().add(text);
		}
		
	}
	
	private void performLogout() {
		GUISystemStartUpPage.theSystemStartupPage.setup();
	}
	
	private void performQuit() {
		System.exit(0);
	}
}
