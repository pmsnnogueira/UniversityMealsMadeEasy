package university_meals_made_easy.customer_application.model.fsm;

import university_meals_made_easy.customer_application.model.data.DataManager;
import university_meals_made_easy.customer_application.model.fsm.state.*;

public enum State {
  AUTHENTICATION,
  MAIN_MENU,
  MEAL_ORDERING,
  MENU_CONSULTATION,
  TICKETS_CONSULTATION,
  REVIEWAL,
  TRANSACTION_HISTORY;

  public IState getState(DataManager dataManager, Context context) {
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
