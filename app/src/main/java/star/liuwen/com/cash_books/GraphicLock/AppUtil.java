package star.liuwen.com.cash_books.GraphicLock;

/**
 * Created by liuwen on 2017/4/17.
 * 九宫格解锁
 */
public class AppUtil {
    /**
     * 两点之间的距离
     *
     * @param a 第一个点
     * @param b 第二个点
     * @return 距离
     */
    public static double twoPointDistance(Point a, Point b) {
        //x轴差的平方加上y轴的平方,然后取平方根
        return Math.sqrt(Math.abs(a.x - b.x) * Math.abs(a.x - b.x) + Math.abs(a.y - b.y) * Math.abs(a.y - b.y));
    }

    /**
     * 判断手指移动的坐标与点的坐标是否重合(默认小于点半径的一半的时候表示重合)
     *
     * @param pointX 点横坐标
     * @param pointY 点纵坐标
     * @param r      点半径
     * @param moveX  手指移动的横坐标
     * @param moveY  手指移动的纵坐标
     * @return
     */
    public static boolean isCoincide(float pointX, float pointY, float r, float moveX, float moveY) {
        return Math.sqrt((pointX - moveX) * (pointX - moveX) + (pointY - moveY) * (pointY - moveY)) < r;
    }

    /**
     * 获取角度
     *
     * @param pointA 第一个点
     * @param pointB 第二个点
     * @return
     */
    public static float getDegrees(Point pointA, Point pointB) {
        return (float) Math.toDegrees(Math.atan2(pointB.y - pointA.y, pointB.x - pointA.x));
    }
}
