package guiPageClasses;

import java.util.ArrayList;
import java.util.List;

//import java.util.Optional;

import applicationMainMethodClasses.FCMainClass;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
//
//import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
//import javafx.scene.control.TextInputDialog;
//import javafx.scene.control.TextField;
//import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import databaseClasses.Database;
import entityClasses.User;

/*******
 * <p> Title: GUIAdminHomePage Class. </p>
 * 
 * <p> Description: The Java/FX-based Admin Home Page.</p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Lynn Robert Carter
 * 
 * @version 1.00		2025-04-20 Initial version
 *  
 */

public class GUIAdminHomePage {
	
	/**********************************************************************************************

	Attributes
	
	**********************************************************************************************/
	
	// These are the application values required by the user interface
	private Label label_PageTitle = new Label();
	private Label label_UserDetails = new Label();
	private Button button_UpdateThisUser = new Button("Account Update");

	
	private Line line_Separator1 = new Line(20, 95, FCMainClass.WINDOW_WIDTH-20, 95);

	private Label label_NumberOfInvitations = new Label("Number of Oustanding Invitations: x");
	private Label label_NumberOfUsers = new Label("Number of Users: x");
	
	private Line line_Separator2 = new Line(20, 165, FCMainClass.WINDOW_WIDTH-20, 165);
	
	private Label label_Invitations = new Label("Send An Invitation");
	private Label label_InvitationEmailAddress = new Label("Email Address");
	private TextField text_InvitationEmailAddress = new TextField();
	private ComboBox <String> combobox_SelectRole = new ComboBox <String>();
	private String [] roles = {"Admin", "Student", "Reviewer", "Instructor", "Staff"};
	private Button button_SendInvitation = new Button("Send Invitation");
    private Alert alertEmailError = new Alert(AlertType.INFORMATION);
    private Alert alertEmailSent = new Alert(AlertType.INFORMATION);
	private Button button_ManageInvitations = new Button("Manage Invitations");
	private Button button_SetOnetimePassword = new Button("Set a One-Time Password");
	private Button button_DeleteUser = new Button("Delete a User");
	private Button button_ListUsers = new Button("List All Users");
	private Button button_AddRemoveRoles = new Button("Add/Remove Roles");
    private Alert alertNotImplemented = new Alert(AlertType.INFORMATION);
	
	private Line line_Separator3 = new Line(20, 255, FCMainClass.WINDOW_WIDTH-20, 255);
	
	private Line line_Separator4 = new Line(20, 525, FCMainClass.WINDOW_WIDTH-20,525);
	
	private Button button_Logout = new Button("Logout");
	private Button button_Quit = new Button("Quit");

	
	private Stage primaryStage;	
	private Pane theRootPane;
	private Database theDatabase;
	private User theUser;


	/**********************************************************************************************

	Constructors
	
	**********************************************************************************************/

	
	/**********
	 * <p> Method: GUIAdminHomePage(Stage ps, Pane theRoot, Database database, User user) </p>
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
	public GUIAdminHomePage(Stage ps, Pane theRoot, Database database, User user) {
		GUISystemStartUpPage.theAdminHomePage = this;
		
		FCMainClass.activeHomePage = 1;
		
		primaryStage = ps;
		theRootPane = theRoot;
		theDatabase = database;
		theUser = user;
		
		primaryStage.setTitle("CSE 360 Foundation Code: Admin Home Page");
	
		double WINDOW_WIDTH = FCMainClass.WINDOW_WIDTH;		

		// Label the window with the title and other common titles and buttons
	
		label_PageTitle.setText("Admin Home Page");
		setupLabelUI(label_PageTitle, "Arial", 28, WINDOW_WIDTH, Pos.CENTER, 0, 5);

		label_UserDetails.setText("User: " + user.getUserName());
		setupLabelUI(label_UserDetails, "Arial", 20, WINDOW_WIDTH, Pos.BASELINE_LEFT, 20, 55);
		
		setupButtonUI(button_UpdateThisUser, "Dialog", 18, 170, Pos.CENTER, 610, 45);
		button_UpdateThisUser.setOnAction((event) -> {performUpdate(); });
	
		setupButtonUI(button_Logout, "Dialog", 18, 250, Pos.CENTER, 20, 540);
		button_Logout.setOnAction((event) -> {performLogout(); });
    
		setupButtonUI(button_Quit, "Dialog", 18, 250, Pos.CENTER, 300, 540);
		button_Quit.setOnAction((event) -> {performQuit(); });
		
		setupLabelUI(label_NumberOfInvitations, "Arial", 20, 200, Pos.BASELINE_LEFT, 20, 105);
		label_NumberOfInvitations.setText("Number of outstanding invitations: " + 
				theDatabase.getNumberOfInvitations());
	
		setupLabelUI(label_NumberOfUsers, "Arial", 20, 200, Pos.BASELINE_LEFT, 20, 135);
		label_NumberOfUsers.setText("Number of users: " + 
				theDatabase.getNumberOfUsers());
	
	
		setupLabelUI(label_Invitations, "Arial", 20, WINDOW_WIDTH, Pos.BASELINE_LEFT, 20, 175);
	
		setupLabelUI(label_InvitationEmailAddress, "Arial", 16, WINDOW_WIDTH, Pos.BASELINE_LEFT,
		20, 210);
	
		setupTextUI(text_InvitationEmailAddress, "Arial", 16, 360, Pos.BASELINE_LEFT,
		130, 205, true);
	
		setupComboBoxUI(combobox_SelectRole, "Dialog", 16, 90, 500, 205);
	
		List<String> list = new ArrayList<String>();	// Create a new list empty list
		for (int i = 0; i < roles.length; i++) {
			list.add(roles[i]);
		}
		combobox_SelectRole.setItems(FXCollections.observableArrayList(list));
		combobox_SelectRole.getSelectionModel().select(0);
		alertEmailSent.setTitle("Invitation");
		alertEmailSent.setHeaderText("Invitation was sent");

		setupButtonUI(button_SendInvitation, "Dialog", 16, 150, Pos.CENTER, 630, 205);
		button_SendInvitation.setOnAction((event) -> {performInvitation(); });
	
		setupButtonUI(button_ManageInvitations, "Dialog", 16, 250, Pos.CENTER, 20, 270);
		button_ManageInvitations.setOnAction((event) -> {manageInvitations(); });
	
		setupButtonUI(button_SetOnetimePassword, "Dialog", 16, 250, Pos.CENTER, 20, 320);
		button_SetOnetimePassword.setOnAction((event) -> {setOnetimePassword(); });

		setupButtonUI(button_DeleteUser, "Dialog", 16, 250, Pos.CENTER, 20, 370);
		button_DeleteUser.setOnAction((event) -> {deleteUser(); });

		setupButtonUI(button_ListUsers, "Dialog", 16, 250, Pos.CENTER, 20, 420);
		button_ListUsers.setOnAction((event) -> {listUser(); });

		setupButtonUI(button_AddRemoveRoles, "Dialog", 16, 250, Pos.CENTER, 20, 470);
		button_AddRemoveRoles.setOnAction((event) -> {addRemoveRoles(); });
		
		// Place all of the items into the Root Pane
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
    		label_NumberOfInvitations, label_NumberOfUsers,
    		line_Separator2,
    		label_Invitations, 
    		label_InvitationEmailAddress, text_InvitationEmailAddress,
    		combobox_SelectRole, button_SendInvitation, line_Separator3,
    		button_ManageInvitations,
    		button_SetOnetimePassword,
    		button_DeleteUser,
    		button_ListUsers,
    		button_AddRemoveRoles,
    		line_Separator4, 
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

	
	/**********
	 * Private local method to initialize the standard fields for a ComboBox
	 * 
	 * @param c		The ComboBox object to be initialized
	 * @param ff	The font to be used
	 * @param f		The size of the font to be used
	 * @param w		The width of the ComboBox
	 * @param x		The location from the left edge (x axis)
	 * @param y		The location from the top (y axis)
	 */
	private void setupComboBoxUI(ComboBox <String> c, String ff, double f, double w, double x, double y){
		c.setStyle("-fx-font: " + f + " " + ff + ";");
		c.setMinWidth(w);
		c.setLayoutX(x);
		c.setLayoutY(y);
	}
	
	/**********************************************************************************************

	User Interface Actions for this page
	
	**********************************************************************************************/
	
	private void performUpdate () {
		theRootPane.getChildren().clear();
		new GUIUserUpdatePage(primaryStage, theRootPane, theDatabase, theUser);
	}
	
	private void performInvitation () {
		String emailAddress = text_InvitationEmailAddress.getText();
		if (invalidEmailAddress(emailAddress)) {
			return;
		}
		System.out.println("The email address in question is: " + emailAddress);
		if (theDatabase.emailaddressHasBeenUsed(emailAddress)) {
			alertEmailError.setContentText("An invitation has already been sent to this email address.");
			alertEmailError.showAndWait();
			return;
		}
		String theSelectedRole = (String) combobox_SelectRole.getValue();
		String invitationCode = theDatabase.generateInvitationCode(emailAddress, theSelectedRole);
		String msg = "Code: " + invitationCode + " for role " + theSelectedRole + 
				" was sent to: " + emailAddress;
		System.out.println(msg);
		alertEmailSent.setContentText(msg);
		alertEmailSent.showAndWait();
		text_InvitationEmailAddress.setText("");
		int numberOfInvitations;
		numberOfInvitations = theDatabase.getNumberOfInvitations();
		label_NumberOfInvitations.setText("Number of outstanding invitations: " + numberOfInvitations);
	}

	private void manageInvitations () {
		System.out.println("\n*** WARNING ***: Manage Invitations Not Yet Implemented");
		alertNotImplemented.setTitle("*** WARNING ***");
		alertNotImplemented.setHeaderText("Manage Invitations Issue");
		alertNotImplemented.setContentText("Manage Invitations Not Yet Implemented");
		alertNotImplemented.showAndWait();
	}
	
	private void setOnetimePassword () {
		System.out.println("\n*** WARNING ***: One-Time Password Not Yet Implemented");
		alertNotImplemented.setTitle("*** WARNING ***");
		alertNotImplemented.setHeaderText("One-Time Password Issue");
		alertNotImplemented.setContentText("One-Time Password Not Yet Implemented");
		alertNotImplemented.showAndWait();
	}
	
	private void deleteUser() {
		theRootPane.getChildren().clear();
		new GUIDeleteUserPage(primaryStage, theRootPane, theDatabase, theUser);
	}
	
	private void listUser() {
		System.out.println("\n*** WARNING ***: List Users Not Yet Implemented");
		alertNotImplemented.setTitle("*** WARNING ***");
		alertNotImplemented.setHeaderText("List User Issue");
		alertNotImplemented.setContentText("List Users Not Yet Implemented");
		alertNotImplemented.showAndWait();
	}
	
	private void addRemoveRoles() {
		theRootPane.getChildren().clear();
		new GUIAddRemoveRolesPage(primaryStage, theRootPane, theDatabase, theUser);
	}

	
	private boolean invalidEmailAddress(String emailAddress) {
		if (emailAddress.length() == 0) {
			alertEmailError.setContentText("Correct the email address and try again.");
			alertEmailError.showAndWait();
			return true;
		}
		return false;
	}

	private void performLogout() {
		GUISystemStartUpPage.theSystemStartupPage.setup();
	}
	
	private void performQuit() {
		System.exit(0);
	}

}
