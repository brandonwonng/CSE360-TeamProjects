package entityClasses;
//import java.util.ArrayList;


/*
 *The Question class is designed to represent questions that students ask.
 *It will store the name/username of the student who asked, question text, the number of answers
 *if the question was resolved, and a list of answers.
 *
 *this class will also provide functionality 
 * 
 */
public class Question {
	private User 	user;
	private String 	textContent;
	private boolean resolution;
	private AnswerSet replies = new AnswerSet();
	
	
	
	public Question() {}
	
	//setter classes follow
	public void setUser(User name) {
		user = name;
	}
	
	
	public void setText(String text) {
		textContent = text;
	}
	
	
	public void setResolution(boolean res) {
		resolution = res;
	}
	
	
	public void addReply(Answer reply) {
		replies.addAnswer(reply);
		
	}
	
	
	//getter methods follow
	public User getUser(){
		return user;
	}
	
	
	public String getText(){
		return textContent;
	}
	
	
	public boolean getResolution(){
		return resolution;
	}
	
	
	public int getNumAnswer(){
		return replies.getNumAnswers();
	}
	
	
	public AnswerSet getAnswers(){
		return replies;
	}
}