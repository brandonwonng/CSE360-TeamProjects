package guiPageClasses;

import java.util.Optional;

import applicationMainMethodClasses.FCMainClass;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
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
import entityClasses.Answer;
import entityClasses.AnswerSet;
import entityClasses.PrivateMessage;
import javafx.scene.text.*;
import utilityClasses.UnreadAnswerTracker; // added for tracking unread answers
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

/*******
 * <p> Title: GUIStudentQuestionPage Class. </p>
 * 
 * <p> Description: The Java/FX-based Student Question Page.</p>
 * 
 * <p> Copyright: Clay Hauser © 2025 </p>
 * 
 * @author Clay Hauser
 * 
 * @version 1.00		2025-04-20 Initial version
 *  
 */

public class GUIStudentTrustedReviewerPage{
	
	/**********************************************************************************************

	Attributes
	
	**********************************************************************************************/
	
	// These are the application values required by the user interface
	private Label label_PageTitle = new Label();

    private Label label_Purpose =  new Label("Manage who your trusted reviewers are."); 
    
	private Line line_Separator1 = new Line(20, 95, FCMainClass.WINDOW_WIDTH-20, 95);
	
	private Line line_Separator4 = new Line(20, 525, FCMainClass.WINDOW_WIDTH-20,525);
	
	private Button button_BackToStudentHomePage = new Button("Return to the Student Home Page");
	
	private ScrollPane untrustedReviewerPaneScroll = new ScrollPane();
	private ScrollPane trustedReviewerPaneScroll = new ScrollPane();
	private Button button_Logout = new Button("Logout");
	private Button button_Quit = new Button("Quit");

	private Stage thePrimaryStage;
	private Pane theRootPane;
	private Pane untrustedReviewerPane;
	private Pane trustedReviewerPane;
	private Database theDatabase;
	private User theUser;
	private ArrayList<User> trustedReviewers = new ArrayList<>();
	private ArrayList<User> allReviewers = new ArrayList<>();
	
	Optional<String> result;


	/**********************************************************************************************

	Constructors
	
	**********************************************************************************************/

	
	/**********
	 * <p> Method: GUIAdminUpdatePage(Stage ps, Pane theRoot, Database database, User user) </p>
	 * 
	 * <p> Description: This method initializes all the elements of the graphical user interface
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
	public GUIStudentTrustedReviewerPage(Stage ps, Pane theRoot, Database database, User user) {
		thePrimaryStage = ps;
		theRootPane = theRoot;
		theDatabase = database;
		theUser = user;
		trustedReviewers = user.getTrustedReviewers();
		
		//Set the list of all of the reviewers. Make sure the current user is not on the list even if they are a reviewer
		User reviewer;
		for(String username: database.getUserList()) {
			database.getUserAccountDetails(username);
			if(database.getCurrentReviewerRole() && !username.equals(theUser.getUserName())) {
				reviewer = new User(database.getCurrentUsername(), database.getCurrentPassword(), database.getCurrentAdminRole(), database.getCurrentStudentRole(),
						database.getCurrentReviewerRole(), database.getCurrentInstructorRole(), database.getCurrentStaffRole());
				allReviewers.add(reviewer);
			}
		}
		
		double WINDOW_WIDTH = FCMainClass.WINDOW_WIDTH;
		
		//Create a new pane to house the question objects
		untrustedReviewerPane = new Pane();
		untrustedReviewerPane.setLayoutX(WINDOW_WIDTH/2);
		untrustedReviewerPane.setLayoutY(100);
		
		//Create a new pane to house the question objects
		trustedReviewerPane = new Pane();
		trustedReviewerPane.setLayoutX(0);
		trustedReviewerPane.setLayoutY(100);
		
		//Create a scrollpane to enable scrolling for viewing your current "Untrusted Reviewers"
		untrustedReviewerPaneScroll.setContent(untrustedReviewerPane);
		untrustedReviewerPaneScroll.setLayoutX(WINDOW_WIDTH/2);
		untrustedReviewerPaneScroll.setLayoutY(100);
		//Lock Window Size to WINDOW_WIDTH/2+100, 360
		untrustedReviewerPaneScroll.setMaxSize(WINDOW_WIDTH/2, 360);
		untrustedReviewerPaneScroll.setMinSize(WINDOW_WIDTH/2, 360);
		
		//Create a scrollpane to enable scrolling for viewing your current "Trusted Reviewers"
		trustedReviewerPaneScroll.setContent(trustedReviewerPane);
		trustedReviewerPaneScroll.setLayoutX(0);
		trustedReviewerPaneScroll.setLayoutY(100);
		//Lock Window Size to WINDOW_WIDTH/2+100, 360
		trustedReviewerPaneScroll.setMaxSize(WINDOW_WIDTH/2, 360);
		trustedReviewerPaneScroll.setMinSize(WINDOW_WIDTH/2, 360);
		
		//Set the stage title
		thePrimaryStage.setTitle("CSE 360 Foundation Code: Student Trusted Reviewer Page");
		
		label_PageTitle.setText("Student Trusted Reviewer Page");
		setupLabelUI(label_PageTitle, "Arial", 28, WINDOW_WIDTH, Pos.CENTER, 0, 5);
		
		setupLabelUI(label_Purpose, "Arial", 10, WINDOW_WIDTH, Pos.CENTER, 0, 35);
		
		//Set up for all main buttons on the page
		
        setupButtonUI(button_BackToStudentHomePage, "Dialog", 18, 300, 
        		Pos.CENTER, WINDOW_WIDTH/2-150, 475);
        button_BackToStudentHomePage.setOnAction((event) -> {goToStudentHomePage();});
		
        setupButtonUI(button_Logout, "Dialog", 18, 250, Pos.CENTER, 20, 540);
        button_Logout.setOnAction((event) -> {performLogout(); });
        
        setupButtonUI(button_Quit, "Dialog", 18, 250, Pos.CENTER, 300, 540);
        button_Quit.setOnAction((event) -> {performQuit(); });
        
        
        // Place all of the just-initialized GUI elements into the pane
        theRoot.getChildren().clear();
        theRoot.getChildren().addAll(label_PageTitle,
        		untrustedReviewerPaneScroll,
        		trustedReviewerPaneScroll,
           		label_Purpose,
        		button_BackToStudentHomePage, 
        		line_Separator4,
        		line_Separator1,
        		button_Logout,
        		button_Quit
        		);
        trustedReviewerPane.getChildren().clear();
        untrustedReviewerPane.getChildren().clear();
        populateTrustedReviewers();
        populateUntrustedReviewers();
        
	}
	
	
	public void setup(User user) {
		theUser = user; //Clay Edit
		allReviewers.clear();
		generateAllReviewers();
		trustedReviewers = user.getTrustedReviewers();
		theRootPane.getChildren().clear();
		theRootPane.getChildren().addAll(label_PageTitle,
        		untrustedReviewerPaneScroll,
        		trustedReviewerPaneScroll,
           		label_Purpose,
        		button_BackToStudentHomePage, 
        		line_Separator4,
        		line_Separator1,
        		button_Logout,
        		button_Quit
    		);
		trustedReviewerPane.getChildren().clear();
        untrustedReviewerPane.getChildren().clear();
        populateTrustedReviewers();
        populateUntrustedReviewers();
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

	/**********
	 * Private local method to initialize the standard fields for a text field
	 *
	private void setupTextUI(TextField t, String ff, double f, double w, Pos p, double x, double y, boolean e){
		t.setFont(Font.font(ff, f));
		t.setMinWidth(w);
		t.setMaxWidth(w);
		t.setAlignment(p);
		t.setLayoutX(x);
		t.setLayoutY(y);		
		t.setEditable(e);
	}	
	
	/**********************************************************************************************

	User Interface Actions for this page
	
	**********************************************************************************************/
	
	private void goToStudentHomePage() {
        // Proceed to the student account update page
		if (GUISystemStartUpPage.theStudentHomePage == null) {
			GUISystemStartUpPage.theStudentHomePage = 
					new GUIStudentHomePage(thePrimaryStage, theRootPane, theDatabase, theUser);
		}
		else	
			GUISystemStartUpPage.theStudentHomePage.setup();
	}
	
	private void populateTrustedReviewers() {
		trustedReviewerPane.getChildren().clear();
		
		Text reviewerName;
		Button removeTrust;
		for(int i = 0; i < trustedReviewers.size(); i++) {
			reviewerName = new Text(trustedReviewers.get(i).getUserName());
			reviewerName.setLayoutX(100);
			reviewerName.setLayoutY(15+(i*40));
			removeTrust = new Button("Remove"); 
			setupButtonUI(removeTrust, "Dialog", 12, 0, 
            		Pos.CENTER, 0,(i * 40));
			removeTrust.setOnAction((event) -> {
    			Button but = (Button) event.getSource();
    			User theUntrusted = trustedReviewers.get((int)but.getLayoutY()/40);
    			trustedReviewers.remove(theUntrusted);
    			System.out.println(theUser.getTrustedReviewers());
    			allReviewers.add(theUntrusted);
    			populateUntrustedReviewers();
    			populateTrustedReviewers();
    			});
			trustedReviewerPane.getChildren().addAll(removeTrust, reviewerName);
		}
	}
	
	private void populateUntrustedReviewers() {
        untrustedReviewerPane.getChildren().clear();
        
		Text reviewerName;
		Button giveTrust;
		for(int i = 0; i < allReviewers.size(); i++) {
			reviewerName = new Text(allReviewers.get(i).getUserName());
			reviewerName.setLayoutX(100);
			reviewerName.setLayoutY(15+(i*40));
			giveTrust = new Button("Give Trust"); 
			setupButtonUI(giveTrust, "Dialog", 12, 0, 
            		Pos.CENTER, 0, (i * 40));
			giveTrust.setOnAction((event) -> {
    			Button but = (Button) event.getSource();
    			User theTrusted = allReviewers.get((int)but.getLayoutY()/40);
    			trustedReviewers.add(theTrusted);
    			System.out.println(theUser.getTrustedReviewers());
    			allReviewers.remove(theTrusted);
    			populateUntrustedReviewers();
    			populateTrustedReviewers();
    			});
			untrustedReviewerPane.getChildren().addAll(giveTrust, reviewerName);
		}
	}
	
	private void generateAllReviewers() {
		//Set the list of all of the reviewers. Make sure the current user is not on the list even if they are a reviewer
		User reviewer;
		boolean flag;
		for(String username: theDatabase.getUserList()) {
			flag = false;
			for(User user : theUser.getTrustedReviewers()) {
				if (user.getUserName().equals(username)) {
					flag = true;
					break;}
			}
			
			theDatabase.getUserAccountDetails(username);
			if(theDatabase.getCurrentReviewerRole() && !username.equals(theUser.getUserName()) && !flag) {
				reviewer = new User(theDatabase.getCurrentUsername(), theDatabase.getCurrentPassword(), theDatabase.getCurrentAdminRole(), theDatabase.getCurrentStudentRole(),
						theDatabase.getCurrentReviewerRole(), theDatabase.getCurrentInstructorRole(), theDatabase.getCurrentStaffRole());
				allReviewers.add(reviewer);
			}
		}
	}
	
	private void performLogout() {
		GUISystemStartUpPage.theSystemStartupPage.setup();
	}

	
	private void performQuit() {
		System.out.println("Perform Quit");
		System.exit(0);
	}

	//Team functions
	
}
