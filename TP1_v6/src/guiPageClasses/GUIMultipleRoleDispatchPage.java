package guiPageClasses;

	import java.util.ArrayList;
	import java.util.List;

	import applicationMainMethodClasses.FCMainClass;
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
	 * <p> Title: GUIMultipleRoleDispatchPage Class. </p>
	 * 
	 * <p> Description: The Java/FX-based Admin Update Page.</p>
	 * 
	 * <p> Copyright: Lynn Robert Carter © 2025 </p>
	 * 
	 * @author Lynn Robert Carter
	 * 
	 * @version 1.00		2025-04-20 Initial version
	 *  
	 */

	public class GUIMultipleRoleDispatchPage {
		
		/**********************************************************************************************

		Attributes
		
		**********************************************************************************************/
		
		// These are the application values required by the user interface
		private Label label_PageTitle = new Label("Multiple Role Dispatch Page");
		private Label label_UserDetails = new Label();
		
		private Line line_Separator1 = new Line(20, 95, FCMainClass.WINDOW_WIDTH-20, 95);

		private Label label_WhichRole = new Label("Which role do you wish to play:");
		private ComboBox <String> combobox_SelectRole = new ComboBox <String>();
		private Button button_PerformRole = new Button("Perform Role");		

		private Line line_Separator4 = new Line(20, 525, FCMainClass.WINDOW_WIDTH-20,525);

		private Button button_Logout = new Button("Logout");
		private Button button_Quit = new Button("Quit");


		private Stage thePrimaryStage;
		private Pane theRootPane;
		private Database theDatabase;
		private User theUser;

		/**********************************************************************************************

		Constructors
		
		**********************************************************************************************/

		
		/**********
		 * <p> Method: GUIMultipleRoleDispatchPage(Stage ps, Pane theRoot, Database database, User user) </p>
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
		public GUIMultipleRoleDispatchPage(Stage ps, Pane theRoot, Database database, User user) {
			thePrimaryStage = ps;
			theRootPane = theRoot;
			theDatabase = database;
			theUser = user;

			thePrimaryStage.setTitle("CSE 360 Foundation Code: Multiple Role Dispatch");	

			double WINDOW_WIDTH = FCMainClass.WINDOW_WIDTH;

			// Label theScene with the name of the startup screen, centered at the top of the pane
			setupLabelUI(label_PageTitle, "Arial", 28, WINDOW_WIDTH, Pos.CENTER, 0, 5);

			label_UserDetails.setText("User: " + user.getUserName() + "   Select which role");
			setupLabelUI(label_UserDetails, "Arial", 20, WINDOW_WIDTH, Pos.CENTER, 0, 50);

			label_UserDetails.setText("User: " + user.getUserName());
			setupLabelUI(label_UserDetails, "Arial", 20, WINDOW_WIDTH, Pos.BASELINE_LEFT, 20, 55);			
			
			setupLabelUI(label_WhichRole, "Arial", 20, 200, Pos.BASELINE_LEFT, 20, 110);

			setupComboBoxUI(combobox_SelectRole, "Dialog", 16, 100, 305, 105);
			
			List<String> list = new ArrayList<String>();	// Create a new list empty list
			theDatabase.getUserAccountDetails(theUser.getUserName());
			
			list = new ArrayList<String>();
			list.add("<Select a role>");
			if (theDatabase.getCurrentAdminRole()) list.add("Admin");
			if (theDatabase.getCurrentStudentRole()) list.add("Student");
			if (theDatabase.getCurrentReviewerRole()) list.add("Reviewer");
			if (theDatabase.getCurrentInstructorRole()) list.add("Instructor");
			if (theDatabase.getCurrentStaffRole()) list.add("Staff");
			combobox_SelectRole.setItems(FXCollections.observableArrayList(list));
			combobox_SelectRole.getSelectionModel().select(0);

			setupButtonUI(button_PerformRole, "Dialog", 16, 100, Pos.CENTER, 495, 105);
			button_PerformRole.setOnAction((event) -> {performRole(); });
		
			setupButtonUI(button_Logout, "Dialog", 18, 250, Pos.CENTER, 20, 540);
			button_Logout.setOnAction((event) -> {performLogout(); });
	    
			setupButtonUI(button_Quit, "Dialog", 18, 250, Pos.CENTER, 300, 540);
			button_Quit.setOnAction((event) -> {performQuit(); });

			doSetup();
		}

		
		/**********
		 * <p> Method: doSetup() </p>
		 * 
		 * <p> Description: This method is called to reset the page and then populate it with new
		 * content.</p>
		 * 
		 */
		public void doSetup() {
	        // Place all of the just-initialized GUI elements into the pane
			combobox_SelectRole.getSelectionModel().select(0);
	        theRootPane.getChildren().clear();
	    	theRootPane.getChildren().addAll(
	        	label_PageTitle,
	        	label_UserDetails,
	        	line_Separator1,
	        	label_WhichRole,
	        	combobox_SelectRole,
	        	button_PerformRole,
	        	line_Separator4, 
	        	button_Logout,
	        	button_Quit);
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

		private void performRole () {
	    	String role = combobox_SelectRole.getValue();
			if (role.compareTo("Admin") == 0) {
				if (GUISystemStartUpPage.theAdminHomePage == null)
					GUISystemStartUpPage.theAdminHomePage = new GUIAdminHomePage(thePrimaryStage, theRootPane, theDatabase, theUser);
				else
					GUISystemStartUpPage.theAdminHomePage.setup();
			} else if (role.compareTo("Student") == 0) {
				if (GUISystemStartUpPage.theStudentHomePage == null)
					GUISystemStartUpPage.theStudentHomePage = new GUIStudentHomePage(thePrimaryStage, theRootPane, theDatabase, theUser);
				else
					GUISystemStartUpPage.theStudentHomePage.setup();;			
			} else if (role.compareTo("Reviewer") == 0) {
				if (GUISystemStartUpPage.theReviewerHomePage == null)
					GUISystemStartUpPage.theReviewerHomePage = new GUIReviewerHomePage(thePrimaryStage, theRootPane, theDatabase, theUser);
				else
					GUISystemStartUpPage.theReviewerHomePage.setup();
			} else if (role.compareTo("Instructor") == 0) {
				if (GUISystemStartUpPage.theInstructorHomePage == null)
					GUISystemStartUpPage.theInstructorHomePage = new GUIInstructorHomePage(thePrimaryStage, theRootPane, theDatabase, theUser);
				else
					GUISystemStartUpPage.theInstructorHomePage.setup();		
			} else if (role.compareTo("Staff") == 0) {
				if (GUISystemStartUpPage.theStaffHomePage == null)
					GUISystemStartUpPage.theStaffHomePage = new GUIStaffHomePage(thePrimaryStage, theRootPane, theDatabase, theUser);
				else
					GUISystemStartUpPage.theStaffHomePage.setup();
			} else {
				// Invalid role
				System.out.println("*** ERROR *** GUISingleRoleDispatch was asked to dispatch to " +
						"a role, " + role + ", that is not supported!");
			}
		}
		
		private void performLogout() {
			GUISystemStartUpPage.theSystemStartupPage.setup();
		}
		
		private void performQuit() {
			System.exit(0);
		}

	}
