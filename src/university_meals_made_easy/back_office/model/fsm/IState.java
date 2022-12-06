package university_meals_made_easy.back_office.model.fsm;

import university_meals_made_easy.back_office.model.data.result.*;
import university_meals_made_easy.database.tables.Meal.Meal;
import university_meals_made_easy.database.tables.Meal.MealPeriod;
import university_meals_made_easy.database.tables.TimeSlot;

import java.time.LocalDate;

public interface IState {
  State getState();
  void changeToConfiguration();
  void changeToMainMenu();
  void changeToMealInsertion();
  void changeToOrderedMealsConsultation();
  void changeToReviewsConsultation();
  void changeToTicketValidation();
  TicketValidationResult validateTicket(int id);
  MealInsertionResult insertMeal(MealPeriod mealPeriod, LocalDate date);
  MealFoodItemInsertionResult insertFoodItem(Meal meal, float price,
                                             String description);
  MealFoodItemsClearingResult clearFoodItems(Meal meal);
  TimeSlotCapacityConfiguringResult configureCapacity(TimeSlot slot,
                                                      int capacity);
}
