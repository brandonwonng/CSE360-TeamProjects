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
import entityClasses.ReviewerTrust;
import entityClasses.Answer;
import entityClasses.AnswerSet;
import entityClasses.PrivateMessage;
import javafx.scene.text.*;
import utilityClasses.UnreadAnswerTracker; // added for tracking unread answers
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class GUIStudentTrustedReviewerPage{

	private Label label_PageTitle = new Label();
    private Label label_Purpose =  new Label("Manage who your trusted reviewers are."); 
	private Line line_Separator1 = new Line(20, 95, FCMainClass.WINDOW_WIDTH-20, 95);
	private Line line_Separator4 = new Line(20, 525, FCMainClass.WINDOW_WIDTH-20,525);
	private Button button_BackToStudentHomePage = new Button("Return to the Home Page");
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

	public GUIStudentTrustedReviewerPage(Stage ps, Pane theRoot, Database database, User user) {
		thePrimaryStage = ps;
		theRootPane = theRoot;
		theDatabase = database;
		theUser = user;
		trustedReviewers = theDatabase.getTrustedReviewers(theUser.getUserName());
		allReviewers.clear();
		generateAllReviewers();
		double WINDOW_WIDTH = FCMainClass.WINDOW_WIDTH;
		untrustedReviewerPane = new Pane();
		untrustedReviewerPane.setLayoutX(WINDOW_WIDTH/2);
		untrustedReviewerPane.setLayoutY(100);
		trustedReviewerPane = new Pane();
		trustedReviewerPane.setLayoutX(0);
		trustedReviewerPane.setLayoutY(100);
		untrustedReviewerPaneScroll.setContent(untrustedReviewerPane);
		untrustedReviewerPaneScroll.setLayoutX(WINDOW_WIDTH/2);
		untrustedReviewerPaneScroll.setLayoutY(100);
		untrustedReviewerPaneScroll.setMaxSize(WINDOW_WIDTH/2, 360);
		untrustedReviewerPaneScroll.setMinSize(WINDOW_WIDTH/2, 360);
		trustedReviewerPaneScroll.setContent(trustedReviewerPane);
		trustedReviewerPaneScroll.setLayoutX(0);
		trustedReviewerPaneScroll.setLayoutY(100);
		trustedReviewerPaneScroll.setMaxSize(WINDOW_WIDTH/2, 360);
		trustedReviewerPaneScroll.setMinSize(WINDOW_WIDTH/2, 360);
		thePrimaryStage.setTitle("CSE 360 Foundation Code: Student Trusted Reviewer Page");
		label_PageTitle.setText("Student Trusted Reviewer Page");
		setupLabelUI(label_PageTitle, "Arial", 28, WINDOW_WIDTH, Pos.CENTER, 0, 5);
		setupLabelUI(label_Purpose, "Arial", 10, WINDOW_WIDTH, Pos.CENTER, 0, 35);
        setupButtonUI(button_BackToStudentHomePage, "Dialog", 18, 300, Pos.CENTER, WINDOW_WIDTH/2-150, 475);
        button_BackToStudentHomePage.setOnAction((event) -> {goToStudentHomePage();});
        setupButtonUI(button_Logout, "Dialog", 18, 250, Pos.CENTER, 20, 540);
        button_Logout.setOnAction((event) -> {performLogout(); });
        setupButtonUI(button_Quit, "Dialog", 18, 250, Pos.CENTER, 300, 540);
        button_Quit.setOnAction((event) -> {performQuit(); });
        theRoot.getChildren().clear();
        theRoot.getChildren().addAll(label_PageTitle,
        	untrustedReviewerPaneScroll,
        	trustedReviewerPaneScroll,
        	label_Purpose,
        	button_BackToStudentHomePage, 
        	line_Separator4,
        	line_Separator1,
        	button_Logout,
        	button_Quit);
        trustedReviewerPane.getChildren().clear();
        untrustedReviewerPane.getChildren().clear();
        populateTrustedReviewers();
        populateUntrustedReviewers();
	}

	public void setup(User user) {
		theUser = user;
		trustedReviewers = theDatabase.getTrustedReviewers(theUser.getUserName());
		allReviewers.clear();
		generateAllReviewers();
		theRootPane.getChildren().clear();
		theRootPane.getChildren().addAll(label_PageTitle,
        	untrustedReviewerPaneScroll,
        	trustedReviewerPaneScroll,
        	label_Purpose,
        	button_BackToStudentHomePage, 
        	line_Separator4,
        	line_Separator1,
        	button_Logout,
        	button_Quit);
		trustedReviewerPane.getChildren().clear();
        untrustedReviewerPane.getChildren().clear();
        populateTrustedReviewers();
        populateUntrustedReviewers();
	}

	private void setupLabelUI(Label l, String ff, double f, double w, Pos p, double x, double y){
		l.setFont(Font.font(ff, f));
		l.setMinWidth(w);
		l.setAlignment(p);
		l.setLayoutX(x);
		l.setLayoutY(y);		
	}

	private void setupButtonUI(Button b, String ff, double f, double w, Pos p, double x, double y){
		b.setFont(Font.font(ff, f));
		b.setMinWidth(w);
		b.setAlignment(p);
		b.setLayoutX(x);
		b.setLayoutY(y);		
	}

	private void goToStudentHomePage() {
		if (GUISystemStartUpPage.theStudentHomePage == null) {
			GUISystemStartUpPage.theStudentHomePage = new GUIStudentHomePage(thePrimaryStage, theRootPane, theDatabase, theUser);
		}
		else	GUISystemStartUpPage.theStudentHomePage.setup();
	}

	private void populateTrustedReviewers() {
		trustedReviewerPane.getChildren().clear();
		trustedReviewers = theDatabase.getTrustedReviewers(theUser.getUserName());
		Text reviewerName;
		Button removeTrust;
		for (int i = 0; i < trustedReviewers.size(); i++) {
		    User trustedUser = trustedReviewers.get(i);
		    ReviewerTrust trust = theUser.getReviewerTrust(trustedUser.getUserName());
		    int weight = (trust != null) ? trust.getWeight() : -1;

		    reviewerName = new Text(trustedUser.getUserName() + " (Weight: " + weight + ")");
		    reviewerName.setLayoutX(10);
		    reviewerName.setLayoutY(15 + (i * 40));

			removeTrust = new Button("Remove"); 
			setupButtonUI(removeTrust, "Dialog", 12, 0, Pos.CENTER, 235,(i * 40));
			removeTrust.setOnAction((event) -> {
				Button but = (Button) event.getSource();
				User theUntrusted = trustedReviewers.get((int)but.getLayoutY()/40);
				theUser.removeTrustedReviewer(theDatabase, theUntrusted);
				allReviewers.add(theUntrusted);
				populateUntrustedReviewers();
				populateTrustedReviewers();
			});

			Button editWeight = new Button("Edit Weight");
			setupButtonUI(editWeight, "Dialog", 12, 100, Pos.CENTER_LEFT, 300, (i * 40));

			ReviewerTrust trust2 = theUser.getReviewerTrust(trustedUser.getUserName());
			editWeight.setOnAction(e -> showEditWeightDialog(trust2));

			trustedReviewerPane.getChildren().addAll(removeTrust, editWeight, reviewerName);
		}
	}

	private void populateUntrustedReviewers() {
		untrustedReviewerPane.getChildren().clear();
		trustedReviewers = theDatabase.getTrustedReviewers(theUser.getUserName());
		Text reviewerName;
		Button giveTrust;
		for(int i = 0; i < allReviewers.size(); i++) {
			reviewerName = new Text(allReviewers.get(i).getUserName());
			reviewerName.setLayoutX(10);
			reviewerName.setLayoutY(15+(i*40));
			giveTrust = new Button("Give Trust"); 
			setupButtonUI(giveTrust, "Dialog", 12, 0, Pos.CENTER, 300, (i * 40));
			giveTrust.setOnAction((event) -> {
				Button but = (Button) event.getSource();
				User theTrusted = allReviewers.get((int)but.getLayoutY()/40);
				theUser.addTrustedReviewer(theDatabase, theTrusted, 5);
				allReviewers.remove(theTrusted);
				populateUntrustedReviewers();
				populateTrustedReviewers();
			});
			untrustedReviewerPane.getChildren().addAll(giveTrust, reviewerName);
		}
	}

	private void generateAllReviewers() {
		User reviewer;
		boolean flag;
		for(String username: theDatabase.getUserList()) {
			flag = false;
			for(User user : trustedReviewers) {
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

	private void showEditWeightDialog(ReviewerTrust trust) {
		if (trust == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Reviewer Not Found");
			alert.setHeaderText(null);
			alert.setContentText("This reviewer is not currently trusted or was not found in your list.");
			alert.showAndWait();
			return;
		}

		TextInputDialog dialog = new TextInputDialog(String.valueOf(trust.getWeight()));
		dialog.setTitle("Edit Reviewer Weight");
		dialog.setHeaderText("Change weight for " + trust.getReviewer().getUserName());
		dialog.setContentText("New weight (1–10):");
		dialog.showAndWait().ifPresent(newVal -> {
			try {
				int newWeight = Integer.parseInt(newVal);
				if (newWeight >= 1 && newWeight <= 10) {
					trust.setWeight(newWeight);
					populateTrustedReviewers();
				} else {
					Alert invalid = new Alert(Alert.AlertType.WARNING, "Weight must be between 1 and 10.");
					invalid.showAndWait();
				}
			} catch (NumberFormatException ex) {
				Alert error = new Alert(Alert.AlertType.ERROR, "Invalid number entered.");
				error.showAndWait();
			}
		});
	}

	private void performLogout() {
		GUISystemStartUpPage.theSystemStartupPage.setup();
	}

	private void performQuit() {
		System.out.println("Perform Quit");
		System.exit(0);
	}
}
