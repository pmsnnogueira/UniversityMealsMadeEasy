package university_meals_made_easy.back_office.model.fsm.state;

import university_meals_made_easy.back_office.model.data.DataManager;
import university_meals_made_easy.back_office.model.fsm.IState;
import university_meals_made_easy.back_office.model.fsm.State;

/**
 * Context class
 * Used to redirect all requests from model manager to the current state of
 * the fsm
 */
public class Context {
  private final DataManager dataManager;
  private IState state;

  /**
   * Context Constructor
   * @param dataManager
   * @throws NullPointerException
   */
  public Context(DataManager dataManager) throws NullPointerException {
    if (dataManager == null)
      throw new NullPointerException("data manager cannot be null");
    this.dataManager = dataManager;
    this.state = new MainMenuState(dataManager, this);
  }

  /**
   * Method used to change to specified state
   * @param state
   * @throws NullPointerException
   */
  void changeState(State state) throws NullPointerException {
    if (state == null)
      throw new NullPointerException("state cannot be null");
    this.state = state.getState(dataManager, this);
  }

  /**
   * @return current fsm state
   */
  public State getState() {
    return state.getState();
  }

  /**
   * Method to change to configuration state
   */
  public void changeToConfiguration() {
    state.changeToConfiguration();
  }

  /**
   * Method to change to main menu state
   */
  public void changeToMainMenu() {
    state.changeToMainMenu();
  }

  /**
   * Method to change to meals insertion state
   */
  public void changeToMealInsertion() {
    state.changeToMealInsertion();
  }

  /**
   * Method to change to ordered meals consultation state
   */
  public void changeToOrderedMealsConsultation() {
    state.changeToOrderedMealsConsultation();
  }

  /**
   * Method to change to reviews consultation state
   */
  public void changeToReviewsConsultation() {
    state.changeToReviewsConsultation();
  }

  /**
   * Method to change to ticket validation state
   */
  public void changeToTicketValidation() {
    state.changeToTicketValidation();
  }
}
