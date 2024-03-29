package university_meals_made_easy.customer_application.model.fsm.state;

import university_meals_made_easy.customer_application.model.data.DataManager;
import university_meals_made_easy.customer_application.model.fsm.State;

/**
 * Class for TransactionHistory State
 * manages this state
 */
public class TransactionHistoryState extends StateAdapter {
  /**
   * Constructor for Class TransactionHistoryState
   * @param dataManager
   * @param context
   */
  public TransactionHistoryState(DataManager dataManager, Context context) {
    super(dataManager, context);
  }

  /**
   * @return
   */
  @Override
  public State getState() {
    return State.TRANSACTION_HISTORY;
  }
}
