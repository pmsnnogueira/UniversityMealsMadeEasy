package university_meals_made_easy.back_office.model.fsm.state;

import university_meals_made_easy.back_office.model.data.DataManager;
import university_meals_made_easy.back_office.model.data.result.MealFoodItemInsertionResult;
import university_meals_made_easy.back_office.model.data.result.MealFoodItemsClearingResult;
import university_meals_made_easy.back_office.model.data.result.MealInsertionResult;
import university_meals_made_easy.back_office.model.fsm.State;
import university_meals_made_easy.database.tables.Meal.Meal;
import university_meals_made_easy.database.tables.Meal.MealPeriod;

import java.time.LocalDate;

/**
 * MealInsertionState class
 * this state is used to insert meals
 */
public class MealInsertionState extends StateAdapter {
  /**
   * MealInsertionState Constructor
   * @param dataManager
   * @param context
   */
  public MealInsertionState(DataManager dataManager, Context context) {
    super(dataManager, context);
  }

  /**
   * @return state
   */
  @Override
  public State getState() {
    return State.MEAL_INSERTION;
  }
  @Override
  public MealInsertionResult insertMeal(MealPeriod mealPeriod, LocalDate date) {
    return dataManager.insertMeal(mealPeriod, date);
  }
  @Override
  public MealFoodItemInsertionResult insertFoodItem(Meal meal, float price, String description) {
    return dataManager.insertFoodItem(meal, price, description);
  }
  @Override
  public MealFoodItemsClearingResult clearFoodItems(Meal meal) {
    return dataManager.clearFoodItems(meal);
  }
}
