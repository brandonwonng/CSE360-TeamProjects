package entityClasses;

public class PrivateMessage {
    private User sender;
    private String content;

    public PrivateMessage(User sender, String content) {
        this.sender = sender;
        this.content = content;
    }

    public User getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }
}
