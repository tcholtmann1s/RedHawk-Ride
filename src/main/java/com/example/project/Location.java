package com.example.project;

public class Location {
  private double latitude;
  private double longitude;

  public Location(double[] coordPair) {
    this.latitude = coordPair[0];
    this.longitude = coordPair[1];
  }

  public Location(double latitude, double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public Location() {
    this.latitude = 0;
    this.longitude = 0;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public double[] getPair() {
    double[] pair = new double[2];
    pair[0] = this.latitude;
    pair[1] = this.longitude;
    return pair;
  }

  @Override
  public String toString() {
    return latitude + ", " + longitude;
  }
}
