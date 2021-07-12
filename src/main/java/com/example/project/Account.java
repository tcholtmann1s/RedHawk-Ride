package com.example.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Account {

  String aNum;
  String rNum;

  public Account(String aNum, String rNum) {
    this.aNum = aNum;
    this.rNum = rNum;
  }

  public String getaNum() {
    return aNum;
  }

  public String getrNum() {
    return rNum;
  }

  public static boolean withdraw(int rNumber, int aNumber, int amt) throws FileNotFoundException {
    File file = new File("Accounts.txt");
    Scanner myReader = new Scanner(file);
    boolean found = false;
    while (myReader.hasNextLine()) {
      String read = myReader.nextLine();
      String[] data = read.split(" ");
      int rNum = Integer.parseInt(data[3]);
      int aNum = Integer.parseInt(data[4]);
      int bal = Integer.parseInt(data[5]);
      if ((rNum == rNumber) && (aNum == aNumber)) {
        found = true;
        bal = bal - amt;
        data[5] = Integer.toString(bal);
      }
    }
    myReader.close();
    return found;
  }

  public static boolean deposit(int rNumber, int aNumber, int amt) throws FileNotFoundException {
    File file = new File("Accounts.txt");
    Scanner myReader = new Scanner(file);
    boolean found = false;
    while (myReader.hasNextLine()) {
      String read = myReader.nextLine();
      String[] data = read.split(" ");
      int rNum = Integer.parseInt(data[3]);
      int aNum = Integer.parseInt(data[4]);
      int bal = Integer.parseInt(data[5]);
      if ((rNum == rNumber) && (aNum == aNumber)) {
        found = true;
        bal = bal + amt;
        data[5] = Integer.toString(bal);
      }
    }
    myReader.close();
    return found;
  }
}
