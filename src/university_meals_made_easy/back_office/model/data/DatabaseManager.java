package university_meals_made_easy.back_office.model.data;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.locks.ReentrantLock;

public class DatabaseManager {
  public final static String databaseFilePath = "university_meals_made_easy.db";
  private static DatabaseManager instance;
  private final Connection connection;
  private final ReentrantLock mutex;

  private DatabaseManager() throws SQLException {
    boolean existedBefore;

    existedBefore = new File(databaseFilePath).exists();
    connection = DriverManager.getConnection(String.format("jdbc:sqlite:%s",
        databaseFilePath));
    if (!existedBefore) {
      createMealTable();
      createAppUserTable();
      createTimeSlotTable();
      createTicketTable();
      createRefundTable();
      createFoodItemTable();
      createTicketFoodItemTable();
      createReviewTable();
      createTopOffTable();
    }
    mutex = new ReentrantLock();
  }
  public static DatabaseManager getInstance() throws SQLException {
    if (instance == null)
      instance = new DatabaseManager();
    return instance;
  }
  private void createMealTable() throws SQLException {
    try (Statement statement = connection.createStatement()) {
      statement.execute("""
          CREATE TABLE meal (
              id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
              meal_period TEXT NOT NULL,
              date_of_meal TEXT NOT NULL
          );
          """);
    }
  }
  private void createAppUserTable() throws SQLException {
    try (Statement statement = connection.createStatement()) {
      statement.execute("""
          CREATE TABLE app_user (
              id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
              username TEXT UNIQUE NOT NULL,
              balance REAL NOT NULL
          );
          """);
    }
  }
  private void createTimeSlotTable() throws SQLException {
    try (Statement statement = connection.createStatement()) {
      statement.execute("""
          CREATE TABLE time_slot (
              id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
              meal_id INTEGER REFERENCES meal (id) NOT NULL,
              time_of_start TEXT NOT NULL,
              time_of_end TEXT NOT NULL,
              capacity INTEGER NOT NULL DEFAULT (30)
          );
          """);
    }
  }
  private void createTicketTable() throws SQLException {
    try (Statement statement = connection.createStatement()) {
      statement.execute("""
          CREATE TABLE ticket (
              id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
              app_user_id INTEGER REFERENCES app_user (id) NOT NULL,
              time_slot_id INTEGER REFERENCES ticket (id) NOT NULL,
              datetime_of_purchase TEXT NOT NULL,
              datetime_of_validation TEXT
          );
          """);
    }
  }
  private void createRefundTable() throws SQLException {
    try (Statement statement = connection.createStatement()) {
      statement.execute("""
          CREATE TABLE refund (
              id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
              app_user_id INTEGER REFERENCES app_user (id) NOT NULL,
              ticket_id INTEGER REFERENCES ticket (id) NOT NULL,
              datetime_of_refund TEXT NOT NULL
          );
          """);
    }
  }
  private void createFoodItemTable() throws SQLException {
    try (Statement statement = connection.createStatement()) {
      statement.execute("""
          CREATE TABLE food_item (
              id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
              meal_id INTEGER REFERENCES meal (id) NOT NULL,
              price REAL NOT NULL,
              description TEXT NOT NULL
          );
          """);
    }
  }
  private void createTicketFoodItemTable() throws SQLException {
    try (Statement statement = connection.createStatement()) {
      statement.execute("""
          CREATE TABLE ticket_food_item (
              ticket_id INTEGER REFERENCES ticket (id) NOT NULL,
              food_item_id INTEGER REFERENCES food_item (id) NOT NULL,
              PRIMARY KEY (ticket_id, food_item_id)
          );
          """);
    }
  }
  private void createReviewTable() throws SQLException {
    try (Statement statement = connection.createStatement()) {
      statement.execute("""
          CREATE TABLE review (
              id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
              app_user_id INTEGER REFERENCES app_user (id) NOT NULL,
              ticket_id INTEGER REFERENCES ticket (id) NOT NULL,
              datetime_of_review TEXT NOT NULL,
              rating INTEGER NOT NULL,
              comment TEXT
          );
          """);
    }
  }
  private void createTopOffTable() throws SQLException {
    try (Statement statement = connection.createStatement()) {
      statement.execute("""
          CREATE TABLE top_off (
              id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
              app_user_id INTEGER REFERENCES app_user(id),
              datetime_of_topoff TEXT NOT NULL,
              top_off_value REAL NOT NULL
          );
          """);
    }
  }
  public void close() {
    try {
      connection.close();
    } catch (SQLException ignored) {}
  }
}
