package prototype.CheckUser;

public class CheckMain {

	public static void main(String[] args) {
		User user1 = new User("최대호");

		System.out.println("출석체크");

		user1.tryCheck();
		user1.printDate();
		System.out.println("이번달출석 횟수. : "+user1.MonthCount());
	}

}
