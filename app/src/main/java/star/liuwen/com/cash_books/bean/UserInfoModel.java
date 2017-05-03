package star.liuwen.com.cash_books.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * Created by liuwen on 2017/1/21.
 */
@Entity
public class UserInfoModel implements Serializable {
    private static final long serialVersionUID = 1L;//为了避免反序列化失败 加的一个uid
    @Id(autoincrement = true)
    private Long id; //用户id
    private String userName;//用户名
    private String password;//密码
    private String signature;//签名
    private String sex; //性别
    private String location;//所在地

    public UserInfoModel() {
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSignature() {
        return this.signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Generated(hash = 953430471)
    public UserInfoModel(Long id, String userName, String password,
                         String signature, String sex, String location) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.signature = signature;
        this.sex = sex;
        this.location = location;
    }

}
