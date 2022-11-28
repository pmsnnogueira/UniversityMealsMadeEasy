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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ModelManager {
  public static final String PROP_STATE = "state";

  private final DataManager dataManager;
  private final Context context;
  private final PropertyChangeSupport pcs;

  public ModelManager(DataManager dataManager)
      throws NullPointerException {
    if (dataManager == null)
      throw new NullPointerException("data manager cannot be null");
    this.dataManager = dataManager;
    context = new Context(dataManager);
    pcs = new PropertyChangeSupport(this);
  }

  public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
    pcs.addPropertyChangeListener(property, listener);
  }
  public State getState() {
    return context.getState();
  }
  public void changeToConfiguration() {
    context.changeToConfiguration();
    pcs.firePropertyChange(PROP_STATE,null, null);
  }
  public void changeToMainMenu() {
    context.changeToMainMenu();
    pcs.firePropertyChange(PROP_STATE,null, null);
  }
  public void changeToMealInsertion() {
    context.changeToMealInsertion();
    pcs.firePropertyChange(PROP_STATE,null, null);
  }
  public void changeToOrderedMealsConsultation() {
    context.changeToOrderedMealsConsultation();
    pcs.firePropertyChange(PROP_STATE,null, null);
  }
  public void changeToReviewsConsultation() {
    context.changeToReviewsConsultation();
    pcs.firePropertyChange(PROP_STATE,null, null);
  }
  public void changeToTicketValidation() {
    context.changeToTicketValidation();
    pcs.firePropertyChange(PROP_STATE,null, null);
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
