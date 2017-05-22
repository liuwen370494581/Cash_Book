package star.liuwen.com.cash_books.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by liuwen on 2017/5/17.
 */
public class CommunityModel implements Serializable {
    private static final long serialVersionUID = 1L;//为了避免反序列化失败 加的一个uid
    private String content;//标题内容
    private ArrayList<String> photos;//图片
    private String userPhotoUrl;//用户头像地址
    private String userName;//用户名字
    private String userLocation;//用户位置
    private String userTime;//用户发布时间
    private ArrayList<String> discuss;//评论
    private int goodJob;//点赞

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<String> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<String> photos) {
        this.photos = photos;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public String getUserTime() {
        return userTime;
    }

    public void setUserTime(String userTime) {
        this.userTime = userTime;
    }

    public ArrayList<String> getDiscuss() {
        return discuss;
    }

    public void setDiscuss(ArrayList<String> discuss) {
        this.discuss = discuss;
    }

    public int getGoodJob() {
        return goodJob;
    }

    public void setGoodJob(int goodJob) {
        this.goodJob = goodJob;
    }


    public String getUserPhotoUrl() {
        return userPhotoUrl;
    }

    public void setUserPhotoUrl(String userPhotoUrl) {
        this.userPhotoUrl = userPhotoUrl;
    }

    public CommunityModel() {
    }



    public CommunityModel(String content, ArrayList<String> photos) {
        this.content = content;
        this.photos = photos;
    }



    public CommunityModel(String content, String userPhotoUrl, String userName, String userLocation, String userTime, ArrayList<String> discuss, int goodJob, ArrayList<String> photos) {
        this.content = content;
        this.userPhotoUrl = userPhotoUrl;
        this.userName = userName;
        this.userLocation = userLocation;
        this.userTime = userTime;
        this.discuss = discuss;
        this.goodJob = goodJob;
        this.photos = photos;
    }
}
