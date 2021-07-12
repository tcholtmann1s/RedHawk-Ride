package com.example.project;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class SystemControl {

  static ArrayList<Student> availableDrivers = new ArrayList<>();

  public static void addDriver(Student student) {
    availableDrivers.add(student);
  }

  public static ArrayList<Student> getAvailableDrivers() {
    return availableDrivers;
  }

  public static Student assignDriver(Ride ride) {

    if (availableDrivers.size() != 0) {
      double minDist =
          Ride.haversine(
              availableDrivers.get(0).getLocation().getLatitude(),
              availableDrivers.get(0).getLocation().getLongitude(),
              ride.getInitialLocation().getLatitude(),
              ride.getInitialLocation().getLongitude());
      Student thisDriver = availableDrivers.get(0);
      for (int i = 0; i < availableDrivers.size(); i++) {
        if (Ride.haversine(
                availableDrivers.get(i).getLocation().getLatitude(),
                availableDrivers.get(i).getLocation().getLongitude(),
                ride.getInitialLocation().getLatitude(),
                ride.getInitialLocation().getLongitude())
            < minDist) {
          minDist =
              Ride.haversine(
                  availableDrivers.get(i).getLocation().getLatitude(),
                  availableDrivers.get(i).getLocation().getLongitude(),
                  ride.getInitialLocation().getLatitude(),
                  ride.getInitialLocation().getLongitude());
          thisDriver = availableDrivers.get(i);
        }
      }
      thisDriver.setRide(ride);
      ride.setDriver(thisDriver);
      thisDriver.setDriving(true);
      return thisDriver;
    } else {
      return null;
    }
  }

  // Called as a cron job on the server
  static void processBankTransaction() throws FileNotFoundException {
    String line;
    String[] transaction;
    File file = new File("transactions.csv");
    Scanner myReader = new Scanner(file);

    while (myReader.hasNextLine()) {
      line = myReader.nextLine();
      transaction = line.split(",");

      if (transaction[2].equals("deposit")) {
        Account.withdraw(
            Integer.parseInt(transaction[0]),
            Integer.parseInt(transaction[1]),
            Integer.parseInt(transaction[3]));
      } else if (transaction[2].equals("withdraw")) {
        Account.deposit(
            Integer.parseInt(transaction[0]),
            Integer.parseInt(transaction[1]),
            Integer.parseInt(transaction[3]));
      }
    }
  }

  public static void removeDriver(Student student) {
    availableDrivers.remove(student);
  }

  static void addTransaction(String aNum, String rNum, String type, String amt)
      throws IOException // adds transaction to list
      {
    BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.csv", true));
    writer.append(aNum + "," + rNum + "," + type + amt + "\n");
    writer.close();
  }
}
