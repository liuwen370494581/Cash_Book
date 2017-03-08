package star.liuwen.com.cash_books.PieChart;


public interface OnPieChartItemSelectedLinstener {

	void onPieChartItemSelected(PieChartView view, int position, String colorRgb, float size, float rate, boolean isFreePart, float rotateTime);

	void onTriggerClicked();
}
