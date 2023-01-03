package university_meals_made_easy.back_office.model.fsm.state;

import university_meals_made_easy.back_office.model.data.DataManager;
import university_meals_made_easy.back_office.model.fsm.State;

/**
 * ReviewsConsultationState class
 * This class is used to see all meal reviews that were made by customers
 */
public class ReviewsConsultationState extends StateAdapter {
  /**
   * ReviewsConsultationState Constructor
   * @param dataManager
   * @param context
   */
  public ReviewsConsultationState(DataManager dataManager, Context context) {
    super(dataManager, context);
  }

  /**
   * @return state
   */
  @Override
  public State getState() {
    return State.REVIEWS_CONSULTATION;
  }
}
