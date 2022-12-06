package university_meals_made_easy.customer_application.model.fsm;

import university_meals_made_easy.customer_application.model.data.DataManager;
import university_meals_made_easy.customer_application.model.fsm.state.*;

/**
 * Enum state class for state machine
 */
public enum State {
  AUTHENTICATION,
  MAIN_MENU,
  MEAL_ORDERING,
  MENU_CONSULTATION,
  TICKETS_CONSULTATION,
  REVIEWAL,
  TRANSACTION_HISTORY;

  /**
   * Returns proper enum
   * @param dataManager
   * @param context
   * @return
   * @throws NullPointerException
   */
  public IState getState(DataManager dataManager, Context context)
      throws NullPointerException {
    return (switch (this) {
      case AUTHENTICATION -> new AuthenticationState(dataManager, context);
      case MAIN_MENU -> new MainMenuState(dataManager, context);
      case MEAL_ORDERING -> new MealOrderingState(dataManager, context);
      case MENU_CONSULTATION ->
          new MenuConsultationState(dataManager, context);
      case TICKETS_CONSULTATION ->
          new TicketsConsultationState(dataManager, context);
      case REVIEWAL -> new ReviewalState(dataManager, context);
      case TRANSACTION_HISTORY ->
          new TransactionHistoryState(dataManager, context);
    });
  }
}
