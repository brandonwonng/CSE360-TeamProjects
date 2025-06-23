package entityClasses;

public class ReviewerTrust {
    private User reviewer;
    private int weight;

    public ReviewerTrust(User reviewer, int weight) {
        this.reviewer = reviewer;
        this.weight = weight;
    }

    public User getReviewer() {
        return reviewer;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
