package university_meals_made_easy.back_office.model.data;

import university_meals_made_easy.back_office.model.data.result.*;
import university_meals_made_easy.database.tables.FoodItem;
import university_meals_made_easy.database.tables.Meal.Meal;
import university_meals_made_easy.database.tables.Meal.MealPeriod;
import university_meals_made_easy.database.tables.TimeSlot;
import university_meals_made_easy.database.tables.transaction.Review;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * DataManager
 */
public class DataManager {
  /**
   * Returns the meal for the given date and meal period.
   * @param date the date of the meal
   * @param period the meal period (e.g. breakfast, lunch, dinner)
   * @return the meal, or null if an error occurs while retrieving the meal from the database
   */
  public Meal getMeal(LocalDate date, MealPeriod period) {
    try {
      return DatabaseManager.getInstance().getMeal(date, period);
    } catch (SQLException e) {
      return null;
    }
  }

  /**
   * Returns the time slots for the given meal.
   * @param meal the meal
   * @return the time slots for the meal, or null if an error occurs while retrieving the time slots from the database
   */
  public List<TimeSlot> getTimeSlots(Meal meal) {
    try {
      return DatabaseManager.getInstance().getTimeSlots(meal);
    } catch (SQLException e) {
      return null;
    }
  }

  /**
   * Returns the ordered meals and their associated food items for the given time slot.
   * @param slot the time slot
   * @return a list of ordered meals and their associated food items, or null if an error occurs while retrieving the data from the database
   */
  public List<List<FoodItem>> getOrderedMealsFoodItems(TimeSlot slot) {
    try {
      return DatabaseManager.getInstance().getOrderedMealsFoodItems(slot);
    } catch (SQLException e) {
      return null;
    }
  }

  /**
   * Returns the previous meals.
   * @return the previous meals, or null if an error occurs while retrieving the data from the database
   */
  public List<Meal> getPreviousMeals() {
    try {
      return DatabaseManager.getInstance().getPreviousMeals();
    } catch (SQLException e) {
      return null;
    }
  }

  /**
   * Returns the reviews for the given meal.
   * @param meal the meal
   * @return the reviews for the meal, or null if an error occurs while retrieving the reviews from the database
   */
  public List<Review> getReviews(Meal meal) {
    try {
      return DatabaseManager.getInstance().getReviews(meal);
    } catch (SQLException e) {
      return null;
    }
  }

  /**
   * Returns the food items for the given meal.
   * @param meal the meal
   * @return the food items for the meal, or null if an error occurs while retrieving the food items from the database
   */
  public List<FoodItem> getFoodItems(Meal meal) {
    try {
      return DatabaseManager.getInstance().getFoodItems(meal);
    } catch (SQLException e) {
      return null;
    }
  }

  /**
   * Validates the given ticket.
   * @param id the ID of the ticket
   * @return the result of the validation, or null if an error occurs while validating the ticket in the database
   */
  public TicketValidationResult validateTicket(int id) {
    try {
      return DatabaseManager.getInstance().validateTicket(id);
    } catch (SQLException e) {
      return null;
    }
  }

  /**
   * Inserts a new meal into the database.
   * @param mealPeriod the meal period (e.g. breakfast, lunch, dinner)
   * @param date the date of the meal
   * @return the result of the insertion, or null if an error occurs while inserting the meal into the database
   */
  public MealInsertionResult insertMeal(MealPeriod mealPeriod,
                                        LocalDate date) {
    try {
      return DatabaseManager.getInstance().insertMeal(mealPeriod, date);
    } catch (SQLException e) {
      return null;
    }
  }

  /**
   * Inserts a new food item for the given meal into the database.
   * @param meal the meal
   * @param price the price of the food item
   * @param description a description of the food item
   * @return the result of the insertion, or null if an error occurs while inserting the food item into the database
   */
  public MealFoodItemInsertionResult insertFoodItem(Meal meal, float price,
                                                    String description) {
    try {
      return DatabaseManager.getInstance().insertFoodItem(meal, price, description);
    } catch (SQLException e) {
      return null;
    }
  }

  /**
   * Clears the food items in the specified meal.
   * @param meal the meal whose food items will be cleared.
   * @return a MealFoodItemsClearingResult instance indicating the success or failure of the operation.
   */
  public MealFoodItemsClearingResult clearFoodItems(Meal meal) {
    try {
      return DatabaseManager.getInstance().clearFoodItems(meal);
    } catch (SQLException e) {
      return null;
    }
  }

  /**
   * Configures the capacity for a given time slot.
   * @param slot The time slot to configure the capacity for.
   * @param capacity The capacity to set for the time slot.
   * @return A result object indicating whether the configuration was successful.
   * @return
   */
  public TimeSlotCapacityConfiguringResult configureCapacity(TimeSlot slot,
                                                             int capacity) {
    try {
      return DatabaseManager.getInstance().configureCapacity(slot, capacity);
    } catch (SQLException e) {
      return null;
    }
  }
}
