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

public class DataManager {
  public Meal getMeal(LocalDate date, MealPeriod period) {
    try {
      return DatabaseManager.getInstance().getMeal(date, period);
    } catch (SQLException e) {
      return null;
    }
  }
  public List<TimeSlot> getTimeSlots(Meal meal) {
    try {
      return DatabaseManager.getInstance().getTimeSlots(meal);
    } catch (SQLException e) {
      return null;
    }
  }
  public List<List<FoodItem>> getOrderedMealsFoodItems(TimeSlot slot) {
    try {
      return DatabaseManager.getInstance().getOrderedMealsFoodItems(slot);
    } catch (SQLException e) {
      return null;
    }
  }
  public List<Meal> getPreviousMeals() {
    try {
      return DatabaseManager.getInstance().getPreviousMeals();
    } catch (SQLException e) {
      return null;
    }
  }
  public List<Review> getReviews(Meal meal) {
    try {
      return DatabaseManager.getInstance().getReviews(meal);
    } catch (SQLException e) {
      return null;
    }
  }
  public List<FoodItem> getFoodItems(Meal meal) {
    try {
      return DatabaseManager.getInstance().getFoodItems(meal);
    } catch (SQLException e) {
      return null;
    }
  }
  public TicketValidationResult validateTicket(int id) {
    try {
      return DatabaseManager.getInstance().validateTicket(id);
    } catch (SQLException e) {
      return null;
    }
  }
  public MealInsertionResult insertMeal(MealPeriod mealPeriod,
                                        LocalDate date) {
    try {
      return DatabaseManager.getInstance().insertMeal(mealPeriod, date);
    } catch (SQLException e) {
      return null;
    }
  }
  public MealFoodItemInsertionResult insertFoodItem(Meal meal, float price,
                                                    String description) {
    try {
      return DatabaseManager.getInstance().insertFoodItem(meal, price, description);
    } catch (SQLException e) {
      return null;
    }
  }
  public MealFoodItemsClearingResult clearFoodItems(Meal meal) {
    try {
      return DatabaseManager.getInstance().clearFoodItems(meal);
    } catch (SQLException e) {
      return null;
    }
  }
  public TimeSlotCapacityConfiguringResult configureCapacity(TimeSlot slot,
                                                             int capacity) {
    try {
      return DatabaseManager.getInstance().configureCapacity(slot, capacity);
    } catch (SQLException e) {
      return null;
    }
  }
}
