package university_meals_made_easy.back_office.model.fsm;

import university_meals_made_easy.back_office.model.data.result.*;
import university_meals_made_easy.database.tables.Meal.Meal;
import university_meals_made_easy.database.tables.Meal.MealPeriod;
import university_meals_made_easy.database.tables.TimeSlot;

import java.time.LocalDate;

/**
 * IState interface
 * contains all the methods used on the fsm
 */
public interface IState {
  /**
   * return current state
   * @return state
   */
  State getState();

  /**
   * change to configuration state
   */
  void changeToConfiguration();

  /**
   * change to main menu state
   */
  void changeToMainMenu();

  /**
   * change to meal insertion state
   */
  void changeToMealInsertion();

  /**
   * change to meal insertion state
   */
  void changeToOrderedMealsConsultation();

  /**
   * change to reviews consultation state
   */
  void changeToReviewsConsultation();

  /**
   * change to ticket validation consultation
   */
  void changeToTicketValidation();
  TicketValidationResult validateTicket(int id);
  MealInsertionResult insertMeal(MealPeriod mealPeriod, LocalDate date);
  MealFoodItemInsertionResult insertFoodItem(Meal meal, float price,
                                             String description);
  MealFoodItemsClearingResult clearFoodItems(Meal meal);
  TimeSlotCapacityConfiguringResult configureCapacity(TimeSlot slot,
                                                      int capacity);
}
