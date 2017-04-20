package star.liuwen.com.cash_books.View.Wheel;

import java.util.List;

/**
 * Created by liuwen on 2017/4/20.
 *  城市
 */
public class CityInfoModel {

    private String name;
    private List<DistrictInfoModel> districtList;

    public CityInfoModel() {
        super();
    }

    public CityInfoModel(String name, List<DistrictInfoModel> districtList) {
        super();
        this.name = name;
        this.districtList = districtList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DistrictInfoModel> getDistrictList() {
        return districtList;
    }

    public void setDistrictList(List<DistrictInfoModel> districtList) {
        this.districtList = districtList;
    }

    @Override
    public String toString() {
        return "CityInfoModel [name=" + name + ", districtList=" + districtList
                + "]";
    }
}
