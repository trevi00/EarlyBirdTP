package CheckUser;

public class User {
	private String name;
	private int point;
	private final CheckLogic logic;
	private final CheckPrinter plogic;
	
	public User(String name) {
		this.name = name;
		this.point = 0; //출석 포인트는 0부터 시작
		this.logic = new CheckLogic();
		this.plogic = new CheckPrinter();
	}

	public boolean tryCheck(){
		if(logic.daychecklogic()){
			point += 10;
			return true;
		}
		return false;
	}
	public void printDate(){
		plogic.print(logic.getDateLogic());
	}
	public int MonthCount(){
		return logic.CountMonth(logic.getDateLogic());
	}
	public int getPoint() {
		return point;
	}
	


	
}
