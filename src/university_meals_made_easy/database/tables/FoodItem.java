package university_meals_made_easy.database.tables;

/**
 * The FoodItem Class contains a specific food item.
 * Has the methods to return information about the food item.
 */
public class FoodItem {
  private final int id;
  private final int mealId;
  private final float price;
  private final String description;

  /**
   * Constructor for FoodItem class that receives
   * some information about the food item,
   * and assign them to the local variables
   * @param id
   * @param mealId
   * @param price
   * @param description
   * @throws NullPointerException
   * @throws IllegalArgumentException
   */
  public FoodItem(int id, int mealId, float price, String description)
      throws NullPointerException, IllegalArgumentException {
    if (description == null)
      throw new NullPointerException("description cannot be null");
    if (price < 0)
      throw new IllegalArgumentException("price cannot be null");
    this.id = id;
    this.mealId = mealId;
    this.price = price;
    this.description = description;
  }

  /**
   * The getId method returns an id for a specific food item.
   * @return id
   */
  public int getId() {
    return id;
  }

  /**
   * The getMealId method returns a mealId for the specific meal item.
   * @return mealId
   */
  public int getMealId() {
    return mealId;
  }

  /**
   * The getPrice method returns the price for a specific food item.
   * @return price
   */
  public float getPrice() {
    return price;
  }

  /**
   * The getDescription method returns the description for a specific food item.
   * @return description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Return the description and price in string type.
   * @return
   */
  @Override
  public String toString() {
    return String.format("%s - %.2f €",description, price);
  }
}
