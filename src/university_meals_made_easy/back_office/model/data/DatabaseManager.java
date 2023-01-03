package university_meals_made_easy.back_office.model.data;

import university_meals_made_easy.Logger;
import university_meals_made_easy.back_office.model.data.result.*;
import university_meals_made_easy.database.tables.FoodItem;
import university_meals_made_easy.database.tables.Meal.Meal;
import university_meals_made_easy.database.tables.Meal.MealPeriod;
import university_meals_made_easy.database.tables.Table;
import university_meals_made_easy.database.tables.TimeSlot;
import university_meals_made_easy.database.tables.transaction.Review;
import university_meals_made_easy.database.tables.transaction.Ticket;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class has all the methods necessary method to interact with the database
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
    connection.setAutoCommit(false);
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
      statement.executeUpdate("""
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
      statement.executeUpdate("""
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
      statement.executeUpdate("""
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
      statement.executeUpdate("""
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
      statement.executeUpdate("""
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
      statement.executeUpdate("""
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
      statement.executeUpdate("""
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
      statement.executeUpdate("""
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
      statement.executeUpdate("""
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
   * The getMeal method, return all the meals for a specific date and meal period.
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
   * The getTimeSlots method, return all the time Slots for a specific meal.
   * @param meal
   * @return
   * @throws NullPointerException
   */
  public List<TimeSlot> getTimeSlots(Meal meal)
      throws NullPointerException {
    List<TimeSlot> timeSlots;
    ResultSet resultSet;

    if (meal == null)
      throw new NullPointerException("meal cannot be null");
    try (Statement statement = connection.createStatement()) {
      resultSet = statement.executeQuery(String.format("""
          SELECT *
          FROM time_slot
          WHERE meal_id = %d;
          """, meal.getId()));
      timeSlots = new ArrayList<>();
      while (resultSet.next())
        timeSlots.add(new TimeSlot(
            resultSet.getInt("id"),
            resultSet.getInt("meal_id"),
            resultSet.getString("time_of_start"),
            resultSet.getString("time_of_end"),
            resultSet.getInt("capacity")
        ));
      return timeSlots;
    } catch (SQLException e) {
      return null;
    }
  }

  /**
   * The getTicketItems method, return all the food items for a specific ticket
   * @param ticket
   * @return
   * @throws NullPointerException
   */
  private List<FoodItem> getTicketItems(Ticket ticket)
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
   * The getTimeSlotTickets method, return all the tickets for a specific timeslot.
   * @param slot
   * @return
   * @throws NullPointerException
   */
  private List<Ticket> getTimeSlotTickets(TimeSlot slot)
      throws NullPointerException {
    ResultSet resultSet;
    List<Ticket> tickets;

    if (slot == null)
      throw new NullPointerException("slot cannot be null");
    try (Statement statement = connection.createStatement()) {
      resultSet = statement.executeQuery(String.format("""
          SELECT *
          FROM ticket
          WHERE time_slot_id = %d;
          """, slot.getId()));
      tickets = new ArrayList<>();
      while (resultSet.next())
        tickets.add(new Ticket(
            resultSet.getInt("id"),
            resultSet.getInt("app_user_id"),
            slot.getId(),
            resultSet.getString("datetime_of_purchase"),
            resultSet.getString("datetime_of_validation")
        ));
      return tickets;
    } catch (SQLException e) {
      return null;
    }
  }

  /**
   * The getOrderedMealsFoodItems method, return all the
   * ordered meals with items for a specific timeslot.
   * @param slot
   * @return
   * @throws NullPointerException
   */
  public List<List<FoodItem>> getOrderedMealsFoodItems(TimeSlot slot)
      throws NullPointerException {
    List<List<FoodItem>> orderedMealsFoodItems;
    List<Ticket> tickets;
    List<FoodItem> foodItems;

    if (slot == null)
      throw new NullPointerException("slot cannot be null");
    tickets = getTimeSlotTickets(slot);
    if (tickets == null)
      return null;
    orderedMealsFoodItems = new ArrayList<>();
    for (Ticket ticket : tickets) {
      foodItems = getTicketItems(ticket);
      if (foodItems == null)
        return null;
      orderedMealsFoodItems.add(foodItems);
    }
    return orderedMealsFoodItems;
  }

  /**
   * The getMeals method, return all the meals.
   * @return meals
   */
  private List<Meal> getMeals() {
    ResultSet resultSet;
    List<Meal> meals;

    try (Statement statement = connection.createStatement()) {
      resultSet = statement.executeQuery("""
          SELECT *
          FROM meal;
          """);
      meals = new ArrayList<>();
      while (resultSet.next())
        meals.add(new Meal(
            resultSet.getInt("id"),
            MealPeriod.from(resultSet.getString("meal_period")),
            resultSet.getString("date_of_meal")
        ));
      return meals;
    } catch (SQLException e) {
      return null;
    }
  }

  /**
   * The getPreviousMeals method, return all the
   * previous meals.
   * @return
   */
  public List<Meal> getPreviousMeals() {
    List<Meal> previousMeals = getMeals();
    Iterator<Meal> iterator;
    LocalDate today;

    if (previousMeals == null)
      return null;
    iterator = previousMeals.iterator();
    today = LocalDate.now();
    while (iterator.hasNext()) {
      if (!iterator.next().getDate().isBefore(today))
        iterator.remove();
    }
    return previousMeals;
  }

  /**
   * The getReviews method, return all the reviews for a specific meal.
   * @param meal
   * @return
   * @throws NullPointerException
   */
  public List<Review> getReviews(Meal meal) throws NullPointerException {
    ResultSet resultSet;
    List<Review> reviews;

    if (meal == null)
      throw new NullPointerException("meal cannot be null");
    try (Statement statement = connection.createStatement()) {
      resultSet = statement.executeQuery(String.format("""
          SELECT review.id, review.app_user_id, ticket_id, datetime_of_review,
                 rating, comment
          FROM review, ticket, time_slot
          WHERE ticket_id = ticket.id
          AND ticket.time_slot_id = time_slot.id
          AND time_slot.meal_id = %d;
          """, meal.getId()));
      reviews = new ArrayList<>();
      while (resultSet.next())
        reviews.add(new Review(
            resultSet.getInt("id"),
            resultSet.getInt("app_user_id"),
            resultSet.getInt("ticket_id"),
            resultSet.getString("datetime_of_review"),
            resultSet.getInt("rating"),
            resultSet.getString("comment")
        ));
      return reviews;
    } catch (SQLException e) {
      return null;
    }
  }

  /**
   * The getFoodItems method, return all the food items for a specific meal.
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
   * Validates a ticket with the specified ID.
   *
   * @param id the ID of the ticket to validate
   * @return the result of the ticket validation
   */
  public TicketValidationResult validateTicket(int id) {
    ResultSet resultSet;

    try (Statement statement = connection.createStatement()) {
      if (!statement.executeQuery(String.format("""
          SELECT *
          FROM ticket
          WHERE id = %d;
          """, id)).next())
        return TicketValidationResult.TICKET_DOES_NOT_EXIST;
      if (!statement.executeQuery(String.format("""
          SELECT *
          FROM ticket
          WHERE id = %d
          AND datetime_of_validation IS NULL;
          """, id)).next())
        return TicketValidationResult.TICKET_ALREADY_VALIDATED;
      statement.executeUpdate(String.format("""
          UPDATE ticket
          SET datetime_of_validation = '%s'
          WHERE id = %d;
          """, LocalDateTime.now().format(Logger.dateTimeFormatter), id));
      connection.commit();
      resultSet = statement.executeQuery(String.format("""
          SELECT TIME('%s') BETWEEN
                  TIME(time_of_start)
                  AND TIME(time_of_end)
                  AS "correct_time_slot"
          FROM time_slot
          WHERE id = (SELECT time_slot_id
                      FROM ticket
                      WHERE id = %d);
          """, LocalDateTime.now().format(Logger.timeFormatter), id));
      if (!resultSet.next())
        return TicketValidationResult.UNEXPECTED_ERROR;
      if (!resultSet.getBoolean("correct_time_slot")) {
        statement.executeUpdate(String.format("""
            UPDATE app_user
            SET balance = balance - 0.3 * (SELECT SUM(price)
                                    FROM food_item
                                    WHERE id IN (SELECT food_item_id
                                                FROM ticket_food_item
                                                WHERE ticket_id = %d))
            WHERE id = (SELECT app_user_id
            FROM ticket
            WHERE id = %d);
            """, id, id));
        connection.commit();
        return TicketValidationResult.WRONG_TIME_SLOT;
      }
      return TicketValidationResult.SUCCESS;
    } catch (SQLException e) {
      e.printStackTrace();
      return TicketValidationResult.UNEXPECTED_ERROR;
    }
  }
  /**
   * Returns the last ID for the specified table.
   *
   * @param table the table to get the last ID from
   * @return the last ID, or -1 if the table is empty
   * @throws SQLException if an error occurs while querying the database
   * @throws NullPointerException if the input argument is null
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
   * Inserts a meal record into the database allowing to specify mealPeriod and date
   * @param mealPeriod the period of the meal (lunch or dinner)
   * @param date the date of the meal
   * @return the result of the meal insertion
   * @throws NullPointerException if either input argument is null
   */
  public MealInsertionResult insertMeal(MealPeriod mealPeriod,
                                        LocalDate date)
      throws NullPointerException {
    String[] lunchTimeSlotTimes =
        {"12:00:00", "12:15:00", "12:30:00", "12:45:00", "13:00:00", "13:15:00", "13:30:00",
            "13:45:00", "14:00:00", "14:15:00", "14:30:00", "14:45:00", "15:00:00"};
    String[] dinnerTimeSlotTimes =
        {"19:30:00", "19:45:00", "20:00:00", "20:15:00", "20:30:00", "20:45:00", "21:00:00",
            "21:15:00", "21:30:00", "21:45:00", "22:00:00", "22:15:00", "22:30:00"};
    String[] mealTimeSlotsTimes;
    int mealId;

    if (mealPeriod == null)
      throw new NullPointerException("meal period cannot be null");
    if (date == null)
      throw new NullPointerException("date cannot be null");
    try (Statement statement = connection.createStatement()) {
      if (statement.executeQuery(String.format("""
          SELECT *
          FROM meal
          WHERE meal_period = '%s'
          AND date_of_meal = '%s';
          """, mealPeriod, date.format(Logger.dateFormatter))).next())
        return MealInsertionResult.ALREADY_EXISTS;
      mealId = getLastId(Table.MEAL) + 1;
      statement.executeUpdate(String.format("""
          INSERT INTO meal
          VALUES (%d, '%s', '%s');
          """, mealId, mealPeriod,
          date.format(Logger.dateFormatter)));
      if (mealPeriod == MealPeriod.LUNCH)
        mealTimeSlotsTimes = lunchTimeSlotTimes;
      else
        mealTimeSlotsTimes = dinnerTimeSlotTimes;
      for (int i = 0; i < mealTimeSlotsTimes.length - 1; i++) {
        statement.executeUpdate(String.format("""
            INSERT INTO time_slot
            VALUES (NULL, %d, '%s', '%s', 30);
            """, mealId, mealTimeSlotsTimes[i], mealTimeSlotsTimes[i + 1]));
      }
      connection.commit();
      return MealInsertionResult.SUCCESS;
    } catch (SQLException e) {
      e.printStackTrace();
      try {
        connection.rollback();
      } catch (SQLException ignored) {}
      return null;
    }
  }

  /**
   * Method called to add a new food item to a meal
   * Allows to specify price and description through its arguments
   * @param meal
   * @param price
   * @param description
   * @return
   * @throws NullPointerException
   * @throws IllegalArgumentException
   */
  public MealFoodItemInsertionResult insertFoodItem(Meal meal, float price,
                                                    String description)
      throws NullPointerException, IllegalArgumentException {
    if (meal == null)
      throw new NullPointerException("meal cannot be null");
    if (description == null)
      throw new NullPointerException("description cannot be null");
    if (price < 0)
      throw new IllegalArgumentException("price cannot be negative");
    try (Statement statement = connection.createStatement()) {
      if (statement.executeQuery(String.format("""
          SELECT *
          FROM food_item
          WHERE meal_id = %d
          AND description = '%s';
          """, meal.getId(), description)).next())
        return MealFoodItemInsertionResult.DUPLICATE_FOOD_ITEM;
      statement.executeUpdate(String.format("""
          INSERT INTO food_item
          VALUES (NULL, %d, %f, '%s');
          """, meal.getId(), price, description));
      connection.commit();
      return MealFoodItemInsertionResult.SUCCESS;
    } catch (SQLException e) {
      return MealFoodItemInsertionResult.UNEXPECTED_ERROR;
    }
  }

  /**
   * Clears all the meal food items in the meal with all the checks and balances
   * @param meal
   * @return
   */
  public MealFoodItemsClearingResult clearFoodItems(Meal meal) {
    if (meal == null)
      throw new NullPointerException("meal cannot be null");
    try (Statement statement = connection.createStatement()) {
      if (!statement.executeQuery(String.format("""
          SELECT *
          FROM food_item
          WHERE meal_id = %d;
          """, meal.getId())).next())
        return MealFoodItemsClearingResult.NO_FOOD_ITEMS;
      if (statement.executeQuery(String.format("""
          SELECT *
          FROM ticket, time_slot
          WHERE time_slot_id = time_slot.id
          AND time_slot.meal_id = %d;
          """, meal.getId())).next())
        return MealFoodItemsClearingResult.ALREADY_BOUGHT_TICKETS;
      statement.executeUpdate(String.format("""
          DELETE FROM food_item
          WHERE meal_id = %d;
          """, meal.getId()));
      connection.commit();
      return MealFoodItemsClearingResult.SUCCESS;
    } catch (SQLException e) {
      return MealFoodItemsClearingResult.UNEXPECTED_ERROR;
    }
  }

  /**
   * Serves to configure the maximum slot capacity in the database
   * @param slot
   * @param capacity
   * @return
   */
  public TimeSlotCapacityConfiguringResult configureCapacity(TimeSlot slot,
                                                             int capacity) {
    ResultSet resultSet;

    if (slot == null)
      throw new NullPointerException("slot cannot be null");
    if (capacity < 0)
      throw new IllegalArgumentException("capacity cannot be negative");
    try (Statement statement = connection.createStatement()) {
      resultSet = statement.executeQuery(String.format("""
          SELECT COUNT(*) AS "N"
          FROM ticket
          WHERE time_slot_id = %d;
          """, slot.getId()));
      if (resultSet.next()
          && resultSet.getInt("N") > capacity)
        return TimeSlotCapacityConfiguringResult
            .ALREADY_TOO_MANY_BOUGHT_TICKETS;
      statement.executeUpdate(String.format("""
          UPDATE time_slot
          SET capacity = %d
          WHERE id = %d;
          """, capacity, slot.getId()));
      connection.commit();
      return TimeSlotCapacityConfiguringResult.SUCCESS;
    } catch (SQLException e) {
      return TimeSlotCapacityConfiguringResult.UNEXPECTED_ERROR;
    }
  }

  /**
   * Closes the connection to the database
   */
  public void close() {
    try {
      connection.close();
    } catch (SQLException ignored) {}
  }
}
