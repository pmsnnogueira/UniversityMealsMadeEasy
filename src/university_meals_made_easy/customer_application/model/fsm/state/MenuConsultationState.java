package university_meals_made_easy.customer_application.model.fsm.state;

import university_meals_made_easy.customer_application.model.data.DataManager;
import university_meals_made_easy.customer_application.model.fsm.State;

public class MenuConsultationState extends StateAdapter {
  public MenuConsultationState(DataManager dataManager, Context context)
      throws NullPointerException {
    super(dataManager, context);
  }
  @Override
  public State getState() {
    return State.MENU_CONSULTATION;
  }
}
