package entityClasses;


public class ReviewerProfile {
    private String reviewerName;
    private String experience;

    public ReviewerProfile(String name) {
        this.reviewerName = name;
        this.experience = "";
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getReviewerName() {
        return reviewerName;
    }
}
