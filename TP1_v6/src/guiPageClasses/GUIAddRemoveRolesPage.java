package guiPageClasses;

import java.util.ArrayList;
import java.util.List;

//import java.util.Optional;

import applicationMainMethodClasses.FCMainClass;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import databaseClasses.Database;
import entityClasses.User;

/*******
 * <p> Title: GUIAddRemoveRolesPage Class. </p>
 * 
 * <p> Description: The Java/FX-based page for changing the assigned roles to users.</p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Lynn Robert Carter
 * 
 * @version 1.00		2025-04-20 Initial version
 *  
 */

public class GUIAddRemoveRolesPage {
	
	/**********************************************************************************************

	Attributes
	
	**********************************************************************************************/
	
	// These are the application values required by the user interface
	private Label label_PageTitle = new Label();
	private Label label_UserDetails = new Label();
	private Button button_UpdateThisUser = new Button("Account Update");
	private String theAddRole = "";
	private String theRemoveRole = "";
	private List<String> addList;
	private Button button_AddRole = new Button("Add This Role");
	private List<String> removeList;
	private Button button_RemoveRole = new Button("Remove This Role");

	
	private Line line_Separator1 = new Line(20, 95, FCMainClass.WINDOW_WIDTH-20, 95);

	private Label label_SelectUser = new Label("Select a user to be updated:");
	private ComboBox <String> combobox_SelectUser = new ComboBox <String>();
	String theSelectedUser = "";

	
	private Label label_CurrentRoles = new Label("This user's current roles:");

	private Label label_SelectRoleToBeAdded = new Label("Select a role to be added:");
	private ComboBox <String> combobox_SelectRoleToAdd = new ComboBox <String>();
	
	private Label label_SelectRoleToBeRemoved = new Label("Select a role to be removed:");
	private ComboBox <String> combobox_SelectRoleToRemove = new ComboBox <String>();
	
//	private String [] roles = {"Admin", "Student", "Reviewer", "Instructor", "Staff"};
	
	private Line line_Separator4 = new Line(20, 525, FCMainClass.WINDOW_WIDTH-20,525);
	
	private Button button_Return = new Button("Return");
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
	 * <p> Method: GUIAddRemoveRolesPage(Stage ps, Pane theRoot, Database database, User user) </p>
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
	public GUIAddRemoveRolesPage(Stage ps, Pane theRoot, Database database, User user) {
		primaryStage = ps;
		theRootPane = theRoot;
		theDatabase = database;
		theUser = user;
		
		primaryStage.setTitle("CSE 360 Foundation Code: Admin Opertaions Page");
	
		double WINDOW_WIDTH = FCMainClass.WINDOW_WIDTH;		

		// Label the window with the title and other common titles and buttons
	
		label_PageTitle.setText("Add/Removed Roles Page");
		setupLabelUI(label_PageTitle, "Arial", 28, WINDOW_WIDTH, Pos.CENTER, 0, 5);

		label_UserDetails.setText("User: " + user.getUserName());
		setupLabelUI(label_UserDetails, "Arial", 20, WINDOW_WIDTH, Pos.BASELINE_LEFT, 20, 55);
		
		setupButtonUI(button_UpdateThisUser, "Dialog", 18, 170, Pos.CENTER, 610, 45);
		button_UpdateThisUser.setOnAction((event) -> {performUpdate(); });
		
		
		setupLabelUI(label_SelectUser, "Arial", 20, 300, Pos.BASELINE_LEFT, 20, 130);
		
		setupComboBoxUI(combobox_SelectUser, "Dialog", 16, 250, 280, 125);
		List<String> userList = database.getUserList();	
		combobox_SelectUser.setItems(FXCollections.observableArrayList(userList));
		combobox_SelectUser.getSelectionModel().select(0);
		combobox_SelectUser.getSelectionModel().selectedItemProperty()
    	.addListener((ObservableValue<? extends String> observable, 
    		String oldvalue, String newValue) -> {doSelectUser();});
	
		setupButtonUI(button_Return, "Dialog", 18, 210, Pos.CENTER, 20, 540);
		button_Return.setOnAction((event) -> {performReturn(); });

		setupButtonUI(button_Logout, "Dialog", 18, 210, Pos.CENTER, 300, 540);
		button_Logout.setOnAction((event) -> {performLogout(); });
    
		setupButtonUI(button_Quit, "Dialog", 18, 210, Pos.CENTER, 570, 540);
		button_Quit.setOnAction((event) -> {performQuit(); });
    
		repaintTheWindow();
		doSelectUser();
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
	
	private void doSelectUser() {
		theSelectedUser = (String) combobox_SelectUser.getValue();
		setupSelectedUser();
	}
	
	private void repaintTheWindow() {
		theRootPane.getChildren().clear();
		if (theSelectedUser.compareTo("<Select a User>") == 0)
			// Only show the request to select a user to be updated and the ComboBox
			theRootPane.getChildren().addAll(
					label_PageTitle, label_UserDetails, button_UpdateThisUser, line_Separator1,
					label_SelectUser,
					combobox_SelectUser, 
					line_Separator4, 
					button_Return,
					button_Logout,
					button_Quit);
		else
			// Show all the fields as there is a selected user (as opposed to the prompt)
			theRootPane.getChildren().addAll(
					label_PageTitle, label_UserDetails, button_UpdateThisUser, line_Separator1,
					label_SelectUser,
					combobox_SelectUser, 
					label_CurrentRoles,
					label_SelectRoleToBeAdded,
					combobox_SelectRoleToAdd,
					button_AddRole,
					label_SelectRoleToBeRemoved,
					combobox_SelectRoleToRemove,
					button_RemoveRole,
					line_Separator4, 
					button_Return,
					button_Logout,
					button_Quit);
			
	}
	
	@SuppressWarnings("unused")
	private void setupSelectedUser() {
		theDatabase.getUserAccountDetails(theSelectedUser);
		
		addList = new ArrayList<String>();
		addList.add("<Select a role>");
		if (!theDatabase.getCurrentAdminRole()) addList.add("Admin");
		if (!theDatabase.getCurrentStudentRole()) addList.add("Student");
		if (!theDatabase.getCurrentReviewerRole()) addList.add("Reviewer");
		if (!theDatabase.getCurrentInstructorRole()) addList.add("Instructor");
		if (!theDatabase.getCurrentStaffRole()) addList.add("Staff");

		removeList = new ArrayList<String>();
		removeList.add("<Select a role>");
		if (theDatabase.getCurrentAdminRole()) removeList.add("Admin");
		if (theDatabase.getCurrentStudentRole()) removeList.add("Student");
		if (theDatabase.getCurrentReviewerRole()) removeList.add("Reviewer");
		if (theDatabase.getCurrentInstructorRole()) removeList.add("Instructor");
		if (theDatabase.getCurrentStaffRole()) removeList.add("Staff");
		
		boolean notTheFirst = false;
		String theCurrentRoles = "";
		if (theDatabase.getCurrentAdminRole()) {
			notTheFirst = true;
			theCurrentRoles += "Admin";
		}
		if (theDatabase.getCurrentStudentRole()) {
			if (notTheFirst)
				theCurrentRoles += ", Student"; 
			else theCurrentRoles += "Student";
			notTheFirst = true;
		}
		if (theDatabase.getCurrentReviewerRole()) {
			if (notTheFirst)
				theCurrentRoles += ", Reviewer"; 
			else theCurrentRoles += "Reviewer";
			notTheFirst = true;
		}
		if (theDatabase.getCurrentInstructorRole()) {
			if (notTheFirst)
				theCurrentRoles += ", Instructor"; 
			else theCurrentRoles += "Instructor";
			notTheFirst = true;
		}
		if (theDatabase.getCurrentStaffRole()) {
			if (notTheFirst)
				theCurrentRoles += ", Staff"; 
			else theCurrentRoles += "Staff";
			notTheFirst = true;
		}
		label_CurrentRoles.setText("This user's current roles: " + theCurrentRoles);
		setupLabelUI(label_CurrentRoles, "Arial", 16, 300, Pos.BASELINE_LEFT, 50, 170);

		
		setupLabelUI(label_SelectRoleToBeAdded, "Arial", 20, 300, Pos.BASELINE_LEFT, 20, 210);
		
		setupComboBoxUI(combobox_SelectRoleToAdd, "Dialog", 16, 150, 280, 205);
		combobox_SelectRoleToAdd.setItems(FXCollections.observableArrayList(addList));
		combobox_SelectRoleToAdd.getSelectionModel().clearAndSelect(0);		
		setupButtonUI(button_AddRole, "Dialog", 16, 150, Pos.CENTER, 460, 205);
		button_AddRole.setOnAction((event) -> {performAddRole(); });

	
		
		setupLabelUI(label_SelectRoleToBeRemoved, "Arial", 20, 300, Pos.BASELINE_LEFT, 20, 280);
		
		setupComboBoxUI(combobox_SelectRoleToRemove, "Dialog", 16, 150, 280, 275);
		combobox_SelectRoleToRemove.setItems(FXCollections.observableArrayList(removeList));
		combobox_SelectRoleToRemove.getSelectionModel().select(0);
		setupButtonUI(button_RemoveRole, "Dialog", 16, 150, Pos.CENTER, 460, 275);
		button_RemoveRole.setOnAction((event) -> {performRemoveRole(); });

		repaintTheWindow();

	}
	
	private void performAddRole() {
		theAddRole = (String) combobox_SelectRoleToAdd.getValue();
		
		if (theAddRole.compareTo("<Select a role>") != 0) {
			if (theDatabase.updateUserRole(theSelectedUser, theAddRole, "true") ) {
				combobox_SelectRoleToAdd = new ComboBox <String>();
				combobox_SelectRoleToAdd.setItems(FXCollections.observableArrayList(addList));
				combobox_SelectRoleToAdd.getSelectionModel().clearAndSelect(0);		
				setupSelectedUser();
			}
		}
	}
	
	private void performRemoveRole() {
		theRemoveRole = (String) combobox_SelectRoleToRemove.getValue();
		
		if (theRemoveRole.compareTo("<Select a role>") != 0) {
			if (theDatabase.updateUserRole(theSelectedUser, theRemoveRole, "false") ) {
				combobox_SelectRoleToRemove = new ComboBox <String>();
				combobox_SelectRoleToRemove.setItems(FXCollections.observableArrayList(addList));
				combobox_SelectRoleToRemove.getSelectionModel().clearAndSelect(0);		
				setupSelectedUser();
			}				
		}
	}
		
	private void performReturn() {
		GUISystemStartUpPage.theAdminHomePage.setup();
	}
	
	private void performLogout() {
		theRootPane.getChildren().clear();
		new GUISystemStartUpPage(primaryStage, theRootPane, theDatabase);
	}
	
	private void performQuit() {
		System.exit(0);
	}

}
