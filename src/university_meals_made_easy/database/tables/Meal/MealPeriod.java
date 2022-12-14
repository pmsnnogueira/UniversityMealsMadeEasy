package university_meals_made_easy.database.tables.Meal;

/**
 * Enum for mealPeriod
 */
public enum MealPeriod {
  LUNCH,
  DINNER;

  /**
   * MealPeriod returns the proper constant.
   * @param meal_period
   * @return
   */
  public static MealPeriod from(String meal_period) {
    return (switch (meal_period) {
      case "lunch" -> LUNCH;
      case "dinner" -> DINNER;
      default -> null;
    });
  }

  /**
   * Returns the constant in string type.
   * @return
   */
  @Override
  public String toString() {
    if (this == LUNCH)
      return "lunch";
    return "dinner";
  }
}
