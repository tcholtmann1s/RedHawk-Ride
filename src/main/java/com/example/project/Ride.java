package com.example.project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

public class Ride {
  private double duration = 0.0;
  private Location initialLocation;
  private Location finalDestination;
  private Student driver;
  private Student rider;
  private Instant startTime;
  private Instant requestTime;
  private Instant endTime;

  public String getStartTime() {
    return DateTimeFormatter.ISO_INSTANT
        .format(startTime.truncatedTo(ChronoUnit.SECONDS))
        .replaceAll("[TZ]", " ");
  }

  public String getEndTime() {
    return DateTimeFormatter.ISO_INSTANT
        .format(endTime.truncatedTo(ChronoUnit.SECONDS))
        .replaceAll("[TZ]", " ");
  }

  public String getRequestTime() {
    return DateTimeFormatter.ISO_INSTANT
        .format(requestTime.truncatedTo(ChronoUnit.SECONDS))
        .replaceAll("[TZ]", " ");
  }

  public Student getRider() {
    return rider;
  }

  public void setStartTime(Instant startTime) {
    this.startTime = startTime;
  }

  public void setEndTime(Instant endTime) {
    this.endTime = endTime;
  }

  public double getDuration() {
    Duration d = Duration.between(startTime, endTime);
    duration = d.toMinutesPart() + (d.toSecondsPart() / 60.0);
    return duration;
  }

  public void setDriver(Student driver) {
    this.driver = driver;
  }

  public Location getInitialLocation() {
    return initialLocation;
  }

  public Location getFinalDestination() {
    return finalDestination;
  }

  public Ride(Location currentLocation, Location finalDestination) {
    this.initialLocation = currentLocation;
    this.finalDestination = finalDestination;
  }

  double getFinalPrice() // get final price after ride is completed
      {
    return Math.floor(
            0.25 * duration
                + 0.50
                    * 100
                    * haversine(
                        this.initialLocation.getLatitude(),
                        this.initialLocation.getLongitude(),
                        this.finalDestination.getLatitude(),
                        this.finalDestination.getLongitude()))
        / 100;
  }

  double getEstimatePrice() // gets estimate price before ride starts
      {
    return Math.floor(
            125
                * haversine(
                    this.initialLocation.getLatitude(),
                    this.initialLocation.getLongitude(),
                    this.finalDestination.getLatitude(),
                    this.finalDestination.getLongitude()))
        / 100;
  }

  boolean completeRide() throws IOException {
    try {
      getDuration();
      SystemControl.addTransaction(
          rider.getAccount().getaNum(),
          rider.getAccount().getrNum(),
          "withdraw",
          Double.toString(this.getFinalPrice()));
      SystemControl.addTransaction(
          driver.getAccount().getaNum(),
          driver.getAccount().getrNum(),
          "deposit",
          Double.toString(this.getFinalPrice()));

      File file = new File("users/" + this.getRider().getUsername() + ".txt");
      if (!file.exists()) {
        file.createNewFile();
      }
      FileWriter writer = new FileWriter(file, true);

      writer.append(
          "NEWRIDE"
              + "\n"
              + rider.getStudentID()
              + "\t"
              + this.getRequestTime()
              + "\t"
              + Arrays.toString(this.getInitialLocation().getPair())
              + "\t"
              + Arrays.toString(this.getFinalDestination().getPair())
              + "\t"
              + this.getEstimatePrice()
              + "\t\n"
              + this.getDriver().getStudentID()
              + "\t\n"
              + this.getStartTime()
              + "\t"
              + this.getEndTime()
              + "\t"
              + this.getFinalPrice()
              + "\t\n\n");
      writer.close();
      return true;
    } catch (Exception e) {
      Screen.showE("An Error Occured.");
      e.printStackTrace();
      return false;
    }
  }

  Student getDriver() {
    return this.driver;
  }

  // Haversine formula implementation from
  // https://www.geeksforgeeks.org/haversine-formula-to-find-distance-between-two-points-on-a-sphere/
  static double haversine(double lat1, double long1, double lat2, double long2) {
    double dLat = Math.toRadians(lat2 - lat1);
    double dLong = Math.toRadians(long2 - long1);

    // to radians
    lat1 = Math.toRadians(lat1);
    lat2 = Math.toRadians(lat2);

    double earthRadius = 3958.8;
    double a =
        Math.pow(Math.sin(dLat / 2), 2)
            + Math.pow(Math.sin(dLong / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
    double c = 2 * Math.asin(Math.sqrt(a));
    return earthRadius * c;
  }

  public void setRider(Student rider) {
    this.rider = rider;
  }

  public void setRequestTime(Instant requestTime) {
    this.requestTime = requestTime;
  }
}
