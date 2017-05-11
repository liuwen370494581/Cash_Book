package star.liuwen.com.cash_books.bean;

import java.io.Serializable;

/**
 * Created by liuwen on 2017/5/11.
 */
public class ColorModel implements Serializable {

    private static final long serialVersionId = 1l;

    private Long id;//ID
    private int url;//颜色被选择的勾
    private int color;//颜色的字段

    public ColorModel() {
    }

    public ColorModel(int url, int color) {
        this.url = url;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getUrl() {
        return url;
    }

    public void setUrl(int url) {
        this.url = url;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public ColorModel(int color) {
        this.color = color;
    }
}


