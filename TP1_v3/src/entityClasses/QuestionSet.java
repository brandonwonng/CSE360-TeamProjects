package entityClasses;
import java.util.ArrayList;


/*
 *The Question class is designed to represent questions that students ask.
 *It will store the name/username of the student who asked, question text, the number of answers
 *if the question was resolved, and a list of answers.
 *
 *this class will also provide functionality 
 * 
 */
public class QuestionSet {
	private ArrayList<Question> questions;
	
	
	
	public QuestionSet() {
		questions = new ArrayList<Question>();
	}
	
	//setter classes follow
	public void addQuestion(Question question) {
		questions.add(question);
	}
	
	
	//getter methods follow	
	
	public int getNumQuestions(){
		return questions.size();
	}
	
	public Question getQuestion(int i) {
		return questions.get(i);	
	}
	
	public Question getQuestion(String text) {
		for (int i = 0; i< questions.size(); i++) {
			Question q = questions.get(i);
			if (q.getText().equals(text)) {
				return q;
			}
		}	
		return null;
	}
	
	//Filter the presented set of questions
	public QuestionSet filterQuestions(User user){
		/* This method will take a user object
		 * then return an ArrayList containing the applicable questions*/
		
		QuestionSet filteredSet =  new QuestionSet();
		for(int index = 0; index < questions.size(); index++ ) {
			if (user.equals(questions.get(index).getUser())) {
				filteredSet.addQuestion(questions.get(index));	
			}
		}	
		return filteredSet;
	}
	
	public QuestionSet filterQuestions(boolean resolution){
		/* This method will take a boolean value for the desired resolution (True = resolved)
		 * then return an ArrayList containing the applicable questions*/
		
		QuestionSet filteredSet =  new QuestionSet();
		for(int index = 0; index < questions.size(); index++ ) {
			if (resolution == (questions.get(index).getResolution())) {
				filteredSet.addQuestion(questions.get(index));	
			}
		}	
		return filteredSet;
	}
	
	
}