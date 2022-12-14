package university_meals_made_easy.customer_application.model.data;

import university_meals_made_easy.Logger;
import university_meals_made_easy.customer_application.model.data.result.*;
import university_meals_made_easy.database.tables.FoodItem;
import university_meals_made_easy.database.tables.Meal.Meal;
import university_meals_made_easy.database.tables.Meal.MealPeriod;
import university_meals_made_easy.database.tables.TimeSlot;
import university_meals_made_easy.database.tables.transaction.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Class that manages user login state
 * logs in or out users and checks for the appropriate errors or exceptions to this process
 */
public class DataManager {
  private final static String TAG = "DataManager";
  private int userId;
  private String username;

  /**
   * Method for login of user according to username
   * receives username and checks for its validaty before setting the current user as the user it received.
   * @param username
   */
  public LoginResult login(String username) throws NullPointerException {
    LoginResult loginResult;

    if (username == null)
      throw new NullPointerException("username cannot be null");
    if (username.isEmpty() || username.contains(" ")
        || username.contains("\t"))
      return LoginResult.FAIL;
    try {
      loginResult = DatabaseManager.getInstance().login(username);
      if (loginResult == LoginResult.SUCCESS) {
        userId = DatabaseManager.getInstance().getId(username);
        this.username = username;
        if (userId != -1)
          return LoginResult.SUCCESS;
      } else
        return loginResult;
    } catch (SQLException e) {
      Logger.getInstance().error(TAG,
          "couldn't login in the database manager");
    }
    return LoginResult.FAIL;
  }

  /**
   * Method for logout of currently logged in user
   * after making the appropriate checks of weather a user is logged in, sets the current logged in user to null
   */
  public void logout() throws UnsupportedOperationException {
    if (userId == -1)
      throw new UnsupportedOperationException(
          "cannot logout if not logged in");
    userId = -1;
  }

  /**
   * Method for getBalance from a currently userId.
   * @return
   */
  public float getBalance() {
    try {
      return DatabaseManager.getInstance().getBalance(userId);
    } catch (SQLException e) {
      return -1;
    }
  }

  /**
   * Method for add money into a specific userId.
   * @return
   */
  public BalanceTopOffResult topOffBalance(float value) {
    try {
      return DatabaseManager.getInstance().topOffBalance(userId, value);
    } catch (SQLException e) {
      return BalanceTopOffResult.UNEXPECTED_ERROR;
    }
  }

  /**
   * Method to return a meal from a specific date and period.
   * @param date
   * @param period
   * @return
   */
  public Meal getMeal(LocalDate date, MealPeriod period) {
    try {
      return DatabaseManager.getInstance().getMeal(date, period);
    } catch (SQLException e) {
      return null;
    }
  }

  /**
   * Method to return the availableTimeSlots from a specific meal.
   * @param meal
   * @return
   */
  public List<TimeSlot> getAvailableTimeSlots(Meal meal) {
    try {
      return DatabaseManager.getInstance().getAvailableTimeSlots(meal);
    } catch (SQLException e) {
      return null;
    }
  }

  /**
   * Method to return the foodItems from a specific meal.
   * @param meal
   * @return
   */
  public List<FoodItem> getFoodItems(Meal meal) {
    try {
      return DatabaseManager.getInstance().getFoodItems(meal);
    } catch (SQLException e) {
      return null;
    }
  }

  /**
   * Method to return the tickets from a specific user.
   * @return
   */
  public List<Ticket> getTickets() {
    try {
      return DatabaseManager.getInstance().getTickets(userId);
    } catch (SQLException e) {
      return null;
    }
  }

  /**
   * Method to return all the tickets that have already been validated from a specific user.
   * @return
   */
  public List<Ticket> getValidatedTickets() {
    try {
      return DatabaseManager.getInstance().getValidatedTickets(userId);
    } catch (SQLException e) {
      return null;
    }
  }

  /**
   * Method to return the food items from a specific ticket.
   * @param ticket
   * @return
   */
  public List<FoodItem> getTicketItems(Ticket ticket) {
    try {
      return DatabaseManager.getInstance().getTicketItems(ticket);
    } catch (SQLException e) {
      return null;
    }
  }

  /**
   * Method to return the transactions made by a user.
   * @return
   */
  public List<Transaction> getTransactionHistory() {
    try {
      return DatabaseManager.getInstance().getTransactionHistory(userId);
    } catch (SQLException e) {
      return null;
    }
  }

  /**
   * Method to assign a meal for a user.
   * @param slot
   * @param foodItems
   * @return
   */
  public BuyResult buy(TimeSlot slot, List<FoodItem> foodItems) {
    try {
      return DatabaseManager.getInstance().buy(userId, slot, foodItems);
    } catch (SQLException e) {
      return BuyResult.UNEXPECTED_ERROR;
    }
  }

  /**
   * Method that can insert a user's meal
   * @param ticket
   * @return
   */
  public RefundResult refund(Ticket ticket) {
    try {
      return DatabaseManager.getInstance().refund(userId, ticket);
    } catch (SQLException e) {
      return RefundResult.UNEXPECTED_ERROR;
    }
  }

  /**
   * Method that can insert a meal review
   * @param ticket
   * @param rating
   * @param comment
   * @return
   */
  public ReviewResult review(Ticket ticket, int rating,
                             String comment) {
    try {
      return DatabaseManager.getInstance().review(userId, ticket, rating,
          comment);
    } catch (SQLException e) {
      return ReviewResult.UNEXPECTED_ERROR;
    }
  }

  /**
   * Method that can return a username saved in the local variable.
   * @return username
   */
  public String getUsername() {
    return username;
  }
}
