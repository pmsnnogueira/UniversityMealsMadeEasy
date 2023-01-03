package university_meals_made_easy.customer_application.model.fsm.state;

import university_meals_made_easy.customer_application.model.data.DataManager;
import university_meals_made_easy.customer_application.model.data.result.BuyResult;
import university_meals_made_easy.customer_application.model.fsm.State;
import university_meals_made_easy.database.tables.FoodItem;
import university_meals_made_easy.database.tables.TimeSlot;

import java.util.List;

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
  @Override
  public BuyResult buy(TimeSlot slot, List<FoodItem> foodItems) {
    return dataManager.buy(slot, foodItems);
  }
  /**
   * @return
   */
  @Override
  public State getState() {
    return State.MEAL_ORDERING;
  }
}
