package university_meals_made_easy.database.tables;

/**
 * Enum that defines all the tables that exist
 * in our database
 */
public enum Table {
  MEAL,
  REFUND,
  REVIEW,
  TICKET,
  TOP_OFF,
  APP_USER,
  FOOD_ITEM,
  TICKET_FOOD_ITEM,
  TIME_SLOT;

  /**
   *
   * @return the enum that corresponds to a specific string
   */
  @Override
  public String toString() {
    return (switch (this) {
      case MEAL -> "meal";
      case REFUND -> "refund";
      case REVIEW -> "review";
      case TICKET -> "ticket";
      case TOP_OFF -> "top_off";
      case APP_USER -> "app_user";
      case FOOD_ITEM -> "food_item";
      case TICKET_FOOD_ITEM -> "ticket_food_item";
      case TIME_SLOT -> "time_slot";
    });
  }
}
