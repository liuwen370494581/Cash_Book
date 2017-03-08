package star.liuwen.com.cash_books.widget;

import java.util.Comparator;

import star.liuwen.com.cash_books.bean.IndexModel;

//显示发卡行的类
public class PinyinComparator implements Comparator<IndexModel> {

    public int compare(IndexModel o1, IndexModel o2) {
        if (o1.topc.equals("@") || o2.topc.equals("#")) {
            return -1;
        } else if (o1.topc.equals("#") || o2.topc.equals("@")) {
            return 1;
        } else {
            return o1.topc.compareTo(o2.topc);
        }
    }

}