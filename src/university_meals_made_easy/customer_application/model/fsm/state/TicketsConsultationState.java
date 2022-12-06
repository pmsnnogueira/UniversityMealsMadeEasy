package university_meals_made_easy.customer_application.model.fsm.state;

import university_meals_made_easy.customer_application.model.data.DataManager;
import university_meals_made_easy.customer_application.model.fsm.State;

/**
 * Class for TicketsConsultation State
 * manages this state
 */
public class TicketsConsultationState extends StateAdapter {
  /**
   * Constructor for Class TicketsConsultationState
   * @param dataManager
   * @param context
   */
  public TicketsConsultationState(DataManager dataManager, Context context) {
    super(dataManager, context);
  }

  /**
   * @return
   */
  @Override
  public State getState() {
    return State.TICKETS_CONSULTATION;
  }
}
