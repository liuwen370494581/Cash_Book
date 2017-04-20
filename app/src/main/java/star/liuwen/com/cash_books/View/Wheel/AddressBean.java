package star.liuwen.com.cash_books.View.Wheel;

import java.io.Serializable;

/**
 * Created by liuwen on 2017/4/20.
 */
public class AddressBean implements Serializable {
    private String id = "";
    private String name = "";
    private String mobile = "";
    private String address = "";
    private String is_default = "";
    private String province = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIs_default() {
        return is_default;
    }

    public void setIs_default(String is_default) {
        this.is_default = is_default;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Override
    public String toString() {
        return "AddressBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", address='" + address + '\'' +
                ", is_default='" + is_default + '\'' +
                ", province='" + province + '\'' +
                '}';
    }
}
