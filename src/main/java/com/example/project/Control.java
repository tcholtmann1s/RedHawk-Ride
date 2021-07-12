package com.example.project;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.Scanner;

public class Control {

  public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
    Screen screen = new Screen();
    boolean loggedIn = false;
    Scanner reader = new Scanner(System.in);

    int choice;
    Student student = null;

    boolean isDriver;
    /*   Student aDriver = new Student("Joe", "Mama");
        aDriver.setLocation(37.317688, -89.528722);
        SystemControl.addDriver(aDriver);

        Student bDriver = new Student("Joe", "Mama");
        bDriver.setLocation(37.316719, -89.530905);
        SystemControl.addDriver(bDriver);
    */

    while (true) {
      // Not logged in loop
      while (!loggedIn) {
        choice = screen.initialChoice();
        switch (choice) {
          case (1):
            String username = screen.promptUsername();
            String password = screen.promptPassword();
            if (createAccount(
                username,
                password,
                screen.promptID(),
                screen.promptAccountNumber(),
                screen.promptRoutingNumber())) {
              student = new Student(username, password);
              if (student.login()) {
                loggedIn = true;
              }
            }
            break;
          case (2):
            student = new Student(screen.promptUsername(), screen.promptPassword());
            if (student.login()) {
              loggedIn = true;
            }
            break;
          default:
            Screen.show("Please enter a valid choice.");
        }
      }

      // Logged in loop
      if (loggedIn) {
        isDriver = screen.isDriver();
        if (isDriver) {
          student.setDriverStatus(isDriver);
        }
        while (loggedIn) {
          if (student.getDriverStatus()) {
            choice = screen.driverChoice();
            switch (choice) {
              case (3): // Drive with test driver
                SystemControl.addDriver(student);
                Student rider =
                    new Student(
                        TestData.names[((int) (Math.random() * 10)) % TestData.names.length],
                        TestData.names[((int) (Math.random() * 10)) % TestData.names.length]);
                rider.setCurrentLocation(
                    TestData.locations[((int) (Math.random() * 10)) % TestData.locations.length]);
                rider.setFinalLocation(
                    TestData.locations[((int) (Math.random() * 10)) % TestData.locations.length]);
                rider.setAccount(
                    new Account(
                        Integer.toString((int) (Math.random() * 100000)),
                        Integer.toString((int) (Math.random() * 10000000))));
                rider.createRide(rider.getLocation(), rider.getDestination());
                Screen.show("Rider created");
                SystemControl.assignDriver(rider.getRide());
                SystemControl.removeDriver(student);
              case (1): // Start driving
                SystemControl.addDriver(student);

                while (loggedIn)
                  if (student.isDriving()) {
                    Screen.show(
                        "You have been assigned a rider: "
                            + student.getRide().getRider().getUsername());
                    Screen.show(
                        "Type Y when you have picked up the rider at "
                            + student.getRide().getInitialLocation());
                    Screen.waitForInput("Y");
                    student.getRide().setRequestTime(Instant.now());
                    student.getRide().setStartTime(Instant.now());

                    Screen.show(
                        "Type Y when you have dropped off the rider at "
                            + student.getRide().getFinalDestination());
                    Screen.waitForInput("Y");
                    student.getRide().setEndTime(Instant.now());
                    student.endRide();
                    Screen.show("Ride Completed");
                    break;
                  } else {
                    Screen.show(
                        "Type R to check if you have been assigned a rider: \nOr Type X to logout.");
                    if (!Screen.waitForInput("R")) {
                      loggedIn = false;
                    }
                  }

                break;
              case (2): // log out
                SystemControl.removeDriver(student);
                loggedIn = false;
                student = null;
                break;
              default:
                Screen.showE("Please enter a valid choice.");
            }
          } else {
            choice = screen.riderChoice();
            switch (choice) {
              case (4): // Start a new simulated ride.
                Screen.show("How many drivers would you like to simulate?");
                int amt = Screen.promptAmount();
                for (int i = 0; i < amt; i++) {
                  Student aDriver =
                      new Student(
                          TestData.names[((int) (Math.random() * 10)) % TestData.names.length],
                          TestData.names[((int) (Math.random() * 10)) % TestData.names.length]);
                  aDriver.setCurrentLocation(
                      TestData.locations[((int) (Math.random() * 10)) % TestData.locations.length]);
                  aDriver.setAccount(
                      new Account(
                          Integer.toString((int) (Math.random() * 10)),
                          Integer.toString((int) (Math.random() * 10))));
                  aDriver.setStudentID(TestData.getRandomID());
                  SystemControl.addDriver(aDriver);
                }
              case (1): // Start a new ride.
                Location location = screen.promptLocation();
                Location destination = screen.promptDestination();
                if (requestRide(student, location, destination)) {
                  student.getRide().setRequestTime(Instant.now());
                  Screen.show("Currently Ride..");
                  student.getRide().setStartTime(Instant.now());
                  if (choice == 4) {
                    Screen.show(
                        "Press Y when the ride is complete."); // For simulation purposes only.
                    Screen.waitForInput("Y");
                    student.getRide().setEndTime(Instant.now());
                    student.endRide();
                  }
                  Screen.show("Final Price");
                  Screen.show(Double.toString(student.getRide().getFinalPrice()));
                  student.clearRide();
                }
                break;
              case (2):
                Screen.show(student.getRideHistory());
                break;
              case (3): // log out
                loggedIn = false;
                student = null;
                break;
              default:
                Screen.showE("Please enter a valid choice.");
            }
          }
        }
      }
    }
  }

  static boolean createAccount(
      String username, String password, String studentID, String aNumber, String rNumber)
      throws IOException, NoSuchAlgorithmException // Attempts to create a new user account
      {
    if (Student.verifyUser(studentID) == true) {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
      String encPass = Base64.getEncoder().encodeToString(hash);
      ;
      FileWriter writer = new FileWriter("Accounts.txt", true);
      writer.write(
          studentID + " " + username + " " + encPass + " " + aNumber + " " + rNumber + "\n");
      writer.close();
      return true;
    } else {
      Screen.showE("Student not verified.");
      return false;
    }
  }

  static boolean requestRide(Student student, Location location, Location destination) {
    student.createRide(location, destination);
    Screen.show(Double.toString(student.getRide().getEstimatePrice()));
    if (Screen.acceptRide() && SystemControl.getAvailableDrivers().size() > 0) {
      Screen.show("Ride Accepted!");
      Student driver = SystemControl.assignDriver(student.getRide());
      Screen.show(student.getRide().getDriver().getUsername());
      return true;
    } else {
      student.cancelRide();
      return false;
    }
  }
}
