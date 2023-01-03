package university_meals_made_easy.customer_application.model.fsm.state;

import university_meals_made_easy.customer_application.model.data.DataManager;
import university_meals_made_easy.customer_application.model.data.result.ReviewResult;
import university_meals_made_easy.customer_application.model.fsm.State;
import university_meals_made_easy.database.tables.transaction.Ticket;

/**
 * Class for Reviewal State
 * manages this state
 */
public class ReviewalState extends StateAdapter {
  /**
   * Constructor for Class ReviewalState
   * @param dataManager
   * @param context
   */
  public ReviewalState(DataManager dataManager, Context context) {
    super(dataManager, context);
  }
  @Override
  public ReviewResult review(Ticket ticket, int rating, String comment) {
    return dataManager.review(ticket, rating, comment);
  }
  /**
   * @return
   */
  @Override
  public State getState() {
    return State.REVIEWAL;
  }
}
