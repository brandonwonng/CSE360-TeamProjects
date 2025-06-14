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
import entityClasses.Answer;
import entityClasses.AnswerSet;
import entityClasses.PrivateMessage;
import javafx.scene.text.*;
import utilityClasses.UnreadAnswerTracker; // added for tracking unread answers

/*******
 * <p> Title: GUIStudentQuestionPage Class. </p>
 * 
 * <p> Description: The Java/FX-based Student Question Page.</p>
 * 
 * <p> Copyright: Clay Hauser © 2025 </p>
 * 
 * @author Clay Hauser
 * 
 * @version 1.00		2025-04-20 Initial version
 *  
 */

public class GUIStudentQuestionPage {
	
	/**********************************************************************************************

	Attributes
	
	**********************************************************************************************/
	
	// These are the application values required by the user interface
	private Label label_PageTitle = new Label();

    private Label label_Purpose = 
    		new Label(" Use this page to ask questions to others."); 
    
	private Line line_Separator1 = new Line(20, 95, FCMainClass.WINDOW_WIDTH-20, 95);
	
	private Line line_Separator4 = new Line(20, 525, FCMainClass.WINDOW_WIDTH-20,525);
	
	private Button button_ViewAllQuestions = new Button("View All Questions");
	
	//Create the button to allow asking of a new question
	Button button_postQuestion = new Button("Ask a new Question");
	
	private Button button_ViewUnresolved = new Button("View All Unresolved Questions");
	private Button button_ViewRecentQuestions = new Button("Recently Asked Questions");
	private Button button_ViewMyAll = new Button("View All of My Questions");
    	private Button button_ViewMyUnresolved = new Button("View My Unresolved Questions");
	private Button button_ViewResolved = new Button("View All Resolved Questions");
	
	private Button button_BackToStudentHomePage = new Button("Return to the Student Home Page");
	
	private ScrollPane questionPaneScroll = new ScrollPane();
	
	QuestionSet questionSet = new QuestionSet();
	QuestionSet questionSubSet;
	Question quest;
	
    private UnreadAnswerTracker unreadTracker = new UnreadAnswerTracker(); // added for tracking unread answers

	private Button button_Logout = new Button("Logout");
	private Button button_Quit = new Button("Quit");

	private Stage thePrimaryStage;
	private Pane theRootPane;
	private Pane questionPane;
	private Database theDatabase;
	private User theUser;
	
	
	Optional<String> result;


	/**********************************************************************************************

	Constructors
	
	**********************************************************************************************/

	
	/**********
	 * <p> Method: GUIAdminUpdatePage(Stage ps, Pane theRoot, Database database, User user) </p>
	 * 
	 * <p> Description: This method initializes all the elements of the graphical user interface
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
	public GUIStudentQuestionPage(Stage ps, Pane theRoot, Database database, User user) {
		thePrimaryStage = ps;
		theRootPane = theRoot;
		theDatabase = database;
		theUser = user;
		
		double WINDOW_WIDTH = FCMainClass.WINDOW_WIDTH;
		
		//Create a new pane to house the question objects
		questionPane = new Pane();
		questionPane.setLayoutX(WINDOW_WIDTH/2 - 100);
		questionPane.setLayoutY(100);
		
		//Create a scrollpane to enable scrolling in the question window
		questionPaneScroll.setContent(questionPane);
		questionPaneScroll.setLayoutX(WINDOW_WIDTH/2 - 100);
		questionPaneScroll.setLayoutY(100);
		
		//Lock Window Size to WINDOW_WIDTH/2+100, 360
		questionPaneScroll.setMaxSize(WINDOW_WIDTH/2+100, 360);
		questionPaneScroll.setMinSize(WINDOW_WIDTH/2+100, 360);
		
		//Set the stage title
		thePrimaryStage.setTitle("CSE 360 Foundation Code: Student Question Page");
		
		label_PageTitle.setText("Student Question Page");
		setupLabelUI(label_PageTitle, "Arial", 28, WINDOW_WIDTH, Pos.CENTER, 0, 5);
		
		setupLabelUI(label_Purpose, "Arial", 10, WINDOW_WIDTH, Pos.CENTER, 0, 35);
		
		//Set up for all main buttons on the page
		setupButtonUI(button_ViewAllQuestions, "Dialog", 18, 150, 
        		Pos.CENTER, 20, 110);
		button_ViewAllQuestions.setOnAction((event) -> {seeAllQuestions();});
		
		setupButtonUI(button_ViewUnresolved, "Dialog", 18, 150, 
        		Pos.CENTER, 20, 150);
		button_ViewUnresolved.setOnAction((event) -> {seeUnresolved();});
		
		setupButtonUI(button_ViewResolved, "Dialog", 18, 150, 
        		Pos.CENTER, 20, 190);
		button_ViewResolved.setOnAction((event) -> {seeResolved();});
		
        	setupButtonUI(button_ViewMyUnresolved, "Dialog", 18, 210,
                Pos.CENTER, 20, 270);
        	button_ViewMyUnresolved.setOnAction((event) -> {seeMyUnresolved();});

		setupButtonUI(button_ViewMyAll, "Dialog", 18, 210,
                Pos.CENTER, 20, 310);
        	button_ViewMyAll.setOnAction((event) -> {seeMyAll();});
		
		// Initialize the dialog box for inputting answers/responses
		TextInputDialog dialogAskQuestion = new TextInputDialog();
		dialogAskQuestion.setTitle("Enter your Question");
		dialogAskQuestion.setHeaderText("Enter your Question");
		
		setupButtonUI(button_postQuestion, "Dialog", 18, 150, 
				Pos.CENTER, 20, 230);
		button_postQuestion.setOnAction((event) -> {result = dialogAskQuestion.showAndWait();
		if(result.isPresent()) {
			askQuestion(result.get());}
		dialogAskQuestion.getEditor().clear();
		});

		setupButtonUI(button_ViewRecentQuestions, "Dialog", 18, 150, 
			    Pos.CENTER, 20, 310); // Adjust Y as needed based on layout
		button_ViewRecentQuestions.setOnAction((event) -> {seeRecentQuestions();});
		
        setupButtonUI(button_BackToStudentHomePage, "Dialog", 18, 300, 
        		Pos.CENTER, WINDOW_WIDTH/2-150, 475);
        button_BackToStudentHomePage.setOnAction((event) -> {goToStudentHomePage();});
		
        setupButtonUI(button_Logout, "Dialog", 18, 250, Pos.CENTER, 20, 540);
        button_Logout.setOnAction((event) -> {performLogout(); });
        
        setupButtonUI(button_Quit, "Dialog", 18, 250, Pos.CENTER, 300, 540);
        button_Quit.setOnAction((event) -> {performQuit(); });
        
        //The GOAL for here is to pull from the database and fill the question set.
        //Questions SHOULD be linked to a AnswerSet so there should not be a need to
        //fill the answer set as well
        
    	//FIXME: For loop for testing purposes, creates the questions
    	for(int i = 0; i< 3; i++) { //Clay edit I was going to remove all of these but didnt incase anyone is still using them for testing
    		quest = new Question();
    		quest.setText(String.format("This is intended to simulate a very long question that someone may have because I want to see if the text wraps or keeps going in a line %d", i));
    		quest.setUser(theUser);
    		quest.setResolution(false);
    		questionSet.addQuestion(quest);
    	}
    	//FIXME: For loop for testing purposes, creates the resolved questions with a set of answers
    	for(int i = 0; i< 3; i++) {
    		quest = new Question();
    		quest.setText(String.format("RESOLVED QUESTION TEST:This is intended to simulate a very long question that someone may have because I want to see if the text wraps or keeps going in a line %d", i));
    		quest.setUser(theUser);
    		quest.setResolution(true);
    		questionSet.addQuestion(quest);
	    		for(int j = 0; j < 10; j++) {
	    			Answer reply = new Answer();
	    			reply.setText(String.format("This is a sample answer %d for question %d", j,i));
				reply.setQuestion(quest);
	    			quest.addReply(reply);
	    		}
    	}

	loadQuestionsFromDatabase(); // John edit: load questions from the database
    	
        // Place all of the just-initialized GUI elements into the pane
        theRoot.getChildren().clear();
        theRoot.getChildren().addAll(label_PageTitle,
        		questionPaneScroll,
           		label_Purpose,
        		button_BackToStudentHomePage, 
        		button_ViewAllQuestions,
        		button_ViewUnresolved,
			button_ViewResolved,
                	button_ViewMyUnresolved,
			button_ViewRecentQuestions,
			button_ViewMyAll,
        		line_Separator4,
        		line_Separator1,
        		button_postQuestion,
        		button_Logout,
        		button_Quit
        		);
        questionPane.getChildren().clear();
        // Check database actions
        
	}
	
	
	public void setup(User user) {
		theUser = user; //Clay Edit
		loadQuestionsFromDatabase(); // John edit: load questions from the database
		theRootPane.getChildren().clear();
		theRootPane.getChildren().addAll(label_PageTitle,
        		questionPaneScroll,
           		label_Purpose,
        		button_BackToStudentHomePage, 
        		button_ViewAllQuestions,
        		button_ViewUnresolved,
			button_ViewResolved,
                	button_ViewMyUnresolved,
			button_ViewRecentQuestions,
			button_ViewMyAll,
        		line_Separator4,
        		line_Separator1,
        		button_postQuestion,
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
	
	private void goToStudentHomePage() {
        // Proceed to the student account update page
		if (GUISystemStartUpPage.theStudentHomePage == null) {
			GUISystemStartUpPage.theStudentHomePage = 
					new GUIStudentHomePage(thePrimaryStage, theRootPane, theDatabase, theUser);
		}
		else	
			GUISystemStartUpPage.theStudentHomePage.setup();
	}
	
	
	private void performLogout() {
		GUISystemStartUpPage.theSystemStartupPage.setup();
	}

	
	private void performQuit() {
		System.out.println("Perform Quit");
		System.exit(0);
	}
	
	// John edit: Method to ask a question
	private void askQuestion(String resultText) {
	Question q = new Question();
	q.setUser(theUser);
	q.setText(resultText);
	q.setResolution(false);
	questionSet.addQuestion(q);
	theDatabase.addQuestion(theUser.getUserName(), resultText);
	}
	
	// John edit: Load questions from the database
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
        //this will change from a fixed length loop, to a loop based on the questionSet.getNumQuestions()
    	for(int i = 0; i< questionSet.getNumQuestions(); i++) {
    		quest = questionSet.getQuestion(i);
			//create the text fields and display it
    		text = new Text(quest.getText());
    		text.setLayoutX(100);
    		text.setLayoutY(23 + (i * 30));
    		//set up the button to view the replies
    		button = new Button("See Replies");
    		setupButtonUI(button, "Dialog", 10, 0, 
            		Pos.CENTER, 0, 10 + (i * 30));
    		button.setOnAction((event) -> {
    			//To get the button linked to the correct question, we take Y axis divided
    			//by the standard separation of the lines revealing the index of the correct question
    			Button but = (Button) event.getSource();
    			viewQuestionAnswers(questionSet.getQuestion(((int)but.getLayoutY()/30)));
    			});
    		questionPane.getChildren().addAll(text, button);
    	}
	}
	
	private void seeUnresolved() {
		/*This method populates only the questions that have been labeled as "unresolved"
		 * It also creates a button linked to each question which allows a user to view 
		 * the answers associated with that question*/
		
		questionPane.getChildren().clear();
        Text text;
        Button button;
        //Establish a subset for the new questions that meet the criteria 
        questionSubSet = questionSet.filterQuestions(false);
        
        for(int i = 0; i < questionSubSet.getNumQuestions(); i++) {
			//Check the status of the question, if it is unresolved then we can 
			//create the text fields and display it
        	quest = questionSubSet.getQuestion(i);
			text = new Text(quest.getText());
    		text.setLayoutX(100);
    		text.setLayoutY(23 + (i * 30));
    		//set up the button to view the replies
    		button = new Button("See Replies");
    		setupButtonUI(button, "Dialog", 10, 0, 
            		Pos.CENTER, 0, 10 + (i * 30));
    		button.setOnAction((event) -> {
    			//To get the button linked to the correct question, we take Y axis divided
    			//by the standard separation of the lines revealing the index of the correct question
    			Button but = (Button) event.getSource();
    			viewQuestionAnswers(questionSubSet.getQuestion((int)but.getLayoutY()/30));
    		});
    		questionPane.getChildren().addAll(text, button);
    		}
    	}	

	private void seeResolved() {
	/*This method populates only the questions that have been labeled as "resolved"
	 * It also creates a button linked to each question which allows a user to view 
	 * the answers associated with that question*/
		
	questionPane.getChildren().clear();
        Text text;
        Button button;
        //Establish a subset for the new questions that meet the criteria 
        questionSubSet = questionSet.filterQuestions(true);
        
        for(int i = 0; i < questionSubSet.getNumQuestions(); i++) {
		//Check the status of the question, if it is unresolved then we can 
		//create the text fields and display it
        	quest = questionSubSet.getQuestion(i);
		text = new Text(quest.getText());
    		text.setLayoutX(100);
    		text.setLayoutY(23 + (i * 30));
    		//set up the button to view the replies
    		button = new Button("See Replies");
    		setupButtonUI(button, "Dialog", 10, 0, 
            		Pos.CENTER, 0, 10 + (i * 30));
    		button.setOnAction((event) -> {
    			//To get the button linked to the correct question, we take Y axis divided
    			//by the standard separation of the lines revealing the index of the correct question
    			Button but = (Button) event.getSource();
    			viewQuestionAnswers(questionSubSet.getQuestion((int)but.getLayoutY()/30));
    		});
    		questionPane.getChildren().addAll(text, button);
    		}
    	}	
	
    private void seeMyUnresolved() {
        /*Display only unresolved questions posted by the current user along
         * with a count of unread replies*/

        questionPane.getChildren().clear();
	    Text text;
	    Button button;
	    System.out.println(String.format("%s", theUser.getUserName()));
		QuestionSet userSet = questionSet.filterQuestions(theUser);
		questionSubSet = userSet.filterQuestions(false);
				
		for(int i = 0; i < questionSubSet.getNumQuestions(); i++) {
		                quest = questionSubSet.getQuestion(i);
		    int unread = unreadTracker.getUnreadCount(quest);
		                text = new Text(quest.getText() + " (" + unread + " new)");
		        text.setLayoutX(100);
		        text.setLayoutY(23 + (i * 30));
		        button = new Button("See Replies");
		        setupButtonUI(button, "Dialog", 10, 0,
		                Pos.CENTER, 0, 10 + (i * 30));
		        button.setOnAction((event) -> {
		                Button but = (Button) event.getSource();
		                viewQuestionAnswers(questionSubSet.getQuestion((int)but.getLayoutY()/30));
		        });
		        questionPane.getChildren().addAll(text, button);
		        }
	}
	//Clay Edit, I felt it would be easier to implement story 7 if there was a button to view all of the questions a user asked
	private void seeMyAll() {
        /*Display all of the questions that a user asked*/

        questionPane.getChildren().clear();
	    Text text;
	    Button button;
		
		// Initialize the dialog box for modifying the question
		TextInputDialog dialogUpdateQuestion = new TextInputDialog();
		dialogUpdateQuestion.setTitle("Update your question");
		dialogUpdateQuestion.setHeaderText("Update your question");
		
	    System.out.println(String.format("%s", theUser.getUserName()));
		QuestionSet userSet = questionSet.filterQuestions(theUser);
				
		for(int i = 0; i < 2 * userSet.getNumQuestions(); i = i+2) {
		                quest = userSet.getQuestion(i/2);
		    int unread = unreadTracker.getUnreadCount(quest);
		                text = new Text("(" + unread + " new) " + quest.getText() );
		        text.setLayoutX(10);
		        text.setLayoutY(23 + ((i+1) * 30));
			
			//See replies button
		        button = new Button("See Replies");
		        setupButtonUI(button, "Dialog", 10, 0,
		                Pos.CENTER, 0, 10 + (i * 30));
		        button.setOnAction((event) -> {
		                Button but = (Button) event.getSource();
		                viewQuestionAnswers(userSet.getQuestion((int)but.getLayoutY()/60));
		        });
			
			//Modify Question Button
			 
			Button modifyButton = new Button("Update Question");
		        setupButtonUI(modifyButton, "Dialog", 10, 0,
		                Pos.CENTER, 80, 10 + (i * 30));
		        modifyButton.setOnAction((event) -> {result = dialogUpdateQuestion.showAndWait();
			if(result.isPresent()) {
				//If there is text in the dialog box, the chosen question will have its text modified
	        	Button but = (Button) event.getSource();
    			Question q = questionSet.getQuestion(((int)but.getLayoutY()/60));
				theDatabase.updateQuestionText(q.getUser().getUserName(), q.getText(), result.get());
				q.setText(result.get());
				seeMyAll();
				dialogUpdateQuestion.getEditor().clear();
		        }});
		        
		        questionPane.getChildren().addAll(text, button, modifyButton);
		        }
	}

	private void seeRecentQuestions() {
	    questionPane.getChildren().clear();
	    Text text;
	    Button button;

	    int total = questionSet.getNumQuestions();
	    int start = Math.max(0, total - 5); // Start index for last 5 questions

	    for (int i = start; i < total; i++) {
	        quest = questionSet.getQuestion(i);
	        int displayIndex = i - start; // reset layout index to avoid overlap

	        text = new Text(quest.getText());
	        text.setLayoutX(100);
	        text.setLayoutY(23 + (displayIndex * 30));

	        button = new Button("See Replies");
	        setupButtonUI(button, "Dialog", 10, 0, Pos.CENTER, 0, 10 + (displayIndex * 30));
	        button.setOnAction((event) -> {
	            Button but = (Button) event.getSource();
	            viewQuestionAnswers(quest); // we already have `quest` from this loop
	        });

	        questionPane.getChildren().addAll(text, button);
	    }
	}

	
	
	private void viewQuestionAnswers(Question q) {
		/*This method allows an user to view all of the answers to a given question
		 * as well as respond to the question*/
		
		questionPane.getChildren().clear();
		if (q.getUser().getUserName().equals(theUser.getUserName())) {//only mark it as read if the owning user sees it
		unreadTracker.markAsRead(q);} // added to mark answers as read
		
		// Initialize the dialog box for inputting answers/responses
		TextInputDialog dialogPostReply = new TextInputDialog();
		dialogPostReply.setTitle("Enter your response");
		dialogPostReply.setHeaderText("Enter your response");
		
		//Create the button to allow replying to the selected question
		Button postReply = new Button("Reply to this question");
		
		setupButtonUI(postReply, "Dialog", 18, 275, Pos.CENTER, 0, 0);
		postReply.setOnAction((event) -> {result = dialogPostReply.showAndWait();
			if(result.isPresent()) {
				//If there is text in the dialog box, a new Answer object will be created
				//and linked to the selected question
	        	Answer ans = new Answer(); 
	        	ans.setText(result.get());
	        	ans.setQuestion(q);
	        	q.addReply(ans);
//	        	unreadTracker.markAsRead(q);//Comment this out Clay
	        }
			dialogPostReply.getEditor().clear();
			});
		
        questionPane.getChildren().add(postReply);
		//Pull the AnswerSet from the Question object
        Answer ans;
		AnswerSet replies = q.getAnswers();
		
		//Create line by line answer displays with one answer per line
        Text text;
		for(int i = 0; i < replies.getNumAnswers(); i++) {
			ans = replies.getAnswer(i);
    			text = new Text(ans.getText());
    			text.setLayoutX(0);
    			text.setLayoutY(50 + (i * 30));
    			questionPane.getChildren().add(text);

			//NOAH EDITS
    			//Create a "Mark as Resolved" button next to replies
    			final Answer currentAnswer = ans;
    			Button resolveButton = new Button("Mark as Resolved");
    			resolveButton.setLayoutX(400);
    			resolveButton.setLayoutY(35 + (i * 30));
    			resolveButton.setFont(Font.font("Dialog", 12));
    		
    			if(ans.getAcceptance()) {
    				resolveButton.setDisable(true);
    				resolveButton.setText("Resolved");
    			}
    			else {
    				resolveButton.setOnAction(e -> {
    				currentAnswer.setAcceptance(true);
    				currentAnswer.getQuestion().setResolution(true);
    				resolveButton.setDisable(true);
    				resolveButton.setText("Resolved");
    			});
    		}
    		
    		questionPane.getChildren().add(resolveButton);

		 // Add PM button here
            Button pmButton = new Button("Private Message");
            pmButton.setFont(Font.font("Dialog", 12));
            pmButton.setLayoutX(300);
            pmButton.setLayoutY(35 + (i * 30));

            if (currentAnswer.getUser() != null ||
                !currentAnswer.getUser().getUserName().equals(theUser.getUserName())) {
            	pmButton.setOnAction(event -> {
            	    TextInputDialog pmDialog = new TextInputDialog();
            	    pmDialog.setTitle("Private Message");
            	    pmDialog.setHeaderText("Send a private message to " + currentAnswer.getUser().getUserName());

            	    Optional<String> pmResult = pmDialog.showAndWait();

            	    pmResult.ifPresent(msg -> {
            	        // Simulate sending the message (or store it later)
            	    	currentAnswer.addPrivateMessage(new PrivateMessage(theUser, msg));

            	        // Show confirmation popup
            	        Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
            	        confirmation.setTitle("Message Sent");
            	        confirmation.setHeaderText(null);
            	        confirmation.setContentText("Your private message was sent to " + currentAnswer.getUser().getUserName() + ".");
            	        confirmation.showAndWait();
            	    });
            	});


            }
            questionPane.getChildren().add(pmButton);
         // Display private messages, only visible to sender or answer owner
            if (currentAnswer.getPrivateMessages() != null) {
                int offset = 50 + (i * 30) + 20; // below the answer text

                for (PrivateMessage pm : currentAnswer.getPrivateMessages()) {
                    boolean isSender = pm.getSender().getUserName().equals(theUser.getUserName());
                    boolean isRecipient = currentAnswer.getUser().getUserName().equals(theUser.getUserName());

                    if (isSender || isRecipient) {
                        Text pmText = new Text("[Private from " + pm.getSender().getUserName() + "]: " + pm.getContent());
                        pmText.setLayoutX(30); // indent from left edge
                        pmText.setLayoutY(offset);
                        offset += 20;
                        questionPane.getChildren().add(pmText);
                    				}
                			}
            			}

        		}
		}
		
	}
}
