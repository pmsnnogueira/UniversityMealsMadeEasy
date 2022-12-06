package university_meals_made_easy.back_office.model.fsm.state;

import university_meals_made_easy.back_office.model.data.DataManager;
import university_meals_made_easy.back_office.model.data.result.*;
import university_meals_made_easy.back_office.model.fsm.IState;
import university_meals_made_easy.back_office.model.fsm.State;
import university_meals_made_easy.database.tables.Meal.Meal;
import university_meals_made_easy.database.tables.Meal.MealPeriod;
import university_meals_made_easy.database.tables.TimeSlot;

import java.time.LocalDate;

public abstract class StateAdapter implements IState {
  protected final DataManager dataManager;
  protected final Context context;

  public StateAdapter(DataManager dataManager, Context context)
      throws NullPointerException {
    if (dataManager == null)
      throw new NullPointerException("data manager cannot be null");
    if (context == null)
      throw new NullPointerException("context cannot be null");
    this.dataManager = dataManager;
    this.context = context;
  }
  @Override
  public State getState() {
    return null;
  }
  @Override
  public void changeToConfiguration() {
    context.changeState(State.CONFIGURATION);
  }
  @Override
  public void changeToMainMenu() {
    context.changeState(State.MAIN_MENU);
  }
  @Override
  public void changeToMealInsertion() {
    context.changeState(State.MEAL_INSERTION);
  }
  @Override
  public void changeToOrderedMealsConsultation() {
    context.changeState(State.ORDERED_MEALS_CONSULTATION);
  }
  @Override
  public void changeToReviewsConsultation() {
    context.changeState(State.REVIEWS_CONSULTATION);
  }
  @Override
  public void changeToTicketValidation() {
    context.changeState(State.TICKET_VALIDATION);
  }
  @Override
  public TicketValidationResult validateTicket(int id) {
    return null;
  }
  @Override
  public MealInsertionResult insertMeal(MealPeriod mealPeriod, LocalDate date) {
    return null;
  }
  @Override
  public MealFoodItemInsertionResult insertFoodItem(Meal meal, float price, String description) {
    return null;
  }
  @Override
  public MealFoodItemsClearingResult clearFoodItems(Meal meal) {
    return null;
  }
  @Override
  public TimeSlotCapacityConfiguringResult configureCapacity(TimeSlot slot, int capacity) {
    return null;
  }
}
