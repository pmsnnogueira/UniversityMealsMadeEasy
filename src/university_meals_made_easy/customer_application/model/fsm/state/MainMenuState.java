package university_meals_made_easy.customer_application.model.fsm.state;

import university_meals_made_easy.customer_application.model.data.DataManager;
import university_meals_made_easy.customer_application.model.fsm.State;

/**
 * Class for MainMenu State
 * manages this state
 * @version 1.0
 */
public class MainMenuState extends StateAdapter {
  /**
   * Constructor for Class MainMenuState
   * @param dataManager
   * @param context
   */
  public MainMenuState(DataManager dataManager, Context context) {
    super(dataManager, context);
  }

  /**
   * This method returns a State
   * @return state
   */
  @Override
  public State getState() {
    return State.MAIN_MENU;
  }
}
