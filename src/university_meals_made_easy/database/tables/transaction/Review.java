package university_meals_made_easy.database.tables.transaction;

import university_meals_made_easy.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Review extends Transaction {
  private final int id;
  private final int appUserId;
  private final int ticketId;
  private final LocalDateTime dateTime;
  private final int rating;
  private final String comment;

  public Review(int id, int appUserId, int ticketId,
                String dateTimeString, int rating, String comment)
      throws NullPointerException, IllegalArgumentException {
    if (dateTimeString == null)
      throw new NullPointerException("date time string cannot be null");
    if (rating < 0)
      throw new IllegalArgumentException("rating must be 0 or positive");
    if (rating > 10)
      throw new IllegalArgumentException("rating must be less than 10");
    this.id = id;
    this.appUserId = appUserId;
    this.ticketId = ticketId;
    try {
      dateTime = LocalDateTime.parse(dateTimeString,
          Logger.dateTimeFormatter);
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException(
          "date time string must have valid format");
    }
    this.rating = rating;
    this.comment = comment;
  }
  public int getId() {
    return id;
  }
  public int getAppUserId() {
    return appUserId;
  }
  public int getTicketId() {
    return ticketId;
  }
  public LocalDateTime getDateTime() {
    return dateTime;
  }
  public String getDateTimeAsString() {
    return dateTime.format(Logger.dateTimeFormatter);
  }
  public int getRating() {
    return rating;
  }
  public String getComment() {
    return comment;
  }
}
