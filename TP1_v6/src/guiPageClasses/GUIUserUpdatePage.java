package guiPageClasses;

import java.util.Optional;

import applicationMainMethodClasses.FCMainClass;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import databaseClasses.Database;
import entityClasses.User;

/*******
 * <p> Title: GUIUserUpdatePage Class. </p>
 * 
 * <p> Description: The Java/FX-based User Update Page.</p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Lynn Robert Carter
 * 
 * @version 1.00		2025-04-20 Initial version
 *  
 */

public class GUIUserUpdatePage {
	
	/**********************************************************************************************

	Attributes
	
	**********************************************************************************************/
	
	// These are the application values required by the user interface
	private Label label_ApplicationTitle = new Label("Update a User's Account Details");
    private Label label_Purpose = 
    		new Label(" Use this page to define or update your account information."); 
	private Label label_Username = new Label("Username:");
	private Label label_Password = new Label("Password:");
	private Label label_FirstName = new Label("First Name:");
	private Label label_MiddleName = new Label("Middle Name:");
	private Label label_LastName = new Label("Last Name:");
	private Label label_PreferredFirstName = new Label("Preferred First Name:");
	private Label label_EmailAddress = new Label("Email Address:");
	
	private Label label_CurrentUsername = new Label();
	private Label label_CurrentPassword = new Label();
	private Label label_CurrentFirstName = new Label();
	private Label label_CurrentMiddleName = new Label();
	private Label label_CurrentLastName = new Label();
	private Label label_CurrentPreferredFirstName = new Label();
	private Label label_CurrentEmailAddress = new Label();
	
	private Button button_UpdatePassword = new Button("Update Password");
	private Button button_UpdateFirstName = new Button("Update First Name");
	private Button button_UpdateMiddleName = new Button("Update Middle Name");
	private Button button_UpdateLastName = new Button("Update Last Name");
	private Button button_UpdatePreferredFirstName = new Button("Update Preferred First Name");
	private Button button_UpdateEmailAddress = new Button("Update Email Address");

	private Button button_ProceedToUserHomePage = new Button("Proceed to the User Home Page");

	private Stage thePrimaryStage;	
	private Pane theRootPane;
	private Database theDatabase;
	private User theUser;
	
	
	Optional<String> result;


	/**********************************************************************************************

	Constructors
	
	**********************************************************************************************/

	
	/**********
	 * <p> Method: GUIUserUpdatePage(Stage ps, Pane theRoot, Database database, User user) </p>
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
	public GUIUserUpdatePage(Stage ps, Pane theRoot, Database database, User user) {
		thePrimaryStage = ps;
		theRootPane = theRoot;
		theDatabase = database;
		theUser = user;
		
		thePrimaryStage.setTitle("CSE 360 Foundation Code: Update User Account Details");
		double WINDOW_WIDTH = FCMainClass.WINDOW_WIDTH;

		TextInputDialog dialogUpdateFirstName = new TextInputDialog();
		dialogUpdateFirstName.setTitle("Update First Name");
		dialogUpdateFirstName.setHeaderText("Update your First Name");
		
		TextInputDialog dialogUpdateMiddleName = new TextInputDialog("");
		dialogUpdateMiddleName.setTitle("Update Middle Name");
		dialogUpdateMiddleName.setHeaderText("Update your Middle Name");
		
		TextInputDialog dialogUpdateLastName = new TextInputDialog("");
		dialogUpdateLastName.setTitle("Update Last Name");
		dialogUpdateLastName.setHeaderText("Update your Last Name");
		
		TextInputDialog dialogUpdatePreferredFirstName = new TextInputDialog("");
		dialogUpdatePreferredFirstName.setTitle("Update Preferred First Name");
		dialogUpdatePreferredFirstName.setHeaderText("Update your Preferred First Name");
		
		TextInputDialog dialogUpdateEmailAddresss = new TextInputDialog("");
		dialogUpdateEmailAddresss.setTitle("Update Email Address");
		dialogUpdateEmailAddresss.setHeaderText("Update your Email Address");

		// Label theScene with the name of the startup screen, centered at the top of the pane
		setupLabelUI(label_ApplicationTitle, "Arial", 28, WINDOW_WIDTH, Pos.CENTER, 0, 5);

        // Label to display the welcome message for the first theUser
        setupLabelUI(label_Purpose, "Arial", 20, WINDOW_WIDTH, Pos.CENTER, 0, 50);
        
        // Display the titles, values, and update buttons for the various admin account attributes
        setupLabelUI(label_Username, "Arial", 18, 190, Pos.BASELINE_RIGHT, 5, 100);
        setupLabelUI(label_CurrentUsername, "Arial", 18, 260, Pos.BASELINE_LEFT, 200, 100);
        
        setupLabelUI(label_Password, "Arial", 18, 190, Pos.BASELINE_RIGHT, 5, 150);
        setupLabelUI(label_CurrentPassword, "Arial", 18, 260, Pos.BASELINE_LEFT, 200, 150);
        setupButtonUI(button_UpdatePassword, "Dialog", 18, 275, Pos.CENTER, 500, 143);
        
        setupLabelUI(label_FirstName, "Arial", 18, 190, Pos.BASELINE_RIGHT, 5, 200);
        setupLabelUI(label_CurrentFirstName, "Arial", 18, 260, Pos.BASELINE_LEFT, 200, 200);
        setupButtonUI(button_UpdateFirstName, "Dialog", 18, 275, Pos.CENTER, 500, 193);
        button_UpdateFirstName.setOnAction((event) -> {result = dialogUpdateFirstName.showAndWait();
        	result.ifPresent(name -> database.updateFirstName(theUser.getUserName(), result.get()));
    		database.getUserAccountDetails(theUser.getUserName());
        	String newName = database.getCurrentFirstName();
        	if (newName == null || newName.length() < 1)label_CurrentFirstName.setText("<none>");
        	else label_CurrentFirstName.setText(newName);
         	});
               
        setupLabelUI(label_MiddleName, "Arial", 18, 190, Pos.BASELINE_RIGHT, 5, 250);
        setupLabelUI(label_CurrentMiddleName, "Arial", 18, 260, Pos.BASELINE_LEFT, 200, 250);
        setupButtonUI(button_UpdateMiddleName, "Dialog", 18, 275, Pos.CENTER, 500, 243);
        button_UpdateMiddleName.setOnAction((event) -> {result = dialogUpdateMiddleName.showAndWait();
    		result.ifPresent(name -> database.updateMiddleName(theUser.getUserName(), result.get()));
    		database.getUserAccountDetails(theUser.getUserName());
    		String newName = database.getCurrentMiddleName();
        	if (newName == null || newName.length() < 1)label_CurrentMiddleName.setText("<none>");
        	else label_CurrentMiddleName.setText(newName);
    		});
        
        setupLabelUI(label_LastName, "Arial", 18, 190, Pos.BASELINE_RIGHT, 5, 300);
        setupLabelUI(label_CurrentLastName, "Arial", 18, 260, Pos.BASELINE_LEFT, 200, 300);
        setupButtonUI(button_UpdateLastName, "Dialog", 18, 275, Pos.CENTER, 500, 293);
        button_UpdateLastName.setOnAction((event) -> {result = dialogUpdateLastName.showAndWait();
    		result.ifPresent(name -> database.updateLastName(theUser.getUserName(), result.get()));
    		database.getUserAccountDetails(theUser.getUserName());
    		String newName = database.getCurrentLastName();
        	if (newName == null || newName.length() < 1)label_CurrentLastName.setText("<none>");
        	else label_CurrentLastName.setText(newName);
    		});
        
        setupLabelUI(label_PreferredFirstName, "Arial", 18, 190, Pos.BASELINE_RIGHT, 
        		5, 350);
        setupLabelUI(label_CurrentPreferredFirstName, "Arial", 18, 260, Pos.BASELINE_LEFT, 
        		200, 350);
        setupButtonUI(button_UpdatePreferredFirstName, "Dialog", 18, 275, Pos.CENTER, 500, 343);
        button_UpdatePreferredFirstName.setOnAction((event) -> 
        	{result = dialogUpdatePreferredFirstName.showAndWait();
    		result.ifPresent(name -> 
    			database.updatePreferredFirstName(theUser.getUserName(), result.get()));
    		database.getUserAccountDetails(theUser.getUserName());
    		String newName = database.getCurrentPreferredFirstName();
         	if (newName == null || newName.length() < 1)label_CurrentPreferredFirstName.setText("<none>");
        	else label_CurrentPreferredFirstName.setText(newName);
     		});
        
        setupLabelUI(label_EmailAddress, "Arial", 18, 190, Pos.BASELINE_RIGHT, 5, 400);
        setupLabelUI(label_CurrentEmailAddress, "Arial", 18, 260, Pos.BASELINE_LEFT, 200, 400);
        setupButtonUI(button_UpdateEmailAddress, "Dialog", 18, 275, Pos.CENTER, 500, 393);
        button_UpdateEmailAddress.setOnAction((event) -> {result = dialogUpdateEmailAddresss.showAndWait();
    		result.ifPresent(name -> database.updateEmailAddress(theUser.getUserName(), result.get()));
    		database.getUserAccountDetails(theUser.getUserName());
    		String newEmail = database.getCurrentEmailAddress();
        	if (newEmail == null || newEmail.length() < 1)label_CurrentEmailAddress.setText("<none>");
        	else label_CurrentEmailAddress.setText(newEmail);
 			});
        
        setupButtonUI(button_ProceedToUserHomePage, "Dialog", 18, 300, 
        		Pos.CENTER, WINDOW_WIDTH/2-150, 450);
        button_ProceedToUserHomePage.setOnAction((event) -> {goToUserHomePage();});
        
        
		String newName = theDatabase.getCurrentUsername();
    	if (newName==null || newName.length() < 1)label_CurrentUsername.setText("<none>");
    	else label_CurrentUsername.setText(newName);
    	
		newName = theDatabase.getCurrentPassword();
    	if (newName==null || newName.length() < 1)label_CurrentPassword.setText("<none>");
    	else label_CurrentPassword.setText(newName);
    	
		newName = theDatabase.getCurrentFirstName();
    	if (newName==null || newName.length() < 1)label_CurrentFirstName.setText("<none>");
    	else label_CurrentFirstName.setText(newName);

		newName = theDatabase.getCurrentMiddleName();
    	if (newName==null || newName.length() < 1)label_CurrentMiddleName.setText("<none>");
    	else label_CurrentMiddleName.setText(newName);

		newName = theDatabase.getCurrentLastName();
    	if (newName==null || newName.length() < 1)label_CurrentLastName.setText("<none>");
    	else label_CurrentLastName.setText(newName);
    	
		newName = theDatabase.getCurrentPreferredFirstName();
    	if (newName==null || newName.length() < 1)label_CurrentPreferredFirstName.setText("<none>");
    	else label_CurrentPreferredFirstName.setText(newName);
    	
		newName = theDatabase.getCurrentEmailAddress();
    	if (newName==null || newName.length() < 1)label_CurrentEmailAddress.setText("<none>");
    	else label_CurrentEmailAddress.setText(newName);
    	
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
		// Remove all the old elements
		theRootPane.getChildren().clear();
        // Place all of the just-initialized GUI elements into the pane
        theRootPane.getChildren().addAll(
        		label_ApplicationTitle, label_Purpose, label_Username,
        		label_CurrentUsername, 
        		label_Password, label_CurrentPassword, button_UpdatePassword, 
        		label_FirstName, label_CurrentFirstName, button_UpdateFirstName,
        		label_MiddleName, label_CurrentMiddleName, button_UpdateMiddleName,
        		label_LastName, label_CurrentLastName, button_UpdateLastName,
        		label_PreferredFirstName, label_CurrentPreferredFirstName,
        		button_UpdatePreferredFirstName, button_UpdateEmailAddress,
        		label_EmailAddress, label_CurrentEmailAddress, 
        		button_ProceedToUserHomePage);
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
	

	private void goToUserHomePage() {
		theRootPane.getChildren().clear();
		switch (FCMainClass.activeHomePage) {
		case 1:
			if (GUISystemStartUpPage.theAdminHomePage == null)
				GUISystemStartUpPage.theAdminHomePage = 
					new GUIAdminHomePage(thePrimaryStage, theRootPane, theDatabase, theUser);
			else
				GUISystemStartUpPage.theAdminHomePage.setup();
			break;
		case 2: 
			if (GUISystemStartUpPage.theStudentHomePage == null)
				GUISystemStartUpPage.theStudentHomePage = 
					new GUIStudentHomePage(thePrimaryStage, theRootPane, theDatabase, theUser);
			else
				GUISystemStartUpPage.theStudentHomePage.setup();
			break;
		case 3: 
			if (GUISystemStartUpPage.theReviewerHomePage == null)
				GUISystemStartUpPage.theReviewerHomePage = 
					new GUIReviewerHomePage(thePrimaryStage, theRootPane, theDatabase, theUser);
			else
				GUISystemStartUpPage.theReviewerHomePage.setup();
			break;
		case 4: 
			if (GUISystemStartUpPage.theInstructorHomePage == null)
				GUISystemStartUpPage.theInstructorHomePage = 
					new GUIInstructorHomePage(thePrimaryStage, theRootPane, theDatabase, theUser);
			else
				GUISystemStartUpPage.theInstructorHomePage.setup();
			break;
		case 5: 
			if (GUISystemStartUpPage.theStaffHomePage == null)
				GUISystemStartUpPage.theStaffHomePage = 
					new GUIStaffHomePage(thePrimaryStage, theRootPane, theDatabase, theUser);
			else
			GUISystemStartUpPage.theStaffHomePage.setup();
			break;
		}
 	}

}
