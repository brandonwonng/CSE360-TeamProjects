package guiPageClasses;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import databaseClasses.Database;
import entityClasses.User;

/*******
 * <p> Title: GUISingleRoleDispatch Class. </p>
 * 
 * <p> Description: The class dispatches the execution to the appropriate role's home
 * page when the user has only one role.  This is not actually a GUI page... it just dispatches
 * to an actual GUI page for the specified role.
 * 
 * WHen a user has more than one role, a different
 * class, GUIMultipleRoleHomePage, asks the user which of their roles do they want to use,
 * and then it dispatches the user to that role's home page.</p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Lynn Robert Carter
 * 
 * @version 1.00		2025-04-20 Initial version
 *  
 */

public class GUISingleRoleDispatch {
	
	/**********************************************************************************************

	Attributes
	
	**********************************************************************************************/
	
	// These are the application values required by the user interface

	private Stage primaryStage;	
	private Pane theRootPane;
	private Database theDatabase;
	private User theUser;

	/**********************************************************************************************

	Constructors
	
	**********************************************************************************************/

	
	/**********
	 * <p> Method: GUISingleRoleDispatch() </p>
	 * 
	 * <p> Description: This method initializes all the elements of the graphical user interface. 
	 * This method determines the location, size, font, color, and change and event handlers for 
	 * each GUI object. </p>
	 * 
	 */
	public GUISingleRoleDispatch() {
	}

	
	/**********
	 * <p> Method: doSingleRoleDispatch(Stage ps, Pane theRoot, Database database, User user) </p>
	 * 
	 * <p> Description: This method is called after a GUI page has alfready been established and
	 * it is being display with potentially new contents for the various GUI elements. </p>
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
	public void doSingleRoleDispatch(Stage ps, Pane theRoot, Database database, User user) {
		primaryStage = ps;
		theRootPane = theRoot;
		theDatabase = database;
		theUser = user;
		
		if (user.getAdminRole()) {
			theRootPane.getChildren().clear();
			new GUIAdminHomePage(primaryStage, theRootPane, theDatabase, theUser);
		} else if (user.getStudentRole()) {
			theRoot.getChildren().clear();
			new GUIStudentHomePage(primaryStage, theRootPane, theDatabase, theUser);			
		} else if (user.getReviewerRole()) {
			theRoot.getChildren().clear();
			new GUIReviewerHomePage(primaryStage, theRootPane, theDatabase, theUser);			
		} else if (user.getInstructorRole()) {
			theRoot.getChildren().clear();
			new GUIInstructorHomePage(primaryStage, theRootPane, theDatabase, theUser);			
		} else if (user.getStaffRole()) {
			theRoot.getChildren().clear();
			new GUIStaffHomePage(primaryStage, theRootPane, theDatabase, theUser);			
		} else {
			// Invalid role
			System.out.println("*** ERROR *** GUISingleRoleDispatch was asked to dispatch to " +
			"a role that is not supported!");
		}
	}
}
