package guiPageClasses;

import applicationMainMethodClasses.FCMainClass;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import databaseClasses.Database;
import entityClasses.Question;
import entityClasses.QuestionSet;
import entityClasses.Answer;
import entityClasses.AnswerSet;
import entityClasses.User;
import entityClasses.PrivateMessage; // Noah Edit
import javafx.scene.control.TextInputDialog; // John edit
import java.util.Optional; // John edit
import java.util.ArrayList; // Noah Edit
import java.util.List;

/*******
 * <p> Title: GUIReviewerHomePage Class. </p>
 * 
 * <p> Description: The Java/FX-based Single Role Home Page.</p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Noah Dow
 * 
 * @version 1.00		2025-04-20 Initial version
 *  
 */

public class GUIReviewerHomePage {
	
	/**********************************************************************************************

	Attributes
	
	**********************************************************************************************/
	
	// These are the application values required by the user interface
	private Label label_PageTitle = new Label();
	private Label label_UserDetails = new Label();
	private Button button_UpdateThisUser = new Button("Account Update");

	private Line line_Separator1 = new Line(20, 95, FCMainClass.WINDOW_WIDTH-20, 95);
	private Line line_Separator4 = new Line(20, 525, FCMainClass.WINDOW_WIDTH-20,525);

	private Button button_ViewAllQuestions = new Button("View All Questions");
	private Button button_ViewMyReviews = new Button("View My Reviews");

	private ScrollPane questionPaneScroll = new ScrollPane();
	private Pane questionPane;
	private QuestionSet questionSet = new QuestionSet();
	private Question quest;

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
	 * <p> Method: GUIReviewerHomePage(Stage ps, Pane theRoot, Database database, User user) </p>
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
	public GUIReviewerHomePage(Stage ps, Pane theRoot, Database database, User user) {
		GUISystemStartUpPage.theReviewerHomePage = this;
		
		FCMainClass.activeHomePage = 3;

		primaryStage = ps;
		theRootPane = theRoot;
		theDatabase = database;
		theUser = user;
		
		double WINDOW_WIDTH = FCMainClass.WINDOW_WIDTH;

		primaryStage.setTitle("CSE 360 Foundation Code: User Home Page");

		label_PageTitle.setText("Reviewer Home Page");
		setupLabelUI(label_PageTitle, "Arial", 28, WINDOW_WIDTH, Pos.CENTER, 0, 5);

		label_UserDetails.setText("User: " + user.getUserName());
		setupLabelUI(label_UserDetails, "Arial", 20, WINDOW_WIDTH, Pos.BASELINE_LEFT, 20, 55);

		setupButtonUI(button_UpdateThisUser, "Dialog", 18, 170, Pos.CENTER, 610, 45);
		button_UpdateThisUser.setOnAction((event) -> { performUpdate(); });

		setupButtonUI(button_ViewAllQuestions, "Dialog", 18, 150, Pos.CENTER, 20, 110);
		button_ViewAllQuestions.setOnAction((event) -> { seeAllQuestions(); });

		//NOAH EDITS: 
		setupButtonUI(button_ViewMyReviews, "Dialog", 18, 150, Pos.CENTER, 20, 150);
		button_ViewMyReviews.setOnAction((event) -> { seeMyReviews(); });

		questionPane = new Pane();
		questionPane.setLayoutX(WINDOW_WIDTH / 2 - 100);
		questionPane.setLayoutY(100);

		questionPaneScroll.setContent(questionPane);
		questionPaneScroll.setLayoutX(WINDOW_WIDTH / 2 - 100);
		questionPaneScroll.setLayoutY(100);
		questionPaneScroll.setMaxSize(WINDOW_WIDTH / 2 + 100, 360);
		questionPaneScroll.setMinSize(WINDOW_WIDTH / 2 + 100, 360);

		setupButtonUI(button_Logout, "Dialog", 18, 250, Pos.CENTER, 20, 540);
		button_Logout.setOnAction((event) -> { performLogout(); });

		setupButtonUI(button_Quit, "Dialog", 18, 250, Pos.CENTER, 300, 540);
		button_Quit.setOnAction((event) -> { performQuit(); });

		loadQuestionsFromDatabase();

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
			label_PageTitle,
			label_UserDetails,
			button_UpdateThisUser,
			button_ViewAllQuestions,
			button_ViewMyReviews, // NOAH EDITS
			line_Separator1,
			line_Separator4,
			questionPaneScroll,
			button_Logout,
			button_Quit
		);
		questionPane.getChildren().clear();
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

	private void performQuit() {
		System.exit(0);
	}

	private void loadQuestionsFromDatabase() {
		questionSet = new QuestionSet();
		for (Question q : theDatabase.getAllQuestions()) {
			questionSet.addQuestion(q);
		}
	}

	private void seeAllQuestions() {
		/*This method populates all questions that have been asked as well as a button linked 
		 * to view the answers/responses of each of the questions*/

		questionPane.getChildren().clear();
		Text text;
		Button button;

		for (int i = 0; i < questionSet.getNumQuestions(); i++) {
			quest = questionSet.getQuestion(i);

			text = new Text(quest.getText());
			text.setLayoutX(100);
			text.setLayoutY(23 + (i * 30));

			button = new Button("See Replies");
			setupButtonUI(button, "Dialog", 10, 0, Pos.CENTER, 0, 10 + (i * 30));
			button.setOnAction((event) -> {
				Button but = (Button) event.getSource();
				viewQuestionAnswers(questionSet.getQuestion(((int)but.getLayoutY() / 30)));
			});

			questionPane.getChildren().addAll(text, button);
		}
	}

	//NOAH EDITS:
	private void seeMyReviews() {
	    //Always reload latest questions/answers so private feedback is current!
	    loadQuestionsFromDatabase();

	    questionPane.getChildren().clear();
	    ArrayList<Answer> myReviews = getAllReviewsByReviewer(theUser);
	    int yOffset = 20;

	    if (myReviews == null || myReviews.size() == 0) {
	        Text noReviews = new Text("You have not written any reviews yet.");
	        noReviews.setLayoutX(20);
	        noReviews.setLayoutY(yOffset);
	        questionPane.getChildren().add(noReviews);
	        return;
	    }

	    for (int i = 0; i < myReviews.size(); i++) {
	        Answer review = myReviews.get(i);
		    
	        List<PrivateMessage> feedbacks = review.getPrivateMessages();
	        int feedbackCount = (feedbacks != null) ? feedbacks.size() : 0;

	        String reviewSummary = String.format(
	            "Review on Q: %s\nYour Review: %s\nPrivate Feedback Messages: %d",
	            review.getQuestion().getText(),
	            review.getText(),
	            feedbackCount
	        );

	        Text reviewText = new Text(reviewSummary);
	        reviewText.setLayoutX(20);
	        reviewText.setLayoutY(yOffset + i * 80);

	        Button viewFeedbackBtn = new Button("View Feedback");
	        setupButtonUI(viewFeedbackBtn, "Dialog", 12, 130, Pos.CENTER, 400, yOffset - 10 + i * 80);
	        final Answer reviewFinal = review;
	        viewFeedbackBtn.setOnAction(e -> {
	            seeMessagesForReview(reviewFinal);
	        });

	        questionPane.getChildren().addAll(reviewText, viewFeedbackBtn);
	    }
	}


	//NOAH EDITS
	private ArrayList<Answer> getAllReviewsByReviewer(User reviewer) {
	    ArrayList<Answer> allReviews = new ArrayList<>();
	    for (int q = 0; q < questionSet.getNumQuestions(); q++) {
	        Question question = questionSet.getQuestion(q);
	        AnswerSet answerSet = question.getAnswers();
	        for (int a = 0; a < answerSet.getNumAnswers(); a++) {
	            Answer answer = answerSet.getAnswer(a);
	            if (answer.getUser() != null && answer.getUser().getUserName().equals(reviewer.getUserName())) {
	                allReviews.add(answer);
	            }
	        }
	    }
	    return allReviews;
	}

	//NOAH EDITS
	private void seeMessagesForReview(Answer review) {
	    questionPane.getChildren().clear();

	    List<PrivateMessage> feedbacks = review.getPrivateMessages();

	    int yOffset = 85;

	    Text header = new Text("Private Feedback for Your Review:");
	    header.setLayoutX(20);
	    header.setLayoutY(30);
	    questionPane.getChildren().add(header);

	    Text reviewText = new Text(review.getText());
	    reviewText.setLayoutX(40);
	    reviewText.setLayoutY(55);
	    questionPane.getChildren().add(reviewText);

	    if (feedbacks == null || feedbacks.size() == 0) {
	        Text none = new Text("No private feedback yet.");
	        none.setLayoutX(20);
	        none.setLayoutY(yOffset);
	        questionPane.getChildren().add(none);
	        return;
	    }

	    for (int i = 0; i < feedbacks.size(); i++) {
	        PrivateMessage pm = feedbacks.get(i);
	        Text pmText = new Text(String.format("[%s]: %s", pm.getSender().getUserName(), pm.getContent()));
	        pmText.setLayoutX(20);
	        pmText.setLayoutY(yOffset + i * 30);
	        questionPane.getChildren().add(pmText);
	    }
	}


    // John edit - method to write a review for an answer
    private void viewQuestionAnswers(Question q) {

        questionPane.getChildren().clear();

        AnswerSet replies = q.getAnswers();
        Answer ans;

        for (int i = 0; i < replies.getNumAnswers(); i++) {
                ans = replies.getAnswer(i);
                String answerDisplay = String.format("[%s]: %s", ans.getUser().getUserName(), ans.getText());

                Text text = new Text(answerDisplay);
                text.setLayoutX(0);
                text.setLayoutY(30 + (i * 40));

                Button writeReview = new Button("Write Review");
                setupButtonUI(writeReview, "Dialog", 12, 0, Pos.CENTER_LEFT, 300, 15 + (i * 40));
                final Answer currentAnswer = ans;
                writeReview.setOnAction(evt -> {
                        TextInputDialog dialog = new TextInputDialog();
                        dialog.setTitle("Write Review");
                        dialog.setHeaderText("Enter your review text");
                        Optional<String> result = dialog.showAndWait();
                        result.ifPresent(reviewText -> {
                                Answer rev = new Answer();
                                rev.setUser(theUser);
                                rev.setQuestion(currentAnswer.getQuestion());
                                rev.setText(reviewText);
                                currentAnswer.addReview(rev);
                                theDatabase.addReview(currentAnswer.getUser().getUserName(),
                                                currentAnswer.getText(), theUser.getUserName(), reviewText);
                        });
                });

                questionPane.getChildren().addAll(text, writeReview);
        }
    }
}
