package enumRiddles;

enum Day {
	MONDAY(1),
	TUESDAY(2),
	WEDNESDAY(3),
	THURSDAY(4),
	FRIDAY(5),
	SATURDAY(6),
	SUNDAY(7);

	private final int dayNumber;

	Day(int dayNumber) {
		this.dayNumber = dayNumber;
	}

	private static Day[] vals = values();

	public Day next() {
		return vals[(this.ordinal()+1) % vals.length];
	}

	public int getDayNumber() {
		return dayNumber;
	}
}


	   
	public class DayTest {
	   public static void main(String[] args) {
	      for (Day day : Day.values()) {
	         System.out.printf("%s (%d), next is %s\n", day, day.getDayNumber(), day.next());
	      }
	   }
	}
