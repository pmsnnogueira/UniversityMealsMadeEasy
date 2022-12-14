package university_meals_made_easy.database.tables.transaction;

import university_meals_made_easy.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
/**
 *This class encapsulates the necessary data that define a Review
 *
 */
public class Review extends Transaction {
  private final int id;
  private final int appUserId;
  private final int ticketId;
  private final LocalDateTime dateTime;
  private final int rating;
  private final String comment;

  /**
   * Constructor for Review class that
   * receives some information about the refund,
   * and assign them to the local variables.
   * @param id
   * @param appUserId
   * @param ticketId
   * @param dateTimeString
   * @param rating
   * @param comment
   * @throws NullPointerException
   * @throws IllegalArgumentException
   */
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

  /**
   *
   * @return the id of the refund itself
   */
  public int getId() {
    return id;
  }

  /**
   *
   * @return the id of the user
   */
  public int getAppUserId() {
    return appUserId;
  }

  /**
   *
   * @return the id of the ticker
   */
  public int getTicketId() {
    return ticketId;
  }

  /**
   *
   * @return the date of that the review was made
   */
  public LocalDateTime getDateTime() {
    return dateTime;
  }


  public String getDateTimeAsString() {
    return dateTime.format(Logger.dateTimeFormatter);
  }

  /**
   *
   * @return the rating of the review
   */
  public int getRating() {
    return rating;
  }

  /**
   *
   * @return the comment of the review
   */
  public String getComment() {
    return comment;
  }

  @Override
  public String toString() {
    return String.format("Reviewed meal: %d on %s", ticketId, getDateTimeAsString());
  }
}
