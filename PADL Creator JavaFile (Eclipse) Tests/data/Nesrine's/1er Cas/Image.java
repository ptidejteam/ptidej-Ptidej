package persistence.struct;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: agnidash
 * Date: 4/22/13
 * Time: 6:08 PM
 */
public class Image implements Serializable {

    private static final long serialVersionUID = 4160338165120216995L;

    private int id;
    private int userId;
    private Date timestamp;
    private String name;
    private String comment;

    // ONLY FOR HIBERNATE
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Image");
        sb.append("{id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", timestamp='").append(timestamp).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", comment='").append(comment).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
