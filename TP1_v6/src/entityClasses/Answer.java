package entityClasses;
import entityClasses.PrivateMessage;
import java.util.ArrayList;
import java.util.List;



public class Answer {
	private User 		user;
	private Question 	question;
	private String 		textContent;
	private boolean		acceptance;

	private List<PrivateMessage> privateMessages = new ArrayList<>();

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

	public void addPrivateMessage(PrivateMessage pm) {
		privateMessages.add(pm);
	}

	public List<PrivateMessage> getPrivateMessages() {
		return privateMessages;
	}
}
