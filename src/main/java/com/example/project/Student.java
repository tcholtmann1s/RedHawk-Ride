package com.example.project;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

public class Student {
  private Location currentLocation;
  private Location finalLocation;
  private Ride ride;
  private Account account;
  private boolean isDriver;
  private boolean isDriving;
  private String username;
  private String password;
  private String studentID;

  public Student(String username, String password) throws NoSuchAlgorithmException {
    this.username = username;
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
    String encPass = Base64.getEncoder().encodeToString(hash);
    this.currentLocation = new Location();
    this.finalLocation = new Location();
    this.account = null;

    this.password = encPass;
  }

  public String getStudentID() {
    return studentID;
  }

  public void setStudentID(String studentID) {
    this.studentID = studentID;
  }

  public void setRide(Ride ride) {
    this.ride = ride;
  }

  public boolean login() {
    try {

      BufferedReader reader = new BufferedReader(new FileReader(new File("Accounts.txt")));
      String line = reader.readLine();
      while (line != null) {
        String[] data = line.split(" ");
        if (data[1].trim().equals(username.trim()) && data[2].trim().equals(password.trim())) {
          account = new Account(data[3], data[4]);
          studentID = data[0];
          return true;
        }

        line = reader.readLine();
      }
    } catch (FileNotFoundException e) {
      Screen.showE("Unexpected Error.  Please try again");
      return false;

    } catch (IOException e) {
      Screen.showE("Unexpected Error.  Please try again");
      e.printStackTrace();
      return false;
    }
    Screen.showE("Error: Invalid Username or Password");
    return false;
  }

  public String getUsername() {
    return username;
  }

  void createRide(Location currentLocation, Location finalDestination) // change return type to Ride
      {
    Ride newRide = new Ride(currentLocation, finalDestination);
    newRide.setRider(this);
    this.ride = newRide;
  }

  void endRide() throws IOException {
    ride.completeRide();
  }

  Ride getRide() {
    return this.ride;
  }

  public void setCurrentLocation(Location currentLocation) {
    this.currentLocation = currentLocation;
  }

  public void setFinalLocation(Location finalLocation) {
    this.finalLocation = finalLocation;
  }

  Location getLocation() {
    return this.currentLocation;
  }

  Location getDestination() {
    return this.finalLocation;
  }

  void setDriverStatus(boolean isDriver) {
    this.isDriver = isDriver;
  }

  boolean getDriverStatus() {
    return this.isDriver;
  }

  static boolean verifyUser(String studentID) {
    try {
      String id;
      File file = new File("validIDs.txt"); // open text file with student info
      Scanner scanner = new Scanner(file);
      while (scanner.hasNextLine()) {
        String read = scanner.nextLine();
        String[] data = read.split(" ");
        if (studentID.trim().equals(data[0].trim())) {
          scanner.close();
          id = data[0].trim();
          File file2 = new File("Accounts.txt"); // open text file with student info
          Scanner scanner2 = new Scanner(file2);
          while (scanner2.hasNextLine()) {
            read = scanner2.nextLine();
            data = read.split(" ");
            if (studentID.trim().equals(data[0].trim())) {
              scanner2.close();
              return false;
            }
          }
          return true;
        } else {
          //          Screen.showE(studentID.trim() + " does not equal " + data[0]);
        }
      }
      scanner.close();
    } catch (FileNotFoundException e) {
      Screen.showE("An error occurred when opening validIDs.txt");
      e.printStackTrace();
      return false;
    }

    return false;
  }

  void cancelRide() // cancels ride if rider doesn't aggre to price of if there is not an available
        // driver
      {
    this.ride = null;
    Screen.show("Ride Cancelled");
  }

  String getRideHistory() throws FileNotFoundException {
    try {
      int i = 1;
      int loop = 0;
      String[] data;
      BufferedReader reader =
          new BufferedReader(new FileReader(new File("users/" + this.getUsername() + ".txt")));
      String line = reader.readLine();
      StringBuilder output = new StringBuilder();
      while (line != null) {
        if (line.equals("NEWRIDE")) {
          output.append("Ride #" + i);
          i++;
          loop = -1;
        }
        switch (loop) {
          case (-1):
            loop++;
            break;
          case (0):
            data = line.split("\t");
            output.append("\nRequest Time: " + data[1]);
            output.append("\nStart Location: " + data[2]);
            output.append("\nEnd Location: " + data[3]);
            output.append("\nEstimated Price: " + data[4]);
            loop++;
            break;
          case (1):
            loop++;
            break;
          case (2):
            data = line.split("\t");
            output.append("\nStart Time: " + data[0]);
            output.append("\nEnd Time: " + data[1]);
            output.append("\nFinal Price: " + data[2]);
            output.append("\n\n");
            loop++;
        }
        line = reader.readLine();
      }
      return output.toString();
    } catch (FileNotFoundException e) {
      return "No rides found";
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public void clearRide() {
    this.ride = null;
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public boolean isDriving() {
    return isDriving;
  }

  public void setDriving(boolean driving) {
    isDriving = driving;
  }
}
