package university_meals_made_easy.back_office.model.data;

import university_meals_made_easy.Logger;
import university_meals_made_easy.back_office.model.data.result.*;
import university_meals_made_easy.database.tables.FoodItem;
import university_meals_made_easy.database.tables.Meal.Meal;
import university_meals_made_easy.database.tables.Meal.MealPeriod;
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
  public List<Review> getReviews(Meal meal) throws NullPointerException {
    ResultSet resultSet;
    List<Review> reviews;

    if (meal == null)
      throw new NullPointerException("meal cannot be null");
    try (Statement statement = connection.createStatement()) {
      resultSet = statement.executeQuery(String.format("""
          SELECT review.id, app_user_id, ticket_id, datetime_of_review,
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
            resultSet.getString("datetime"),
            resultSet.getInt("rating"),
            resultSet.getString("comment")
        ));
      return reviews;
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
  public TicketValidationResult validateTicket(int id) {
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
          AND datetime_of_validation = NULL;
          """, id)).next())
        return TicketValidationResult.TICKET_ALREADY_VALIDATED;
      if (statement.executeQuery(String.format("""
          SELECT *
          FROM refund
          WHERE ticket_id = %d;
          """, id)).next())
        return TicketValidationResult.TICKET_ALREADY_REFUNDED;
      statement.execute(String.format("""
          UPDATE ticket
          SET datetime_of_validation = '%s'
          WHERE id = %d;
          """, LocalDateTime.now().format(Logger.dateTimeFormatter), id));
      return TicketValidationResult.SUCCESS;
    } catch (SQLException e) {
      return TicketValidationResult.UNEXPECTED_ERROR;
    }
  }
  public MealInsertionResult insertMeal(MealPeriod mealPeriod,
                                        LocalDate date)
      throws NullPointerException {
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
      statement.execute(String.format("""
          INSERT INTO meal
          VALUES (NULL, '%s', '%s');
          """, mealPeriod, date.format(Logger.dateFormatter)));
      return MealInsertionResult.SUCCESS;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }
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
      statement.execute(String.format("""
          INSERT INTO food_item
          VALUES (NULL, %d, %f, '%s');
          """, meal.getId(), price, description));
      return MealFoodItemInsertionResult.SUCCESS;
    } catch (SQLException e) {
      return MealFoodItemInsertionResult.UNEXPECTED_ERROR;
    }
  }
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
      statement.execute(String.format("""
          DELETE FROM food_item
          WHERE meal_id = %d;
          """, meal.getId()));
      return MealFoodItemsClearingResult.SUCCESS;
    } catch (SQLException e) {
      return MealFoodItemsClearingResult.UNEXPECTED_ERROR;
    }
  }
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
      statement.execute(String.format("""
          UPDATE time_slot
          SET capacity = %d
          WHERE id = %d;
          """, capacity, slot.getId()));
      return TimeSlotCapacityConfiguringResult.SUCCESS;
    } catch (SQLException e) {
      return TimeSlotCapacityConfiguringResult.UNEXPECTED_ERROR;
    }
  }
  public void close() {
    try {
      connection.close();
    } catch (SQLException ignored) {}
  }
}
