package university_meals_made_easy.database.tables;

/**
 * The TicketFoodItem Class contains a specific ticket.
 * Has the methods to return information about the ticket.
 */
public class TicketFoodItem {
  private final int ticketId;
  private final int foodItemId;


  /**
   * Constructor for TicketFoodItem class
   * that receives some information about the ticket,
   * and assign them to the local variables.
   * @param ticketId
   * @param foodItemId
   */
  public TicketFoodItem(int ticketId, int foodItemId) {
    this.ticketId = ticketId;
    this.foodItemId = foodItemId;
  }

  /**
   * The getTicketId method returns an id for a specific ticket.
   * @return ticketId
   */
  public int getTicketId() {
    return ticketId;
  }

  /**
   * The getFoodItemId method returns an food item id
   * for a specific ticket.
   * @return foodItemId
   */
  public int getFoodItemId() {
    return foodItemId;
  }
}
