package university_meals_made_easy.customer_application.model.fsm.state;

import university_meals_made_easy.customer_application.model.data.DataManager;
import university_meals_made_easy.customer_application.model.fsm.State;

public class TicketsConsultationState extends StateAdapter {
  public TicketsConsultationState(DataManager dataManager, Context context)
      throws NullPointerException {
    super(dataManager, context);
  }
  @Override
  public State getState() {
    return State.TICKETS_CONSULTATION;
  }
}
