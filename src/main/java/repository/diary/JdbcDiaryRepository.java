package repository.diary;

import database.DatabaseConnection;

import java.sql.Connection;

public class JdbcDiaryRepository implements DiaryRepository {

    final Connection connection = DatabaseConnection.getConnection();

}
