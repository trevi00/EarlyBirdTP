package CheckUser;

import java.time.LocalDate;
import java.util.Set;

public class CheckPrinter {

    public void print(Set<LocalDate> checkdays){
        if(checkdays.isEmpty()) {
            System.out.println("출석 한 적이 없습니다.");
            return;
        }
        System.out.println("출석 출력.");
        for(LocalDate date : checkdays) {
            System.out.println(date.getYear()+ "년 " +date.getMonthValue() + "월 " + date.getDayOfMonth() + "일");
        }
    }
}
