package university_meals_made_easy.customer_application.model.fsm.state;

import university_meals_made_easy.customer_application.model.data.DataManager;
import university_meals_made_easy.customer_application.model.fsm.State;

/**
 * Class that manages user authentication state
 * State machine that extends StateAdapter and overrides multiple functions for the purpose of managing the login state
 * @version 1.0
 */
public class AuthenticationState extends StateAdapter {
  public AuthenticationState(DataManager dataManager, Context context) {
    super(dataManager, context);
  }

  /**
   * Method to return current state of authentication
   * @return
   */
  @Override
  public State getState() {
    return State.AUTHENTICATION;
  }

  /**
   * Method to keep track of the state when login is executed
   * @param username
   * @return
   * @throws NullPointerException
   */
  @Override
  public boolean login(String username) throws NullPointerException {
    if (username == null)
      throw new NullPointerException("username cannot be null");
    if (dataManager.login(username)) {
      context.changeState(State.MAIN_MENU);
      return true;
    }
    return false;
  }

  /**
   * Method to keep track of the state when logout is executed
   * @return
   */
  @Override
  public boolean logout() {
    return false;
  }

  /**
   * Method to change to Main Menu
   * Always returns false
   * @return
   */
  @Override
  public boolean changeToMainMenu() {
    return false;
  }

  /**
   * Method to change to Meal Ordering Menu
   * Always returns false
   * @return
   */
  @Override
  public boolean changeToMealOrdering() {
    return false;
  }

  /**
   * Method to change to Consultation Menu
   * Always returns false
   * @return
   */
  @Override
  public boolean changeToMenuConsultation() {
    return false;
  }

  /**
   * Method to change to Review Meal Menu
   * Always returns false
   * @return
   */
  @Override
  public boolean changeToReviewal() {
    return false;
  }

  /**
   * Method to change to Ticket Consultation Menu
   * Always returns false
   * @return
   */
  @Override
  public boolean changeToTicketsConsultation() {
    return false;
  }

  /**
   * Method to change to Transaction History Menu
   * Always returns false
   * @return
   */
  @Override
  public boolean changeToTransactionHistory() {
    return false;
  }
}
