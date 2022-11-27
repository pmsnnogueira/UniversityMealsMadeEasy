package university_meals_made_easy.back_office.model;

import university_meals_made_easy.back_office.model.data.DataManager;
import university_meals_made_easy.back_office.model.fsm.state.Context;

public class ModelManager {
  private final DataManager dataManager;
  private final Context context;

  public ModelManager(DataManager dataManager)
      throws NullPointerException {
    if (dataManager == null)
      throw new NullPointerException("data manager cannot be null");
    this.dataManager = dataManager;
    context = new Context(dataManager);
  }
  public void changeToConfiguration() {
    context.changeToConfiguration();
  }
  public void changeToMainMenu() {
    context.changeToMainMenu();
  }
  public void changeToMealInsertion() {
    context.changeToMealInsertion();
  }
  public void changeToOrderedMealsConsultation() {
    context.changeToOrderedMealsConsultation();
  }
  public void changeToReviewsConsultation() {
    context.changeToReviewsConsultation();
  }
  public void changeToTicketValidation() {
    context.changeToTicketValidation();
  }
}
