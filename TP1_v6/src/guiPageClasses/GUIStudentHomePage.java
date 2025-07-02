package guiPageClasses;

import java.util.ArrayList;
import java.util.List;

import applicationMainMethodClasses.FCMainClass;
import javafx.geometry.Pos;
//import javafx.scene.control.Alert;	//clay edits: the alert of this not being used was annoying me
import javafx.scene.control.Button;
import javafx.scene.control.Label;
//import javafx.scene.control.Alert.AlertType; //clay edits: the alert of this not being used was annoying me
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import databaseClasses.Database;
import entityClasses.Answer;
import entityClasses.PrivateMessage;
import entityClasses.Question;
import entityClasses.User;

/*******
 * <p> Title: GUIReviewerHomePage Class. </p>
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

public class GUIStudentHomePage {
	
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
	private Button button_TrustedReviewers = new Button("Manage Trusted Reviewers");
	private Button button_ReviewerExperiences = new Button("Reviewer Experiences");
	private Button button_Logout = new Button("Logout");
	private Button button_Quit = new Button("Quit");

	private Stage primaryStage;	
	private Pane theRootPane;
	private Database theDatabase;
	private User theUser;
//	private Alert alertNotImplemented = new Alert(AlertType.INFORMATION); //Clay edits: alert of it not being used was annoying me
	/**********************************************************************************************

	Constructors
	
	**********************************************************************************************/

	
	/**********
	 * <p> Method: GUIStudentHomePage(Stage ps, Pane theRoot, Database database, User user) </p>
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
	public GUIStudentHomePage(Stage ps, Pane theRoot, Database database, User user) {
		GUISystemStartUpPage.theStudentHomePage = this;
		
		FCMainClass.activeHomePage = 2;

		primaryStage = ps;
		theRootPane = theRoot;
		theDatabase = database;
		theUser = user;
		
		double WINDOW_WIDTH = FCMainClass.WINDOW_WIDTH;	
		
		primaryStage.setTitle("CSE 360 Foundation Code: User Home Page");

		// Label the window with the title and other common titles and buttons
		
		label_PageTitle.setText("Student Home Page");
		setupLabelUI(label_PageTitle, "Arial", 28, WINDOW_WIDTH, Pos.CENTER, 0, 5);

		label_UserDetails.setText("User: " + user.getUserName());
		setupLabelUI(label_UserDetails, "Arial", 20, WINDOW_WIDTH, Pos.BASELINE_LEFT, 20, 55);
		
		setupButtonUI(button_UpdateThisUser, "Dialog", 18, 170, Pos.CENTER, 610, 45);
		button_UpdateThisUser.setOnAction((event) -> {performUpdate(); });
		//Clay Edits
		setupButtonUI(button_AskQuestion, "Dialog", 18, 190, Pos.CENTER, 20, 110);
	   	button_AskQuestion.setOnAction((event) -> {goToQuestions(); });

		setupButtonUI(button_TrustedReviewers, "Dialog", 18, 190, Pos.CENTER, 20, 150);
	    	button_TrustedReviewers.setOnAction((event) -> {goToTrustedReviewers(); });
	    	
	    setupButtonUI(button_ReviewerExperiences, "Dialog", 18, 190, Pos.CENTER, 20, 190);
	    	button_ReviewerExperiences.setOnAction((event) -> { showReviewerExperiences(); });

        setupButtonUI(button_Logout, "Dialog", 18, 250, Pos.CENTER, 20, 540);
        button_Logout.setOnAction((event) -> {performLogout(); });
        
        setupButtonUI(button_Quit, "Dialog", 18, 250, Pos.CENTER, 300, 540);
        button_Quit.setOnAction((event) -> {performQuit(); });
        
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
		button_TrustedReviewers,
		button_ReviewerExperiences,
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
	
	private void goToTrustedReviewers() {
		if (GUISystemStartUpPage.theStudentTrustedReviewerPage == null)
			GUISystemStartUpPage.theStudentTrustedReviewerPage = 
				new GUIStudentTrustedReviewerPage(primaryStage, theRootPane, theDatabase, theUser);
		else
			GUISystemStartUpPage.theStudentTrustedReviewerPage.setup(theUser);
	}
	
	private void showReviewerExperiences() {
	    theRootPane.getChildren().clear();

	    Label header = new Label("Reviewer Experience Profiles");
	    setupLabelUI(header, "Arial", 24, 600, Pos.CENTER, 100, 20);

	    List<String> reviewers = theDatabase.getUserList();
	    int yOffset = 70;

	    for (String reviewer : reviewers) {
	        if (reviewer.equals("<Select a User>")) continue;

	        String exp = theDatabase.getReviewerExperience(reviewer);
	        if (exp != null && !exp.trim().isEmpty()) {
	            Label nameLabel = new Label(reviewer + ":");
	            setupLabelUI(nameLabel, "Arial", 16, 600, Pos.BASELINE_LEFT, 40, yOffset);

	            Label expLabel = new Label(exp);
	            setupLabelUI(expLabel, "Arial", 14, 600, Pos.BASELINE_LEFT, 60, yOffset + 25);

	            Button viewReviewsBtn = new Button("View Reviews");
	            setupButtonUI(viewReviewsBtn, "Dialog", 12, 150, Pos.CENTER, 60, yOffset + 60);
	            final String reviewerFinal = reviewer;
	            viewReviewsBtn.setOnAction(e -> {
	                showReviewsByReviewer(reviewerFinal);
	            });

	            theRootPane.getChildren().addAll(nameLabel, expLabel, viewReviewsBtn);
	            yOffset += 110;
	        }
	    }

	    Button backButton = new Button("Back");
	    setupButtonUI(backButton, "Dialog", 16, 120, Pos.CENTER, 20, 540);
	    backButton.setOnAction(e -> setup());

	    theRootPane.getChildren().addAll(header, backButton);
	}
	
	private void showReviewsByReviewer(String reviewerUserName) {
	    theRootPane.getChildren().clear();

	    Label header = new Label("Reviews by " + reviewerUserName);
	    setupLabelUI(header, "Arial", 24, 600, Pos.CENTER, 100, 20);

	    int yOffset = 70;
	    boolean foundAny = false;

	    ArrayList<Answer> reviews = theDatabase.getReviewsForReviewer(reviewerUserName);
	    
	    if (reviews.isEmpty()) {
	        Label none = new Label("No reviews found for this reviewer.");
	        setupLabelUI(none, "Arial", 14, 600, Pos.CENTER, 100, 70);
	        theRootPane.getChildren().add(none);
	    } else {
	        int yOffset1 = 70;
	        for (Answer review : reviews) {
	            String context = (review.getQuestion() != null) ? review.getQuestion().getText() : "";
	            Label reviewLabel = new Label(context + "\nReview: " + review.getText());
	            setupLabelUI(reviewLabel, "Arial", 14, 700, Pos.BASELINE_LEFT, 40, yOffset1);
	            theRootPane.getChildren().add(reviewLabel);
	            yOffset1 += 40;

	            // Show private feedback messages sent about this review
	            ArrayList<PrivateMessage> messages = theDatabase.getPrivateMessages(reviewerUserName);
	            
	            for (PrivateMessage pm : messages) {
	                if (pm.getParentText().equals(review.getText())) {
	                    Label messageLabel = new Label("    → Feedback from " + pm.getSender().getUserName() + ": " + pm.getContent());
	                    setupLabelUI(messageLabel, "Arial", 13, 700, Pos.BASELINE_LEFT, 60, yOffset1);
	                    theRootPane.getChildren().add(messageLabel);
	                    yOffset1 += 25;
	                }
	            
	           }


	            yOffset1 += 20; // spacing between review blocks
	        }

	    }

	    if (!foundAny) {
	        Label none = new Label("No reviews found for this reviewer.");
	        setupLabelUI(none, "Arial", 14, 600, Pos.CENTER, 100, yOffset);
	        theRootPane.getChildren().add(none);
	    }

	    Button backButton = new Button("Back");
	    setupButtonUI(backButton, "Dialog", 16, 120, Pos.CENTER, 20, 540);
	    backButton.setOnAction(e -> showReviewerExperiences());

	    theRootPane.getChildren().addAll(header, backButton);
	}






	private void performQuit() {
		System.exit(0);
	}
}
