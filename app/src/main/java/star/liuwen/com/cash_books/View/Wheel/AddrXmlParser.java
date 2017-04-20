package star.liuwen.com.cash_books.View.Wheel;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuwen on 2017/4/20.
 */
public class AddrXmlParser extends DefaultHandler {

    private List<ProvinceInfoModel> provinceList = new ArrayList<ProvinceInfoModel>();


    public java.util.List<ProvinceInfoModel> getDataList() {
        return provinceList;
    }

    @Override
    public void startDocument() throws SAXException {

    }

    ProvinceInfoModel provinceModel = new ProvinceInfoModel();
    CityInfoModel cityModel = new CityInfoModel();
    DistrictInfoModel districtModel = new DistrictInfoModel();

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        if (qName.equals("province")) {
            provinceModel = new ProvinceInfoModel();
            provinceModel.setName(attributes.getValue(0));
            provinceModel.setCityList(new ArrayList<CityInfoModel>());
        } else if (qName.equals("city")) {
            cityModel = new CityInfoModel();
            cityModel.setName(attributes.getValue(0));
            cityModel.setDistrictList(new ArrayList<DistrictInfoModel>());
        } else if (qName.equals("district")) {
            districtModel = new DistrictInfoModel();
            districtModel.setName(attributes.getValue(0));
            districtModel.setZipcode(attributes.getValue(1));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (qName.equals("district")) {
            cityModel.getDistrictList().add(districtModel);
        } else if (qName.equals("city")) {
            provinceModel.getCityList().add(cityModel);
        } else if (qName.equals("province")) {
            provinceList.add(provinceModel);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
    }
}
