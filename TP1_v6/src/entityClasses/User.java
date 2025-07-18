package entityClasses;
import java.util.ArrayList;
import java.util.List;
import databaseClasses.Database;
/*******
 * <p> Title: User Class </p>
 * 
 * <p> Description: This User class represents a user entity in the system.  It contains the user's
 *  details such as userName, password, and roles being played. </p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Lynn Robert Carter
 * 
 * 
 */ 

public class User {
	
	/*
	 * These are the private attributes for this entity object
	 */
    private String userName;
    private String password;
    private boolean adminRole;
    private boolean studentRole;
    private boolean reviewerRole;
    private boolean instructorRole;
    private boolean staffRole;
    private ArrayList<User> trustedReviewers = new ArrayList<>();
    private ArrayList<ReviewerTrust> trustedReviewerList = new ArrayList<>();
    private ArrayList<ReviewerTrust> reviewerTrustList = new ArrayList<>();


    /*****
     * <p> Method: User() </p>
     * 
     * <p> Description: This default constructor is not used in this system. </p>
     */
    public User() {
    	
    }

    
    /*****
     * <p> Method: User(String userName, String password, boolean r1, boolean r2,
     * 		boolean r3, boolean r4, boolean r5) </p>
     * 
     * <p> Description: This constructor is used to establish user entity objects. </p>
     * 
     * @param userName specifies the account userName for this user
     * 
     * @param password specifies the account password for this user
     * 
     * @param r1 specifies the the Admin attribute (TRUE or FALSE) for this user
     * 
     * @param r2 specifies the the Student attribute (TRUE or FALSE) for this user
     * 
     * @param r3 specifies the the Reviewer attribute (TRUE or FALSE) for this user
     * 
     * @param r4 specifies the the Instructor attribute (TRUE or FALSE) for this user
     * 
     * @param r5 specifies the the Staff attribute (TRUE or FALSE) for this user
     * 
     */
    // Constructor to initialize a new User object with userName, password, and role.
    public User(String userName, String password, boolean r1, boolean r2, boolean r3, boolean r4,
    		boolean r5) {
        this.userName = userName;
        this.password = password;
        this.adminRole = r1;
        this.studentRole = r2;
        this.reviewerRole = r3;
        this.instructorRole = r4;
        this.staffRole = r5;
    }

    
    /*****
     * <p> Method: void setAdminRole(boolean role) </p>
     * 
     * <p> Description: This setter defines the Admin role attribute. </p>
     * 
     * @param role is a boolean that specifies if this user in playing the Admin role.
     * 
     */
    // Sets the role of the Admin user.
    public void setAdminRole(boolean role) {
    	this.adminRole=role;
    }

    
    /*****
     * <p> Method: void setStudentRole(boolean role) </p>
     * 
     * <p> Description: This setter defines the Student role attribute. </p>
     * 
     * @param role is a boolean that specifies if this user in playing the Student role.
     * 
     */
    // Sets the role of the Student user.
    public void setStudentRole(boolean role) {
    	this.studentRole=role;
    }

    
    /*****
     * <p> Method: void setReviewerRole(boolean role) </p>
     * 
     * <p> Description: This setter defines the Reviewer role attribute. </p>
     * 
     * @param role is a boolean that specifies if this user in playing the Reviewer role.
     * 
     */
    // Sets the role of the Reviewer user.
    public void setReviewerRole(boolean role) {
    	this.reviewerRole=role;
    }

    
    /*****
     * <p> Method: void setInstructorRole(boolean role) </p>
     * 
     * <p> Description: This setter defines the Instructor role attribute. </p>
     * 
     * @param role is a boolean that specifies if this user in playing the Instructor role.
     * 
     */
    // Sets the role of the Instructor user.
    public void setInstructorRole(boolean role) {
    	this.instructorRole=role;
    }

    
    /*****
     * <p> Method: void setStaffRole(boolean role) </p>
     * 
     * <p> Description: This setter defines the Staff role attribute. </p>
     * 
     * @param role is a boolean that specifies if this user in playing the Staff role.
     * 
     */
    // Sets the role of the Staff user.
    public void setStaffRole(boolean role) {
    	this.adminRole=role;
    }

    
    /*****
     * <p> Method: String getAdminRole() </p>
     * 
     * <p> Description: This getter returns the value of the Admin role attribute. </p>
     * 
     * @return a String of "TRUE" or "FALSE" based on state of the attribute
     * 
     */
    // Gets the current value of the Admin role attribute.
    public String getUserName() { return userName; }

    
    /*****
     * <p> Method: String getStudentRole() </p>
     * 
     * <p> Description: This getter returns the value of the Student role attribute. </p>
     * 
     * @return a String of "TRUE" or "FALSE" based on state of the attribute
	 *
     */
    // Gets the current value of the Student role attribute.
    public String getPassword() { return password; }

    
    /*****
     * <p> Method: String getAdminRole() </p>
     * 
     * <p> Description: This getter returns the value of the Admin role attribute. </p>
     * 
     * @return a String of "TRUE" or "FALSE" based on state of the attribute
	 *
     */
    // Gets the current value of the Admin role attribute.
    public boolean getAdminRole() { return adminRole; }

    
    /*****
     * <p> Method: String getStudentRole() </p>
     * 
     * <p> Description: This getter returns the value of the Student role attribute. </p>
     * 
     * @return a String of "TRUE" or "FALSE" based on state of the attribute
	 *
     */
    // Gets the current value of the Student role attribute.
	public boolean getStudentRole() { return studentRole; }

    
    /*****
     * <p> Method: String getReviewerRole() </p>
     * 
     * <p> Description: This getter returns the value of the Reviewer role attribute. </p>
     * 
     * @return a String of "TRUE" or "FALSE" based on state of the attribute
	 *
     */
    // Gets the current value of the Reviewer role attribute.
    public boolean getReviewerRole() { return reviewerRole; }

    
    /*****
     * <p> Method: String getInstructorRole() </p>
     * 
     * <p> Description: This getter returns the value of the Instructor role attribute. </p>
     * 
     * @return a String of "TRUE" or "FALSE" based on state of the attribute
	 *
     */
    // Gets the current value of the Instructor role attribute.
    public boolean getInstructorRole() { return instructorRole; }

    
    /*****
     * <p> Method: String getStaffRole() </p>
     * 
     * <p> Description: This getter returns the value of the Staff role attribute. </p>
     * 
     * @return a String of "TRUE" or "FALSE" based on state of the attribute
	 *
     */
    // Gets the current value of the Staff role attribute.
    public boolean getStaffRole() { return staffRole; }

    
    /*****
     * <p> Method: int getNumRoles() </p>
     * 
     * <p> Description: This getter returns the number of roles this user plays (0 - 5). </p>
     * 
     * @return a value 0 - 5 of the number of roles this user plays
	 *
     */
    // Gets the current value of the Staff role attribute.
    public int getNumRoles() {
    	int numRoles = 0;
    	if (adminRole) numRoles++;
    	if (studentRole) numRoles++;
    	if (reviewerRole) numRoles++;
    	if (instructorRole) numRoles++;
    	if (staffRole) numRoles++;
    	return numRoles;
    }

    public ArrayList<User> getTrustedReviewers(Database db){
    	
    	return db.getTrustedReviewers(userName);
    }
	/*****
     * <p> Method: void addTrustedReviewer() </p>
     * 
     * <p> Description: This setter adds a reviewer to the trusted list and returns true if it was successful. </p>
     * 
     * @return a boolean if adding the reviewer was successful or not
	 *
     */
    // Check if the chosen user is a reviewer, if they are add them to the list
    public boolean addTrustedReviewer(Database db, User trustedUser, int weight) {
		this.trustedReviewers.add(trustedUser);
		trustedReviewerList.add(new ReviewerTrust(trustedUser, weight));
		//Clay Edit 21: This needs to include code that updates the database with a user pair for trusted reviewers
		db.grantTrust(this.userName, trustedUser.getUserName());
		return true;
    }

		/*****
     * <p> Method: void removeTrustedReviewer() </p>
     * 
     * <p> Description: This setter removes a reviewer to the trusted list and returns true if it was successful. </p>
     * 
     * @return a boolean if removing the reviewer was successful or not
     *
     */
    // Check if the chosen user is a reviewer, if they are add them to the list
    public boolean removeTrustedReviewer(Database db, User untrustedUser) {
			this.trustedReviewers.remove(untrustedUser);
			//Clay Edit 21: This needs to include code that updates the database
			db.removeTrust(this.userName, untrustedUser.getUserName());
			return true;
    }
    
    /*****
     * <p> Method: String getRole() </p>
     *
     * <p> Description: Returns the primary role of the user as a string. </p>
     *
     * @return a string such as "admin", "student", etc.
     */
    public String getRole() {
        if (adminRole) return "admin";
        if (studentRole) return "student";
        if (reviewerRole) return "reviewer";
        if (instructorRole) return "instructor";
        if (staffRole) return "staff";
        return "unknown";
    }
    

    public int getReviewerWeight(String reviewerUserName) {
        for (ReviewerTrust rt : trustedReviewerList) {
            if (rt.getReviewer().getUserName().equals(reviewerUserName)) {
                return rt.getWeight();
            }
        }
        return 5; // default weight
    }

    public void setReviewerWeight(String reviewerUserName, int weight) {
        for (ReviewerTrust rt : trustedReviewerList) {
            if (rt.getReviewer().getUserName().equals(reviewerUserName)) {
                rt.setWeight(weight);
                return;
            }
        }
    }
    
    public ReviewerTrust getReviewerTrust(String reviewerUserName) {
        for (ReviewerTrust trust : trustedReviewerList) {
            if (trust.getReviewer().getUserName().equals(reviewerUserName)) {
                return trust;
            }
        }
        return null; // not found
    }




}
