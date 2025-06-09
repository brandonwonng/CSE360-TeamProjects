package guiPageClasses;

import java.sql.SQLException;

import applicationMainMethodClasses.FCMainClass;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import databaseClasses.Database;
import entityClasses.User;

/*******
 * <p> Title: GUIStartupPage Class. </p>
 * 
 * <p> Description: The Java/FX-based System Startup Page.</p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Lynn Robert Carter
 * 
 * @version 1.00		2025-04-20 Initial version
 *  
 */

public class GUISystemStartUpPage {
	
	/**********************************************************************************************

	Attributes
	
	**********************************************************************************************/
	
	// These are the application values required by the user interface
	
	// This set is for the very first start of the system
	private boolean firstInvocation = true;
	private Label label_ApplicationTitle = new Label("Foundation Application Startup Page");
    private Label label_FirstUserLine1 = 
    		new Label(" You are the first user.  You must be an administrator.");
    private Label label_FirstUserLine2 = 
    		new Label("Enter the Admin's Username, the Password twice, and then click on " + 
    				"Setup Admin Account.");
    private Label label_PasswordsDoNotMatch = new Label();
	private TextField text_AdminUsername = new TextField();
	private PasswordField text_AdminPassword1 = new PasswordField();
	private PasswordField text_AdminPassword2 = new PasswordField();
	private Button button_AdminSetup = new Button("Setup Admin Account");
	private String adminUsername = "";
	private String adminPassword1 = "";
	private String adminPassword2 = "";		
	
	// This set is for all subsequent starts of the system
	private Label label_OperationalStartTitle = new Label("Log In or Invited User Account Setup ");
	private Label label_LogInInsrtuctions = new Label("Enter your user name and password and "+	
				"then click on the LogIn button");
    private Alert alertUsernamePasswordError = new Alert(AlertType.INFORMATION);

    
	private User user;
    private Label label_NewUserCreation = new Label(" User Account Creation.");
    private Label label_NewUserLine = new Label("Please enter a username and a password.");
    private TextField text_Username = new TextField();
	private PasswordField text_Password1 = new PasswordField();
	private PasswordField text_Password2 = new PasswordField();
	private PasswordField text_Password = new PasswordField();
	private Button button_UserSetup = new Button("User Setup");
	private Button button_Login = new Button("Log In");	
	
	private Label label_AccountSetupInsrtuctions = new Label("No account? "+	
			"Enter your invitation code and click on the Account Setup button");
	private TextField text_Invitation = new TextField();
	private Button button_SetupAccount = new Button("Setup Account");
    private Alert alertInvitationCodeIsInvalid = new Alert(AlertType.INFORMATION);
	
	private Button button_Quit = new Button("Quit");

	private Stage primaryStage;	
	private Pane theRootPane;
	
	/**********************************************************************************************

	These singleton entity objects are the heart of the Graphical User Interface
	
	**********************************************************************************************/

	
	/**********
	 * <p> Public Static Singleton: theAddRemoveRolesPage </p>
	 * 
	 * <p> Description: This object is the template for adding and removing roles from a user. </p>
	 *
	 */
	public static GUIAddRemoveRolesPage theAddRemoveRolesPage = null;

	
	/**********
	 * <p> Public Static Singleton: theAdminHomePage </p>
	 * 
	 * <p> Description: This object is the template for administrative activities. </p>
	 *
	 */
	public static GUIAdminHomePage theAdminHomePage = null;

	
	/**********
	 * <p> Public Static Singleton: theAdminUpdatePage </p>
	 * 
	 * <p> Description: This object is the template for updating the Admin attributes. </p>
	 *
	 */
	public static GUIAdminUpdatePage theAdminUpdatePage = null;

	
	/**********
	 * <p> Public Static Singleton: theAdminUpdatePage </p>
	 * 
	 * <p> Description: This object is the template for Instructor role activities. </p>
	 *
	 */
	public static GUIInstructorHomePage theInstructorHomePage = null;

	
	/**********
	 * <p> Public Static Singleton: theMultipleRoleDispatchPage </p>
	 * 
	 * <p> Description: This object is the template for interacting with a user and allow the user
	 * to select one of the several roles they play. </p>
	 *
	 */
	public static GUIMultipleRoleDispatchPage theMultipleRoleDispatchPage = null;

	
	/**********
	 * <p> Public Static Singleton: theReviewerHomePage </p>
	 * 
	 * <p> Description: This object is the template for Reviewer role activities. </p>
	 *
	 */
	public static GUIReviewerHomePage theReviewerHomePage = null;

	
	/**********
	 * <p> Public Static Singleton: theSingleRoleDispatch </p>
	 * 
	 * <p> Description: This object is used to send a user directly to their home page when the
	 * user has only one role. </p>
	 *
	 */
	public static GUISingleRoleDispatch theSingleRoleDispatch = null;

	
	/**********
	 * <p> Public Static Singleton: theStaffHomePage </p>
	 * 
	 * <p> Description: This object is the template for Staff role activities. </p>
	 *
	 */
	public static GUIStaffHomePage theStaffHomePage = null;

	
	/**********
	 * <p> Public Static Singleton: theStudentHomePage </p>
	 * 
	 * <p> Description: This object is the template for Student role activities. </p>
	 *
	 */
	public static GUIStudentHomePage theStudentHomePage = null;	

	
	/**********
	 * <p> Public Static Singleton: theSystemStartupPage </p>
	 * 
	 * <p> Description: This object is the template for first login when the app starts. </p>
	 *
	 */
	public static GUISystemStartUpPage theSystemStartupPage = null;

	
	/**********
	 * <p> Public Static Singleton: theUserUpdatePage </p>
	 * 
	 * <p> Description: This object is the template for users to update their attributes. </p>
	 *
	 */
	public static GUIUserUpdatePage theUserUpdatePage = null;
	
	
	/**********
	 * <p> Public Static Singleton: theUserUpdatePage </p>
	 * 
	 * <p> Description: This object is the template for users to update their attributes. </p>
	 *
	 */
	public static GUIStudentQuestionPage theStudentQuestionPage = null; //clay edits

	/**********************************************************************************************

	Constructor
	
	**********************************************************************************************/

	private static  Database database;

	
	/**********
	 * <p> Method: GUISystemStartUpPage(Stage ps, Pane theRoot, Database database) </p>
	 * 
	 * <p> Description: This method is called when the application first starts. It must handle
	 * two cases: 1) when no has been established and 2) when one or more users have been 
	 * established.
	 * 
	 * If there are no users in the database, this means that the person starting the system jmust
	 * be an administrator, so a special GUI is provided to allow this Admin to set a username and
	 * password.
	 * 
	 * If there is at least one user, then a different display is shown for existing users to login
	 * and for potential new users to provide an invitation code and if it is valid, they are taken
	 * to a page where they can specify a username and password.</p>
	 * 
	 * @param ps specifies the JavaFX Stage to be used for this GUI and it's methods
	 * 
	 * @param theRoot specifies the JavaFX Pane to be used for this GUI and it's methods
	 * 
	 * @param db specifies the Database to be used by this GUI and it's methods
	 * 
	 */
	@SuppressWarnings("unused")
	public GUISystemStartUpPage(Stage ps, Pane theRoot, Database db) {
		theSystemStartupPage = this;
		
		primaryStage = ps;
		database = db;
		
		theRootPane = theRoot;

            if (database.isDatabaseEmpty()) {
            	// This is the first startup, so special actions occur!
        		primaryStage.setTitle("CSE 360 Foundation Code: First User Account Setup");	
       		
            	// Label theScene with the name of the system startup screen
            	setupLabelUI(label_ApplicationTitle, "Arial", 32, 
        			FCMainClass.WINDOW_WIDTH, Pos.CENTER, 0, 10);
  		
            	// Label to display the welcome message for the first user
            	setupLabelUI(label_FirstUserLine1, "Arial", 24, 
            		FCMainClass.WINDOW_WIDTH, Pos.CENTER, 0, 70);
		
            	// Label to display the welcome message for the first user
            	setupLabelUI(label_FirstUserLine2, "Arial", 18, 
            		FCMainClass.WINDOW_WIDTH, Pos.CENTER, 0, 130);
    		
            	// Establish the text input operand field for the Admin username
            	setupTextUI(text_AdminUsername, "Arial", 18, 300, Pos.BASELINE_LEFT, 50, 160, 
            		true);
            	text_AdminUsername.setPromptText("Enter Admin Username");
            	text_AdminUsername.textProperty().addListener((observable, oldValue, newValue) 
            			-> {setAdminUsername(); });
    		
            	// Establish the text input operand field for the password
            	setupTextUI(text_AdminPassword1, "Arial", 18, 300, Pos.BASELINE_LEFT, 50, 210, 
            		true);
            	text_AdminPassword1.setPromptText("Enter Admin Password");
            	text_AdminPassword1.textProperty().addListener((observable, oldValue, newValue)
    				-> {setAdminPassword1(); });
    		
            	// Establish the text input operand field for the password
            	setupTextUI(text_AdminPassword2, "Arial", 18, 300, Pos.BASELINE_LEFT, 50, 260, 
            		true);
            	text_AdminPassword2.setPromptText("Enter Admin Password Again");
            	text_AdminPassword2.textProperty().addListener((observable, oldValue, newValue) 
    				-> {setAdminPassword2(); });
         
            	// Set up the Log In button
            	setupButtonUI(button_AdminSetup, "Dialog", 18, 200, Pos.CENTER, 475, 210);
            	button_AdminSetup.setOnAction((event) -> {doSetupAdmin(); });
    		
            	// Label to display the Passwords do not match error message
            	setupLabelUI(label_PasswordsDoNotMatch, "Arial", 18, 
            			FCMainClass.WINDOW_WIDTH, Pos.CENTER, 0, 300);
            	
                
                setupButtonUI(button_Quit, "Dialog", 18, 250, Pos.CENTER, 300, 520);
                button_Quit.setOnAction((event) -> {performQuit(); });



            	// Place all of the just-initialized GUI elements into the pane
            	theRoot.getChildren().addAll(label_ApplicationTitle, label_FirstUserLine1,
        			label_FirstUserLine2, text_AdminUsername, text_AdminPassword1, 
        			text_AdminPassword2, button_AdminSetup, label_PasswordsDoNotMatch,
        			button_Quit);

            } else {
            	setupNormalLogin();
                setup();
            }
	}

	
	/**********
	 * <p> Method: setup() </p>
	 * 
	 * <p> Description: This method is called to reset the page and then populate it with new
	 * content.</p>
	 * 
	 */
	public void setup() {
		if (firstInvocation) setupNormalLogin();
		//theUser = null;
		text_Username.setText("");
		text_Password.setText("");
		text_Invitation.setText("");
		theRootPane.getChildren().clear();
		theRootPane.getChildren().addAll(
				label_ApplicationTitle, label_OperationalStartTitle,
				label_LogInInsrtuctions, label_AccountSetupInsrtuctions, text_Username,
				button_Login, text_Password, text_Invitation, button_SetupAccount,
				button_Quit);
	}

	
	@SuppressWarnings("unused")
	private void setupNormalLogin() {
        // There are accounts, so this user needs to log in using a username and password,
        // or set up an account using an invitation code.
    	firstInvocation = false;
        	
        // The log in portion of the page
    	// Label theScene with the name of the system startup screen
		primaryStage.setTitle("CSE 360 Foundation Code: User Login Page");	
    	
    	setupLabelUI(label_ApplicationTitle, "Arial", 32, 
			FCMainClass.WINDOW_WIDTH, Pos.CENTER, 0, 10);
       	
    	setupLabelUI(label_OperationalStartTitle, "Arial", 24,
    		FCMainClass.WINDOW_WIDTH, Pos.CENTER, 0, 60);
    		
    	setupLabelUI(label_LogInInsrtuctions, "Arial", 18, 
    		FCMainClass.WINDOW_WIDTH, Pos.BASELINE_LEFT, 20, 120);
    		
    	// Establish the text input operand field for the username
    	setupTextUI(text_Username, "Arial", 18, 300, Pos.BASELINE_LEFT, 50, 160, true);
    		text_Username.setPromptText("Enter Username");
    		
    	// Establish the text input operand field for the password
    	setupTextUI(text_Password, "Arial", 18, 300, Pos.BASELINE_LEFT, 50, 210, true);
    		text_Password.setPromptText("Enter Password");
         
        // Set up the Log In button
        setupButtonUI(button_Login, "Dialog", 18, 200, Pos.CENTER, 475, 180);
            button_Login.setOnAction((event) -> {doLogin(); });
            
        alertUsernamePasswordError.setTitle("Invalid username/password!");
        alertUsernamePasswordError.setHeaderText(null);

    		
    	// The account setup portion of the page
    		
    	setupLabelUI(label_AccountSetupInsrtuctions, "Arial", 18, 
    		FCMainClass.WINDOW_WIDTH, Pos.BASELINE_LEFT, 20, 300);
    		
    	// Establish the text input operand field for the password
    	setupTextUI(text_Invitation, "Arial", 18, 300, Pos.BASELINE_LEFT, 50, 340, true);
    		text_Invitation.setPromptText("Enter Invitation Code");
            
        // Set up the setup button
        setupButtonUI(button_SetupAccount, "Dialog", 18, 200, Pos.CENTER, 475, 340);
            button_SetupAccount.setOnAction((event) -> {doSetupAccount(); });
            
          // Set up the Quit button  
        setupButtonUI(button_Quit, "Dialog", 18, 250, Pos.CENTER, 300, 520);
        button_Quit.setOnAction((event) -> {performQuit(); });

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
	 */
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
	
	private void performQuit() {
		System.out.println("Perform Quit");
		System.exit(0);
	}	
	
	private void setAdminUsername() {
		adminUsername = text_AdminUsername.getText();
	}
	
	private void setAdminPassword1() {
		adminPassword1 = text_AdminPassword1.getText();
		label_PasswordsDoNotMatch.setText("");
	}

	private void setAdminPassword2() {
		adminPassword2 = text_AdminPassword2.getText();		
		label_PasswordsDoNotMatch.setText("");
	}

	private void doSetupAdmin() {
		String userValidResp = UserNameRecognizer.checkForValidUserName(adminUsername);
		// Make sure the two passwords are the same
		if (userValidResp != "") {
			// The username is not valid, error window will appear and detail what is wrong
			alertUsernamePasswordError.setContentText(String.format("%s. Try again!",userValidResp));
    		alertUsernamePasswordError.showAndWait();
    		return;
		}
		// Make sure the password is valid
		String validPassResp = PasswordEvaluator.evaluatePassword(adminPassword1);
		
    	if (validPassResp != "") {
    		// If password is not valid, window will popup and detail what is wrong
    		alertUsernamePasswordError.setContentText(String.format("%s. Try again!",validPassResp));
    		alertUsernamePasswordError.showAndWait();
    		return;
    	}
		if (adminPassword1.compareTo(adminPassword2) == 0) {
        	// Create the passwords and proceed to the user home page
            try {
            	// Create a new User object with admin role and register in the database
            	User user = new User(adminUsername, adminPassword1, true, false, false, false, false);
                database.register(user);
                System.out.println("Administrator Username and Password have been set.");
                
                // Navigate to the Account Update Page
                GUISystemStartUpPage.theAdminUpdatePage = 
                		new GUIAdminUpdatePage(primaryStage, theRootPane, database, user);
            	} catch (SQLException e) {
                System.err.println("Database error: " + e.getMessage());
                e.printStackTrace();
            }

		}
		else {
			// The two passwords are NOT the same, so clear the passwords, explain the passwords
			// must be the same, and clear the message as soon as the first character is typed.
			text_AdminPassword1.setText("");
			text_AdminPassword2.setText("");
			label_PasswordsDoNotMatch.setText("The two passwords must match. Please try again!");
		}
	}
	
	private void doLogin() {		
		String username = text_Username.getText();
		String password = text_Password.getText();
    	boolean loginResult = false;
    	
    	// Checks for a valid username
    	String validNameResp = UserNameRecognizer.checkForValidUserName(username);
    	if (validNameResp != "") {
    		// If username is not valid, window will popup and detail what is wrong
    		alertUsernamePasswordError.setContentText(String.format("%s. Try again!",validNameResp));
    		alertUsernamePasswordError.showAndWait();
    		return;
    	}
    	// Checks for a valid password
    	String validPassResp = PasswordEvaluator.evaluatePassword(password);
    	if (validPassResp != "") {
    		// If password is not valid, window will popup and detail what is wrong
    		alertUsernamePasswordError.setContentText(String.format("%s. Try again!",validPassResp));
    		alertUsernamePasswordError.showAndWait();
    		return;
    	}
		// Fetch the user
    	if (database.getUserAccountDetails(username) == false) {
    		alertUsernamePasswordError.setContentText("Incorrect username/password. Try again!");
    		alertUsernamePasswordError.showAndWait();
    		return;
    	}
    	
    	String actualPassword = database.getCurrentPassword();
    	user = new User(username, password, database.getCurrentAdminRole(), 
    			database.getCurrentStudentRole(), database.getCurrentReviewerRole(), 
    			database.getCurrentInstructorRole(), database.getCurrentStaffRole());
    	
/*    	System.out.println("Username: " + username +
    			" Password: " + password + 
    			" Admin: " + database.getCurrentAdminRole() + 
    			"; Student: " + database.getCurrentStudentRole() + 
    			"; Reviewer: " + database.getCurrentReviewerRole() +
    			"; Instructor: " + database.getCurrentInstructorRole() + 
    			"; Staff: " + database.getCurrentStaffRole());
 */   	
    	if (password.compareTo(actualPassword) != 0) {
    		alertUsernamePasswordError.setContentText("Incorrect username/password. Try again!");
    		alertUsernamePasswordError.showAndWait();
    		return;
    	}
    	
		int numberOfRoles = database.getNumberOfRoles(user);		
		if (numberOfRoles == 1) {
			// Single Account Home Page - The user has no choice here
			
			// Admin role
			if (user.getAdminRole()) {
				loginResult = database.loginAdmin(user);
				if (loginResult) {
					if (theSingleRoleDispatch == null)
						theSingleRoleDispatch =  new GUISingleRoleDispatch();
					theSingleRoleDispatch.doSingleRoleDispatch(primaryStage, theRootPane, database, user);
				}
			// Student role
			} else if (user.getStudentRole()) {
				loginResult = database.loginStudent(user);
				if (loginResult) {
					if (theSingleRoleDispatch == null)
						theSingleRoleDispatch =  new GUISingleRoleDispatch();
					theSingleRoleDispatch.doSingleRoleDispatch(primaryStage, theRootPane, database, user);
				}
			// Reviewer role
			} else if (user.getReviewerRole()) {
				loginResult = database.loginReviewer(user);
				if (loginResult) {
					if (theSingleRoleDispatch == null)
						theSingleRoleDispatch =  new GUISingleRoleDispatch();
					theSingleRoleDispatch.doSingleRoleDispatch(primaryStage, theRootPane, database, user);
				}
			// Instructor role
			} else if (user.getInstructorRole()) {
				loginResult = database.loginInstructor(user);
				if (loginResult) {
					if (theSingleRoleDispatch == null)
						theSingleRoleDispatch =  new GUISingleRoleDispatch();
					theSingleRoleDispatch.doSingleRoleDispatch(primaryStage, theRootPane, database, user);
				}
			// Staff roles
			} else if (user.getInstructorRole()) {
				loginResult = database.loginStaff(user);
				if (loginResult) {
					if (theSingleRoleDispatch == null)
						theSingleRoleDispatch =  new GUISingleRoleDispatch();
					theSingleRoleDispatch.doSingleRoleDispatch(primaryStage, theRootPane, database, user);
				}
			}
		} else if (numberOfRoles > 1) {
			// Multiple Account Home Page - The user chooses which role to play
			if (theMultipleRoleDispatchPage == null)
				theMultipleRoleDispatchPage = 
					new GUIMultipleRoleDispatchPage(primaryStage, theRootPane, database, user);
			theMultipleRoleDispatchPage.doSetup();
		}
	}

	
	@SuppressWarnings("unused")
	private void doSetupAccount() {
		String invitationCode = text_Invitation.getText();
		String role = database.getRoleGivenAnInvitationCode(invitationCode);
		if (role.length() == 0) {
			alertInvitationCodeIsInvalid.setTitle("Invalid Invitation Code");
			alertInvitationCodeIsInvalid.setHeaderText("The invitation code is not valid.");
			alertInvitationCodeIsInvalid.setContentText("Correct the code and try again.");
			alertInvitationCodeIsInvalid.showAndWait();
			return;
		}
		primaryStage.setTitle("CSE 360 Foundation Code: New Admin Username and Password setup Page");
		
    	// Label to display the welcome message for the new user
    	setupLabelUI(label_NewUserCreation, "Arial", 32, FCMainClass.WINDOW_WIDTH, 
			Pos.CENTER, 0, 10);
	
    	// Label to display the welcome message for the first user
    	setupLabelUI(label_NewUserLine, "Arial", 24, FCMainClass.WINDOW_WIDTH, 
		Pos.CENTER, 0, 70);
		
		// Establish the text input operand field for the username
		setupTextUI(text_Username, "Arial", 18, 300, Pos.BASELINE_LEFT, 50, 160, true);
		text_Username.setPromptText("Enter the Username");
		text_Username.textProperty().addListener((observable, oldValue, newValue) 
				-> {setAdminUsername(); });
		
		// Establish the text input operand field for the password
		setupTextUI(text_Password1, "Arial", 18, 300, Pos.BASELINE_LEFT, 50, 210, true);
		text_Password1.setPromptText("Enter the Password");
		
		// Establish the text input operand field for the password
		setupTextUI(text_Password2, "Arial", 18, 300, Pos.BASELINE_LEFT, 50, 260, true);
		text_Password2.setPromptText("Enter the Password Again");
     
        // Set up the Log In button
        setupButtonUI(button_UserSetup, "Dialog", 18, 200, Pos.CENTER, 475, 210);
        button_UserSetup.setOnAction((event) -> {doCreateUser(role, invitationCode); });
		
    	// Label to display the Passwords do not match error message
    	setupLabelUI(label_PasswordsDoNotMatch, "Arial", 18, FCMainClass.WINDOW_WIDTH, 
		Pos.CENTER, 0, 300);


    	// Place all of the just-initialized GUI elements into the pane
    	theRootPane.getChildren().clear();
    	theRootPane.getChildren().addAll(label_NewUserCreation, label_NewUserLine, text_Username, 
    			text_Password1, text_Password2, button_UserSetup, label_PasswordsDoNotMatch);
    	
	}
	

	private void doCreateUser(String role, String iCode) {
		System.out.println("Entered doCreateUser.");
		String username = text_Username.getText();
		String password = text_Password1.getText();
		String emailAddress = database.getEmailAddressUsingCode(iCode);

		User user;
				
		if (role.compareTo("Admin") == 0) {
			FCMainClass.activeHomePage = 1;
			user = new User(username, password, true, false, false, false, false);
		} else if (role.compareTo("Student") == 0) {
			FCMainClass.activeHomePage = 2;
			user = new User(username, password, false, true, false, false, false);			
		} else if (role.compareTo("Reviewer") == 0) {
			FCMainClass.activeHomePage = 3;
			user = new User(username, password, false, false, true, false, false);			
		} else if (role.compareTo("Instructor") == 0) {
			FCMainClass.activeHomePage = 4;
			user = new User(username, password, false, false, false, true, false);			
		} else if (role.compareTo("Staff") == 0) {
			FCMainClass.activeHomePage = 5;
			user = new User(username, password, false, false, false, false, true);			
		} else {
			user = new User("", "", false, false, false, false, false);
		}
		
		// Make sure the Username is valid
		String userValidResp = UserNameRecognizer.checkForValidUserName(username);
		
		if (userValidResp != "") {
			// The username is not valid, error window will appear and detail what is wrong
			alertUsernamePasswordError.setContentText(String.format("%s. Try again!",userValidResp));
    		alertUsernamePasswordError.showAndWait();
    		return;
		}
		// Make sure the password is valid
		String validPassResp = PasswordEvaluator.evaluatePassword(password);
		
    	if (validPassResp != "") {
    		// If password is not valid, window will popup and detail what is wrong
    		alertUsernamePasswordError.setContentText(String.format("%s. Try again!",validPassResp));
    		alertUsernamePasswordError.showAndWait();
    		return;
    	}
		// Make sure the two passwords are the same		
		if (text_Password1.getText().compareTo(text_Password2.getText()) == 0) {
			System.out.println("The User Passwords match");
			
        	// Create the account and proceed to the user account update page
            try {
            	// Create a new User object with the pre-set role and register in the database
                database.register(user);
                System.out.println("The user's Username and Password have been set.");
                System.out.println("Username: " + username);
                database.removeInvitationAfterUse(text_Invitation.getText());
                database.getUserAccountDetails(username);
                if (database.getCurrentEmailAddress() == null ||
                		database.getCurrentEmailAddress().length() == 0) {
                	System.out.println("Updating user; " + username + " email address to: "+ emailAddress);
                	database.updateEmailAddress(username, emailAddress);
                }
                // Navigate to the Welcome Login Page
                if (theUserUpdatePage == null)
                	theUserUpdatePage = 
                		new GUIUserUpdatePage(primaryStage, theRootPane, database, user);
                else
                	theUserUpdatePage.setup();
            } catch (SQLException e) {
                System.err.println("Database error: " + e.getMessage());
                e.printStackTrace();
            }

		}
		else {
			// The two passwords are NOT the same, so clear the passwords, explain the passwords
			// must be the same, and clear the message as soon as the first character is typed.
			text_AdminPassword1.setText("");
			text_AdminPassword2.setText("");
			label_PasswordsDoNotMatch.setText("The two passwords must match. Please try again!");
		}
	}
	
}
