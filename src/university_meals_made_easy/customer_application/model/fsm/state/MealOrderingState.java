package university_meals_made_easy.customer_application.model.fsm.state;

import university_meals_made_easy.customer_application.model.data.DataManager;
import university_meals_made_easy.customer_application.model.fsm.State;

/**
 * Class for MealOrdering State
 * manages this state
 */
public class MealOrderingState extends StateAdapter {
  /**
   * Constructor for Class MealOrderingState
   * @param dataManager
   * @param context
   */
  public MealOrderingState(DataManager dataManager, Context context) {
    super(dataManager, context);
  }

  /**
   * @return
   */
  @Override
  public State getState() {
    return State.MEAL_ORDERING;
  }
}
