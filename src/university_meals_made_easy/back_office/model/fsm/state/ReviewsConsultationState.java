package university_meals_made_easy.back_office.model.fsm.state;

import university_meals_made_easy.back_office.model.data.DataManager;
import university_meals_made_easy.back_office.model.fsm.State;

public class ReviewsConsultationState extends StateAdapter {
  public ReviewsConsultationState(DataManager dataManager, Context context)
      throws NullPointerException {
    super(dataManager, context);
  }
  @Override
  public State getState() {
    return State.REVIEWS_CONSULTATION;
  }
}
