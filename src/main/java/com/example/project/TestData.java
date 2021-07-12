package com.example.project;

public class TestData {
  public static String[] names = {
    "Mark", "Jacob", "Allen", "Kallie", "Scott", "Sierra", "Travis", "Michael", "Adam", "Steve",
    "Stan", "Heather", "Betty", "Barry"
  };

  public static Location[] locations = {
    new Location(37.317688, -89.528722),
    new Location(37.316719, -89.530905),
    new Location(37.315265, -89.527756),
    new Location(37.311796, -89.532231),
    new Location(37.296810, -89.522461),
    new Location(37.313191, -89.530959),
    new Location(37.307030, -89.525670)
  };

  public static String getRandomID() {
    return "S0" + Integer.toString((int) (Math.random() * 10000000));
  }
}
