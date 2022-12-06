package university_meals_made_easy.database.tables;

public class TicketFoodItem {
  private final int ticketId;
  private final int foodItemId;

  public TicketFoodItem(int ticketId, int foodItemId) {
    this.ticketId = ticketId;
    this.foodItemId = foodItemId;
  }
  public int getTicketId() {
    return ticketId;
  }
  public int getFoodItemId() {
    return foodItemId;
  }
}
