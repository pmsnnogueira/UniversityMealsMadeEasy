package university_meals_made_easy.back_office.model.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
  public final static String databaseFilePath = "university_meals_made_easy.db";
  private static DatabaseManager databaseManager;
  private final Connection connection;
  private final Statement statement;

  private DatabaseManager() throws SQLException {
    connection = DriverManager.getConnection(String.format("jdbc:sqlite:%s",
        databaseFilePath));
    statement = connection.createStatement();
  }
  public static DatabaseManager getInstance() throws SQLException {
    if (databaseManager == null)
      databaseManager = new DatabaseManager();
    return databaseManager;
  }
  public void close() throws SQLException {
    connection.close();
  }
}
