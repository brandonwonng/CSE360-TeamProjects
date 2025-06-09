package entityClasses;

public class Answer{
	private User 		user;
	private Question 	question;
	private String 		textContent;
	private boolean		acceptance;
	
	public Answer() {}
	
	public void setUser(User name) {
		user = name;
	}
	
	public void setQuestion(Question quest) {
		question = quest;
	}
	
	
	public void setText(String text) {
		textContent = text;
	}
	
	
	public void setAcceptance(boolean accept) {
		acceptance = accept;
	}
	
	
	//Getter methods
	public User getUser() {
		return user;
	}
	
	public String getText() {
		return textContent;
	}
	
	public Question getQuestion() {
		return question;
	}
	
	public boolean getAcceptance() {
		return acceptance;
	}
}