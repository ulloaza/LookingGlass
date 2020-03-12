package schedulingApp;

public class MyCalender {
	
	private int daysInMonth[] = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

	
	
	public MyCalender() {
		
	}
	
	
	public int getDaysInMonth(int month) {
		return daysInMonth[month];
	}
	
	
	
	
}
