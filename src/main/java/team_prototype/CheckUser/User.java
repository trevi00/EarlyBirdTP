package team_prototype.CheckUser;

public class User {
	private String name;
	private int point;
	private static final CheckLogic logic = new CheckLogic();
	private final CheckPrinter plogic = new CheckPrinter();
	
	public User(String name) {
		this.name = name;
		this.point = 0; //출석 포인트는 0부터 시작
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
