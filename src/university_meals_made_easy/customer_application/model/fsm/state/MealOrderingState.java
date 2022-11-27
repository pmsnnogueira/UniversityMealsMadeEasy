package university_meals_made_easy.customer_application.model.fsm.state;

import university_meals_made_easy.customer_application.model.data.DataManager;
import university_meals_made_easy.customer_application.model.fsm.State;

public class MealOrderingState extends StateAdapter {
  public MealOrderingState(DataManager dataManager, Context context) {
    super(dataManager, context);
  }
  @Override
  public State getState() {
    return State.MEAL_ORDERING;
  }
}
