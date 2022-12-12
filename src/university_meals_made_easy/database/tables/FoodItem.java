package university_meals_made_easy.database.tables;

public class FoodItem {
  private final int id;
  private final int mealId;
  private final float price;
  private final String description;

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
  public int getId() {
    return id;
  }
  public int getMealId() {
    return mealId;
  }
  public float getPrice() {
    return price;
  }
  public String getDescription() {
    return description;
  }

  @Override
  public String toString() {
    return String.format("%s - %.2f €",description, price);
  }
}
