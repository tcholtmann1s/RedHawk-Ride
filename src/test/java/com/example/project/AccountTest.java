package com.example.project;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import org.junit.jupiter.api.Test;

public class AccountTest {

  @Test
  void getAccountNumberSuccess() throws IOException, NoSuchAlgorithmException {
    assertEquals("123456789", new Account("123456789", "987654321").getaNum());
  }

  @Test
  void getRoutingNumberSuccess() throws IOException, NoSuchAlgorithmException {
    assertEquals("987654321", new Account("123456789", "987654321").getrNum());
  }
}
