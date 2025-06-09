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
public class AnswerSet {
	private ArrayList<Answer> answers;
	
	
	
	public AnswerSet() {
		answers = new ArrayList<Answer>();
	}
	
	//setter classes follow
	public void addAnswer(Answer answer) {
		answers.add(answer);
	}
	
	
	//getter methods follow	
	
	public int getNumAnswers(){
		return answers.size();
	}
	
	public Answer getAnswer(int i) {
		return answers.get(i);
	}
	
//	//Filter the presented set of questions
//	public AnswerSet filterQuestions(User user){
//		/* This method will take a user object
//		 * then return an ArrayList containing the applicable questions*/
//		
//		AnswerSet filteredSet =  new AnswerSet();
//		for(int index = 0; index < answers.size(); index++ ) {
//			if (user.equals(answers.get(index).getUser())) {
//				filteredSet.addQuestion(answers.get(index));	
//			}
//		}	
//		return filteredSet;
//	}
//	
//	public AnswerSet filterQuestions(boolean resolution){
//		/* This method will take a boolean value for the desired resolution (True = resolved)
//		 * then return an ArrayList containing the applicable questions*/
//		
//		AnswerSet filteredSet =  new AnswerSet();
//		for(int index = 0; index < answers.size(); index++ ) {
//			if (resolution == (answers.get(index).getResolution())) {
//				filteredSet.addQuestion(answers.get(index));	
//			}
//		}	
//		return filteredSet;
//	}
	
	
}