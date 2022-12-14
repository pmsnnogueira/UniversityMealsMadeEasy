package university_meals_made_easy.back_office.model;

import university_meals_made_easy.back_office.model.data.DataManager;
import university_meals_made_easy.back_office.model.data.result.*;
import university_meals_made_easy.back_office.model.fsm.State;
import university_meals_made_easy.back_office.model.fsm.state.Context;
import university_meals_made_easy.database.tables.FoodItem;
import university_meals_made_easy.database.tables.Meal.Meal;
import university_meals_made_easy.database.tables.Meal.MealPeriod;
import university_meals_made_easy.database.tables.TimeSlot;
import university_meals_made_easy.database.tables.transaction.Review;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;
import java.util.List;

/**
 * A manager class that coordinates the interactions between the data manager and the context object.
 * This class is responsible for managing the current state of the finite state machine (FSM), and for
 * providing the necessary methods for the UI to change the state of the FSM. It also provides methods
 * for querying the data manager and getting information about meals, time slots, and ordered meals.
 */
public class ModelManager {
  public static final String PROP_STATE = "state";

  private final DataManager dataManager;
  private final Context context;
  private final PropertyChangeSupport pcs;

  /**
   * ModelManager Constructor
   * @param dataManager
   * @throws NullPointerException
   */
  public ModelManager(DataManager dataManager)
      throws NullPointerException {
    if (dataManager == null)
      throw new NullPointerException("data manager cannot be null");
    this.dataManager = dataManager;
    context = new Context(dataManager);
    pcs = new PropertyChangeSupport(this);
  }

  /**
   * This method is used to add new listeners to the property change support
   * @param property
   * @param listener
   */
  public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
    pcs.addPropertyChangeListener(property, listener);
  }

  /**
   * This method returns the current state of the fsm
   * @return state
   */
  public State getState() {
    return context.getState();
  }

  /**
   * This method is used to change the current state of fsm to Configuration
   */
  public void changeToConfiguration() {
    context.changeToConfiguration();
    pcs.firePropertyChange(PROP_STATE,null, null);
  }

  /**
   * This method is used to change the current state of fsm to MainMenu
   */
  public void changeToMainMenu() {
    context.changeToMainMenu();
    pcs.firePropertyChange(PROP_STATE,null, null);
  }

  /**
   * This method is used to change the current state of fsm to MealInsertion
   */
  public void changeToMealInsertion() {
    context.changeToMealInsertion();
    pcs.firePropertyChange(PROP_STATE,null, null);
  }

  /**
   * This method is used to change the current state of fsm to MealsConsultation
   */
  public void changeToOrderedMealsConsultation() {
    context.changeToOrderedMealsConsultation();
    pcs.firePropertyChange(PROP_STATE,null, null);
  }

  /**
   * This method is used to change the current state of fsm to ReviewsConsultation
   */
  public void changeToReviewsConsultation() {
    context.changeToReviewsConsultation();
    pcs.firePropertyChange(PROP_STATE,null, null);
  }

  /**
   * This method is used to change the current state of fsm to TicketValidation
   */
  public void changeToTicketValidation() {
    context.changeToTicketValidation();
    pcs.firePropertyChange(PROP_STATE,null, null);
  }
  /**
   * Returns the meal for the given date and period.
   *
   * @param date the date of the meal
   * @param period the period of the meal
   * @return the meal for the given date and period
   */
  public Meal getMeal(LocalDate date, MealPeriod period) {
    return dataManager.getMeal(date, period);
  }
  /**
   * Returns the time slots for the given meal.
   *
   * @param meal the meal to get time slots for
   * @return a list of time slots for the given meal
   */
  public List<TimeSlot> getTimeSlots(Meal meal) {
    return dataManager.getTimeSlots(meal);
  }

  /**
   * Returns a list of ordered meal food items for the given time slot.
   */
  public List<List<FoodItem>> getOrderedMealsFoodItems(TimeSlot slot) {
    return dataManager.getOrderedMealsFoodItems(slot);
  }

  /**
   * Returns the previous meals.
   *
   * @return a list of previous meals
   */
  public List<Meal> getPreviousMeals() {
    return dataManager.getPreviousMeals();
  }
  /**
   * Returns the reviews for the given meal.
   *
   * @param meal the meal to get reviews for
   * @return a list of reviews for the given meal
   */

  /**
   * Gets a list of reviews for a given meal.
   * @param meal
   * @return a list of reviews for the meal.
   */
  public List<Review> getReviews(Meal meal) {
    return dataManager.getReviews(meal);
  }

  /**
   * Gets a list of food items for a given meal.
   * @param meal
   * @return a list of food items for the meal.
   */
  public List<FoodItem> getFoodItems(Meal meal) {
    return dataManager.getFoodItems(meal);
  }

  /**
   * Validates a ticket with the given ID.
   * @param id
   * @return a result object indicating whether the ticket is valid.
   */
  public TicketValidationResult validateTicket(int id) {
    return context.validateTicket(id);
  }

  /**
   * Inserts a meal for the given meal period and date.
   * @param mealPeriod
   * @param date
   * @return a result object indicating whether the meal was inserted successfully.
   */
  public MealInsertionResult insertMeal(MealPeriod mealPeriod,
                                        LocalDate date) {
    return context.insertMeal(mealPeriod, date);
  }

  /**
   * Inserts a food item for the given meal with the specified price and description.
   * @param meal
   * @param price
   * @param description
   * @return a result object indicating whether the food item was inserted successfully.
   */
  public MealFoodItemInsertionResult insertFoodItem(Meal meal, float price,
                                                    String description) {
    return context.insertFoodItem(meal, price, description);
  }

  /**
   * Clears the food items for the given meal.
   * @param meal
   * @return a result object indicating whether the food items were cleared successfully.
   */
  public MealFoodItemsClearingResult clearFoodItems(Meal meal) {
    return context.clearFoodItems(meal);
  }

  /**
   * Configures the capacity for a given time slot.
   * @param slot
   * @param capacity
   * @return a result object indicating whether the configuration was successful.
   */
  public TimeSlotCapacityConfiguringResult configureCapacity(TimeSlot slot,
                                                             int capacity) {
    TimeSlotCapacityConfiguringResult result = context.configureCapacity(slot, capacity);
    pcs.firePropertyChange(PROP_STATE, null, null);
    return result;
  }
}
