package repository.diary;

import java.time.LocalDate;

public interface DiaryRepository {

    void save(Diary diary);

    List<Diary> findAllByUsername(String username);

    Diary findByUsernameAndDate(String username, LocalDate date);

    void deleteByUsernameAndDate(String username, LocalDate date);

}
