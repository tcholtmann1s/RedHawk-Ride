package com.example.project;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import org.junit.jupiter.api.Test;

public class RideTest {
  private double duration = 0.0;
  Location location = new Location(37.316719, -89.530905);
  private Location initialLocation = new Location(37.316719, -89.530905);
  ;
  private Location finalDestination = new Location(37.296810, -89.522461);
  ;
  private Student driver;
  private Student rider;
  private boolean isComplete = true;

  @Test
  void getEstimatePriceSuccess() throws IOException, NoSuchAlgorithmException {
    assertEquals(1.81, new Ride(initialLocation, finalDestination).getEstimatePrice());
  }

  @Test
  void getFinalPriceSuccess() throws IOException, NoSuchAlgorithmException {
    assertEquals(0.72, new Ride(initialLocation, finalDestination).getFinalPrice());
  }

  @Test
  void completeRideSuccess() throws IOException, NoSuchAlgorithmException, InterruptedException {
    Ride ride = new Ride(initialLocation, finalDestination);
    Student driver = new Student("Driver", "");
    driver.setAccount(new Account("123456789", "123456789"));
    driver.setStudentID("S01935236");
    Student rider = new Student("Rider", "");
    rider.setAccount(new Account("987654321", "987654321"));
    rider.setStudentID("S01935237");
    rider.setRide(ride);
    driver.setRide(ride);
    ride.setRider(rider);
    ride.setDriver(driver);
    ride.setRequestTime(Instant.now());
    ride.setStartTime(Instant.now());
    Thread.sleep(4000);
    ride.setEndTime(Instant.now());
    assertEquals(true, ride.completeRide());
  }
}
