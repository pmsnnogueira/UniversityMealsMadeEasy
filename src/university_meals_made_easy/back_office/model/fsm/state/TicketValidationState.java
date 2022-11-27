package university_meals_made_easy.back_office.model.fsm.state;

import university_meals_made_easy.back_office.model.data.DataManager;
import university_meals_made_easy.back_office.model.fsm.State;

public class TicketValidationState extends StateAdapter {
  public TicketValidationState(DataManager dataManager, Context context)
      throws NullPointerException {
    super(dataManager, context);
  }
  @Override
  public State getState() {
    return State.TICKET_VALIDATION;
  }
}
