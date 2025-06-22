package entityClasses;

public class PrivateMessage {
    private User receiver;
    private User sender;
    private String content;
    private String parentText;
    private boolean read;

    public PrivateMessage(User sender, String content) {
        this.sender = sender;
        this.content = content;
    }
    
    public PrivateMessage(User receiver, User sender, String content, String parentText) {
        this.sender = sender;
        this.content = content;
        this.parentText = parentText;
        this.receiver = receiver;
        read = false;
    }
    
    public User getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }
        public String getParentText() {
    	return parentText;
    }
    
    public User getReceiver() {
    	return receiver;
    }
    
    public boolean getRead() {
    	return read;
    }
    
    public void setRead(boolean status) {
    	read = status;
    }
}
