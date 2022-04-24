import java.util.Date;

public class Tweet {
    private String createdAt;
    private String autorID;
    private String ID;
    private String text;

    public Tweet(String createdAt, String autorID, String ID, String text) {
        this.createdAt = createdAt;
        this.autorID = autorID;
        this.ID = ID;
        this.text = text;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getAutorID() {
        return autorID;
    }

    public void setAutorID(String autorID) {
        this.autorID = autorID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
