package star.liuwen.com.cash_books.PieChart;

public class TransUtils {
    public static int obj2Int(Object o) {
        return ((Number) o).intValue();
    }

    public static float obj2Float(Object o) {
        return ((Number) o).floatValue();
    }

    public static double angle2radians(float angle) {
        return angle / 180f * Math.PI;
    }

    public static double radians2angle(double radians) {
        return 180f * radians / Math.PI;
    }
}
