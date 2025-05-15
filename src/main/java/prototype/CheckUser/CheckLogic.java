package prototype.CheckUser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;

public class CheckLogic {
    private final Set<LocalDate> DateLogic = new HashSet<>();

    public boolean daychecklogic() {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        if(now.isAfter(LocalTime.of(4, 0)) && now.isBefore(LocalTime.of(8, 0))) {
            return false; // 오전 4시 ~ 8시 출석체크 불가능
        }
        if(DateLogic.contains(today)) {
            return false; //오늘 출석했을시 출석 불가능.
        }
        System.out.println("현재 저장된 출석 날짜들: " + DateLogic);
        System.out.println("오늘 날짜: " + today);
        System.out.println("출석 여부: " + DateLogic.contains(today));
        DateLogic.add(today);
        return true;
    }
    //이번달의 출석 횟수.
    public int CountMonth(Set<LocalDate> checkdays) {
        Month month = LocalDate.now().getMonth();
        int year = LocalDate.now().getYear();
        int count = 0;

        for(LocalDate date : checkdays) {
            if(date.getMonth() == month && date.getYear() == year) {
                count++;
            }
        }

        return count;
    }

    public Set<LocalDate> getDateLogic() {
        return DateLogic;
    }
}
