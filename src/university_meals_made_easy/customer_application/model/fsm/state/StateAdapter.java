package university_meals_made_easy.customer_application.model.fsm.state;

import university_meals_made_easy.customer_application.model.data.DataManager;
import university_meals_made_easy.customer_application.model.fsm.IState;
import university_meals_made_easy.customer_application.model.fsm.State;

/**
 * Abstract class that implements all methods of the state machine in a default manner
 * Implements a default for each state
 */
public abstract class StateAdapter implements IState {
  protected final DataManager dataManager;
  protected final Context context;

  /**
   * Constructor for class StateAdapter
   * Recieves relevant inputs and checks for their validity before setting them
   * @param dataManager
   * @param context
   * @throws NullPointerException
   */
  public StateAdapter(DataManager dataManager, Context context)
      throws NullPointerException {
    if (dataManager == null)
      throw new NullPointerException("data manager cannot be null");
    if (context == null)
      throw new NullPointerException("context cannot be null");
    this.dataManager = dataManager;
    this.context = context;
  }

  /**
   * Default method for login always returns flase
   * @param username
   * @return
   */
  @Override
  public boolean login(String username) {
    return false;
  }

  /**
   * Default method for logout
   * @return
   */
  @Override
  public boolean logout() {
    context.changeState(State.AUTHENTICATION);
    return true;
  }

  /**
   * Default method for changeToMainMenu
   * @return
   */
  @Override
  public boolean changeToMainMenu() {
    context.changeState(State.MAIN_MENU);
    return true;
  }

  /**
   * Default method for changeToMealOrdering
   * @return
   */
  @Override
  public boolean changeToMealOrdering() {
    context.changeState(State.MEAL_ORDERING);
    return true;
  }

  /**
   * Default method for changeToMenuConsultation
   * @return
   */
  @Override
  public boolean changeToMenuConsultation() {
    context.changeState(State.MENU_CONSULTATION);
    return true;
  }

  /**
   * Default method for changeToReviewal
   * @return
   */
  @Override
  public boolean changeToReviewal() {
    context.changeState(State.REVIEWAL);
    return true;
  }

  /**
   * Default method for changeToTicketsConsultation
   * @return
   */
  @Override
  public boolean changeToTicketsConsultation() {
    context.changeState(State.TICKETS_CONSULTATION);
    return true;
  }

  /**
   * Default method for changeToTransactionHistory
   * @return
   */
  @Override
  public boolean changeToTransactionHistory() {
    context.changeState(State.TRANSACTION_HISTORY);
    return true;
  }
}
