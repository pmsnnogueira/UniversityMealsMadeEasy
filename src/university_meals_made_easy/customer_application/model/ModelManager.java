package university_meals_made_easy.customer_application.model;

import university_meals_made_easy.customer_application.model.data.DataManager;
import university_meals_made_easy.customer_application.model.data.DatabaseManager;
import university_meals_made_easy.customer_application.model.data.result.*;
import university_meals_made_easy.customer_application.model.fsm.State;
import university_meals_made_easy.customer_application.model.fsm.state.Context;
import university_meals_made_easy.database.tables.FoodItem;
import university_meals_made_easy.database.tables.Meal.Meal;
import university_meals_made_easy.database.tables.Meal.MealPeriod;
import university_meals_made_easy.database.tables.TimeSlot;
import university_meals_made_easy.database.tables.transaction.Ticket;
import university_meals_made_easy.database.tables.transaction.Transaction;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Class to make the connection between the View and the data model
 * @version 1.0
 */
public class ModelManager {
  public static final String PROP_STATE = "state";
  private final DataManager dataManager;
  private final Context context;
  private final PropertyChangeSupport pcs;

  /**
   * Constructor for model manager, recieves datamanager and makes appropriate checks before setting the new context
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
   * Adds propertyChangeListner to current PropertyChangeSupport object
   * @param property
   * @param listener
   */
  public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
    pcs.addPropertyChangeListener(property, listener);
  }

  /**
   * This method is used to return the current state
   * @return state
   */
  public State getState() {
    return context.getState();
  }

  /**
   * This method is used to change the current state of fsm to login
   * @return result
   */
  public LoginResult login(String username) {
    LoginResult result = context.login(username);
    pcs.firePropertyChange(PROP_STATE, null, null);
    return result;
  }

  /**
   * This method is used to change the current state of fsm to logout
   * @return result
   */
  public boolean logout() {
    boolean result = context.logout();
    pcs.firePropertyChange(PROP_STATE, null, null);
    return result;
  }

  /**
   * This method is used to change the current state of fsm to MainMenu
   * @return result
   */
  public boolean changeToMainMenu() {
    boolean result =  context.changeToMainMenu();
    pcs.firePropertyChange(PROP_STATE, null, null);
    return result;
  }

  /**
   * This method is used to change the current state of fsm to MealOrdering
   * @return result
   */
  public boolean changeToMealOrdering() {
    boolean result =  context.changeToMealOrdering();
    pcs.firePropertyChange(PROP_STATE, null, null);
    return result;
  }

  /**
   * This method is used to change the current state of fsm to MenuConsultation
   * @return result
   */
  public boolean changeToMenuConsultation() {
    boolean result = context.changeToMenuConsultation();
    pcs.firePropertyChange(PROP_STATE, null, null);
    return result;
  }

  /**
   * This method is used to change the current state of fsm to ToReviewal
   * @return result
   */
  public boolean changeToReviewal() {
    boolean result =  context.changeToReviewal();
    pcs.firePropertyChange(PROP_STATE, null, null);
    return result;
  }

  /**
   * This method is used to change the current state of fsm to TicketsConsultation
   * @return result
   */
  public boolean changeToTicketsConsultation() {
    boolean result =  context.changeToTicketsConsultation();
    pcs.firePropertyChange(PROP_STATE, null, null);
    return result;
  }

  /**
   * This method is used to change the current state of fsm to TransactionHistory
   * @return result
   */
  public boolean changeToTransactionHistory() {
    boolean result = context.changeToTransactionHistory();
    pcs.firePropertyChange(PROP_STATE, null, null);
    return result;
  }
  public float getBalance() {
    return dataManager.getBalance();
  }
  public List<TimeSlot> getAvailableTimeSlots(Meal meal) {
    return dataManager.getAvailableTimeSlots(meal);
  }
  public List<FoodItem> getFoodItems(Meal meal) {
    return dataManager.getFoodItems(meal);
  }
  public List<Ticket> getTickets() {
    return dataManager.getTickets();
  }
  public List<FoodItem> getTicketItems(Ticket ticket) {
    return dataManager.getTicketItems(ticket);
  }
  public List<Transaction> getTransactionHistory() {
    return dataManager.getTransactionHistory();
  }
  public BuyResult buy(TimeSlot slot, List<FoodItem> foodItems) {
    return context.buy(slot, foodItems);
  }
  public RefundResult refund(Ticket ticket) {
    return context.refund(ticket);
  }
  public ReviewResult review(Ticket ticket, int rating,
                             String comment) {
    return context.review(ticket, rating, comment);
  }
  public BalanceTopOffResult topOffBalance(float value) {
    return context.topOffBalance(value);
  }
  public Meal getMeal(LocalDate date, MealPeriod period) {
    return dataManager.getMeal(date, period);
  }
}
