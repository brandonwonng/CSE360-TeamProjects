package guiPageClasses;

import java.util.Optional;

import applicationMainMethodClasses.FCMainClass;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
/*******
 * <p> Title: GUIStaffQuestionPage Class. </p>
 * 
 * <p> Description: The Java/FX-based Staff Question Page.</p>
 * 
 * <p> Copyright: Clay Hauser © 2025 </p>
 * 
 * @author Clay Hauser
 * 
 * @version 1.00		2025-04-20 Initial version
 *  
 */

public class GUIStaffQuestionPage {
	
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
	
	private Button button_ViewRecentQuestions = new Button("Recently Asked Questions");

	private Button button_BackToHomePage = new Button("Return to the Home Page");
	
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
	public GUIStaffQuestionPage(Stage ps, Pane theRoot, Database database, User user) {
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
		thePrimaryStage.setTitle("CSE 360 Foundation Code: Staff Question Page");
		
		label_PageTitle.setText("Staff Question Page");
		setupLabelUI(label_PageTitle, "Arial", 28, WINDOW_WIDTH, Pos.CENTER, 0, 5);
		
		setupLabelUI(label_Purpose, "Arial", 10, WINDOW_WIDTH, Pos.CENTER, 0, 35);
		
		//Set up for all main buttons on the page
		setupButtonUI(button_ViewAllQuestions, "Dialog", 18, 150, 
        		Pos.CENTER, 20, 110);
		button_ViewAllQuestions.setOnAction((event) -> {seeAllQuestions();});

		
		// Initialize the dialog box for inputting answers/responses
		TextInputDialog dialogAskQuestion = new TextInputDialog();
		dialogAskQuestion.setTitle("Enter your Question");
		dialogAskQuestion.setHeaderText("Enter your Question");
		
		setupButtonUI(button_ViewRecentQuestions, "Dialog", 18, 150, 
			    Pos.CENTER, 20, 350); // Adjust Y as needed based on layout
		button_ViewRecentQuestions.setOnAction((event) -> {seeRecentQuestions();});
		
        setupButtonUI(button_BackToHomePage, "Dialog", 18, 300, 
        		Pos.CENTER, WINDOW_WIDTH/2-150, 475);
        button_BackToHomePage.setOnAction((event) -> {goToUserHomePage();});
		
        setupButtonUI(button_Logout, "Dialog", 18, 250, Pos.CENTER, 20, 540);
        button_Logout.setOnAction((event) -> {performLogout(); });
        
        setupButtonUI(button_Quit, "Dialog", 18, 250, Pos.CENTER, 300, 540);
        button_Quit.setOnAction((event) -> {performQuit(); });

	loadQuestionsFromDatabase(); // John edit: load questions from the database
    	
        // Place all of the just-initialized GUI elements into the pane
        theRoot.getChildren().clear();
        theRoot.getChildren().addAll(label_PageTitle,
        		questionPaneScroll,
           		label_Purpose,
        		button_BackToHomePage, 
        		button_ViewAllQuestions,
			button_ViewRecentQuestions,
        		line_Separator4,
        		line_Separator1,
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
        		button_BackToHomePage, 
        		button_ViewAllQuestions,
			button_ViewRecentQuestions,
        		line_Separator4,
        		line_Separator1,
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
	
	private void goToUserHomePage() {
	    if (GUISystemStartUpPage.theStaffHomePage == null) {
            GUISystemStartUpPage.theStaffHomePage =
                new GUIStaffHomePage(thePrimaryStage, theRootPane, theDatabase, theUser);
        } else {
            GUISystemStartUpPage.theStaffHomePage.setup();
        }
	}

	
	
	private void performLogout() {
		GUISystemStartUpPage.theSystemStartupPage.setup();
	}

	
	private void performQuit() {
		System.out.println("Perform Quit");
		System.exit(0);
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
    	for(int i = 0; i< questionSet.getNumQuestions()*2; i+=2) {
    		quest = questionSet.getQuestion(i/2);
			//create the text fields and display it
    		text = new Text(quest.getText());
    		text.setLayoutX(0);
    		text.setLayoutY(23 + ((i+1) * 30));
    		//set up the button to view the replies
    		button = new Button("See Replies");
    		setupButtonUI(button, "Dialog", 10, 0, 
            		Pos.CENTER, 0, 10 + (i * 30));
    		button.setOnAction((event) -> {
    			//To get the button linked to the correct question, we take Y axis divided
    			//by the standard separation of the lines revealing the index of the correct question
    			Button but = (Button) event.getSource();
    			viewQuestionAnswers(questionSet.getQuestion(((int)but.getLayoutY()/60)));
    			});
    		questionPane.getChildren().addAll(text, button);
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
		
		setupButtonUI(postReply, "Dialog", 18, 150, Pos.CENTER, 10, 0);
		
	// John edit: Set the button action to post a reply
        postReply.setOnAction((event) -> {
            result = dialogPostReply.showAndWait();
            if (result.isPresent()) {
                    Answer ans = new Answer();
                    ans.setText(result.get());
                    ans.setQuestion(q);
                    q.addReply(ans);
                    ans.setUser(theUser);
                    theDatabase.addAnswer(q.getUser().getUserName(), q.getText(), theUser.getUserName(), ans.getText());
            }
            dialogPostReply.getEditor().clear();
    });		
  		
        questionPane.getChildren().addAll(postReply);
		//Pull the AnswerSet from the Question object
        Answer ans;
		AnswerSet replies = q.getAnswers();
		Button reviewButt;
		
		//Create line by line answer displays with one answer per line
		for (int i = 0; i < replies.getNumAnswers()*2; i+=2) {
            ans = replies.getAnswer(i/2);
            String reviewerDisplayName = ans.getUser().getUserName();

            // Only show weight if the answer is from a reviewer
            if (ans.getUser().getRole().equalsIgnoreCase("reviewer")) {
                ReviewerTrust trust = theUser.getReviewerTrust(ans.getUser().getUserName());
                if (trust != null) {
                    reviewerDisplayName += " (Weight: " + trust.getWeight() + ")";
                }
            }

            String answerDisplay = String.format("[%s]: %s", reviewerDisplayName, ans.getText());
            Text text = new Text(answerDisplay);
            text.setLayoutX(0);
            text.setLayoutY(50 + ((i+1) * 30));
            questionPane.getChildren().add(text);
	    //Review Button Code
            reviewButt = new Button("See reviews");
    		setupButtonUI(reviewButt, "Dialog", 12, 0, 
            		Pos.CENTER, 125, 35 + (i * 30)); //Clay Edits 19: Changed value
    		reviewButt.setOnAction((event) -> {
    			//To get the button linked to the correct question, we take Y axis divided
    			//by the standard separation of the lines revealing the index of the correct question
    			Button but = (Button) event.getSource();
    			seeReviews(replies.getAnswer((((int)but.getLayoutY() - 35)/60)));
    			});
            //End review button code
            final Answer currentAnswer = ans;
            Button resolveButton = new Button("Mark as Resolved");
            resolveButton.setLayoutX(230);
            resolveButton.setLayoutY(35 + (i * 30));
            resolveButton.setFont(Font.font("Dialog", 12));

            if (ans.getAcceptance()) {
                resolveButton.setDisable(true);
                resolveButton.setText("Resolved");
            } else {
            	// John edit: Set the action for the resolve button
                resolveButton.setOnAction(e -> {
                    currentAnswer.setAcceptance(true);
                    currentAnswer.getQuestion().setResolution(true);
                    // Persist resolution state
                    theDatabase.updateAnswerAcceptance(currentAnswer.getQuestion().getUser().getUserName(),
                                    currentAnswer.getQuestion().getText(),
                                    currentAnswer.getUser().getUserName(),
                                    currentAnswer.getText(), true);
                    theDatabase.updateQuestionResolution(currentAnswer.getQuestion().getUser().getUserName(),
                                    currentAnswer.getQuestion().getText(), true);
                    resolveButton.setDisable(true);
                    resolveButton.setText("Resolved");
                });
            }
            questionPane.getChildren().addAll(resolveButton, reviewButt);

            // Add PM button here
            Button pmButton = new Button("Private Message");
            pmButton.setFont(Font.font("Dialog", 12));
            pmButton.setLayoutX(0);
            pmButton.setLayoutY(35 + (i * 30));

            if (currentAnswer.getUser() != null ||
                !currentAnswer.getUser().getUserName().equals(theUser.getUserName())) {
            	pmButton.setOnAction(event -> {
            	    TextInputDialog pmDialog = new TextInputDialog();
            	    pmDialog.setTitle("Private Message");
            	    pmDialog.setHeaderText("Send a private message to " + currentAnswer.getUser().getUserName());

            	    Optional<String> pmResult = pmDialog.showAndWait();

            	    pmResult.ifPresent(msg -> {
            	        PrivateMessage pm = new PrivateMessage(currentAnswer.getUser(),theUser, msg, currentAnswer.getText());
            	        currentAnswer.addPrivateMessage(pm);


            	        Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
            	        confirmation.setTitle("Message Sent");
            	        confirmation.setHeaderText(null);
            	        confirmation.setContentText("Your private message was sent to " + currentAnswer.getUser().getUserName() + ".");
            	        confirmation.showAndWait();
            	    });


            	});


            }
            questionPane.getChildren().add(pmButton);
            
            if (theUser.getRole().equalsIgnoreCase("instructor")) {
                Button deleteButton = new Button("Delete");
                deleteButton.setFont(Font.font("Dialog", 12));
                deleteButton.setLayoutX(350);
                deleteButton.setLayoutY(35 + (i * 30));

                deleteButton.setOnAction(e -> {
                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                    confirm.setTitle("Delete Answer");
                    confirm.setHeaderText("Are you sure you want to delete this answer?");

                    Optional<javafx.scene.control.ButtonType> confirmation = confirm.showAndWait();
                    if (confirmation.isPresent() && confirmation.get() == javafx.scene.control.ButtonType.OK) {
                        q.getAnswers().removeAnswer(currentAnswer);

                        viewQuestionAnswers(q); // refresh the UI
                    }
                });

                questionPane.getChildren().add(deleteButton);
            }


         // Display private messages, only visible to sender or answer owner
            if (currentAnswer.getPrivateMessages() != null) {
                int offset = 50 + (i * 30) + 20; // below the answer text

                for (PrivateMessage pm : currentAnswer.getPrivateMessages()) {
                    boolean isSender = pm.getSender().getUserName().equals(theUser.getUserName());
                    boolean isRecipient = currentAnswer.getUser().getUserName().equals(theUser.getUserName());
                    boolean isInstructor = theUser.getRole().equalsIgnoreCase("instructor");

                    if (isSender || isRecipient || isInstructor) {
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


	
	private void seeReviews(Answer ans) {
		if(ans.getNumReview()==0) {
			for (Answer rev : theDatabase.getReviewsForAnswer(ans.getUser().getUserName(), ans.getText())) {
				ans.addReview(rev);
				//System.out.println("added review");
			}
		}

		questionPane.getChildren().clear();
		Answer review;
		Button back = new Button("Go Back");
  		setupButtonUI(back, "Dialog", 18, 150, Pos.CENTER, 0, 0);
  		back.setOnAction((event) -> {
  			viewQuestionAnswers(ans.getQuestion());
		});
  		questionPane.getChildren().add(back);
  		Button button;//Clay edit 21 June
  		Button button_feedback;
		for(int i = 0; i<ans.getNumReview()*2; i+=2) {
			review = ans.getReviews().getAnswer(i/2);
            String answerDisplay = String.format("[%s]: %s", review.getUser().getUserName(), review.getText());
            Text text = new Text(answerDisplay);
            text.setLayoutX(0);
            text.setLayoutY(60 + ((i+1) * 30));
            //Clay edit 21 June
            button = new Button("Add as trusted reviewer");
	        setupButtonUI(button, "Dialog", 12, 0, Pos.CENTER, 0, 50 + (i * 30));
	        for(User user: theUser.getTrustedReviewers(theDatabase)) {
	        	if(user.getUserName().equals(review.getUser().getUserName())) {
	        		button.setDisable(true);
	        		break;
	        	}
	        }
	        button.setOnAction((event) -> {
	            Button but = (Button) event.getSource();
	            int index = (int) (but.getLayoutY() - 50) /60;
	            theUser.addTrustedReviewer(theDatabase, ans.getReviews().getAnswer(index).getUser(), 5);
	            seeReviews(ans);
	        });
	        
	        button_feedback = new Button("Send Feedback to Reviewer");
	        setupButtonUI(button_feedback, "Dialog", 12, 0, Pos.CENTER, 150, 50 + (i * 30));
	        button_feedback.setOnAction((event) -> {
	        	Button but = (Button) event.getSource();
	            int index = (int) (but.getLayoutY() - 50) /60;
	            Answer theReview = ans.getReviews().getAnswer(index);
	        	TextInputDialog pmDialog = new TextInputDialog();
        	    pmDialog.setTitle("Private Message");
        	    pmDialog.setHeaderText("Send a private message");

        	    Optional<String> pmResult = pmDialog.showAndWait();
        	    
        	    pmResult.ifPresent(msg -> {
    	            theDatabase.logPrivateMessage(theUser.getUserName(), theReview.getUser().getUserName(), msg, theReview.getText());
        	    });


	        });
	        
	        	Button button_viewFeedback = new Button("View Review Feedback");
	        	setupButtonUI(button_viewFeedback, "Dialog", 12, 0, Pos.CENTER, 325, 50 + (i * 30));
	        	button_viewFeedback.setOnAction((event) ->{
	        		//add code to see feedback for this 
	        		Button but = (Button) event.getSource();
		            int index = (int) (but.getLayoutY() - 50) /60;
		            Answer theReview = ans.getReviews().getAnswer(index);
	        		seeMessages(theReview, ans);
	        	});
	        	questionPane.getChildren().add(button_viewFeedback);
	        
            questionPane.getChildren().addAll(text,button, button_feedback);
		}
		return;
	}
	//HW 4 change
	private void seeMessages(Answer review, Answer parentAnswer) {
		questionPane.getChildren().clear();
		ArrayList<PrivateMessage> messages = theDatabase.getAllPrivateMessages();
		ArrayList<PrivateMessage> myMessages = new ArrayList<>();
		
		for(PrivateMessage mesg : messages) {
  			if(mesg.getParentText().equals(review.getText())) {
  				  myMessages.add(mesg);
  				  theDatabase.updateMessageRead(mesg.getSender().getUserName(), mesg.getReceiver().getUserName(), 
  						  mesg.getContent(), mesg.getParentText());
  			}
		}
		
  		Text answerText = new Text(String.format("Answer: %s", parentAnswer.getText()));
  		answerText.setLayoutX(0);
  		answerText.setLayoutY(45);
  		questionPane.getChildren().add(answerText);
  		
		Button back = new Button("Go Back");
  		setupButtonUI(back, "Dialog", 18, 150, Pos.CENTER, 0, 0);
  		back.setOnAction((event) -> {
  			seeReviews(parentAnswer);
		});
  		questionPane.getChildren().add(back);
  		
  		for(int i =0; i<myMessages.size()*2; i+=2) {
  			PrivateMessage message = myMessages.get(i/2);
  			String messageDisplay = String.format("%s gave the following:\n%s", message.getSender().getUserName(), message.getContent());
            Text text = new Text(messageDisplay);
            text.setLayoutX(0);
            text.setLayoutY(70 + ((i) * 30));
  			questionPane.getChildren().add(text);
  		}
  		
  		return;	
  		}

}
