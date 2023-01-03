package university_meals_made_easy.back_office.model.fsm;

import university_meals_made_easy.back_office.model.data.DataManager;
import university_meals_made_easy.back_office.model.fsm.state.*;

/**
 * Enum with all fsm state values
 */
public enum State {
  MAIN_MENU,
  TICKET_VALIDATION,
  ORDERED_MEALS_CONSULTATION,
  REVIEWS_CONSULTATION,
  MEAL_INSERTION,
  CONFIGURATION;

  /**
   * Factory for creating new states
   * @param dataManager
   * @param context
   * @return
   * @throws NullPointerException
   */
  public IState getState(DataManager dataManager, Context context)
      throws NullPointerException {
    return (switch (this) {
      case MAIN_MENU -> new MainMenuState(dataManager, context);
      case TICKET_VALIDATION -> new TicketValidationState(dataManager, context);
      case ORDERED_MEALS_CONSULTATION ->
          new OrderedMealsConsultationState(dataManager, context);
      case REVIEWS_CONSULTATION ->
          new ReviewsConsultationState(dataManager, context);
      case MEAL_INSERTION -> new MealInsertionState(dataManager, context);
      case CONFIGURATION -> new ConfigurationState(dataManager, context);
    });
  }
}
