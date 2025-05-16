package repository.diary;

import database.DatabaseConnection;

import java.sql.Connection;
import java.time.LocalDate;

public class JdbcDiaryRepository implements DiaryRepository {

    final Connection connection = DatabaseConnection.getConnection();

    @Override
    public void save(Diary diary) {

    }

    @Override
    public List<Diary> findAllByUsername(String username) {
        return null;
    }

    @Override
    public Diary findByUsernameAndDate(String username, LocalDate date) {
        return null;
    }

    @Override
    public void deleteByUsernameAndDate(String username, LocalDate date) {

    }

}
