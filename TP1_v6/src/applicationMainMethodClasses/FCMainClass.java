package applicationMainMethodClasses;
	
import java.sql.SQLException;

import databaseClasses.Database;
import guiPageClasses.GUISystemStartUpPage;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;

/*******
 * <p> Title: FCMainClass Class </p>
 * 
 * <p> Description: This is a Main Class that launches the FoundationCode JavaFX demonstration
 * application.  It sets up the basic environment, establishes the first GUI page, shows it to the
 * user, and stops.  When the user interacts with the GUI, the program responds accordingly. </p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Lynn Robert Carter
 * 
 * @version 2.00	2025-04-120 Refactor of this Main Class for the Summer 2025 offering of CSE 360
 * 
 */

public class FCMainClass extends Application {
	
	
	/**********
	 * This specifies the width of the application window
	 */
	public final static double WINDOW_WIDTH = 800;

	/**********
	 * This specifies the height of the application window
	 */
	public final static double WINDOW_HEIGHT = 600;

	/**********
	 * This is the system startup singleton object
	 */
	public GUISystemStartUpPage theGUISystemStartup;

	/**********
	 * This is the system startup page Scene
	 */
	public Scene theGUIStartupPageScene;	

	/**********
	 * This is the system startup page Stage
	 */
	public static Stage theStage;

	/**********
	 * This is the system startup page Pane
	 */
	public static Pane theWindowPaneRoot;

	/**********
	 * The startup page for role: 1 = Admin; 2 = Student; 3 = Reviewer; 4 = Instructor; 5 = Staff
	 */
	public static int activeHomePage;
	
	private static  Database database = new Database();
	
    private Alert databaseInUse = new Alert(AlertType.INFORMATION);
    
    
	/**********
	 * <p> Method: FCMainClass() </p>
	 * 
	 * <p> Description: This is the default constructor used to establish this main class.
	 */
   
    public FCMainClass() {
    	
    }


	/**********
	 * <p> Method: void start(Stage primaryStage) </p>
	 * 
	 * <p> Description: This constructor is called by the JavaFX runtime once it has been properly
	 * loaded into memory and has set up the basic environment.
	 * 
	 * This method establishes the basic properties of the "Stage" object on which the various
	 * application windows reside.</p>
	 * 
	 * @param primaryStage is the JavaFX Stage object upon which the GUI is built
	 * 
	 */
	@Override
	public void start(Stage primaryStage) {
		
		theStage = primaryStage;

		// Create a pane within the window
		Pane windowPaneRoot = new Pane();				
		theWindowPaneRoot = windowPaneRoot;
		
		// Create the Graphical User Interface
		try {
			// Connect to the database
			database = new Database();
			database.connectToDatabase();
		} catch (SQLException e) {
			// If the connection request fails, it usually means some other app is using it
			databaseInUse.setTitle("*** ERROR ***");
			databaseInUse.setHeaderText("Database Is Already Being Used");
			databaseInUse.setContentText("Please stop the other instance and try again!");
			databaseInUse.showAndWait();
			System.exit(0);
		} 

		// Establish the GUI for the for the very first use
		theGUISystemStartup = new GUISystemStartUpPage(primaryStage, windowPaneRoot, database);	
		
		Scene scene = new Scene(windowPaneRoot, WINDOW_WIDTH, WINDOW_HEIGHT);
		theGUIStartupPageScene = scene;
														// Create a scene in that window pane
		primaryStage.setScene(scene);					// Set the scene on the stage
		primaryStage.show();							// Show the stage to the user

		// When the stage is shown to the user, the pane within the window is visible.  This means
		// that the labels, fields, and buttons of the Graphical User Interface (GUI) are visible 
		// and it is now possible for the user to select input fields and enter values into them, 
		// click on buttons, and read the labels, the results, and the error messages.
		//
		// After setting up the GUI, this application class stops and all control is turned over to
		// the GUI as specified in the guiStartupClass.java class.

	}
	
	/*********************************************************************************************/

	/*******
	 * <p> Method: static void main(String[] args)</p>
	 * 
	 * <p> Description: The method that launches the JavaFX application.</p>
	 * 
	 * @param args are the command line run arguments
	 */
	public static void main(String[] args) {				// This method may not be required
		launch(args);										// for all JavaFX applications using
	}														// other IDEs.
}
