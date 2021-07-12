package com.example.project;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import org.junit.jupiter.api.Test;

public class ControlTest {
  private final Control control = new Control();

  @Test
  void createAccountInvalidS0() throws IOException, NoSuchAlgorithmException {
    assertEquals(
        false, control.createAccount("test", "password", "S0123456789", "123456789", "987654321"));
  }
}
