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

/**
 * The DatabaseManager Class is responsible to create a new database,
 * or can connect with an existing one.
 */
public class DatabaseManager {
  public final static String databaseFilePath = "university_meals_made_easy.db";
  private static DatabaseManager instance;
  private final Connection connection;

  /**
   * Constructor for DatabaseManager class that verify if exists a db,
   * if not, a new database is created with the following methods.
   * @throws SQLException
   */
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

  /**
   * The getInstance, returns the databaseManager instance.
   * @return instance
   * @throws SQLException
   */
  public static DatabaseManager getInstance() throws SQLException {
    if (instance == null)
      instance = new DatabaseManager();
    return instance;
  }

  /**
   * The createMealTable method, creates the table meal in database.
   * @throws SQLException
   */
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

  /**
   * The createAppUserTable method, creates the table app_user in database.
   * @throws SQLException
   */
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

  /**
   * The createTimeSlotTable method, creates the table time_slot in database.
   * @throws SQLException
   */
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

  /**
   * The createTicketTable method, creates the table ticket in database.
   * @throws SQLException
   */
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

  /**
   * The createRefundTable method, creates the table refund in database.
   * @throws SQLException
   */
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

  /**
   * The createFoodItemTable method, creates the table food_item in database.
   * @throws SQLException
   */
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

  /**
   * The createTicketFoodItemTable method, creates the table ticket_food_item in database.
   * @throws SQLException
   */
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

  /**
   * The createReviewTable method, creates the table review in database.
   * @throws SQLException
   */
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

  /**
   * The createTopOffTable method, creates the table top_off in database.
   * @throws SQLException
   */
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


  /**
   * The getBalance method, returns the userId balance
   * @param userId
   * @return
   * @throws IllegalArgumentException
   */
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

  /**
   * The topOffBalance method can add money to the user account.
   * @param userId
   * @param value
   * @return
   * @throws IllegalArgumentException
   */
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

  /**
   * The getMeal method, return all the meals from a specific date and meal period.
   * @param date
   * @param period
   * @return
   * @throws NullPointerException
   */
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


  /**
   * The getAvailableTimeSlots method, returns the available timeslots from a specific meal.
   * @param meal
   * @return
   * @throws NullPointerException
   */
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

  /**
   * The getFoodItems method, returns all the food items from a specific meal.
   * @param meal
   * @return
   * @throws NullPointerException
   */
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

  /**
   * The getTickets method, returns all the bought meals from a specific user
   * @param userId
   * @return
   * @throws NullPointerException
   */
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

  /**
   * The getValidatedTickets method, returns all the tickets that have already been validated from a specific user.
   * @param userId
   * @return
   * @throws NullPointerException
   */
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

  /**
   * The getTicketItems method, return the food items from a specific ticket
   * @param ticket
   * @return
   * @throws NullPointerException
   */
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

  /**
   * The getTransactionHistory method, returns all the transactions from a specific user
   * @param userId
   * @return
   */
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

  /**
   * The buy method, assign a meal for a user with a specific user, timeslot and fooditems
   * @param userId
   * @param slot
   * @param foodItems
   * @return
   * @throws IllegalArgumentException
   * @throws NullPointerException
   */
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
    LocalDate localDate = LocalDate.now();
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
      ResultSet resultSet1 = statement.executeQuery(String.format("""
          SELECT *
          FROM meal
          WHERE id = %d
          AND date_of_meal = '%s'
          """, slot.getMealId(),localDate.format(Logger.dateFormatter)));
      if(resultSet1.next()) {
        cost += cost * 0.15;
      }
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

  /**
   * The refund methods, is responsible for controlling the ability to return a user's meal.
   * @param userId
   * @param ticket
   * @return
   * @throws IllegalArgumentException
   * @throws NullPointerException
   */
  public RefundResult refund(int userId, Ticket ticket)
      throws IllegalArgumentException, NullPointerException {
    if (userId == -1)
      throw new IllegalArgumentException("user must be authenticated");
    if (ticket == null)
      throw new NullPointerException("ticket cannot be null");
    try (Statement statement = connection.createStatement()) {
      ResultSet resultSet = statement.executeQuery(String.format("""
          SELECT *
          FROM time_slot
          WHERE id = %d
          """, ticket.getTimeSlotId()));
      if(resultSet.next()) {
        TimeSlot timeSlot = new TimeSlot(
            resultSet.getInt("id"),
            resultSet.getInt("meal_id"),
            resultSet.getString("time_of_start"),
            resultSet.getString("time_of_end"),
            resultSet.getInt("capacity")
        );
        LocalDate localDate = LocalDate.now();
        if(statement.executeQuery(String.format("""
          SELECT *
          FROM meal
          WHERE date_of_meal = '%s'
          AND id = %d
          """, localDate.format(Logger.dateFormatter), timeSlot.getMealId())).next()) {
          return RefundResult.UNEXPECTED_ERROR;
        }
      }
      statement.execute(String.format("""
          DELETE FROM ticket
          WHERE id = %d
          """, ticket.getId()));
      statement.execute(String.format("""
          DELETE FROM ticket_food_item
          WHERE ticket_id = %d
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
      e.printStackTrace();
      return RefundResult.UNEXPECTED_ERROR;
    }
  }

  /**
   * The review method can insert a user review from a specific meal into the database.
   * @param userId
   * @param ticket
   * @param rating
   * @param comment
   * @return
   * @throws IllegalArgumentException
   * @throws NullPointerException
   */
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

  /**
   * The close method, closes the connection with database
   */
  public void close() {
    try {
      connection.close();
    } catch (SQLException ignored) {}
  }

  /**
   * The login method verify if the user is already in database, if not a new user is created.
   * @param username
   * @return
   */
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

  /**
   * The getLastId method returns the maximum id that is currently in table.
   * @param table
   * @return
   * @throws SQLException
   */
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

  /**
   * The getId method, returns the id from a username.
   * @param username
   * @return
   */
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