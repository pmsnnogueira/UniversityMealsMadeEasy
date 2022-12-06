package university_meals_made_easy.customer_application.model.fsm.state;

import university_meals_made_easy.customer_application.model.data.DataManager;
import university_meals_made_easy.customer_application.model.data.result.*;
import university_meals_made_easy.customer_application.model.fsm.IState;
import university_meals_made_easy.customer_application.model.fsm.State;
import university_meals_made_easy.database.tables.FoodItem;
import university_meals_made_easy.database.tables.Meal.Meal;
import university_meals_made_easy.database.tables.Meal.MealPeriod;
import university_meals_made_easy.database.tables.TimeSlot;
import university_meals_made_easy.database.tables.transaction.Ticket;

import java.time.LocalDate;
import java.util.List;

/**
 * Class that keeps the context for the different states
 * Has methods to change, get and manage states
 * @version 1.0
 */
public class Context {
  private final DataManager dataManager;
  private IState state;

  /**
   * Constructor of the class Context,
   * sets inicial state and datamanager
   * @param dataManager
   * @throws NullPointerException
   */
  public Context(DataManager dataManager) throws NullPointerException {
    if (dataManager == null)
      throw new NullPointerException("data manager cannot be null");
    this.dataManager = dataManager;
    state = new AuthenticationState(dataManager, this);
  }

  /**
   * Method for changing state
   * makes adequate checks and changes state according to the one recieved
   * @param state
   * @throws NullPointerException
   */
  void changeState(State state) throws NullPointerException {
    if (state == null)
      throw new NullPointerException("state cannot be null");
    this.state = state.getState(dataManager, this);
  }

  /**
   * @return
   */
  public State getState() {
    return state.getState();
  }

  /**
   * @param username
   * @return
   */
  public LoginResult login(String username) {
    return state.login(username);
  }

  /**
   * @return
   */
  public boolean logout() {
    return state.logout();
  }

  /**
   * @return
   */
  public boolean changeToMainMenu() {
    return state.changeToMainMenu();
  }

  /**
   * @return
   */
  public boolean changeToMealOrdering() {
    return state.changeToMealOrdering();
  }

  /**
   * @return
   */
  public boolean changeToMenuConsultation() {
    return state.changeToMenuConsultation();
  }

  /**
   * @return
   */
  public boolean changeToReviewal() {
    return state.changeToReviewal();
  }

  /**
   * @return
   */
  public boolean changeToTicketsConsultation() {
    return state.changeToTicketsConsultation();
  }

  /**
   * @return
   */
  public boolean changeToTransactionHistory() {
    return state.changeToTransactionHistory();
  }
  public BuyResult buy(TimeSlot slot, List<FoodItem> foodItems) {
    return state.buy(slot, foodItems);
  }
  public RefundResult refund(Ticket ticket) {
    return state.refund(ticket);
  }
  public ReviewResult review(Ticket ticket, int rating, String comment) {
    return state.review(ticket, rating, comment);
  }
  public BalanceTopOffResult topOffBalance(float value) {
    return state.topOffBalance(value);
  }
}
