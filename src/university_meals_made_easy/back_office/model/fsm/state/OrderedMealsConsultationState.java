package university_meals_made_easy.back_office.model.fsm.state;

import university_meals_made_easy.back_office.model.data.DataManager;
import university_meals_made_easy.back_office.model.fsm.State;

/**
 * OrderedMealsConsultationState class
 * this method is used to get information about all the ordered meals
 */
public class OrderedMealsConsultationState extends StateAdapter {
  /**
   * OrderedMealsConsultationState Constructor
   * @param dataManager
   * @param context
   */
  public OrderedMealsConsultationState(DataManager dataManager,
                                       Context context) {
    super(dataManager, context);
  }

  /**
   * @return state
   */
  @Override
  public State getState() {
    return State.ORDERED_MEALS_CONSULTATION;
  }
}
