package university_meals_made_easy.back_office.model.fsm.state;

import university_meals_made_easy.back_office.model.data.DataManager;
import university_meals_made_easy.back_office.model.data.result.TicketValidationResult;
import university_meals_made_easy.back_office.model.fsm.State;

/**
 * TicketValidationState class
 * this class is used to validate meal tickets using the ticket id
 */
public class TicketValidationState extends StateAdapter {
  /**
   * TicketValidationState Constructor
   * @param dataManager
   * @param context
   */
  public TicketValidationState(DataManager dataManager, Context context) {
    super(dataManager, context);
  }

  /**
   * @return state
   */
  @Override
  public State getState() {
    return State.TICKET_VALIDATION;
  }
  @Override
  public TicketValidationResult validateTicket(int id) {
    return dataManager.validateTicket(id);
  }
}
