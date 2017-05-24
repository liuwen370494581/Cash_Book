package star.liuwen.com.cash_books.bean;

import java.io.Serializable;

/**
 * Created by liuwen on 2017/5/23.
 */
public class DiscussModel implements Serializable {

    private static final long serialVersionId = 1l;

    private int userUrl;
    private String userName;
    private String userDiscuss;


    public int getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(int userUrl) {
        this.userUrl = userUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserDiscuss() {
        return userDiscuss;
    }

    public void setUserDiscuss(String userDiscuss) {
        this.userDiscuss = userDiscuss;
    }

    public DiscussModel() {
    }

    public DiscussModel(int userUrl, String userName, String userDiscuss) {
        this.userUrl = userUrl;
        this.userName = userName;
        this.userDiscuss = userDiscuss;
    }
}
