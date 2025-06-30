package entityClasses;
import java.util.ArrayList;

public class WorkRequest {
	private User 				originator;
	private String	 			description;
	private boolean				closed;
	private ArrayList<String> 	workComments;
	private String			parent;	

	public WorkRequest() {}
	
	public WorkRequest(User user, String desc) {
		originator = user;
		description = desc;
		closed = false;
		workComments = new ArrayList<>();
		parent = "";
	}
	
	public WorkRequest(User user, String desc, boolean status) {
		originator = user;
		description = desc;
		closed = status;
		workComments = new ArrayList<>();
		parent = "";
	}
	public WorkRequest(User user, String desc, boolean status, String parentDescription) {
		originator = user;
		description = desc;
		closed = status;
		workComments = new ArrayList<>();
		parent = parentDescription;
	}

	public User getOriginator() {
		return originator;
	}
	
	public String getDescription() {
		return description;
	}
	
	public boolean getClosedStatus() {
		return closed;
	}
	
	public String getParent() {
		return parent;
	}
	
	public ArrayList<String> getComments(){
		return workComments;
	}
	
	public void addComment(String comment) {
		workComments.add(comment);
	}
	
	public void updateClosedStatus(boolean status) {
		closed = status;
	}

}
