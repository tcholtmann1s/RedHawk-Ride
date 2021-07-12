package com.example.project;

import java.util.Scanner;

public class Screen {

  static Scanner reader = new Scanner(System.in);

  public static boolean acceptRide() {
    System.out.print("Do you accept the ride?  If so, enter y: ");
    String val = reader.nextLine();
    if (val.equals("y")) {
      return true;
    } else {
      return false;
    }
  }

  public static boolean waitForInput(String e) {
    return (reader.nextLine().equals(e));
  }

  public static int promptAmount() {
    return (Integer.parseInt(reader.nextLine()));
  }

  public int initialChoice() {
    System.out.println(
        "Welcome to Redhawk Rides!\n\n\nType 1 to create an account\nType 2 to log into an existing account");
    int val = Integer.parseInt(reader.nextLine());
    return val;
  }

  public String promptUsername() {
    System.out.print("Username: ");
    String val = reader.nextLine();
    return val;
  }

  public String promptPassword() {
    System.out.print("Password: ");
    String val = reader.nextLine();
    return val;
  }

  public String promptID() {
    System.out.print("ID: ");
    Scanner reader = (new Scanner(System.in));
    String val = reader.nextLine();
    return val;
  }

  public static void show(String arg) {
    System.out.println(arg);
  }

  public static void showE(String arg) {
    System.err.println(arg);
  }

  public boolean isDriver() {
    System.out.println(
        "Enter a 0 if you would like to be a driver.\nEnter a 1 if you would like to be a rider\n\nYour Choice: ");
    int val = Integer.parseInt(reader.nextLine());
    if (val == 0) {
      return true;
    }
    return false;
  }

  public int riderChoice() {
    System.out.println(
        "Logged In!\n\n\nType 1 to create a ride\nType 2 to view ride history\nType 3 to logout\nType 4 to create a ride with a simulated driver");
    int val = Integer.parseInt(reader.nextLine());
    return val;
  }

  public int driverChoice() {
    System.out.println(
        "Logged in as driver!\n\nType 1 to start driving.\nType 2 to logout\nType 3 to start driving with a test rider.");
    int val = Integer.parseInt(reader.nextLine());
    return val;
  }

  public Location promptLocation() {
    System.out.println(
        "Please input a latitude and longitude, or enter L to see a list of locations");
    String input = reader.nextLine();
    if (input.equals("L")) {
      Location loc = new Location();
      while (true) {
        System.out.println("Enter one of the following choices:\n");
        System.out.println("Enter 1 for Laferla Hall");
        System.out.println("Enter 2 for Dempster Hall");
        System.out.println("Enter 3 for Towers Complex");
        System.out.println("Enter 4 for University Commons");
        System.out.println("Enter 5 for River Campus Performing Arts Center");
        System.out.println("Enter 6 for Academic Hall");
        System.out.println("Enter 7 for Catapult Creative House");
        int thisLoc = Integer.parseInt(reader.nextLine());
        switch (thisLoc) {
          case (1):
            loc.setLatitude(37.317688);
            loc.setLongitude(-89.528722);
            return loc;
          case (2):
            loc.setLatitude(37.316719);
            loc.setLongitude(-89.530905);
            return loc;
          case (3):
            loc.setLatitude(37.315265);
            loc.setLongitude(-89.527756);
            return loc;
          case (4):
            loc.setLatitude(37.311796);
            loc.setLongitude(-89.532231);
            return loc;
          case (5):
            loc.setLatitude(37.296810);
            loc.setLongitude(-89.522461);
            return loc;
          case (6):
            loc.setLatitude(37.313191);
            loc.setLongitude(-89.530959);
            return loc;
          case (7):
            loc.setLatitude(37.307030);
            loc.setLongitude(-89.525670);
            return loc;
          default:
            System.err.println("Please enter a valid choice.");
        }
      }
    } else {
      double latitude = Double.parseDouble(input);
      double longitude = Double.parseDouble(reader.nextLine());
      Location loc = new Location(latitude, longitude);
      return loc;
    }
  }

  public Location promptDestination() {
    System.out.println(
        "Please input a latitude and longitude, or enter L to see a list of locations");
    String input = reader.nextLine();
    if (input.equals("L")) {
      Location loc = new Location();
      while (true) {
        System.out.println("Enter one of the following choices:\n");
        System.out.println("Enter 1 for Laferla Hall");
        System.out.println("Enter 2 for Dempster Hall");
        System.out.println("Enter 3 for Towers Complex");
        System.out.println("Enter 4 for University Commons");
        System.out.println("Enter 5 for River Campus Performing Arts Center");
        System.out.println("Enter 6 for Academic Hall");
        System.out.println("Enter 7 for Catapult Creative House");
        int choiceLoc = Integer.parseInt(reader.nextLine());
        switch (choiceLoc) {
          case (1):
            loc.setLatitude(37.317688);
            loc.setLongitude(-89.528722);
            return loc;
          case (2):
            loc.setLatitude(37.316719);
            loc.setLongitude(-89.530905);
            return loc;
          case (3):
            loc.setLatitude(37.315265);
            loc.setLongitude(-89.527756);
            return loc;
          case (4):
            loc.setLatitude(37.311796);
            loc.setLongitude(-89.532231);
            return loc;
          case (5):
            loc.setLatitude(37.296810);
            loc.setLongitude(-89.522461);
            return loc;
          case (6):
            loc.setLatitude(37.313191);
            loc.setLongitude(-89.530959);
            return loc;
          case (7):
            loc.setLatitude(37.307030);
            loc.setLongitude(-89.525670);
            return loc;
          default:
            System.err.println("Please enter a valid choice.");
        }
      }
    } else {
      double latitude = Double.parseDouble(input);
      double longitude = Double.parseDouble(reader.nextLine());
      Location loc = new Location(latitude, longitude);
      return loc;
    }
  }

  public String promptAccountNumber() {
    System.out.print("Please enter your bank account number: ");
    return reader.nextLine();
  }

  public String promptRoutingNumber() {
    System.out.print("Please enter your bank routing number: ");
    return reader.nextLine();
  }
}
