package university_meals_made_easy.database.tables.Meal;

public enum MealPeriod {
  LUNCH,
  DINNER;

  public static MealPeriod from(String meal_period) {
    return (switch (meal_period) {
      case "lunch" -> LUNCH;
      case "dinner" -> DINNER;
      default -> null;
    });
  }
  @Override
  public String toString() {
    if (this == LUNCH)
      return "lunch";
    return "dinner";
  }
}
