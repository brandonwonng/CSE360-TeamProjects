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
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import databaseClasses.Database;
import entityClasses.User;

/*******
 * <p> Title: GUIListAllUsers Class. </p>
 * 
 * <p> Description: The Java/FX-based page that displays all registered users from the database.</p>
 * 
 * <p> Copyright: Noah Dow © 2025 </p>
 * 
 * @author Noah Dow
 * 
 * @version 1.00		2025-04-20 Initial version
 *  
 */

public class GUIListAllUsers {
	
	/**********************************************************************************************

	Attributes
	
	**********************************************************************************************/
	
	// These are the application values required by the user interface
	private Label label_PageTitle = new Label();
	private VBox userListVBox = new VBox(10);

	//Buttons for navigation
	private Button button_Return = new Button("Return");
	private Button button_Logout = new Button("Logout");
	private Button button_Quit = new Button("Quit");
	
	private Stage primaryStage;	
	private Pane theRootPane;
	private Database theDatabase;
	private User theUser;

	//THIS IS THE DEMO FOR HOW WE CAN COMMIT CHANGES TO OUR GROUP PROJECT FILE!!!!
	/**********************************************************************************************

	Constructors
	
	**********************************************************************************************/

	
	/**********
	 * <p> Method: GUIListAllUsers(Stage ps, Pane theRoot, Database database, User user) </p>
	 * 
	 * <p> Description: This method displays all the users within the database. </p>
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
	public GUIListAllUsers(Stage ps, Pane theRoot, Database database, User user) {
		primaryStage = ps;
		theRootPane = theRoot;
		theDatabase = database;
		theUser = user;
		
		primaryStage.setTitle("CSE 360 Foundation Code: Admin Opertaions Page");
	
		double WINDOW_WIDTH = FCMainClass.WINDOW_WIDTH;		

		//Setup layout and spacing	
		userListVBox.setLayoutX(50);
		userListVBox.setLayoutY(100);
		userListVBox.setPrefWidth(500);

		//Retrieve all the usernames from the database
		List<String> userNames = theDatabase.getUserList();

		//Loop through each username and display user details
		for(String username : userNames) {
			if(username.equals("<Select a User>")) continue;
			theDatabase.getUserAccountDetails(username);
			String fullName = theDatabase.getCurrentFirstName() + " " + theDatabase.getCurrentLastName();
			String email = theDatabase.getCurrentEmailAddress();
			String roles = theDatabase.getCurrentRolesString();
			
			Label info = new Label(
					"Username: " + username + "\n" +
					"Full Name: " + fullName + "\n" +
					"Email: " + email + "\n" +
					"Roles: " + roles + "\n"
					);
			info.setFont(Font.font("Arial", 16));
			userListVBox.getChildren().add(info);
		}
		//Setup navigation buttons
		setupButtonUI(button_Return, "Dialog", 18, 210, Pos.CENTER, 20, 540);
		button_Return.setOnAction((event) -> {performReturn(); });

		setupButtonUI(button_Logout, "Dialog", 18, 210, Pos.CENTER, 300, 540);
		button_Logout.setOnAction((event) -> {performLogout(); });
    
		setupButtonUI(button_Quit, "Dialog", 18, 210, Pos.CENTER, 570, 540);
		button_Quit.setOnAction((event) -> {performQuit(); });
    
		//Add UI elements to the root pane
		theRootPane.getChildren().clear();
		theRootPane.getChildren().addAll(userListVBox, button_Return, button_Logout, button_Quit);
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
