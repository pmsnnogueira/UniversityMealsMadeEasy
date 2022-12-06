package university_meals_made_easy.back_office.model;

import university_meals_made_easy.back_office.model.data.DataManager;
import university_meals_made_easy.back_office.model.fsm.State;
import university_meals_made_easy.back_office.model.fsm.state.Context;
import university_meals_made_easy.database.tables.FoodItem;
import university_meals_made_easy.database.tables.Meal.Meal;
import university_meals_made_easy.database.tables.Meal.MealPeriod;
import university_meals_made_easy.database.tables.TimeSlot;
import university_meals_made_easy.database.tables.transaction.Review;

import java.time.LocalDate;
import java.util.List;

public class ModelManager {
  private final DataManager dataManager;
  private final Context context;

  public ModelManager(DataManager dataManager)
      throws NullPointerException {
    if (dataManager == null)
      throw new NullPointerException("data manager cannot be null");
    this.dataManager = dataManager;
    context = new Context(dataManager);
  }
  public State getState() {
    return context.getState();
  }
  public void changeToConfiguration() {
    context.changeToConfiguration();
  }
  public void changeToMainMenu() {
    context.changeToMainMenu();
  }
  public void changeToMealInsertion() {
    context.changeToMealInsertion();
  }
  public void changeToOrderedMealsConsultation() {
    context.changeToOrderedMealsConsultation();
  }
  public void changeToReviewsConsultation() {
    context.changeToReviewsConsultation();
  }
  public void changeToTicketValidation() {
    context.changeToTicketValidation();
  }
  public Meal getMeal(LocalDate date, MealPeriod period) {
    return dataManager.getMeal(date, period);
  }
  public List<TimeSlot> getTimeSlots(Meal meal) {
    return dataManager.getTimeSlots(meal);
  }
  public List<List<FoodItem>> getOrderedMealsFoodItems(TimeSlot slot) {
    return dataManager.getOrderedMealsFoodItems(slot);
  }
  public List<Meal> getPreviousMeals() {
    return dataManager.getPreviousMeals();
  }
  public List<Review> getReviews(Meal meal) {
    return dataManager.getReviews(meal);
  }
  public List<FoodItem> getFoodItems(Meal meal) {
    return dataManager.getFoodItems(meal);
  }
}
