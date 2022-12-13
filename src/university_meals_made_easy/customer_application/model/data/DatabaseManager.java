package university_meals_made_easy.customer_application.model.data;

import university_meals_made_easy.Logger;
import university_meals_made_easy.customer_application.model.data.result.*;
import university_meals_made_easy.database.tables.FoodItem;
import university_meals_made_easy.database.tables.Meal.Meal;
import university_meals_made_easy.database.tables.Meal.MealPeriod;
import university_meals_made_easy.database.tables.Table;
import university_meals_made_easy.database.tables.TimeSlot;
import university_meals_made_easy.database.tables.transaction.*;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
  public final static String databaseFilePath = "university_meals_made_easy.db";
  private static DatabaseManager instance;
  private final Connection connection;

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
  public float getBalance(int userId) throws IllegalArgumentException {
    ResultSet resultSet;

    if (userId == -1)
      throw new IllegalArgumentException("user must be authenticated");
    try (Statement statement = connection.createStatement()) {
      resultSet = statement.executeQuery(String.format("""
          SELECT *
          FROM app_user
          WHERE id = %d;
          """, userId));
      if (!resultSet.next())
        return -1;
      return resultSet.getFloat("balance");
    } catch (SQLException e) {
      return -1;
    }
  }
  public BalanceTopOffResult topOffBalance(int userId, float value)
      throws IllegalArgumentException {
    ResultSet resultSet;

    if (userId == -1)
      throw new IllegalArgumentException("user must be authenticated");
    if (value < 0)
      throw new IllegalArgumentException("value cannot be negative");
    try (Statement statement = connection.createStatement()) {
      resultSet = statement.executeQuery(String.format("""
          SELECT *
          FROM app_user
          WHERE id = %d;
          """, userId));
      if (!resultSet.next())
        return BalanceTopOffResult.USER_DOES_NOT_EXIST;
      statement.execute(String.format("""
          UPDATE app_user
          SET balance = %f
          WHERE id = %d;
          """, resultSet.getFloat("balance") + value, userId));
      statement.execute(String.format("""
          INSERT INTO top_off
          VALUES (NULL, %d, '%s', %f);
          """, userId, LocalDateTime.now().format(Logger.dateTimeFormatter),
          value));
      return BalanceTopOffResult.SUCCESS;
    } catch (SQLException e) {
      return BalanceTopOffResult.UNEXPECTED_ERROR;
    }
  }
  public Meal getMeal(LocalDate date, MealPeriod period)
      throws NullPointerException {
    ResultSet resultSet;

    if (date == null)
      throw new NullPointerException("date cannot be null");
    if (period == null)
      throw new NullPointerException("period cannot be null");
    try (Statement statement = connection.createStatement()) {
      resultSet = statement.executeQuery(String.format("""
          SELECT *
          FROM meal
          WHERE date_of_meal = '%s'
          AND meal_period = '%s'
          """, date.format(Logger.dateFormatter), period));
      if (resultSet.next())
        return new Meal(
            resultSet.getInt("id"),
            MealPeriod.from(resultSet.getString("meal_period")),
            resultSet.getString("date_of_meal"));
    } catch (SQLException e) {
      return null;
    }
    return null;
  }
  public List<TimeSlot> getAvailableTimeSlots(Meal meal)
      throws NullPointerException {
    List<TimeSlot> availableTimeSlots;
    ResultSet resultSet;

    if (meal == null)
      throw new NullPointerException("meal cannot be null");
    try (Statement statement = connection.createStatement()) {
      resultSet = statement.executeQuery(String.format("""
          SELECT *
          FROM time_slot outer_time_slot
          WHERE meal_id = %d
          AND capacity > (SELECT COUNT(*)
                          FROM ticket
                          WHERE time_slot_id = outer_time_slot.id);
          """, meal.getId()));
      availableTimeSlots = new ArrayList<>();
      while (resultSet.next())
        availableTimeSlots.add(new TimeSlot(
            resultSet.getInt("id"),
            resultSet.getInt("meal_id"),
            resultSet.getString("time_of_start"),
            resultSet.getString("time_of_end"),
            resultSet.getInt("capacity")
        ));
      return availableTimeSlots;
    } catch (SQLException e) {
      return null;
    }
  }
  public List<FoodItem> getFoodItems(Meal meal) throws NullPointerException {
    ResultSet resultSet;
    List<FoodItem> foodItems;

    if (meal == null)
      throw new NullPointerException("meal cannot be null");
    try (Statement statement = connection.createStatement()) {
      resultSet = statement.executeQuery(String.format("""
          SELECT *
          FROM food_item
          WHERE meal_id = %d;
          """, meal.getId()));
      foodItems = new ArrayList<>();
      while (resultSet.next())
        foodItems.add(new FoodItem(
            resultSet.getInt("id"),
            resultSet.getInt("meal_id"),
            resultSet.getFloat("price"),
            resultSet.getString("description")
        ));
      return foodItems;
    } catch (SQLException e) {
      return null;
    }
  }
  public List<Ticket> getTickets(int userId) throws NullPointerException {
    ResultSet resultSet;
    List<Ticket> tickets;

    if (userId == -1)
      throw new IllegalArgumentException("user must be authenticated");
    try (Statement statement = connection.createStatement()) {
      resultSet = statement.executeQuery(String.format("""
          SELECT ticket.id, app_user_id, time_slot_id, datetime_of_purchase,
          datetime_of_validation
          FROM ticket, app_user
          WHERE app_user_id = app_user.id
          AND app_user.id = %d
          AND ticket.datetime_of_validation is null;
          """, userId));
      tickets = new ArrayList<>();
      while (resultSet.next())
        tickets.add(new Ticket(
            resultSet.getInt("id"),
            resultSet.getInt("app_user_id"),
            resultSet.getInt("time_slot_id"),
            resultSet.getString("datetime_of_purchase"),
            resultSet.getString("datetime_of_validation")
        ));
      return tickets;
    } catch (SQLException e) {
      return null;
    }
  }
  public List<Ticket> getValidatedTickets(int userId) throws NullPointerException {
    ResultSet resultSet;
    List<Ticket> tickets;

    if (userId == -1)
      throw new IllegalArgumentException("user must be authenticated");
    try (Statement statement = connection.createStatement()) {
      resultSet = statement.executeQuery(String.format("""
          SELECT ticket.id, app_user_id, time_slot_id, datetime_of_purchase,
          datetime_of_validation
          FROM ticket, app_user
          WHERE app_user_id = app_user.id
          AND app_user.id = %d
          AND ticket.datetime_of_validation is not null;
          """, userId));
      tickets = new ArrayList<>();
      while (resultSet.next())
        tickets.add(new Ticket(
            resultSet.getInt("id"),
            resultSet.getInt("app_user_id"),
            resultSet.getInt("time_slot_id"),
            resultSet.getString("datetime_of_purchase"),
            resultSet.getString("datetime_of_validation")
        ));
      return tickets;
    } catch (SQLException e) {
      return null;
    }
  }
  public List<FoodItem> getTicketItems(Ticket ticket)
      throws NullPointerException {
    ResultSet resultSet;
    List<FoodItem> foodItems;

    if (ticket == null)
      throw new NullPointerException("ticket cannot be null");
    try (Statement statement = connection.createStatement()) {
      resultSet = statement.executeQuery(String.format("""
          SELECT food_item.id, meal_id, price, description
          FROM food_item, ticket_food_item
          WHERE ticket_food_item.food_item_id = food_item.id
          AND ticket_food_item.ticket_id = %d;
          """, ticket.getId()));
      foodItems = new ArrayList<>();
      while (resultSet.next())
        foodItems.add(new FoodItem(
            resultSet.getInt("id"),
            resultSet.getInt("meal_id"),
            resultSet.getFloat("price"),
            resultSet.getString("description")
        ));
      return foodItems;
    } catch (SQLException e) {
      return null;
    }
  }
  public List<Transaction> getTransactionHistory(int userId) {
    List<Transaction> transactions;
    ResultSet resultSet;

    if (userId == -1)
      throw new IllegalArgumentException("user must be authenticated");
    try (Statement statement = connection.createStatement()) {
      resultSet = statement.executeQuery(String.format("""
          SELECT *
          FROM ticket
          WHERE app_user_id = %d;
          """, userId));
      transactions = new ArrayList<>();
      while (resultSet.next())
        transactions.add(new Ticket(
            resultSet.getInt("id"),
            userId,
            resultSet.getInt("time_slot_id"),
            resultSet.getString("datetime_of_purchase"),
            resultSet.getString("datetime_of_validation")
        ));
      resultSet = statement.executeQuery(String.format("""
          SELECT *
          FROM refund
          WHERE app_user_id = %d;
          """, userId));
      while (resultSet.next())
        transactions.add(new Refund(
            resultSet.getInt("id"),
            resultSet.getInt("app_user_id"),
            resultSet.getInt("ticket_id"),
            resultSet.getString("datetime_of_refund")
        ));
      resultSet = statement.executeQuery(String.format("""
          SELECT *
          FROM review
          WHERE app_user_id = %d;
          """, userId));
      while (resultSet.next())
        transactions.add(new Review(
            resultSet.getInt("id"),
            resultSet.getInt("app_user_id"),
            resultSet.getInt("ticket_id"),
            resultSet.getString("datetime_of_review"),
            resultSet.getInt("rating"),
            resultSet.getString("comment")
        ));
      resultSet = statement.executeQuery(String.format("""
          SELECT *
          FROM top_off
          WHERE app_user_id = %d;
          """, userId));
      while (resultSet.next())
        transactions.add(new TopOff(
            resultSet.getInt("id"),
            userId,
            resultSet.getString("datetime_of_topoff"),
            resultSet.getFloat("top_off_value")
        ));
      return transactions;
    } catch (SQLException e) {
      return null;
    }
  }
  public BuyResult buy(int userId, TimeSlot slot, List<FoodItem> foodItems)
      throws IllegalArgumentException, NullPointerException {
    ResultSet resultSet;
    float cost = 0;
    float balance;
    int ticketId;

    if (userId == -1)
      throw new IllegalArgumentException("user must be authenticated");
    if (slot == null)
      throw new NullPointerException("slot cannot be null");
    if (foodItems == null)
      throw new NullPointerException("food items cannot be null");
    if (foodItems.isEmpty())
      throw new IllegalArgumentException("food items cannot be empty");
    for (FoodItem foodItem : foodItems)
      cost += foodItem.getPrice();
    balance = getBalance(userId);
    if (balance < cost)
      return BuyResult.INSUFFICIENT_BALANCE;
    try (Statement statement = connection.createStatement()) {
      resultSet = statement.executeQuery(String.format("""
          SELECT COUNT(*) AS "N"
          FROM ticket
          WHERE time_slot_id = %d;
          """, slot.getId()));
      if (!resultSet.next()
          && resultSet.getInt("N") >= slot.getCapacity())
        return BuyResult.SLOT_ALREADY_FULL;
      ticketId = getLastId(Table.TICKET) + 1;
      statement.execute(String.format("""
          INSERT INTO ticket
          VALUES (%d, %d, %d, '%s', NULL);
          """, ticketId, userId, slot.getId(),
          LocalDateTime.now().format(Logger.dateTimeFormatter)));
      for (FoodItem foodItem : foodItems)
        statement.execute(String.format("""
            INSERT INTO ticket_food_item
            VALUES (%d, %d);
            """, ticketId, foodItem.getId()));
      statement.execute(String.format("""
          UPDATE app_user
          SET balance = %f
          WHERE id = %d;
          """, balance - cost, userId));
      return BuyResult.SUCCESS;
    } catch (SQLException e) {
      return BuyResult.UNEXPECTED_ERROR;
    }
  }
  public RefundResult refund(int userId, Ticket ticket)
      throws IllegalArgumentException, NullPointerException {
    if (userId == -1)
      throw new IllegalArgumentException("user must be authenticated");
    if (ticket == null)
      throw new NullPointerException("ticket cannot be null");
    try (Statement statement = connection.createStatement()) {
      statement.execute(String.format("""
          DELETE FROM ticket
          WHERE id = %d
          """, ticket.getId()));
      statement.execute(String.format("""
          INSERT INTO refund
          VALUES (NULL, %d, %d, '%s');
          """, userId, ticket.getId(),
          LocalDateTime.now().format(Logger.dateTimeFormatter)));
      List<FoodItem> foodItems = getTicketItems(ticket);
      if(foodItems == null)
        return RefundResult.UNEXPECTED_ERROR;
      float price = 0;
      for(FoodItem foodItem : foodItems) {
        price += foodItem.getPrice();
      }
      statement.execute(String.format("""
          UPDATE app_user
          SET balance = balance + %f
          WHERE id = %d
          """, price, userId));
      return RefundResult.SUCCESS;
    } catch (SQLException e) {
      return RefundResult.UNEXPECTED_ERROR;
    }
  }
  public ReviewResult review(int userId, Ticket ticket, int rating,
                             String comment)
      throws IllegalArgumentException, NullPointerException {
    if (userId == -1)
      throw new IllegalArgumentException("user must be authenticated");
    if (ticket == null)
      throw new NullPointerException("ticket cannot be null");
    if (rating < 0)
      throw new IllegalArgumentException("rating must be above 0");
    if (rating > 10)
      throw new IllegalArgumentException("rating must be under 10");
    try (Statement statement = connection.createStatement()) {
      statement.execute(String.format("""
          INSERT INTO review
          VALUES (NULL, %d, %d, '%s', %d, '%s');
          """, userId, ticket.getId(),
          LocalDateTime.now().format(Logger.dateTimeFormatter),
          rating, comment));
      return ReviewResult.SUCCESS;
    } catch (SQLException e) {
      return ReviewResult.UNEXPECTED_ERROR;
    }
  }
  public void close() {
    try {
      connection.close();
    } catch (SQLException ignored) {}
  }
  public LoginResult login(String username) {
    try (Statement statement = connection.createStatement()) {
      if (!statement.executeQuery(String.format("""
          SELECT *
          FROM app_user
          WHERE username = '%s';WW
          """, username)).next()) {
        statement.execute(String.format("""
              INSERT INTO app_user
              VALUES (NULL, '%s', 0);
              """, username));
      }
      return LoginResult.SUCCESS;
    } catch (SQLException e) {
      return LoginResult.UNEXPECTED_ERROR;
    }
  }
  private int getLastId(Table table) throws SQLException {
    ResultSet resultSet;

    if (table == null)
      throw new NullPointerException("table cannot be null");
    try (Statement statement = connection.createStatement()) {
      resultSet = statement.executeQuery(String.format("""
          SELECT MAX(ID) AS "N"
          FROM '%s';
          """, table));
      if (!resultSet.next())
        return -1;
      return resultSet.getInt("N");
    }
  }
  public int getId(String username) {
    ResultSet resultSet;

    try (Statement statement = connection.createStatement()) {
      resultSet = statement.executeQuery(String.format("""
          SELECT *
          FROM app_user
          WHERE username = '%s';
          """, username));
      if (resultSet.next())
        return resultSet.getInt("id");
      return -1;
    } catch (SQLException e) {
      return -1;
    }
  }
}