package university_meals_made_easy.back_office.model.fsm.state;

import university_meals_made_easy.back_office.model.data.DataManager;
import university_meals_made_easy.back_office.model.fsm.State;

public class MealInsertionState extends StateAdapter {
  public MealInsertionState(DataManager dataManager, Context context) {
    super(dataManager, context);
  }
  @Override
  public State getState() {
    return State.MEAL_INSERTION;
  }
}
